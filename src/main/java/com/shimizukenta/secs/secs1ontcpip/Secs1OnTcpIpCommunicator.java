package com.shimizukenta.secs.secs1ontcpip;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeoutException;

import com.shimizukenta.secs.ReadOnlyTimeProperty;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsSendMessageException;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1DetectTerminateException;
import com.shimizukenta.secs.secs1.Secs1SendMessageException;

/**
 * This class is implementation of SECS-I (SEMI-E4) on TCP/IP<br />
 * To create new instance, {@link #newInstance(Secs1OnTcpIpCommunicatorConfig)}<br />
 * To create new instance and open, {@link #open(Secs1OnTcpIpCommunicatorConfig)}
 * 
 * @author kenta-shimizu
 *
 */
public class Secs1OnTcpIpCommunicator extends Secs1Communicator {
	
	private final Secs1OnTcpIpCommunicatorConfig secs1OnTcpIpConfig;
	private AsynchronousSocketChannel channel;
	
	public Secs1OnTcpIpCommunicator(Secs1OnTcpIpCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.secs1OnTcpIpConfig = config;
		this.channel = null;
	}
	
	/**
	 * Create SECS-I-on-TCP/IP instance.
	 * 
	 * @param config
	 * @return new Secs1OnTcpIp instance
	 */
	public static Secs1OnTcpIpCommunicator newInstance(Secs1OnTcpIpCommunicatorConfig config) {
		return new Secs1OnTcpIpCommunicator(config);
	}
	
	/**
	 * Create SECS-I-on-Tcp/IP instance and {@link #open()}
	 * 
	 * @param config
	 * @return new Secs1OnTcpIp instance
	 * @throws IOException
	 */
	public static Secs1OnTcpIpCommunicator open(Secs1OnTcpIpCommunicatorConfig config) throws IOException {
		
		final Secs1OnTcpIpCommunicator inst = newInstance(config);
		
		try {
			inst.open();
		}
		catch ( IOException e ) {
			
			try {
				inst.close();
			}
			catch ( IOException giveup ) {
			}
			
			throw e;
		}
		
		return inst;
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		executeConnectTask();
	}
	
	@Override
	public void close() throws IOException {
		super.close();
	}
	
	private void executeConnectTask() {
		
		executeLoopTask(() -> {
			
			try (
					AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
					) {
				
				SocketAddress socketAddr = secs1OnTcpIpConfig.socketAddress().getSocketAddress();
				
				String socketAddrString = socketAddr.toString();
				
				notifyLog("Secs1OnTcpIpCommunicator#try-connect", socketAddrString);
				
				ch.connect(socketAddr, null, new CompletionHandler<Void, Void>() {

					@Override
					public void completed(Void none, Void attachment) {
						
						try {
							synchronized ( Secs1OnTcpIpCommunicator.this ) {
								Secs1OnTcpIpCommunicator.this.channel = ch;
							}
							
							notifyCommunicatableStateChange(true);
							
							notifyLog("Secs1OnTcpIpCommunicator#connected", ch);
							
							final ByteBuffer buffer = ByteBuffer.allocate(1024);
							
							final Callable<Void> task = () -> {
								
								try {
									
									for ( ;; ) {
										
										((Buffer)buffer).clear();
										
										Future<Integer> f = ch.read(buffer);
										
										try {
											int r = f.get().intValue();
											
											if ( r < 0 ) {
												break;
											}
											
											((Buffer)buffer).flip();
											
											putByte(buffer);
											
											circuitNotifyAll();
										}
										catch ( InterruptedException e ) {
											f.cancel(true);
											throw e;
										}
									}
								}
								catch ( ExecutionException e ) {
									
									Throwable t = e.getCause();
									
									if ( t instanceof RuntimeException ) {
										throw (RuntimeException)t;
									}
									
									if ( ! (t instanceof AsynchronousCloseException) ) {
										notifyLog(e);
									}
								}
								catch ( InterruptedException ignore ) {
								}
								
								return null;
							};
							
							executeInvokeAny(task);
						}
						catch ( InterruptedException ignore) {
						}
						catch ( ExecutionException e ) {
							
							Throwable t = e.getCause();
							
							if ( t instanceof RuntimeException ) {
								throw (RuntimeException)t;
							}
							
							if ( ! (t instanceof AsynchronousCloseException) ) {
								notifyLog(e);
							}
						}
						finally {
							
							notifyCommunicatableStateChange(false);
							
							synchronized ( Secs1OnTcpIpCommunicator.this ) {
								Secs1OnTcpIpCommunicator.this.channel = null;
							}
							
							try {
								ch.shutdownOutput();
							}
							catch ( IOException giveup ) {
							}
							
							notifyLog("Secs1OnTcpIpCommunicator#closed", socketAddrString);
							
							synchronized ( ch ) {
								ch.notifyAll();
							}
						}
					}
					
					@Override
					public void failed(Throwable t, Void attachment) {
						
						try {
							if ( t instanceof RuntimeException ) {
								throw (RuntimeException)t;
							}
							
							notifyLog(t);
						}
						finally {
							
							synchronized ( ch ) {
								ch.notifyAll();
							}
						}
					}
				});
				
				synchronized ( ch ) {
					ch.wait();
				}
			}
			catch ( IOException e ) {
				notifyLog(e);
			}
			
			secs1OnTcpIpConfig.reconnectSeconds().sleep();
		});
	}
	
	
	private final BlockingQueue<Byte> byteQueue = new LinkedBlockingQueue<>();
	
