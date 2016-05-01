package com.pi.gcn.base;

import java.nio.ByteBuffer;

public abstract class MC_32 extends MC {
	public MC_32(ByteBuffer b) {
		this(b.getInt());
	}

	public MC_32(int insn) {
		super(insn);
	}

	@Override
	public void write(ByteBuffer b) {
		b.putInt((int) insn);
	}

	@Override
	public int sizeof() {
		return 4;
	}
}
