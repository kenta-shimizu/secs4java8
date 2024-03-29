package com.shimizukenta.secs.hsmsss.impl;

import com.shimizukenta.secs.hsms.HsmsSession;
import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;

public interface HsmsSsMessageBuilder extends HsmsMessageBuilder {

	public AbstractHsmsMessage buildLinktestRequest(HsmsSession session);
}
