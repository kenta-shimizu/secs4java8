package com.shimizukenta.secs.hsmsgs.impl;

import com.shimizukenta.secs.hsms.impl.AbstractHsmsMessage;
import com.shimizukenta.secs.hsms.impl.HsmsMessageBuilder;

public interface HsmsGsMessageBuilder extends HsmsMessageBuilder {

	public AbstractHsmsMessage buildLinktestRequest();
}
