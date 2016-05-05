package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.salu.SOP2;

public class I_SOP2 {
	private static MultiOp mp2(int vi) {
		return new MultiOp(vi + 2, vi);
	}
	
	public static void register(InstructionSet s) {
		/* Begin SOP2 */
		s.add("S_ADD_U32", SOP2.class, 0x0, "D.u = S0.u + S1.u", "SCC = unsigned carry out");
		s.add("S_SUB_U32", SOP2.class, 0x1, "D.u = S0.u - S1.u", "SCC = unsigned carry out");
		s.add("S_ADD_I32", SOP2.class, 0x2, "D.u = S0.i + S1.i", "SCC = signed overflow");
		s.add("S_SUB_I32", SOP2.class, 0x3, "D.u = S0.i - S1.i", "SCC = borrow");
		s.add("S_ADDC_U32", SOP2.class, 0x4, "D.u = S0.u + S1.u + SCC", "SCC = unsigned carry out");
		s.add("S_SUBB_U32", SOP2.class, 0x5, "D.u = S0.u - S1.u - SCC", "SCC = unsigned carry out");
		s.add("S_MIN_I32", SOP2.class, 0x6, "D.i = (S0.i < S1.i) ? S0.i : S1.i", "SCC = 1 if S0 is min");
		s.add("S_MIN_U32", SOP2.class, 0x7, "D.u = (S0.u < S1.u) ? S0.u : S1.u", "SCC = 1 if S0 is min");
		s.add("S_MAX_I32", SOP2.class, 0x8, "D.i = (S0.i > S1.i) ? S0.i : S1.i", "SCC = 1 if S0 is max");
		s.add("S_MAX_U32", SOP2.class, 0x9, "D.u = (S0.u > S1.u) ? S0.u : S1.u", "SCC = 1 if S0 is max");
		s.add("S_CSELECT_B32", SOP2.class, 0xA, "D.u = SCC ? S0.u : S1.u");
		s.add("S_CSELECT_B64", SOP2.class, 0xB, "D.u = SCC ? S0.u : S1.u");
		s.add("S_AND_B32", SOP2.class, mp2(0xC), "D.u = S0.u & S1.u", "SCC = 1 if result is non-zero");
		s.add("S_AND_B64", SOP2.class, mp2(0xD), "D.u = S0.u & S1.u", "SCC = 1 if result is non-zero");
		s.add("S_OR_B32", SOP2.class, mp2(0xE), "D.u = S0.u | S1.u", "SCC = 1 is result is non-zero");
		s.add("S_OR_B64", SOP2.class, mp2(0xF), "D.u = S0.u | S1.u", "SCC = 1 is result is non-zero");

		s.add("S_XOR_B32", SOP2.class, mp2(0x10), "D.u = S0.u ^ S1.u", "SCC = 1 if result is non-zero");
		s.add("S_XOR_B64", SOP2.class, mp2(0x11), "D.u = S0.u ^ S1.u", "SCC = 1 if result is non-zero");
		s.add("S_ANDN2_B32", SOP2.class, mp2(0x12), "D.u = S0.u & ~S1.u", "SCC = 1 if result is non-zero");
		s.add("S_ANDN2_B64", SOP2.class, mp2(0x13), "D.u = S0.u & ~S1.u", "SCC = 1 if result is non-zero");
		s.add("S_ORN2_B32", SOP2.class, mp2(0x14), "D.u = S0.u | ~S1.u", "SCC = 1 is result is non-zero");
		s.add("S_ORN2_B64", SOP2.class, mp2(0x15), "D.u = S0.u | ~S1.u", "SCC = 1 is result is non-zero");
		s.add("S_LSHR_B64", SOP2.class, mp2(0x15), "D.u = S0.u >> S1.u[5:0]", "SCC = 1 if result is non-zero");
		s.add("S_NAND_B32", SOP2.class, mp2(0x16), "D.u = ~(S0.u & S1.u)", "SCC = 1 is result is non-zero");
		s.add("S_NAND_B64", SOP2.class, mp2(0x17), "D.u = ~(S0.u & S1.u)", "SCC = 1 is result is non-zero");
		s.add("S_NOR_B32", SOP2.class, mp2(0x18), "D.u = ~(S0.u | S1.u)", "SCC = 1 is result is non-zero");
		s.add("S_NOR_B64", SOP2.class, mp2(0x19), "D.u = ~(S0.u | S1.u)", "SCC = 1 is result is non-zero");
		s.add("S_XNOR_B32", SOP2.class, mp2(0x1A), "D.u = ~(S0.u ^ S1.u)", "SCC = 1 if result is non-zero");
		s.add("S_XNOR_B64", SOP2.class, mp2(0x1B), "D.u = ~(S0.u ^ S1.u)", "SCC = 1 if result is non-zero");
		s.add("S_LSHL_B32", SOP2.class, mp2(0x1C), "D.u = S0.u << S1.u[4:0]", "SCC = 1 if result is non-zero");
		s.add("S_LSHL_B64", SOP2.class, mp2(0x1D), "D.u = S0.u << S1.u[5:0]", "SCC = 1 if result is non-zero");
		s.add("S_LSHR_B32", SOP2.class, mp2(0x1E), "D.u = S0.u >> S1.u[4:0]", "SCC = 1 if result is non-zero");
		
		s.add("S_ASHR_I32", SOP2.class, mp2(0x20), "D.i = signext(S0.i) >> S1.i[4:0]", "SCC = 1 if result is non-zero");
		s.add("S_ASHR_I64", SOP2.class, mp2(0x21), "D.i = signext(S0.i) >> S1.i[4:0]", "SCC = 1 if result is non-zero");
		s.add("S_BFM_B32", SOP2.class, mp2(0x22), "Bitfield mask", "D.u = ((1 << S0.u[4:0]) - 1) << S1.u[4:0]");
		s.add("S_BFM_B64", SOP2.class, mp2(0x23), "Bitfield mask", "D.u = ((1 << S0.u[5:0]) - 1) << S1.u[5:0]");
		s.add("S_MUL_I32", SOP2.class, mp2(0x24), "D.i = S0.i * S1.i");
		s.add("S_BFE_U32", SOP2.class, mp2(0x25), "Unsigned bitfield extract", "D.i = (S0.u >> S1.u[4:0]) & ((1 << S1.u[22:16]) - 1)", "SCC = 1 if result is non-zero");
		s.add("S_BFE_I32", SOP2.class, mp2(0x26), "Signed bitfield extract", "D.i = (S0.u >> S1.u[4:0]) & ((1 << S1.u[22:16]) - 1)", "SCC = 1 if result is non-zero");
		s.add("S_BFE_U64", SOP2.class, mp2(0x27), "Unsigned bitfield extract", "D.i = (S0.u >> S1.u[4:0]) & ((1 << S1.u[22:16]) - 1)", "SCC = 1 if result is non-zero");
		s.add("S_BFE_I64", SOP2.class, mp2(0x28), "Signed bitfield extract", "D.i = (S0.u >> S1.u[5:0]) & ((1 << S1.u[22:16]) - 1)", "SCC = 1 if result is non-zero");
		s.add("S_CBRANCH_G_FORK", SOP2.class, mp2(0x29));
		s.add("S_ABSDIFF_I32", SOP2.class, mp2(0x2A), "D.i = abs(S0.i - S1.i)", "SCC = 1 if result is non-zero");
		/* End SOP2 */
	}
}
