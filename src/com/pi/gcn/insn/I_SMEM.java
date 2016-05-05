package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.base.MC;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.smem.SMEM;
import com.pi.gcn.smem.SMRD;

public class I_SMEM {
	private static final String[] S_BUFFER_SEMANTICS = { "m_offset = IMM ? OFFSET : SGPR[OFFSET]",
			"m_base = { SGPR[SBASE * 2 +1][15:0], SGPR[SBASE] }", "m_stride = SGPR[SBASE * 2 +1][31:16]",
			"m_num_records = SGPR[SBASE * 2 + 2]", "m_size = (m_stride == 0) ? 1 : m_num_records",
			"m_addr = (SGPR[SBASE * 2] + m_offset) & ~0x3", "read_dword_from_kcache(m_base, m_offset, m_size)" };

	private static final String[] S_CONST_SEMANTICS = { "m_offset = IMM ? OFFSET : SGPR[OFFSET]",
			"m_addr = (SGPR[SBASE * 2] + m_offset) & ~0x3", "SGPR[SDST] = read_dword_from_kcache(m_addr)" };

	// S_BUFFER are complicated
	public static void register(InstructionSet s) {
		Class<? extends MC> memory;

		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			memory = SMEM.class;
			s.add("S_STORE_DWORD", memory, 0x10, "kcache[...] = SDATA", "See I_SMEM#S_CONST_SEMANTICS");
			s.add("S_STORE_DWORDX2", memory, 0x11, "kcache[...] = SDATA...", "See I_SMEM#S_CONST_SEMANTICS");
			s.add("S_STORE_DWORDX4", memory, 0x12, "kcache[...] = SDATA...", "See I_SMEM#S_CONST_SEMANTICS");
			
			s.add("S_MEMREALTIME", memory, 0x16, "SDATA.u64 = RTC.u64", "Real time clock");
			
			s.add("S_BUFFER_STORE_DWORD", memory, 0x18, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");
			s.add("S_BUFFER_STORE_DWORDX2", memory, 0x19, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");
			s.add("S_BUFFER_STORE_DWORDX4", memory, 0x1A, "kcache[...] = SDATA...", "See I_SMEM#S_BUFFER_SEMANTICS");

			s.add("S_DCACHE_WB", memory, 0x21, "Write dirty lines in scalar cache");
			s.add("S_DCACHE_WB_VOL", memory, 0x23, "Write dirty voltaile lines in scalar cache");

			s.add("S_ATC_PROBE", memory, 0x26, "Prefetch an address into the SQC data cache");
			s.add("S_ATC_PROBE_BUFFER", memory, 0x26, "Prefetch a buffer address into the SQC data cache");
		} else {
			memory = SMRD.class;
		}

		s.add("S_LOAD_DWORD", memory, 0x0, "SDATA = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX2", memory, 0x1, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX4", memory, 0x2, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX8", memory, 0x3, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");
		s.add("S_LOAD_DWORDX16", memory, 0x4, "SDATA... = kcache[...]", "See I_SMEM#S_CONST_SEMANTICS");

		s.add("S_BUFFER_LOAD_DWORD", memory, 0x8, "SDATA = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX2", memory, 0x9, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX4", memory, 0xA, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX8", memory, 0xB, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");
		s.add("S_BUFFER_LOAD_DWORDX16", memory, 0xC, "SDATA... = kcache[...]", "See I_SMEM#S_BUFFER_SEMANTICS");

		if (s.processor.has(SubtargetFeature.SeaIslands) || s.processor.has(SubtargetFeature.VolcanicIslands))
			s.add("S_DCACHE_INV_VOL", memory, new MultiOp(0x1d, 0x22), "Invalidate volatile lines in L1 kcache");

		s.add("S_MEMTIME", memory, new MultiOp(0x1e, 0x24), "SDATA.u64 = STC.u64", "Shader core clock");
		s.add("S_DCACHE_INV", memory, new MultiOp(0x1f, 0x20), "Invalidate the L1 kcache");
		/* End SMEM */
	}
}
