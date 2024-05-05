package com.shimizukenta.secs.secs1ontcpip.impl;

import com.shimizukenta.secs.SecsLogListener;
import com.shimizukenta.secs.secs1.impl.AbstractSecs1LogObserverFacade;
import com.shimizukenta.secs.secs1.impl.Secs1LogObservableImpl;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpChannelConnectionLog;
import com.shimizukenta.secs.secs1ontcpip.Secs1OnTcpIpLogObservable;

public interface Secs1OnTcpIpLogObservableImpl extends Secs1OnTcpIpLogObservable, Secs1LogObservableImpl {
	
	@Override
	default public boolean addSecs1OnTcpIpChannelConnectionLogListener(SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog> listener) {
		return this.secs1OnTcpIpLogObserver().addSecs1OnTcpIpChannelConnectionLogListener(listener);
	}
	
	@Override
	default public boolean removeSecs1OnTcpIpChannelConnectionLogListener(SecsLogListener<? super Secs1OnTcpIpChannelConnectionLog> listener) {
		return this.secs1OnTcpIpLogObserver().removeSecs1OnTcpIpChannelConnectionLogListener(listener);
	}
	
	@Override
	default public AbstractSecs1LogObserverFacade secs1LogObserver() {
		return this.secs1OnTcpIpLogObserver();
	}

	public AbstractSecs1OnTcpIpLogObserverFacade secs1OnTcpIpLogObserver();

}
