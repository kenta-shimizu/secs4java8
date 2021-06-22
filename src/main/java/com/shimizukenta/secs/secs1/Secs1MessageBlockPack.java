package com.shimizukenta.secs.secs1;

import java.util.ArrayList;
import java.util.List;

public final class Secs1MessageBlockPack {
	
	private final Secs1Message msg;
	private final List<Secs1MessageBlock> blocks;
	private int present;
	
	private Secs1MessageBlockPack(Secs1Message msg, List<Secs1MessageBlock> blocks) {
		this.msg = msg;
		this.blocks = new ArrayList<>(blocks);
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
	
	public static Secs1MessageBlockPack get(Secs1Message msg) throws Secs1SendMessageException {
		return new Secs1MessageBlockPack(msg, msg.toBlocks());
	}
}
