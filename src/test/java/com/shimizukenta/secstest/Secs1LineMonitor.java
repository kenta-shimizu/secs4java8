package com.shimizukenta.secstest;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.shimizukenta.secs.AbstractSecsLog;
import com.shimizukenta.secs.SecsCommunicator;
import com.shimizukenta.secs.SecsException;
import com.shimizukenta.secs.SecsLog;
import com.shimizukenta.secs.gem.COMMACK;
import com.shimizukenta.secs.gem.ONLACK;
import com.shimizukenta.secs.secs1.Secs1Communicator;
import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpCommunicatorConfig;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicator;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpReceiverCommunicatorConfig;

public class Secs1LineMonitor implements Closeable {
	
	private static String fromHostToEquip = "H -> E";
	private static String fromEquipToHost = "E -> H";
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	private final Secs1Communicator toEquip;
	private final Secs1Communicator toHost;
	private final SocketAddress serverAddr;
	
	public Secs1LineMonitor(
			Secs1OnTcpIpCommunicatorConfig toEquip,
			Secs1OnTcpIpCommunicatorConfig toHost,
			SocketAddress serverAddr) {
		
		toEquip.isCheckMessageBlockDeviceId(false);
		this.toEquip = Secs1OnTcpIpCommunicator.newInstance(toEquip);
		
		toHost.isCheckMessageBlockDeviceId(false);
		this.toHost = Secs1OnTcpIpCommunicator.newInstance(toHost);
		
		this.serverAddr = serverAddr;
	}
	
	private static final String BRBR = System.lineSeparator() + System.lineSeparator();
	private final Collection<AsynchronousSocketChannel> channels = new CopyOnWriteArrayList<>();
	private final BlockingQueue<SecsLog> logQueue = new LinkedBlockingQueue<>();
	
