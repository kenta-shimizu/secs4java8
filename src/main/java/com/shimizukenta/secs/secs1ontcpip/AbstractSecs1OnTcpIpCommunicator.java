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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.secs1.AbstractSecs1Communicator;
import com.shimizukenta.secs.secs1.Secs1DetectTerminateException;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;

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
		
		this.executeLoopTask(() -> {
			connectTask();
		});
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	private void connectTask() throws InterruptedException {
		
		try (
				AsynchronousSocketChannel channel = AsynchronousSocketChannel.open();
				) {
			
			SocketAddress socketAddr = secs1OnTcpIpConfig.socketAddress().getSocketAddress();
			
			notifyLog(Secs1OnTcpIpConnectionLog.tryConnect(socketAddr));
			
			channel.connect(socketAddr, null, new CompletionHandler<Void, Void>() {

				@Override
				public void completed(Void none, Void attachment) {
					
					SocketAddress local = null;
					SocketAddress remote = null;
					
					try {
						
						local = channel.getLocalAddress();
						remote = channel.getRemoteAddress();
						
						synchronized ( AbstractSecs1OnTcpIpCommunicator.this.channels ) {
							AbstractSecs1OnTcpIpCommunicator.this.channels.add(channel);
						}
						
						notifyCommunicatableStateChange(true);
						
						notifyLog(Secs1OnTcpIpConnectionLog.connected(local, remote));
						
						final ByteBuffer buffer = ByteBuffer.allocate(1024);
						
						final Callable<Void> readingTask = () -> {
							
							try {
								
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
							
							return null;
						};
						
						executeInvokeAny(Arrays.asList(readingTask));
						
					}
					catch ( IOException e ) {
						notifyLog(e);
					}
					catch ( InterruptedException ignore) {
					}
					catch ( ExecutionException e ) {
						
						Throwable t = e.getCause();
						
						if ( t instanceof RuntimeException ) {
							throw (RuntimeException)t;
						}
						
						notifyLog(t);
					}
					finally {
						
						synchronized ( AbstractSecs1OnTcpIpCommunicator.this.channels ) {
							AbstractSecs1OnTcpIpCommunicator.this.channels.remove(channel);
						}
						
						notifyCommunicatableStateChange(false);
						
						try {
							channel.shutdownOutput();
						}
						catch ( IOException giveup ) {
						}
						
						notifyLog(Secs1OnTcpIpConnectionLog.disconnected(local, remote));
						
						synchronized ( channel ) {
							channel.notifyAll();
						}
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if ( ! (t instanceof ClosedChannelException) ) {
						notifyLog(t);
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
		
		secs1OnTcpIpConfig.reconnectSeconds().sleep();
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
	protected void sendBytes(byte[] bs)
			throws SecsSendMessageException, SecsException, InterruptedException {
		
		final AsynchronousSocketChannel channel = getChannel();
		
		final ByteBuffer buffer = ByteBuffer.allocate(bs.length);
		buffer.put(bs);
		((Buffer)buffer).flip();
		
		while ( buffer.hasRemaining() ) {
			
			final Future<Integer> f = channel.write(buffer);
			
			try {
				int w = f.get().intValue();
				
				if ( w <= 0 ) {
					throw new Secs1DetectTerminateException();
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
				
				throw new Secs1SendMessageException(t);
			}
		}
	}

}
