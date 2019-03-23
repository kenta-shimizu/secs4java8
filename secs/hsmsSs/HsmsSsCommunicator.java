package secs.hsmsSs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import secs.SecsCommunicator;

public abstract class HsmsSsCommunicator extends SecsCommunicator {
	
	private final ExecutorService execServ = Executors.newCachedThreadPool(r -> {
		Thread th = new Thread(r);
		th.setDaemon(true);
		return th;
	});
	
	private final HsmsSsCommunicatorConfig hsmsSsConfig;
	private final HsmsSsMessageAnswerManager answerManager;

	public HsmsSsCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
		
		this.hsmsSsConfig = config;
		this.answerManager = new HsmsSsMessageAnswerManager(execServ);
	}
	
	protected ExecutorService executorServicde() {
		return execServ;
	}
	
	protected HsmsSsCommunicatorConfig hsmsSsConfig() {
		return hsmsSsConfig;
	}
	
	protected HsmsSsMessageAnswerManager answerManager() {
		return answerManager;
	}
	
	private final BlockingQueue<HsmsSsMessage> recvDataMsgQueue = new LinkedBlockingQueue<>();
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		execServ.execute(() -> {
			try {
				for ( ;; ) {
					putReceiveMessage(recvDataMsgQueue.take());
				}
			}
			catch ( InterruptedException ignore ) {
			}
		});
		
	}
	
	@Override
	public void close() throws IOException {
		
		final List<IOException> ioExcepts = new ArrayList<>();
		
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
	
	public static HsmsSsCommunicator newInstance(HsmsSsCommunicatorConfig config) {
		
		switch ( config.protocol() ) {
		case PASSIVE: {
			return new HsmsSsPassiveCommunicator(config);
			/* break; */
		}
		case ACTIVE: {
			return new HsmsSsActiveCommunicator(config);
			/* break; */
		}
		default: {
			throw new IllegalStateException("undefined protocol: " + config.protocol());
		}
		}
	}
	
	public static HsmsSsCommunicator open(HsmsSsCommunicatorConfig config) throws IOException {
		
		final HsmsSsCommunicator inst = newInstance(config);
		
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
