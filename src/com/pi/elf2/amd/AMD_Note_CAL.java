package com.pi.elf2.amd;

public enum AMD_Note_CAL {
	// Sequential DWORDs
	ATI_INPUTS(2),
	ATI_OUTPUTS(3),
	// Sequential {DWORD offset, DWORD size}
	ATI_FLOAT_CONSTS(5),
	ATI_INT_CONSTS(6),
	ATI_BOOL_CONSTS(7),
	// Sequential DWORD
	ATI_EARLY_EXIT(8),
	// Single DWORD bool
	ATI_GLOBAL_BUFFERS(9),
	// Sequential {DWORD index, DWORD size}
	ATI_CONSTANT_BUFFERS(10),
	// Sequential {DWORD input, DWORD samplerID}
	ATI_INPUT_SAMPLERS(11),
	// Single DWORD bool (uses scratch memory)
	ATI_SCRATCH_BUFFERS(13),
	// Single DWORD bool
	ATI_PERSISTENT_BUFFERS(12),
	// Sequential {DWORD address, DWORD value}
	ATI_PROGINFO(1);

	public final int value;

	public static AMD_Note_CAL lookup(int v) {
		for (AMD_Note_CAL c : values())
			if (c.value == v)
				return c;
		throw new RuntimeException("Bad value: " + v);
	}

	private AMD_Note_CAL(int v) {
		this.value = (int) v;
	}
}