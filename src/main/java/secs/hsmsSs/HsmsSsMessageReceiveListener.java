package secs.hsmsSs;

import java.util.EventListener;

public interface HsmsSsMessageReceiveListener extends EventListener {

	public void receive(HsmsSsMessage message);
}
