package com.pi.gcn.data;

public class InlineFloat implements GenSrc {
	public static final InlineFloat IF_P5 = new InlineFloat(0.5f);
	public static final InlineFloat IF_NP5 = new InlineFloat(-0.5f);
	public static final InlineFloat IF_1 = new InlineFloat(1f);
	public static final InlineFloat IF_N1 = new InlineFloat(-1f);
	public static final InlineFloat IF_2 = new InlineFloat(2f);
	public static final InlineFloat IF_N2 = new InlineFloat(-2f);
	public static final InlineFloat IF_4 = new InlineFloat(4f);
	public static final InlineFloat IF_N4 = new InlineFloat(-4f);
	public static final InlineFloat IF_INVERSE_2PI = new InlineFloat((float) (1 / (2 * Math.PI)));
	public final float constant;

	private InlineFloat(float cs) {
		this.constant = cs;
	}

	@Override
	public int get(ComputeData data) {
		return Float.floatToIntBits(constant);
	}
}