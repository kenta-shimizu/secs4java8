package secs.secs1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import secs.SecsCommunicator;
import secs.SecsMessageAnswerManager;

public abstract class Secs1Communicator extends SecsCommunicator {
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	private final Secs1MessageBlockManager blockManager;
	private final SecsMessageAnswerManager<Secs1Message> answerManager;
	
	private final Secs1CommunicatorConfig secs1Config;
	
	public Secs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
		this.blockManager = new Secs1MessageBlockManager(config);
		this.answerManager = new SecsMessageAnswerManager<>(execServ);
	}
	
	protected Secs1CommunicatorConfig secs1Config() {
		return secs1Config;
	}
	
	protected ExecutorService executorService() {
		return execServ;
	}
	
	abstract protected void sendByte(byte b) throws IOException;
	abstract protected void sendByte(byte[] bs) throws IOException;
	abstract protected Byte pollByte() throws InterruptedException;
	abstract protected Optional<Byte> pollByte(long timeout, TimeUnit unit) throws InterruptedException;
	
	private Optional<Byte> pollByteTx(float v) throws InterruptedException {
		return pollByte((long)(v * 1000.0F), TimeUnit.MILLISECONDS);
	}
	
	protected Optional<Byte> pollByteT1() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t1());
	}
	
	protected Optional<Byte> pollByteT2() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t2());
	}
	
	protected Optional<Byte> pollByteT4() throws InterruptedException {
		return pollByteTx(secs1Config.timeout().t4());
	}
	
	private final BlockingQueue<Secs1Message> recvMsgQueue = new LinkedBlockingQueue<>();
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		blockManager.addSecs1MessageReceiveListener(msg -> {
			try {
				
				//TODO
				
				recvMsgQueue.put(msg);
				
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					putReceiveMessage(recvMsgQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
		
		//TODO
		
	}
	
	@Override
	public void close() throws IOException {
		
		List<IOException> ioExcepts = new ArrayList<>();
		
		try {
			super.close();
		}
		catch ( IOException e ) {
			ioExcepts.add(e);
		}
		
		try {
			execServ.shutdown();
			if ( ! execServ.awaitTermination(10L, TimeUnit.MILLISECONDS) ) {
				execServ.shutdownNow();
				if ( ! execServ.awaitTermination(5L, TimeUnit.SECONDS) ) {
					ioExcepts.add(new IOException("ExecutorService#shutdown failed"));
				}
			}
		}
		catch ( InterruptedException giveup ) {
		}
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
}
