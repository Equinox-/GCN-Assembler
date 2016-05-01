package com.pi.gcn.base;

import java.nio.ByteBuffer;

public abstract class MC_64 extends MC {
	public MC_64(ByteBuffer b) {
		this(b.getLong());
	}

	public MC_64(long insn) {
		super(insn);
	}

	@Override
	public void write(ByteBuffer b) {
		b.putLong(insn);
	}
	
	@Override
	public int sizeof() {
		return 8;
	}
}
