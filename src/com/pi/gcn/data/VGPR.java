package com.pi.gcn.data;

public class VGPR implements GenSrc, GenDest {
	public final byte vgpr;

	public VGPR(int b) {
		this.vgpr = (byte) b;
	}

	@Override
	public int get(ComputeData data) {
		return data.vgpr[vgpr & 0xFF - 1];
	}

	@Override
	public void set(ComputeData data, int v) {
		data.vgpr[vgpr & 0xFF - 1] = v;
	}
}
