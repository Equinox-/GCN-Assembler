package com.pi.gcn.proc;

public enum SubtargetFeature {
	SouthernIslands,
	SeaIslands,
	LDSBankCount32,
	LDSBankCount16,
	ISAVersion7_0_0(7, 0, 0),
	ISAVersion7_0_1(7, 0, 1),
	VolcanicIslands,
	SGPRInitBug,
	ISAVersion8_0_0(8, 0, 0),
	ISAVersion8_0_1(8, 0, 1),
	ISAVersion8_0_3(8, 0, 3),
	FastFMAF32;

	public final boolean isISAVersion;
	public final int major, minor, stepping;

	private SubtargetFeature() {
		this.isISAVersion = false;
		this.major = this.minor = this.stepping = -1;
	}

	private SubtargetFeature(int major, int minor, int stepping) {
		this.isISAVersion = true;
		this.major = major;
		this.minor = minor;
		this.stepping = stepping;
	}
}
