package com.pi.gcn.data;

public class ROregister implements GenSrc {
	public final Register32 register;

	public ROregister(Register32 r) {
		this.register = r;
	}

	@Override
	public int get(ComputeData data) {
		return data.readRegister(register);
	}
}
