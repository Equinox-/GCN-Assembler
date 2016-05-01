package com.pi.gcn.data;

public class LiteralConstant implements GenSrc {
	public final int constant;

	public LiteralConstant(int cs) {
		this.constant = cs;
	}

	public LiteralConstant(long cs) {
		this.constant = (int) cs;
	}

	@Override
	public int get(ComputeData data) {
		return constant;
	}
}
