package com.pi.gcn.base;

import java.nio.ByteBuffer;

public abstract class MC_64_Lit extends MC_64 {
	protected long literal;

	public MC_64_Lit(ByteBuffer b) {
		super(b);
		if (hasLiteral()) {
			literal = b.getLong();
			throw new UnsupportedOperationException("64bit literals not supported");
		}
	}

	public MC_64_Lit(long insn) {
		super(insn);
	}

	public abstract boolean hasLiteral();

	@Override
	public void write(ByteBuffer b) {
		super.write(b);
		if (hasLiteral())
			b.putLong(literal);
	}

	@Override
	public int sizeof() {
		return super.sizeof() + (hasLiteral() ? 8 : 0);
	}
}
