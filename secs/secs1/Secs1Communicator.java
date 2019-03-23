package secs.secs1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import secs.SecsCommunicator;

public abstract class Secs1Communicator extends SecsCommunicator {
	
	private final Secs1CommunicatorConfig secs1Config;
	
	public Secs1Communicator(Secs1CommunicatorConfig config) {
		super(config);
		
		this.secs1Config = config;
	}
	
	protected Secs1CommunicatorConfig secs1Config() {
		return secs1Config;
	}
	
	@Override
	public void open() throws IOException {
		
		super.open();
		
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
		
		
		//TODO
		
		
		if ( ! ioExcepts.isEmpty() ) {
			throw ioExcepts.get(0);
		}
	}
}
