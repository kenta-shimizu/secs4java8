package com.shimizukenta.testutil.tcpipadapter;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class TcpIpAdapter implements Closeable {
	
	private final Inner a;
	private final Inner b;
	
	public TcpIpAdapter(SocketAddress a, SocketAddress b) {
		this.a = new Inner(a);
		this.b = new Inner(b);
	}
	
	public void open() throws IOException {
		this.a.open(this.b);
		this.b.open(this.a);
	}

	@Override
	public void close() throws IOException {
		
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
			t.printStackTrace();
		}
	}
	
	private static SocketAddress parseSocketAddress(String s) {
		String[] ss = s.split(":");
		int port = Integer.parseInt(ss[1]);
		return new InetSocketAddress(ss[0], port);
	}
	
	private class Inner implements Closeable {
		
		private final SocketAddress addr;
		private AsynchronousServerSocketChannel server;
		private Collection<AsynchronousSocketChannel> channels = new CopyOnWriteArrayList<>();
		
		public Inner(SocketAddress socketAddress) {
			this.addr = socketAddress;
			this.server = null;
		}
		
		public void open(Inner another) throws IOException {
			
			server = AsynchronousServerSocketChannel.open();
			server.bind(addr);
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {

				@Override
				public void completed(AsynchronousSocketChannel channel, Void attachment) {
					
					server.accept(null, this);
					
					try {
						channels.add(channel);
						
						ByteBuffer buffer = ByteBuffer.allocate(1024);
						
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
								
								another.put(bs);
							}
							catch ( InterruptedException e ) {
								f.cancel(true);
								throw e;
							}
						}
					}
					catch ( InterruptedException ignore ) {
					}
					catch ( ExecutionException e ) {
						e.printStackTrace();
					}
					finally {
						
						channels.remove(channel);
						
						closeChannel(channel);
					}
				}

				@Override
				public void failed(Throwable t, Void attachment) {
					t.printStackTrace();
				}
			});
		}
		
		public void close() throws IOException {
			
			channels.forEach(this::closeChannel);
			
			if ( server != null ) {
				server.close();
			}
			
		}
		
		private void closeChannel(AsynchronousSocketChannel channel) {
			
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
		
		public void put(byte[] bs) throws InterruptedException {
			
			for ( AsynchronousSocketChannel channel : channels ) {
				
				ByteBuffer buffer = ByteBuffer.allocate(bs.length);
				buffer.put(bs);
				((Buffer)buffer).flip();
				
				try {
					while ( buffer.hasRemaining() ) {
						
						Future<Integer> f = channel.write(buffer);
						
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
				catch ( ExecutionException e ) {
					e.printStackTrace();
				}
			}
		}
	}

}
