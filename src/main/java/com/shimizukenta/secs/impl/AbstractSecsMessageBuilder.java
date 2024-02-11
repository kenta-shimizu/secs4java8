package com.shimizukenta.secs.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.shimizukenta.secs.SecsCommunicatorConfigValueGettable;
import com.shimizukenta.secs.SecsMessage;
import com.shimizukenta.secs.secs2.Secs2;

public abstract class AbstractSecsMessageBuilder<M extends SecsMessage, C extends SecsCommunicatorConfigValueGettable> implements SecsMessageBuilder<M, C> {
	
	private final AtomicInteger autoNum;
	
	public AbstractSecsMessageBuilder() {
		this.autoNum = new AtomicInteger(0);
	}
	
	protected boolean isEquip(C communicator) {
		return communicator.isEquip();
	}
	
	abstract protected byte[] device2Bytes(C communicator);
	
	protected byte[] system4Bytes(C communicator) {
		
		final int num = autoNum.incrementAndGet();
		
		if (isEquip(communicator)) {
			
			byte[] dev2bs = this.device2Bytes(communicator);
			
			return new byte[] {
				dev2bs[0],
				dev2bs[1],
				(byte)(num >> 8),
				(byte)num
			};
			
		} else {
			
			return new byte[] {
					(byte)0x00,
					(byte)0x00,
					(byte)(num >> 8),
					(byte)num
				};
		}
	}
	
	@Override
	public M buildDataMessage(C communicator, int strm, int func, boolean wbit) {
		return this.buildDataMessage(communicator, strm, func, wbit, Secs2.empty());
	}
	
	@Override
	public M buildDataMessage(C communicator, SecsMessage primaryMsg, int strm, int func, boolean wbit) {
		return this.buildDataMessage(communicator, primaryMsg, strm, func, wbit, Secs2.empty());
	}
	
}
