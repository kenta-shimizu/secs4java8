package com.shimizukenta.secs.secs1ontcpip.impl;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
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
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpDetectTerminateException;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpNotConnectedException;

/**
 * This abstract class is implementation of SECS-I (SEMI-E4) on TCP/IP
 * 
 * @author kenta-shimizu
 *
 */
public abstract class AbstractSecs1OnTcpIpCommunicator extends AbstractSecs1Communicator
		implements Secs1OnTcpIpCommunicator,
		Secs1OnTcpIpLogObservableImpl {
	
	private final ListProperty<AsynchronousSocketChannel> channels = ListProperty.newInstance();
	
	private final Secs1OnTcpIpCommunicatorConfig secs1OnTcpIpConfig;
	private final AbstractSecs1OnTcpIpLogObserverFacade secs1OnTcpIpLogObserver;

	public AbstractSecs1OnTcpIpCommunicator(Secs1OnTcpIpCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.secs1OnTcpIpConfig = config;
		this.secs1OnTcpIpLogObserver = new AbstractSecs1OnTcpIpLogObserverFacade(config, this.executorService()) {};
		
		this.channels.computeIsNotEmpty().addChangeListener(this.secsCommunicateStateObserver()::setSecsCommunicateState);
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
			
			SocketAddress socketAddr = secs1OnTcpIpConfig.socketAddress().optional().orElseThrow(UnsetSocketAddressException::new);
			
			this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionTryConnect(socketAddr);
			
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
							
							AbstractSecs1OnTcpIpCommunicator.this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionConnected(pLocal, pRemote);
							
							final Collection<Callable<Void>> tasks = Arrays.asList(
									() -> {
										reading(channel);
										return null;
									});
							
							executeInvokeAny(tasks);
							
						}
						catch (IOException e) {
							AbstractSecs1OnTcpIpCommunicator.this.offerThrowableToLog(e);
						}
						catch ( ExecutionException e ) {
							
							Throwable t = e.getCause();
							
							if ( t instanceof RuntimeException ) {
								throw (RuntimeException)t;
							}
							
							AbstractSecs1OnTcpIpCommunicator.this.offerThrowableToLog(t);
						}
						finally {
							
							removeChannel(channel);
							
							try {
								channel.shutdownOutput();
							}
							catch ( IOException giveup ) {
							}
							
							AbstractSecs1OnTcpIpCommunicator.this.secs1OnTcpIpLogObserver().offerSecs1OnTcpIpChannelConnectionConnectClosed(pLocal, pRemote);
							
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
						AbstractSecs1OnTcpIpCommunicator.this.offerThrowableToLog(t);
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
			AbstractSecs1OnTcpIpCommunicator.this.offerThrowableToLog(e);
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
