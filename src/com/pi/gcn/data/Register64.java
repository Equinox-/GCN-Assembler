package com.pi.gcn.data;

public enum Register64 {
	EXEC(Register32.EXEC_LO, Register32.EXEC_HI),
	VCC(Register32.VCC_LO, Register32.VCC_HI);
	public final Register32 low, high;

	private Register64(Register32 low, Register32 high) {
		this.low = low;
		this.high = high;
	}
}
