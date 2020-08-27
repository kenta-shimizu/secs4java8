package com.shimizukenta.secs.hsmsss;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import com.shimizukenta.secs.AbstractSecsInnerManager;
import com.shimizukenta.secs.secs2.Secs2;
import com.shimizukenta.secs.secs2.Secs2BytesParser;

public class HsmsSsByteReader extends AbstractSecsInnerManager implements Callable<Object> {
	
	private final HsmsSsCommunicator parent;
	private final AsynchronousSocketChannel channel;

	public HsmsSsByteReader(HsmsSsCommunicator parent, AsynchronousSocketChannel channel) {
		super(parent);
		this.parent = parent;
		this.channel = channel;
	}
	
	
	private static final byte[] emptyBytes = new byte[]{0, 0, 0, 0};
	
	/**
	 * until detect-terminate or Timeout-T8
	 */
	@Override
	public Object call() throws Exception {
		
		try {
			
			final ByteBuffer lenBf = ByteBuffer.allocate(8);
			final ByteBuffer headBf = ByteBuffer.allocate(10);
			
			for ( ;; ) {
				
				((Buffer)lenBf).clear();
				((Buffer)headBf).clear();
				
				lenBf.put(emptyBytes);
				
				readToByteBuffer(lenBf, false);
				
				while ( lenBf.hasRemaining() ) {
					readToByteBuffer(lenBf, true);
				}
				
				while ( headBf.hasRemaining() ) {
					readToByteBuffer(headBf, true);
				}
				
				((Buffer)lenBf).flip();
				
				long bodyLength = lenBf.getLong() - 10;
				
				if ( bodyLength < 0 ) {
					continue;
				}
				
				BodyReader bodyReader = new BodyReader(this, bodyLength);
				
				while ( ! bodyReader.completed() ) {
					bodyReader.reading();
				}
				
				((Buffer)headBf).flip();
				byte[] head = new byte[10];
				headBf.get(head);
				
				Secs2 body = Secs2BytesParser.getInstance().parse(bodyReader.getByteBuffers());
				HsmsSsMessage msg = parent.createHsmsSsMessage(head, body);
				
				listeners.forEach(lstnr -> {
					lstnr.receive(msg);
				});
				
				notifyReceiveMessagePassThrough(msg);
				notifyLog("Received HsmsSs-Message", msg);
			}
		}
		catch ( HsmsSsDetectTerminateException | HsmsSsTimeoutT8Exception e ) {
			notifyLog(e);
		}
		catch ( InterruptedException ignore ) {
		}
		
		return null;
	}
	
	
	private int readToByteBuffer(ByteBuffer buffer, boolean detectT8Timeout)
			throws HsmsSsDetectTerminateException, HsmsSsTimeoutT8Exception, InterruptedException {
		
		Future<Integer> f = channel.read(buffer);
		
		try {
			int r;
			
			if ( detectT8Timeout ) {
				
				long t8 = (long)(parent.hsmsSsConfig().timeout().t8() * 1000.0F);
				r = f.get(t8, TimeUnit.MILLISECONDS);
				
			} else {
				
				r = f.get();
			}
			
			if ( r < 0 ) {
				throw new HsmsSsDetectTerminateException();
			}
			
			return r;
		}
		catch ( TimeoutException e ) {
			f.cancel(true);
			throw new HsmsSsTimeoutT8Exception(e);
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			if ( t instanceof Error ) {
				throw (Error)t;
			}
			
			throw new HsmsSsDetectTerminateException(e);
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	private static final int bufferSize = 1024;
	
	private class BodyReader {
		
		private final HsmsSsByteReader parent;
		private final long size;
		private long present;
		private final LinkedList<ByteBuffer> buffers = new LinkedList<>();
		
		public BodyReader(HsmsSsByteReader parent, long size) {
			this.parent = parent;
			this.size = size;
			this.present = 0;
			addBuffer();
		}
		
		private void addBuffer() {
			long i = size - present;
			if ( i > bufferSize ) {
				buffers.add(ByteBuffer.allocate(bufferSize));
			} else {
				buffers.add(ByteBuffer.allocate((int)i));
			}
		}
		
		public void reading()
				throws HsmsSsDetectTerminateException, HsmsSsTimeoutT8Exception
				, InterruptedException {
			
			ByteBuffer bf = buffers.getLast();
			
			int r = parent.readToByteBuffer(bf, true);
			
			this.present += r;
			
			if ( ! bf.hasRemaining() ) {
				
				if ( present < size ) {
					addBuffer();
				}
			}
		}
		
		public boolean completed() {
			return present == size;
		}
		
		public List<ByteBuffer> getByteBuffers() {
			return buffers.stream()
					.map(bf -> {
						((Buffer)bf).flip();
						return bf;
					})
					.collect(Collectors.toList());
		}
	}
	
	private final Collection<HsmsSsMessageReceiveListener> listeners = new CopyOnWriteArrayList<>();
	
	public boolean addHsmsSsMessageReceiveListener(HsmsSsMessageReceiveListener lstnr) {
		return listeners.add(lstnr);
	}
	
}
