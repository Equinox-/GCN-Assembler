package com.pi.gcn.data;

public class RWregister implements GenSrc, GenDest {
	public final Register32 register;

	public RWregister(Register32 r) {
		if (!r.rw)
			throw new IllegalArgumentException(r + " isn't a read-write register");
		this.register = r;
	}

	@Override
	public int get(ComputeData data) {
		return data.readRegister(register);
	}

	@Override
	public void set(ComputeData data, int v) {
		data.writeRegister(register, v);
	}
}