	public void open() throws IOException {
		
		this.toEquip.addSecsMessageReceiveListener(msg -> {
			
			if ( msg instanceof Secs1Message ) {
				
				try {
					this.logQueue.put(buildLog(fromEquipToHost, msg));
					
					this.toHost.send((Secs1Message)msg).ifPresent(r -> {
						
						try {
							this.logQueue.put(buildLog(fromHostToEquip, r));
							
							this.toEquip.send(r);
						}
						catch ( SecsException e ) {
							echo(e);
						}
						catch ( InterruptedException ignore ) {
						}
					});
				}
				catch ( SecsException e ) {
					echo(e);
				}
				catch ( InterruptedException ignore ) {
				}
			}
		});
		
		this.toHost.addSecsMessageReceiveListener(msg -> {
			
			if ( msg instanceof Secs1Message ) {
				
				try {
					this.logQueue.put(buildLog(fromHostToEquip, msg));
					
					this.toEquip.send((Secs1Message)msg).ifPresent(r -> {
						
						try {
							this.logQueue.put(buildLog(fromEquipToHost, r));
							
							this.toHost.send(r);
						}
						catch ( SecsException e ) {
							echo(e);
						}
						catch ( InterruptedException ignore ) {
						}
					});
				}
				catch ( SecsException e ) {
					echo(e);
				}
				catch ( InterruptedException ignore ) {
				}
			}
		});
		
		this.toEquip.open();
		this.toHost.open();
		
		this.execServ.execute(() -> {
			try {
				for ( ;; ) {
					this.openServer();
					TimeUnit.SECONDS.sleep(5L);
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		this.execServ.execute(() -> {
			try {
				for ( ;; ) {
					SecsLog log = logQueue.take();
					echo(log);
					
					byte[] bs = (log.toString() + BRBR).getBytes(StandardCharsets.UTF_8);
					for ( AsynchronousSocketChannel ch : channels ) {
						this.writing(ch, bs);
					}
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
	}
	
	@Override
	public void close() throws IOException {
		
		IOException ioExcept = null;
		
		try {
			this.toEquip.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		
		try {
			this.toHost.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			this.execServ.shutdown();
			if ( ! this.execServ.awaitTermination(1L, TimeUnit.MILLISECONDS) ) {
				this.execServ.shutdownNow();
				if ( ! this.execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
					ioExcept = new IOException("ExecutorService#shutdown failed");
				}
			}
		}
		catch ( InterruptedException giveup ) {
		}
		
		if ( ioExcept != null ) 
			throw ioExcept;
	}
	
	private void openServer() throws InterruptedException {
		
		try (
				AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
				) {
			
			server.bind(this.serverAddr);
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel ch, Void attachment) {
					
					try {
						channels.add(ch);
						
						server.accept(attachment, this);
						
						final Collection<Callable<Void>> tasks = Arrays.asList(
								() -> {
									try {
										reading(ch);
									}
									catch ( InterruptedException ignore ) {
									}
									
									return null;
									}
								);
						
						execServ.invokeAny(tasks);
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( ExecutionException e ) {
						Throwable t = e.getCause();
						
						if ( t instanceof RuntimeException ) {
							throw (RuntimeException)t;
						} else {
							echo(t);
						}
					}
					finally {
						channels.remove(ch);
						
						try {
							ch.shutdownOutput();
						}
						catch ( IOException giveup ) {
						}
						
						try {
							ch.close();
						}
						catch ( IOException giveup ) {
						}
					}
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					
					synchronized ( server ) {
						server.notifyAll();
					}
					
					if ( t instanceof RuntimeException ) {
						throw (RuntimeException)t;
					} else {
						echo(t);
					}
				}
			});
			
			synchronized ( server ) {
				server.wait();
			}
		}
		catch ( IOException e ) {
			echo(e);
		}
	}
	
	private void reading(AsynchronousSocketChannel channel) throws InterruptedException {
		
		final ByteBuffer bf = ByteBuffer.allocate(256);
		
		try {
			for ( ;; ) {
				((Buffer)bf).clear();
				
				final Future<Integer> f = channel.read(bf);
				
				try {
					int r = f.get().intValue();
					
					if ( r < 0 ) {
						return;
					}
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
			} else {
				echo(t);
			}
		}
	}
	
	private void writing(AsynchronousSocketChannel channel, byte[] bs) throws InterruptedException {
		
		final ByteBuffer bf = ByteBuffer.allocate(bs.length);
		bf.put(bs);
		((Buffer)bf).flip();
		
		try {
			
			while ( bf.hasRemaining() ) {
				
				final Future<Integer> f = channel.write(bf);
				
				try {
					int w = f.get().intValue();
					
					if ( w <= 0 ) {
						return;
					}
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
			} else {
				echo(t);
			}
		}
	}
	
	private static SecsLog buildLog(CharSequence title, Object value) {
		return new AbstractSecsLog(title, value) {
			
			private static final long serialVersionUID = -5060438522039943110L;
		};
	}
	
	
	private static SocketAddress addrMonitor = new InetSocketAddress("127.0.0.1", 23000);
	private static SocketAddress addrToEquip = new InetSocketAddress("127.0.0.1", 23001);
	private static SocketAddress addrToHost = new InetSocketAddress("127.0.0.1", 23002);
	
	public static void main(String[] args) {
		
		try {
			
			final Secs1OnTcpIpCommunicatorConfig toEquip = new Secs1OnTcpIpCommunicatorConfig();
			toEquip.isEquip(false);
			toEquip.isMaster(false);
			toEquip.socketAddress(addrToEquip);
			
			final Secs1OnTcpIpCommunicatorConfig toHost = new Secs1OnTcpIpCommunicatorConfig();
			toHost.isEquip(true);
			toHost.isMaster(true);
			toHost.socketAddress(addrToHost);
			
			try (
					Secs1LineMonitor monitor = new Secs1LineMonitor(toEquip, toHost, addrMonitor);
					) {
				
				monitor.open();
				
				final Secs1OnTcpIpReceiverCommunicatorConfig equipConf = new Secs1OnTcpIpReceiverCommunicatorConfig();
				equipConf.deviceId(1000);
				equipConf.isEquip(true);
				equipConf.isMaster(true);
				equipConf.socketAddress(addrToEquip);
				equipConf.gem().mdln("MDLN-A");
				equipConf.gem().softrev("000001");
				equipConf.logSubjectHeader("Equip: ");
				
				final Secs1OnTcpIpReceiverCommunicatorConfig hostConf = new Secs1OnTcpIpReceiverCommunicatorConfig();
				hostConf.deviceId(1000);
				hostConf.isEquip(false);
				hostConf.isMaster(false);
				hostConf.socketAddress(addrToHost);
				hostConf.logSubjectHeader("Host: ");
				
				try (
						SecsCommunicator equip = Secs1OnTcpIpReceiverCommunicator.newInstance(equipConf);
						) {
					
					equip.addSecsMessageReceiveListener((msg, comm) -> {
						
						int strm = msg.getStream();
						int func = msg.getFunction();
						
						try {
							switch ( strm ) {
							case 1: {
								switch ( func ) {
								case 17: {
									if ( msg.wbit() ) {
										comm.gem().s1f18(msg, ONLACK.OK);
									}
									break;
								}
								}
								break;
							}
							default: {
								/* Nothing */
							}
							}
						}
						catch ( InterruptedException ignore ) {
						}
						catch ( SecsException e ) {
							echo(e);
						}
					});
					
//					equip.addSecsLogListener(log -> {echo(log);});
					
					equip.open();
					
					try (
							SecsCommunicator host = Secs1OnTcpIpReceiverCommunicator.newInstance(hostConf);
							) {
						
						host.addSecsMessageReceiveListener((msg, comm) -> {
							
							int strm = msg.getStream();
							int func = msg.getFunction();
							
							try {
								switch ( strm ) {
								case 1: {
									switch ( func ) {
									case 13: {
										comm.gem().s1f14(msg, COMMACK.OK);
										break;
									}
									}
									break;
								}
								default: {
									/* Nothing */
								}
								}
							}
							catch ( InterruptedException ignore ) {
							}
							catch ( SecsException e ) {
								echo(e);
							}
						});
						
//						host.addSecsLogListener(log -> {echo(log);});
						
						host.open();
						
						equip.waitUntilCommunicatable();
						host.waitUntilCommunicatable();
						
						host.gem().s1f17();
						TimeUnit.MILLISECONDS.sleep(100L);
						equip.gem().s1f13();
						
					}
				}
				
//				synchronized ( Secs1LineMonitor.class ) {
//					Secs1LineMonitor.class.wait();
//				}
				
			}
			catch ( InterruptedException ignore ) {
			}
		}
		catch ( Throwable t ) {
			echo(t);
		}
	}
	
	private static Object syncStaticEcho = new Object();
	
	private static void echo(Object o) {
		
		synchronized ( syncStaticEcho ) {
			
			if ( o instanceof Throwable) {
				
				try (
						StringWriter sw = new StringWriter();
						) {
					
					try (
							PrintWriter pw = new PrintWriter(sw);
							) {
						
						((Throwable) o).printStackTrace(pw);
						pw.flush();
						
						System.out.println(sw.toString());
					}
				}
				catch ( IOException e ) {
					e.printStackTrace();
				}
				
			} else {
				
				System.out.println(o);
			}
			
			System.out.println();
		}
	}
	
}
