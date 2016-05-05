package com.pi.gcn.data;

public class InlineInt implements GenSrc {
	public static final InlineInt ZERO = new InlineInt(0);
	public static final InlineInt[] POSITIVE = new InlineInt[64];
	public static final InlineInt[] NEGATIVE = new InlineInt[15];

	static {
		for (int i = 1; i <= POSITIVE.length; i++)
			POSITIVE[i - 1] = new InlineInt(i);
		for (int i = 1; i <= NEGATIVE.length; i++)
			NEGATIVE[i - 1] = new InlineInt(-i);
	}

	public final int constant;

	private InlineInt(int cs) {
		this.constant = cs;
	}

	@Override
	public int get(ComputeData data) {
		return constant;
	}
}
