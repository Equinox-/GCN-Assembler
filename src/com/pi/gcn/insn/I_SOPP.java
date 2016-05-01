package com.pi.gcn.insn;

import com.pi.gcn.salu.SOPP;

public class I_SOPP {
	private static final String DO_JUMP = "PC = PC + signext(SIMM16 * 4) + 4";
	public static void register(InstructionSet s) {
		/* Begin SOPP */
		s.add("S_BARRIER", SOPP.class, 0xA, "Sync waves within a work-group");
		s.add("S_BRANCH", SOPP.class, 0x2, DO_JUMP);
		s.add("S_BRANCH_CDBGSYS", SOPP.class, 0x17, "if (conditional_debug_system != 0) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_CDBGSYS_AND_USER", SOPP.class, 0x1A, "if (conditional_debug_system && conditional_debug_user) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_CDBGSYS_OR_USER", SOPP.class, 0x19, "if (conditional_debug_system || conditional_debug_user) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_CDBGUSER", SOPP.class, 0x18, "if (conditional_debug_user) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_EXECNZ", SOPP.class, 0x9, "if (EXEC != 0) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_EXECZ", SOPP.class, 0x8, "if (EXEC == 0) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_SCC0", SOPP.class, 0x4, "if (SCC == 0) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_SCC1", SOPP.class, 0x5, "if (SCC == 1) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_VCCNZ", SOPP.class, 0x7, "if (VCC != 0) " + DO_JUMP + "; else NOP");
		s.add("S_CBRANCH_VCCZ", SOPP.class, 0x6, "if (VCC == 0) " + DO_JUMP + "; else NOP");
		s.add("S_DECPERFLEVEL", SOPP.class, 0x15, "perf[SIMM16[3:0]] -= 1", "Decrement performance counter");
		s.add("S_ENDPGM", SOPP.class, 0x1, "Terminate wavefront");
		s.add("S_ENDPG_SAVED", SOPP.class, 0x1B, "Terminate saved wavefront");
		s.add("S_ICACHE_INV", SOPP.class, 0x13, "Invalidate the L1 cache");
		s.add("S_INCPERFLEVEL", SOPP.class, 0x14, "perf[SIMM16[3:0]] += 1", "Increment performance counter");
		s.add("S_NOP", SOPP.class, 0x0, "Do nothing SIM16[2:0] times");
		s.add("S_SENDMSG", SOPP.class, 0x10, "Send a message");
		s.add("S_SENDMSGHALT", SOPP.class, 0x11, "Send a message and HALT");
		s.add("S_SET_GPR_IDX_MODE", SOPP.class, 0x1D, "MO[15:12] = SIMM4");
		s.add("S_SET_GPR_IDX_OFF", SOPP.class, 0x1C, "No GPR indexing");
		s.add("S_SETHALT", SOPP.class, 0xD, "Set HALT to SIMM16[0]");
		s.add("S_SETKILL", SOPP.class, 0xB, "Set KILL to SIMM16[0]");
		s.add("S_SETPRIO", SOPP.class, 0xF, "Set wave priority to SIMM16[1:0]");
		s.add("S_SLEEP", SOPP.class, 0xE, "Sleep for about 64*SIMM16[2:0] clocks");
		s.add("S_TRAP", SOPP.class, 0x12, "Enter trap ID SIMM16[7:0]");
		s.add("S_TRACEDATA", SOPP.class, 0x16, "Send M0 as user data to thread-trace");
		s.add("S_WAITCNT", SOPP.class, 0xC, "Wait for writing op count to below SIMM16[3:0]");
		/* End SOPP */
	}
}
