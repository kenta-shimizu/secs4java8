package secs.hsmsSs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HsmsSsPassiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsPassiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
	}
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.PASSIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not PASSIVE");
		}
		
		executorService().execute(() -> {
			try {
				for ( ;; ) {
					loop();
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
		
		
		//TODO
		
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
	
	private void loop() throws InterruptedException {
		
		//TODO
	}

}
