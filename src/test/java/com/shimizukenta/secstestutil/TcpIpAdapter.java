package com.shimizukenta.secstestutil;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.CompletionHandler;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TcpIpAdapter implements Closeable {
	
	private final Inner a;
	private final Inner b;
	
	private boolean opened;
	private boolean closed;
	
	public TcpIpAdapter(SocketAddress a, SocketAddress b) {
		this.a = new Inner(a);
		this.b = new Inner(b);
		this.opened = false;
		this.closed = false;
	}
	
	public SocketAddress socketAddressA() throws IOException {
		return this.a.socketAddress();
	}
	
	public SocketAddress socketAddressB() throws IOException {
		return this.b.socketAddress();
	}
	
	public static interface ThrowableListener {
		public void throwed(Throwable t);
	}
	
	private final Collection<ThrowableListener> throwListeners = new CopyOnWriteArrayList<>();
	
	public boolean addThrowableListener(ThrowableListener l) {
		return throwListeners.add(l);
	}
	
	public boolean removeThrowableListener(ThrowableListener l) {
		return throwListeners.remove(l);
	}
	
	private void putThrowable(Throwable t) {
		throwListeners.forEach(l -> {
			l.throwed(t);
		});
	}
	
	public void open() throws IOException {
		
		synchronized (this) {
			
			if ( this.closed ) {
				throw new IOException("Already closed");
			}
			
			if ( this.opened ) {
				throw new IOException("Already opened");
			}
			
			this.opened = true;
		}
		
		this.a.open(this.b);
		this.b.open(this.a);
	}

	@Override
	public void close() throws IOException {
		
		synchronized ( this ) {
			
			if ( this.closed ) {
				return;
			}
			
			this.closed = true;
		}
		
		IOException ioExcept = null;
		
		try {
			this.a.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		try {
			this.b.close();
		}
		catch ( IOException e ) {
			ioExcept = e;
		}
		
		if ( ioExcept != null ) {
			throw ioExcept;
		}
	}
	
	public static TcpIpAdapter open(SocketAddress a, SocketAddress b) throws IOException {
		
		final TcpIpAdapter inst = new TcpIpAdapter(a, b);
		
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
	
	public static void main(String[] args) {
		
		try (
				TcpIpAdapter adapter = new TcpIpAdapter(
						parseSocketAddress(args[0]),
						parseSocketAddress(args[1]));
				) {
			
			adapter.open();
			
			synchronized ( TcpIpAdapter.class ) {
				TcpIpAdapter.class.wait();
			}
		}
		catch ( InterruptedException ignore ) {
		}
		catch ( Throwable t ) {
			echo(t);
		}
	}
	
	private static SocketAddress parseSocketAddress(String s) {
		String[] ss = s.split(":");
		int port = Integer.parseInt(ss[1]);
		return new InetSocketAddress(ss[0], port);
	}
	
	private class Inner implements Closeable {
		
		private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
			Thread th = new Thread(r);
			th.setDaemon(false);
			return th;
		});
		
		private final Collection<AsynchronousSocketChannel> channels = new CopyOnWriteArrayList<>();
		
		private final SocketAddress addr;
		
		private AsynchronousServerSocketChannel server;
		
		public Inner(SocketAddress socketAddress) {
			this.addr = socketAddress;
			this.server = null;
		}
		
		public SocketAddress socketAddress() throws IOException {
			synchronized ( this ) {
				if ( this.server == null ) {
					throw new IOException("Socket Address not binded");
				} else {
					return this.server.getLocalAddress();
				}
			}
		}
		
		public void open(Inner another) throws IOException {
			
			synchronized ( this ) {
				this.server = AsynchronousServerSocketChannel.open();
				
				this.server.setOption(StandardSocketOptions.SO_REUSEADDR, true);
				this.server.bind(addr);
			}
			
			this.server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
				
				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					
					Inner.this.server.accept(attachment, this);
					
					try {
						
						channels.add(channel);
						
						final Collection<Callable<Void>> tasks = Arrays.asList(
								() -> {
									
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
												
												another.put(bs);
											}
											catch ( InterruptedException e ) {
												f.cancel(true);
												throw e;
											}
										}
									}
									catch ( ExecutionException e ) {
										
										Throwable t = e.getCause();
										
										if ( t instanceof Exception ) {
											throw (Exception)t;
										}
									}
									
									return null;
								});
						
						execServ.invokeAny(tasks);
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( ExecutionException e ) {
						
						Throwable t = e.getCause();
						
						if ( t instanceof RuntimeException ) {
							throw (RuntimeException)t;
						}
						
						if ( ! (t instanceof ClosedChannelException) ) {
							putThrowable(t);
						}
					}
					finally {
						
						channels.remove(channel);
						
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
					}
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					if ( ! (t instanceof ClosedChannelException) ) {
						putThrowable(t);
					}
				}
			});
		}
		
		public void close() throws IOException {
			
			IOException ioExcept = null;
			
			try {
				execServ.shutdownNow();
				if ( ! execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
					ioExcept = new IOException("ExecutorService#shurdown failed");
				}
			}
			catch ( InterruptedException giveup ) {
			}
			
			if ( this.server != null ) {
				try {
					this.server.close();
				}
				catch ( IOException e ) {
					ioExcept = e;
				}
			}
			
			if ( ioExcept != null ) {
				throw ioExcept;
			}
		}
		
		public void put(byte[] bs) throws InterruptedException, ExecutionException {
			
			for ( AsynchronousSocketChannel channel : channels ) {
				
				ByteBuffer buffer = ByteBuffer.allocate(bs.length);
				buffer.put(bs);
				((Buffer)buffer).flip();
				
				while ( buffer.hasRemaining() ) {
					
					final Future<Integer> f = channel.write(buffer);
					
					try {
						int w = f.get().intValue();
						
						if ( w <= 0 ) {
							break;
						}
					}
					catch ( InterruptedException e ) {
						f.cancel(true);
						throw e;
					}
				}
			}
		}
	}
	
	private static final Object staticSyncEcho = new Object();
	
	private static void echo(Throwable t) {
		synchronized ( staticSyncEcho ) {
			try (
					StringWriter sw = new StringWriter();
					) {
				
				try (
						PrintWriter pw = new PrintWriter(sw);
						) {
					
					t.printStackTrace(pw);
					pw.flush();
					
					System.out.println(sw.toString());
				}
			}
			catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}
}
