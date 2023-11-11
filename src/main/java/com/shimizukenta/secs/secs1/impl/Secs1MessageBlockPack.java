package com.shimizukenta.secs.secs1.impl;

import java.util.ArrayList;
import java.util.List;

import com.shimizukenta.secs.secs1.Secs1MessageBlock;

public final class Secs1MessageBlockPack {
	
	private final AbstractSecs1Message msg;
	private final List<Secs1MessageBlock> blocks;
	private int present;
	
	public Secs1MessageBlockPack(AbstractSecs1Message msg) {
		this.msg = msg;
		this.blocks = new ArrayList<>(msg.toBlocks());
		this.present = 0;
	}
	
	public AbstractSecs1Message message() {
		return msg;
	}
	
	public Secs1MessageBlock present() {
		return this.blocks.get(present);
	}
	
	public void reset() {
		this.present = 0;
	}
	
	public void next() {
		++ this.present;
	}
	
	public boolean ebit() {
		return this.blocks.get(this.present).ebit();
	}
	
}
