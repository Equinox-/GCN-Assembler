package com.pi.gcn.data;

public enum Register32 {
	MODE,
	STATUS(false),
	M0,
	TRAP_STS,
	EXEC_LO,
	EXEC_HI,
	VCC_LO,
	VCC_HI,
	SCC,
	PC,
	VCCZ,
	HW_ID(false),
	GPR_ALLOC(false),
	LDS_ALLOC(false),
	IB_STS(false),
	EXECZ,
	FLAT_SCRATCH_LO,
	FLAT_SCRATCH_HI,
	TBA_LO,
	TBA_HI,
	TMA_LO,
	TMA_HI;

	public final boolean rw;

	private Register32() {
		this(true);
	}

	private Register32(boolean rw) {
		this.rw = rw;
	}
}
