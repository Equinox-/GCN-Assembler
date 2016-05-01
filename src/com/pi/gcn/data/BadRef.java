package com.pi.gcn.data;

public class BadRef implements GenSrc, GenDest {
	public final int code;

	public BadRef(int code) {
		this.code = code;
	}

	@Override
	public void set(ComputeData data, int v) {
		throw new UnsupportedOperationException("Bad ref?");
	}

	@Override
	public int get(ComputeData data) {
		throw new UnsupportedOperationException("Bad ref?");
	}

}
