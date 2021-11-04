package com.shimizukenta.secs.secs1;

import java.util.ArrayList;
import java.util.List;

public final class Secs1MessageBlockPack {
	
	private final AbstractSecs1Message msg;
	private final List<AbstractSecs1MessageBlock> absBlocks;
	private int present;
	
	public Secs1MessageBlockPack(AbstractSecs1Message msg) {
		this.msg = msg;
		this.absBlocks = new ArrayList<>(msg.toAbstractBlocks());
		this.present = 0;
	}
	
	public AbstractSecs1Message message() {
		return msg;
	}
	
	public AbstractSecs1MessageBlock present() {
		return this.absBlocks.get(present);
	}
	
	public void reset() {
		this.present = 0;
	}
	
	public void next() {
		++ this.present;
	}
	
	public boolean ebit() {
		return this.absBlocks.get(this.present).ebit();
	}
	
}
