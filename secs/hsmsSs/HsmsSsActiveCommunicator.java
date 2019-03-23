package secs.hsmsSs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HsmsSsActiveCommunicator extends HsmsSsCommunicator {

	public HsmsSsActiveCommunicator(HsmsSsCommunicatorConfig config) {
		super(config);
	}
	
	@Override
	public void open() throws IOException {
		super.open();
		
		if ( hsmsSsConfig().protocol() != HsmsSsProtocol.ACTIVE ) {
			throw new IOException("HsmsSsCommunicatorConfig#protocol is not ACTIVE");
		}
		
		//TODO
		
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

}