	protected void putByte(ByteBuffer buffer) throws InterruptedException {
		while ( buffer.hasRemaining() ) {
			byteQueue.put(buffer.get());
		}
	}
	
	@Override
	protected Optional<Byte> pollByte() {
		Byte b = byteQueue.poll();
		return b == null ? Optional.empty() : Optional.of(b);
	}
	
	@Override
	protected Optional<Byte> pollByte(ReadOnlyTimeProperty timeout) throws InterruptedException {
		Byte b = timeout.poll(byteQueue);
		return b == null ? Optional.empty() : Optional.of(b);
	}
	
	@Override
	protected Optional<Byte> pollByte(byte[] request) throws InterruptedException {
		
		try {
			Byte b = executeInvokeAny(createPollByteTask(request));
			if ( b != null ) {
				return Optional.of(b);
			}
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			if ( t instanceof Error ) {
				throw (Error)t;
			}
			
			notifyLog(t);
		}
		
		return Optional.empty();
	}
	
	@Override
	protected Optional<Byte> pollByte(byte[] request, ReadOnlyTimeProperty timeout) throws InterruptedException {
		
		try {
			Byte b = executeInvokeAny(createPollByteTask(request), timeout);
			if ( b != null ) {
				return Optional.of(b);
			}
		}
		catch ( TimeoutException timeup ) {
		}
		catch ( ExecutionException e ) {
			
			Throwable t = e.getCause();
			
			if ( t instanceof RuntimeException ) {
				throw (RuntimeException)t;
			}
			
			if ( t instanceof Error ) {
				throw (Error)t;
			}
			
			notifyLog(t);
		}
		
		return Optional.empty();
	}
	
	private Callable<Byte> createPollByteTask(byte[] request) {
		
		return new Callable<Byte>() {
			
			@Override
			public Byte call() {
				try {
					
					for ( ;; ) {
						
						byte b = byteQueue.take();
						
						for ( byte r : request ) {
							if ( r == b ) {
								return Byte.valueOf(b);
							}
						}
					}
				}
				catch ( InterruptedException ignore ) {
				}
				
				return null;
			}
		};
	}
	
	@Override
	protected void pollByteUntilEmpty() {
		byteQueue.clear();
	}
	
	@Override
	protected void sendByte(byte[] bs)
			throws SecsSendMessageException, SecsException, InterruptedException {
		
		final AsynchronousSocketChannel ch;
		
		synchronized ( this ) {
			ch = channel;
		}
		
		if ( ch == null ) {
			throw new Secs1OnTcpIpNotConnectedException();
		}
		
		ByteBuffer buffer = ByteBuffer.allocate(bs.length);
		buffer.put(bs);
		((Buffer)buffer).flip();
		
		while ( buffer.hasRemaining() ) {
			
			Future<Integer> f = ch.write(buffer);
			
			try {
				int w = f.get().intValue();
				
				if ( w <= 0 ) {
					throw new Secs1DetectTerminateException();
				}
			}
			catch ( ExecutionException e ) {
				
				Throwable t = e.getCause();
				
				if ( t instanceof RuntimeException ) {
					throw (RuntimeException)t;
				}
				
				if ( t instanceof Error ) {
					throw (Error)t;
				}
				
				throw new Secs1SendMessageException(t);
			}
			catch ( InterruptedException e ) {
				f.cancel(true);
				throw e;
			}
		}
	}

}
