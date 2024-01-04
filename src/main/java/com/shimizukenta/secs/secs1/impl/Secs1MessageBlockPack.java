package com.shimizukenta.secs.secs1.impl;

import java.util.ArrayList;
import java.util.List;

import com.shimizukenta.secs.secs1.Secs1Message;
import com.shimizukenta.secs.secs1.Secs1MessageBlock;

public final class Secs1MessageBlockPack {
	
	private final Secs1Message msg;
	private final List<Secs1MessageBlock> blocks;
	private int present;
	
	public Secs1MessageBlockPack(Secs1Message msg) {
		this.msg = msg;
		this.blocks = new ArrayList<>(msg.toBlocks());
		this.present = 0;
	}
	
	public Secs1Message message() {
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
