package com.shimizukenta.secs.secs1ontcpip.impl;

import com.shimizukenta.secs.secs1.impl.AbstractSecs1LogObserverFacade;
import com.shimizukenta.secs.secs1.impl.Secs1LogObservableImpl;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpLogObservable;

public interface Secs1OnTcpIpLogObservableImpl extends Secs1OnTcpIpLogObservable, Secs1LogObservableImpl {
	
	@Override
	default public AbstractSecs1LogObserverFacade secs1LogObserver() {
		return this.secs1OnTcpIpLogObserver();
	}

	public AbstractSecs1OnTcpIpLogObserverFacade secs1OnTcpIpLogObserver();

}
