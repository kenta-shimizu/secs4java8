package secs.secs1OnTcpIp;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import secs.SecsLog;
import secs.secs1.Secs1Communicator;

public class Secs1OnTcpIpCommunicator extends Secs1Communicator {
	
	private final Secs1OnTcpIpCommunicatorConfig secs1OnTcpIpConfig;
	private final Collection<AsynchronousSocketChannel> channels = new ArrayList<>();
	
	public Secs1OnTcpIpCommunicator(Secs1OnTcpIpCommunicatorConfig config) {
		super(Objects.requireNonNull(config));
		
		this.secs1OnTcpIpConfig = config;
	}

	@Override
	protected void sendByte(byte[] bs) throws IOException, InterruptedException {
		
		synchronized ( channels ) {
			
			if ( channels.isEmpty() ) {
				throw new IOException("Channel not opened");
			}
			
			for ( AsynchronousSocketChannel ch : channels ) {
				
				ByteBuffer buffer = ByteBuffer.allocate(bs.length);
				buffer.put(bs);
				((Buffer)buffer).flip();
				
				while ( buffer.hasRemaining() ) {
					
					Future<Integer> f = ch.write(buffer);
					
					try {
						Integer w = f.get();
						
						if ( w <= 0 ) {
							throw new IOException("Secs1OnTcpIpCommunicator#sendByte-AsynchronousSocketChannel#write terminate");
						}
					}
					catch ( InterruptedException e ) {
						f.cancel(true);
						throw e;
					}
					catch (ExecutionException e) {
						throw new IOException("Secs1OnTcpIpCommunicator#sendByte-AsynchronousSocketChannel#write", e.getCause());
					}
				}
			}
			
		}
	}
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		executorService().execute(() -> {
			
			try {
				
				for ( ;; ) {
					
					try (
							AsynchronousSocketChannel ch = AsynchronousSocketChannel.open();
							) {
						
						final SocketAddress socketAddr = secs1OnTcpIpConfig.socketAddress();
						
						final String socketAddressInfo = socketAddr.toString();
						
						entryLog(new SecsLog("Secs1OnTcpIpCommunicator try-connect", socketAddressInfo));
						
						try {
							
							ch.connect(socketAddr, null, new CompletionHandler<Void, Void>(){
								
								@Override
								public void completed(Void none, Void attachemnt) {
									
									synchronized ( channels ) {
										channels.add(ch);
									}
									
									notifyCommunicatableStateChange(true);
									
									entryLog(new SecsLog("Secs1OnTcpIpCommunicator connected", socketAddressInfo));
									
									final ByteBuffer buffer = ByteBuffer.allocate(1024);
									
									ch.read(buffer, null, new CompletionHandler<Integer, Void>(){
	
										@Override
										public void completed(Integer r, Void attachment) {
											
											if ( r < 0 ) {
												
												synchronized ( channels ) {
													channels.notifyAll();
												}
												
											} else {
												
												((Buffer)buffer).flip();
												byte[] bs = new byte[buffer.remaining()];
												buffer.get(bs);
												
												putRecvByte(bs);
												
												((Buffer)buffer).clear();
												ch.read(buffer, null, this);
											}
										}
	
										@Override
										public void failed(Throwable t, Void attachment) {
											
											synchronized ( channels ) {
												channels.notifyAll();
											}
											
											entryLog(new SecsLog("Secs1OnTcpIpCommunicator#open-AsynchronousSocketChannel#read failed", t));
										}
										
									});
								}
								
								@Override
								public void failed(Throwable t, Void attachment) {
									
									synchronized ( channels ) {
										channels.notifyAll();
									}
									
									entryLog(new SecsLog("Secs1OnTcpIpCommunicator#open-AsynchronousSocketChannel#connect failed", t));
								}
							});
							
							synchronized ( channels ) {
								channels.wait();
							}
						}
						finally {
							
							notifyCommunicatableStateChange(false);
							
							synchronized ( channels ) {
								
								if ( channels.remove(ch) ) {
									
									entryLog(new SecsLog("Secs1OnTcpIpCommunicator disconnected", socketAddressInfo));
									
									try {
										ch.shutdownOutput();
									}
									catch (ClosedChannelException ignore) {
									}
									catch (IOException e) {
										
										entryLog(new SecsLog("Secs1OnTcpIpCommunicator#open-AsynchronousSocketChannel#shutdownOutput failed", e));
									}
								}
							}
						}
					}
					catch ( IOException e ) {
						
						entryLog(new SecsLog("Secs1OnTcpIpCommunicator#open-AsynchronousSocketChannel#open failed", e));
					}

					{
						long t = (long)(secs1OnTcpIpConfig.reconnectSeconds() * 1000.0F);
						if ( t > 0 ) {
							TimeUnit.MILLISECONDS.sleep(t);
						}
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			
			if ( closed ) {
				return;
			}
			
			super.close();
		}
		
	}
	
	public static Secs1OnTcpIpCommunicator newInstance(Secs1OnTcpIpCommunicatorConfig config) {
		return new Secs1OnTcpIpCommunicator(config);
	}
	
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
	
}
