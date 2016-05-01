package com.pi.kernel;

import java.util.HashMap;
import java.util.Map;

public enum ComputePgmResourceRegister {
	VGPRS(0, 0x3F),
	SGPRS(6, 0xF),
	PRIORITY(10, 0x03),
	FLOAT_MODE(12, 0xFF),
	PRIV(20, 0x1),
	DX10_CLAMP(21, 0x1),
	DEBUG_MODE(22, 0x1),
	IEEE_MODE(23, 0x1),
	SCRATCH_EN(32, 0x1),
	USER_SGPR(33, 0x1F),
	TGID_X_EN(39, 0x1),
	TGID_Y_EN(40, 0x1),
	TGID_Z_EN(41, 0x1),
	TG_SIZE_EN(42, 0x1),
	TIDIG_COMP_CNT(43, 0x3),
	EXCP_EN_MSB(45, 0x03),
	LDS_SIZE(47, 0x1FF),
	EXCP_EN(56, 0x7F);
	private final long shift;
	private final long mask;
	private final long shift_mask;

	private ComputePgmResourceRegister(long shift, long mask) {
		this.shift = shift;
		this.mask = mask;
		this.shift_mask = mask << shift;
	}

	public long set(long src, long val) {
		return (src & ~shift_mask) | (val & mask) << shift;
	}

	public long get(long src) {
		return (src >>> shift) & mask;
	}

	public static Map<ComputePgmResourceRegister, Long> decodeMap(long l) {
		Map<ComputePgmResourceRegister, Long> lps = new HashMap<>();
		for (ComputePgmResourceRegister s : values())
			lps.put(s, s.get(l));
		return lps;
	}

	public static String decode(long l) {
		return decodeMap(l).toString();
	}
}
