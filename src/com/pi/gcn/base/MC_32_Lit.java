package com.pi.gcn.base;

import java.nio.ByteBuffer;

public abstract class MC_32_Lit extends MC_32 {
	protected int literal;

	public MC_32_Lit(ByteBuffer b) {
		super(b);
		if (hasLiteral())
			literal = b.getInt();
	}

	public MC_32_Lit(int insn) {
		super(insn);
	}

	public abstract boolean hasLiteral();

	@Override
	public void write(ByteBuffer b) {
		super.write(b);
		if (hasLiteral())
			b.putInt(literal);
	}

	@Override
	public int sizeof() {
		return super.sizeof() + (hasLiteral() ? 4 : 0);
	}
}
