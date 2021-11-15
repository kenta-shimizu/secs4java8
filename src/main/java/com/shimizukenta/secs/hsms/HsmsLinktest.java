package com.shimizukenta.secs.hsms;

public interface HsmsLinktest {
	
	public void testing() throws HsmsSendMessageException, HsmsWaitReplyMessageException, HsmsException, InterruptedException;
	public void resetTimer();
}
