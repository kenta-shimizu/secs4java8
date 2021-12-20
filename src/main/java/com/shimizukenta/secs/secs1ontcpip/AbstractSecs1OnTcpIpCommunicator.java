package com.shimizukenta.secs.secs1ontcpip;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.shimizukenta.secs.secs1.AbstractSecs1Communicator;
import com.shimizukenta.secs.secs1.Secs1SendByteException;

/**
 * This abstract class is implementation of SECS-I (SEMI-E4) on TCP/IP
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1OnTcpIpCommunicator extends AbstractSecs1Communicator
		implements Secs1OnTcpIpCommunicator {
	
	private final Secs1OnTcpIpCommunicatorConfig secs1OnTcpIpConfig;
	private final List<AsynchronousSocketChannel> channels = new ArrayList<>();
	
	public AbstractSecs1OnTcpIpCommunicator(Secs1OnTcpIpCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.secs1OnTcpIpConfig = config;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.executorService().execute(() -> {
			try {
				while ( ! this.isClosed() ) {
					this.connect();
					if ( this.isClosed() ) {
						return;
					}
					secs1OnTcpIpConfig.reconnectSeconds().sleep();
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	private void connect() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			SocketAddress socketAddr = secs1OnTcpIpConfig.socketAddress().getSocketAddress();
			
			notifyLog(Secs1OnTcpIpConnectionLog.tryConnect(socketAddr));
			
			channel.connect(socketAddr, null, new CompletionHandler<Void, Void>() {

				@Override
				public void completed(Void none, Void attachment) {
					
					SocketAddress pLocal = null;
					SocketAddress pRemote = null;
					
					try {
						try {
							
							pLocal = channel.getLocalAddress();
							pRemote = channel.getRemoteAddress();
							
							addChannel(channel);
							
							notifyLog(Secs1OnTcpIpConnectionLog.connected(pLocal, pRemote));
							
							final Collection<Callable<Void>> tasks = Arrays.asList(
									() -> {
										reading(channel);
										return null;
									});
							
							executeInvokeAny(tasks);
							
						}
						catch ( IOException e ) {
							notifyLog(e);
						}
						catch ( ExecutionException e ) {
							
							Throwable t = e.getCause();
							
							if ( t instanceof RuntimeException ) {
								throw (RuntimeException)t;
							}
							
							notifyLog(t);
						}
						finally {
							
							removeChannel(channel);
							
							try {
								channel.shutdownOutput();
							}
							catch ( IOException giveup ) {
							}
							
							try {
								notifyLog(Secs1OnTcpIpConnectionLog.disconnected(pLocal, pRemote));
							}
							catch ( InterruptedException ignore ) {
							}
							
							synchronized ( channel ) {
								channel.notifyAll();
							}
						}
					}
					catch ( InterruptedException ignore ) {
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if ( ! (t instanceof ClosedChannelException) ) {
						try {
							notifyLog(t);
						}
						catch ( InterruptedException ignore ) {
						}
					}
					
					synchronized ( channel ) {
						channel.notifyAll();
					}
				}
			});
			
			synchronized ( channel ) {
				channel.wait();
			}
		}
		catch ( IOException e ) {
			notifyLog(e);
		}
	}
	
	private void reading(AsynchronousSocketChannel channel) throws Exception {
		
		try {
			final ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			for ( ;; ) {
				
				((Buffer)buffer).clear();
				
				Future<Integer> f = channel.read(buffer);
				
				try {
					int r = f.get().intValue();
					
					if ( r < 0 ) {
						break;
					}
					
					((Buffer)buffer).flip();
					
					byte[] bs = new byte[buffer.remaining()];
					
					buffer.get(bs);
					
					putBytes(bs);
				}
				catch ( InterruptedException e ) {
					f.cancel(true);
					throw e;
				}
			}
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( ! (t instanceof ClosedChannelException) ) {
				if ( t instanceof Exception ) {
					throw (Exception)t;
				}
			}
		}
		catch ( InterruptedException ignore ) {
		}
	}
	
	private boolean addChannel(AsynchronousSocketChannel channel) {
		synchronized ( this.channels ) {
			boolean f = this.channels.add(channel);
			this.notifyCommunicatableStateChange( ! this.channels.isEmpty() );
			return f;
		}
	}
	
	private boolean removeChannel(AsynchronousSocketChannel channel) {
		synchronized ( this.channels ) {
			boolean f = this.channels.remove(channel);
			this.notifyCommunicatableStateChange( ! this.channels.isEmpty() );
			return f;
		}
	}
	
	private AsynchronousSocketChannel getChannel() throws Secs1OnTcpIpNotConnectedException {
		synchronized ( this.channels ) {
			if ( this.channels.isEmpty() ) {
				throw new Secs1OnTcpIpNotConnectedException();
			}
			return channels.get(0);
		}
	}
	
	@Override
	public void sendBytes(byte[] bs)
			throws Secs1SendByteException, InterruptedException {
		
		final AsynchronousSocketChannel channel = getChannel();
		
		final ByteBuffer buffer = ByteBuffer.allocate(bs.length);
		buffer.put(bs);
		((Buffer)buffer).flip();
		
		while ( buffer.hasRemaining() ) {
			
			final Future<Integer> f = channel.write(buffer);
			
			try {
				int w = f.get().intValue();
				
				if ( w <= 0 ) {
					throw new Secs1OnTcpIpDetectTerminateException();
				}
			}
			catch ( InterruptedException e ) {
				f.cancel(true);
				throw e;
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				throw new Secs1SendByteException(t);
			}
		}
	}

}
