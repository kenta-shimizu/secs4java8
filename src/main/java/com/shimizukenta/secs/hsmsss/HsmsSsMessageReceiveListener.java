package com.shimizukenta.secs.hsmsss;

import java.util.EventListener;

public interface HsmsSsMessageReceiveListener extends EventListener {

	public void receive(HsmsSsMessage message);
}
