package secs;

import java.util.EventListener;

public interface SecsCommunicatableStateChangeListener extends EventListener {
	public void changed(boolean communicatable);
}
