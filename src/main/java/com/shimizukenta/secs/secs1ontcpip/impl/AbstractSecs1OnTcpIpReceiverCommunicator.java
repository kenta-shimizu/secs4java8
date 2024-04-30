package com.shimizukenta.secs.secs1ontcpip.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.shimizukenta.secs.UnsetSocketAddressException;
import com.shimizukenta.secs.local.property.ListProperty;
import com.shimizukenta.secs.secs1.Secs1SendByteException;
import com.shimizukenta.secs.secs1.impl.AbstractSecs1Communicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpDetectTerminateException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpNotConnectedException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicatorConfig;

public abstract class AbstractSecs1OnTcpIpReceiverCommunicator extends AbstractSecs1Communicator
		implements Secs1OnTcpIpReceiverCommunicator, Secs1OnTcpIpLogObservableImpl {
	
	private final ListProperty<AsynchronousSocketChannel> channels = ListProperty.newInstance();
	
	private final Secs1OnTcpIpReceiverCommunicatorConfig config;
	private final AbstractSecs1OnTcpIpLogObserverFacade secs1OnTcpIpLogObserver;
	
	public AbstractSecs1OnTcpIpReceiverCommunicator(Secs1OnTcpIpReceiverCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		this.config = config;
		this.secs1OnTcpIpLogObserver = new AbstractSecs1OnTcpIpLogObserverFacade(config, this.executorService()) {};
		
		channels.computeIsNotEmpty().addChangeListener(this::notifyCommunicatableStateChange);
	}
	
	@Override
	public AbstractSecs1OnTcpIpLogObserverFacade secs1OnTcpIpLogObserver() {
		return this.secs1OnTcpIpLogObserver;
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
			
			SocketAddress gLocal = config.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
			
			this.secs1OnTcpIpLogObserver().offerHsmsChannelConnectionTryBind(gLocal);
			
			server.bind(gLocal);
			
			gLocal = server.getLocalAddress();
			
			this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionBinded(gLocal);
			
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
							
							AbstractSecs1OnTcpIpReceiverCommunicator.this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionAccepted(pLocal, pRemote);
							
							final Collection<Callable<Void>> tasks = Arrays.asList(
									() -> {
										reading(channel);
										return null;
									});
							
							executeInvokeAny(tasks);
							
						}
						catch (IOException e) {
							AbstractSecs1OnTcpIpReceiverCommunicator.this.offerThrowableToLog(e);
						}
						catch ( ExecutionException e ) {
							
							Throwable t = e.getCause();
							
							if ( t instanceof RuntimeException ) {
								throw (RuntimeException)t;
							}
							
							AbstractSecs1OnTcpIpReceiverCommunicator.this.offerThrowableToLog(t);
						}
						finally {
							
							removeChannel(channel);
							
							try {
								channel.shutdownOutput();
							}
							catch (IOException giveup) {
							}
							
							try {
								channel.close();
							}
							catch (IOException giveup){
							}
							
							AbstractSecs1OnTcpIpReceiverCommunicator.this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionAcceptClosed(pLocal, pRemote);
						}
					}
					catch (InterruptedException ignore) {
					}
				}
				
				@Override
				public void failed(Throwable t, Void attachment) {
					
					if ( ! (t instanceof ClosedChannelException) ) {
						AbstractSecs1OnTcpIpReceiverCommunicator.this.offerThrowableToLog(t);
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
					AbstractSecs1OnTcpIpReceiverCommunicator.this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionBindClosed(gLocal);
				}
			}
		}
		catch (IOException e) {
			AbstractSecs1OnTcpIpReceiverCommunicator.this.offerThrowableToLog(e);
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
		catch (ExecutionException e) {
			
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
			return this.channels.add(channel);
		}
	}
	
	private boolean removeChannel(AsynchronousSocketChannel channel) {
		synchronized ( this.channels ) {
			return this.channels.remove(channel);
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
