package com.shimizukenta.secs.hsms.impl;

import com.shimizukenta.secs.hsms.HsmsException;
import com.shimizukenta.secs.hsms.HsmsSendMessageException;
import com.shimizukenta.secs.hsms.HsmsWaitReplyMessageException;

public interface HsmsLinktest {
	
	public void testing() throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException;
	public void resetTimer();
}
