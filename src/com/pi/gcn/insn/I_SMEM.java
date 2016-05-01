package com.pi.gcn.insn;

import com.pi.gcn.smem.SMEM;

public class I_SMEM {
	private static final String[] S_BUFFER_SEMANTICS = { "m_offset = IMM ? OFFSET : SGPR[OFFSET]",
			"m_base = { SGPR[SBASE * 2 +1][15:0], SGPR[SBASE] }", "m_stride = SGPR[SBASE * 2 +1][31:16]",
			"m_num_records = SGPR[SBASE * 2 + 2]", "m_size = (m_stride == 0) ? 1 : m_num_records",
			"m_addr = (SGPR[SBASE * 2] + m_offset) & ~0x3", "read_dword_from_kcache(m_base, m_offset, m_size)" };

	private static final String[] S_CONST_SEMANTICS = { "m_offset = IMM ? OFFSET : SGPR[OFFSET]",
			"m_addr = (SGPR[SBASE * 2] + m_offset) & ~0x3", "SGPR[SDST] = read_dword_from_kcache(m_addr)" };

	// S_BUFFER are complicated
	public static void register(InstructionSet s) {
		/* Begin SMEM */
		s.add("S_ATC_PROBE", SMEM.class, 0x26, "Prefetch an address into the SQC data cache");
		s.add("S_ATC_PROBE_BUFFER", SMEM.class, 0x26, "Prefetch a buffer address into the SQC data cache");
		s.add("S_BUFFER_LOAD_DWORD", SMEM.class, 0x8, "SDATA = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX2", SMEM.class, 0x9, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX4", SMEM.class, 0xA, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX8", SMEM.class, 0xB, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX16", SMEM.class, 0xC, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_STORE_DWORD", SMEM.class, 0x18, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_STORE_DWORDX2", SMEM.class, 0x19, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_STORE_DWORDX4", SMEM.class, 0x1A, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_DCACHE_INV", SMEM.class, 0x20, "Invalidate the L1 kcache");
		s.add("S_DCACHE_INV_VOL", SMEM.class, 0x22, "Invalidate volatile lines in L1 kcache");
		s.add("S_DCACHE_WB", SMEM.class, 0x21, "Write dirty lines in scalar cache");
		s.add("S_DCACHE_WB_VOL", SMEM.class, 0x23, "Write dirty voltaile lines in scalar cache");
		s.add("S_LOAD_DWORD", SMEM.class, 0x0, "SDATA = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX2", SMEM.class, 0x1, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX4", SMEM.class, 0x2, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX8", SMEM.class, 0x3, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX16", SMEM.class, 0x4, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_MEMREALTIME", SMEM.class, 0x16, "SDATA.u64 = RTC.u64", "Real time clock");
		s.add("S_MEMTIME", SMEM.class, 0x24, "SDATA.u64 = STC.u64", "Shader core clock");
		s.add("S_STORE_DWORD", SMEM.class, 0x10, "kcache[...] = SDATA", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_STORE_DWORDX2", SMEM.class, 0x11, "kcache[...] = SDATA...", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_STORE_DWORDX4", SMEM.class, 0x12, "kcache[...] = SDATA...", "See I_SMEM#S_CONST_SEMANTICS");
		/* End SMEM */
	}
}
