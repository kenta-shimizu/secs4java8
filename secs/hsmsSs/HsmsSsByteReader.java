package secs.hsmsSs;

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

import secs.secs2.Secs2;

public class HsmsSsByteReader implements Callable<Object> {
	
	private final AsynchronousSocketChannel channel;
	private final HsmsSsCommunicatorConfig config;

	public HsmsSsByteReader(HsmsSsCommunicatorConfig config, AsynchronousSocketChannel channel) {
		this.config = config;
		this.channel = channel;
	}
	
	/**
	 * until detect-terminate or Timeout-T8
	 */
	@Override
	public Object call() throws Exception {
		
		final Object rtn = new Object();
		
		try {
			
			final ByteBuffer lenBf = ByteBuffer.allocate(4);
			final ByteBuffer headBf = ByteBuffer.allocate(10);
			
			for ( ;; ) {
				
				((Buffer)lenBf).clear();
				((Buffer)headBf).clear();
				
				if ( reading(lenBf, false) ) {
					return rtn;
				}
				
				while ( lenBf.hasRemaining() ) {
					if ( reading(lenBf, true) ) {
						return rtn;
					}
				}
				
				while ( headBf.hasRemaining() ) {
					if ( reading(headBf, true) ) {
						return rtn;
					}
				}
				
				((Buffer)lenBf).flip();
				
				int bodyLength = lenBf.getInt() - 10;
				
				if ( bodyLength < 0 ) {
					continue;
				}
				
				ByteBuffer bodyBf = ByteBuffer.allocate(bodyLength);
				
				while ( bodyBf.hasRemaining() ) {
					if ( reading(bodyBf, true) ) {
						return rtn;
					}
				}
				
				((Buffer)headBf).flip();
				byte[] headBytes = new byte[headBf.remaining()];
				headBf.get(headBytes);
				
				((Buffer)bodyBf).flip();
				byte[] bodyBytes = new byte[bodyBf.remaining()];
				bodyBf.get(bodyBytes);
				
				final HsmsSsMessage msg = new HsmsSsMessage(headBytes, Secs2.parse(bodyBytes));
				
				listeners.forEach(lstnr -> {
					lstnr.receive(msg);
				});
			}
		}
		catch ( TimeoutException e ) {
			throw new HsmsSsTimeoutT8Exception(e);
		}
		catch ( InterruptedException ignore ) {
		}
		
		return rtn;
	}
	
	/**
	 * 
	 * @param buffer
	 * @param detectT8Timeout
	 * @return true if detect-terminate
	 * @throws ExecutionException
	 * @throws TimeoutException
	 * @throws InterruptedException
	 */
	private boolean reading(ByteBuffer buffer, boolean detectT8Timeout)
			throws ExecutionException, TimeoutException, InterruptedException {
		
		Future<Integer> f = channel.read(buffer);
		
		try {
			int r;
			
			if ( detectT8Timeout ) {
				
				long t8 = (long)(config.timeout().t8() * 1000.0F);
				r = f.get(t8, TimeUnit.MILLISECONDS);
				
			} else {
				
				r = f.get();
			}
			
			if ( r < 0 ) {
				/* detect-terminate */
				return true;
			}
			
			return false;
		}
		catch ( InterruptedException e ) {
			f.cancel(true);
			throw e;
		}
	}
	
	private final Collection<HsmsSsMessageReceiveListener> listeners = new CopyOnWriteArrayList<>();
	
	protected boolean addHsmsSsMessageReceiveListener(HsmsSsMessageReceiveListener lstnr) {
		return listeners.add(lstnr);
	}

}
