package com.shimizukenta.secs.hsmsss;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.secs2.AbstractSecs2;

public class HsmsSsByteReader implements Callable<Object> {
	
	private final HsmsSsCommunicator parent;
	private final AsynchronousSocketChannel channel;

	public HsmsSsByteReader(HsmsSsCommunicator parent, AsynchronousSocketChannel channel) {
		this.parent = parent;
		this.channel = channel;
	}
	
	/**
	 * until detect-terminate or Timeout-T8
	 */
	@Override
	public Object call() throws Exception {
		
		try {
			
			final ByteBuffer lenBf = ByteBuffer.allocate(4);
			final ByteBuffer headBf = ByteBuffer.allocate(10);
			
			for ( ;; ) {
				
				((Buffer)lenBf).clear();
				((Buffer)headBf).clear();
				
				reading(lenBf, false);
				
				while ( lenBf.hasRemaining() ) {
					reading(lenBf, true);
				}
				
				while ( headBf.hasRemaining() ) {
					reading(headBf, true);
				}
				
				((Buffer)lenBf).flip();
				
				int bodyLength = lenBf.getInt() - 10;
				
				if ( bodyLength < 0 ) {
					continue;
				}
				
				ByteBuffer bodyBf = ByteBuffer.allocate(bodyLength);
				
				while ( bodyBf.hasRemaining() ) {
					reading(bodyBf, true);
				}
				
				((Buffer)headBf).flip();
				byte[] headBytes = new byte[headBf.remaining()];
				headBf.get(headBytes);
				
				((Buffer)bodyBf).flip();
				byte[] bodyBytes = new byte[bodyBf.remaining()];
				bodyBf.get(bodyBytes);
				
				final HsmsSsMessage msg = new HsmsSsMessage(headBytes, AbstractSecs2.parse(bodyBytes));
				
				listeners.forEach(lstnr -> {
					lstnr.receive(msg);
				});
			}
		}
		catch ( HsmsSsDetectTerminateException | HsmsSsTimeoutT8Exception e ) {
			parent.entryLog(new SecsLog(e));
		}
		catch ( InterruptedException ignore ) {
		}
		
		return null;
	}
	
	
	private void reading(ByteBuffer buffer, boolean detectT8Timeout)
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
			
		}
		catch ( TimeoutException e ) {
			f.cancel(true);
			throw new HsmsSsTimeoutT8Exception(e);
		}
		catch ( ExecutionException e ) {
			throw new HsmsSsDetectTerminateException(e.getCause());
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	private final Collection<HsmsSsMessageReceiveListener> listeners = new CopyOnWriteArrayList<>();
	
	public boolean addHsmsSsMessageReceiveListener(HsmsSsMessageReceiveListener lstnr) {
		return listeners.add(lstnr);
	}

}
