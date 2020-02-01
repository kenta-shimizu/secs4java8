package com.shimizukenta.secs.secs1;

import java.util.EventListener;

public interface Secs1MessageReceiveListener extends EventListener {
	public void receive(Secs1Message msg);
}
