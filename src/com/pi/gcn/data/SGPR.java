package com.pi.gcn.data;

public class SGPR implements GenSrc, GenDest {
	public final byte sgpr;

	public SGPR(int b) {
		this.sgpr = (byte) b;
	}

	@Override
	public int get(ComputeData data) {
		return data.sgpr[sgpr & 0xFF];
	}

	@Override
	public void set(ComputeData data, int v) {
		data.sgpr[sgpr & 0xFF] = v;
	}
}
