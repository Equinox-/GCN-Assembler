package com.pi.gcn.insn;

import com.pi.gcn.salu.SOPC;

public class I_SOPC {
	public static void register(InstructionSet s) {
		/* Begin SOPC */
		s.add("S_BITCMP0_B32", SOPC.class, 0xC, "SCC = (S0.u[S1.u[4:0]] == 0)");
		s.add("S_BITCMP0_B64", SOPC.class, 0xE, "SCC = (S0.u[S1.u[5:0]] == 0)");
		s.add("S_BITCMP1_B32", SOPC.class, 0xD, "SCC = (S0.u[S1.u[4:0]] == 1)");
		s.add("S_BITCMP1_B64", SOPC.class, 0xF, "SCC = (S0.u[S1.u[5:0]] == 1)");
		s.add("S_CMP_EQ_I32", SOPC.class, 0x0, "SCC = (S0.i == S1.i)");
		s.add("S_CMP_EQ_U32", SOPC.class, 0x6, "SCC = (S0.u == S1.u)");
		s.add("S_CMP_EQ_U64", SOPC.class, 0x12, "SCC = (S0.i64 == S1.i64)");
		s.add("S_CMP_GE_I32", SOPC.class, 0x3, "SCC = (S0.i >= S1.i)");
		s.add("S_CMP_GE_U32", SOPC.class, 0x9, "SCC = (S0.u >= S1.u)");
		s.add("S_CMP_GT_I32", SOPC.class, 0x2, "SCC = (S0.i > S1.i)");
		s.add("S_CMP_GT_U32", SOPC.class, 0x8, "SCC = (S0.u > S1.u)");
		s.add("S_CMP_LE_I32", SOPC.class, 0x5, "SCC = (S0.i <= S1.i)");
		s.add("S_CMP_LE_U32", SOPC.class, 0xB, "SCC = (S0.u <= S1.u)");
		s.add("S_CMP_LG_I32", SOPC.class, 0x1, "SCC = (S0.i != S1.i)");
		s.add("S_CMP_LG_U32", SOPC.class, 0x7, "SCC = (S0.u != S1.u)");
		s.add("S_CMP_LG_U64", SOPC.class, 0x13, "SCC = (S0.i64 != S1.i64)");
		s.add("S_CMP_LT_I32", SOPC.class, 0x4, "SCC = (S0.i < S1.i)");
		s.add("S_CMP_LT_U32", SOPC.class, 0xA, "SCC = (S0.u < S1.u)");
		s.add("S_CMP_NE_U64", SOPC.class, 0xA, "SXCCX = (S0 != S1)");
		s.add("S_SET_GPR_IDX_ON", SOPC.class, 0x11, "M0[7:0] = S0.u[7:0], M0[15:12] = SIMM4, VSRC[0-3]_REL = S1[0-3]", "Enables GPR indexing mode");
		s.add("S_SETVSKIP", SOPC.class, 0x10, "VSKIP = S0.u[S1.u[4:0]]", "Extract one bit from SRC0 and use that to enable VSIP mode");
		/* End SOPC */
	}
}
