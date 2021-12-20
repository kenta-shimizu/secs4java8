package com.shimizukenta.secs.secs1ontcpip;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
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

public abstract class AbstractSecs1OnTcpIpReceiverCommunicator extends AbstractSecs1Communicator
		implements Secs1OnTcpIpReceiverCommunicator {
	
	private final Secs1OnTcpIpReceiverCommunicatorConfig config;
	private final List<AsynchronousSocketChannel> channels = new ArrayList<>();
	
	public AbstractSecs1OnTcpIpReceiverCommunicator(Secs1OnTcpIpReceiverCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		this.config = config;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		this.executorService().execute(() -> {
			try {
				while ( ! this.isClosed() ) {
					this.bind();
					if ( this.isClosed() ) {
						return;
					}
					config.rebindSeconds().sleep();
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
	
	private void bind() throws InterruptedException {
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			SocketAddress gLocal = config.socketAddress().getSocketAddress();
			
			notifyLog(Secs1OnTcpIpReceiverConnectionLog.tryBInd(gLocal));
			
			server.bind(gLocal);
			
			gLocal = server.getLocalAddress();
			
			notifyLog(Secs1OnTcpIpReceiverConnectionLog.binded(gLocal));
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					
					server.accept(attachment, this);
					
					SocketAddress pLocal = null;
					SocketAddress pRemote = null;
					
					try {
						
						try {
							pLocal = channel.getLocalAddress();
							pRemote = channel.getRemoteAddress();
	
							addChannel(channel);
							
							notifyLog(Secs1OnTcpIpReceiverConnectionLog.accepted(pLocal, pRemote));
							
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
								channel.close();
							}
							catch ( IOException giveup ) {
							}
							
							try {
								notifyLog(Secs1OnTcpIpReceiverConnectionLog.channelClosed(pLocal, pRemote));
							}
							catch ( InterruptedException ignore ) {
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
					
					synchronized ( server ) {
						server.notifyAll();
					}
				}
			});
			
			synchronized ( server ) {
				
				try {
					server.wait();
				}
				finally {
					notifyLog(Secs1OnTcpIpReceiverConnectionLog.serverClosed(gLocal));
				}
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
				
				final Future<Integer> f = channel.read(buffer);
				
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
