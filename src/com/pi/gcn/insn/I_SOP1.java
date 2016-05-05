package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.salu.SOP1;

public class I_SOP1 {
	private static MultiOp mp3(int vi) {
		return new MultiOp(vi + 3, vi);
	}

	private static MultiOp mp4(int vi) {
		return new MultiOp(vi + 4, vi);
	}

	public static void register(InstructionSet s) {
		s.add("S_MOV_B32", SOP1.class, mp3(0x0), "D.u = S0.u");
		s.add("S_MOV_B64", SOP1.class, mp3(0x1), "D.u = S0.u");
		s.add("S_CMOV_B32", SOP1.class, mp3(0x2), "if (SCC) D.u = S0.u; else NOP");
		s.add("S_CMOV_B64", SOP1.class, mp3(0x3), "if (SCC) D.u = S0.u; else NOP");
		s.add("S_NOT_B32", SOP1.class, mp3(0x4), "D.u = ~S0.u", "SCC = 1 if result non-zero");
		s.add("S_NOT_B64", SOP1.class, mp3(0x5), "D.u = ~S0.u", "SCC = 1 if result non-zero");
		s.add("S_WQM_B32", SOP1.class, mp3(0x6), "D.u = WholeQuadMode(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_WQM_B64", SOP1.class, mp3(0x7), "D.u = WholeQuadMode(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_BREV_B32", SOP1.class, mp3(0x8), "D.u = S0.u[0:31] (bit reverse)");
		s.add("S_BREV_B64", SOP1.class, mp3(0x9), "D.u = S0.u[0:63] (bit reverse)");
		s.add("S_BCNT0_I32_B32", SOP1.class, mp3(0xA), "D.i = CountZeroBits(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_BCNT0_I32_B64", SOP1.class, mp3(0xB), "D.i = CountZeroBits(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_BCNT1_I32_B32", SOP1.class, mp3(0xC), "D.i = CountZeroBits(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_BCNT1_I32_B64", SOP1.class, mp3(0xD), "D.i = CountZeroBits(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_FF0_I32_B32", SOP1.class, mp3(0xE), "D.i = FindFirstZero(S0.u) from LSB; if no zeros, return -1");
		s.add("S_FF0_I32_B64", SOP1.class, mp3(0xF), "D.i = FindFirstZero(S0.u) from LSB; if no zeros, return -1");
		s.add("S_FF1_I32_B32", SOP1.class, mp3(0x10), "D.i = FindFirstOne(S0.u) from LSB; if no zeros, ones -1");
		s.add("S_FF1_I32_B64", SOP1.class, mp3(0x11), "D.i = FindFirstOne(S0.u) from LSB; if no zeros, ones -1");
		s.add("S_FLBIT_I32_B32", SOP1.class, mp3(0x12), "D.i = FindFirstOne(S0.u) from MSB; if no ones, return -1");
		s.add("S_FLBIT_I32_B64", SOP1.class, mp3(0x13), "D.i = FindFirstOne(S0.u) from MSB; if no ones, return -1");
		s.add("S_FLBIT_I32", SOP1.class, mp3(0x14),
				"D.i = Find first bit opposite of sign bit from MSB. If S0 == -1, return -1");
		s.add("S_FLBIT_I32_I64", SOP1.class, mp3(0x15),
				"D.i = Find first bit opposite of sign bit from MSB. If S0 == -1, return -1");
		s.add("S_SEXT_I32_I8", SOP1.class, mp3(0x16), "D.i = signext(S0.i[7:0])");
		s.add("S_SEXT_I32_I16", SOP1.class, mp3(0x17), "D.i = signext(S0.i[15:0])");
		s.add("S_BITSET0_B32", SOP1.class, mp3(0x18), "D.u[S0.u[4:0]] = 0");
		s.add("S_BITSET0_B64", SOP1.class, mp3(0x19), "D.u[S0.u[5:0]] = 0");
		s.add("S_BITSET1_B32", SOP1.class, mp3(0x1A), "D.u[S0.u[4:0]] = 1");
		s.add("S_BITSET1_B64", SOP1.class, mp3(0x1B), "D.u[S0.u[5:0]] = 1");
		s.add("S_GETPC_B64", SOP1.class, mp3(0x1C),
				"D.u = PC + 4; destination receives the byte address of the next instruction");
		s.add("S_SETPC_B64", SOP1.class, mp3(0x1D), "PC = S0.u",
				"S0.u is a byte address of the instruction to jump to");
		s.add("S_SWAPPC_B64", SOP1.class, mp3(0x1E), "D.u = PC + 4; PC = S0.u");
		s.add("S_RFE_B64", SOP1.class, mp3(0x1F), "PC = S0.u, PRIV = 0", "Return from Exception");
		s.add("S_AND_SAVEEXEC_B64", SOP1.class, mp4(0x20), "D.u = EXEC, EXEC = S0.u & EXEC",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_OR_SAVEEXEC_B64", SOP1.class, mp4(0x21), "D.u = EXEC, EXEC = S0.u | EXEC",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_XOR_SAVEEXEC_B64", SOP1.class, mp4(0x22), "D.u = EXEC, EXEC = S0.u ^ EXEC",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_ANDN2_SAVEEXEC_B64", SOP1.class, mp4(0x23), "D.u = EXEC, EXEC = S0.u & ~EXEC",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_ORN2_SAVEEXEC_B64", SOP1.class, mp4(0x24), "D.u = EXEC, EXEC = S0.u | ~EXEC",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_NAND_SAVEEXEC_B64", SOP1.class, mp4(0x25), "D.u = EXEC, EXEC = ~(S0.u & EXEC)",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_NOR_SAVEEXEC_B64", SOP1.class, mp4(0x26), "D.u = EXEC, EXEC = ~(S0.u | EXEC)",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_XNOR_SAVEEXEC_B64", SOP1.class, mp4(0x27), "D.u = EXEC, EXEC = ~(S0.u ^ EXEC)",
				"SCC = 1 if the new value of EXEC is non-zero");
		s.add("S_QUADMASK_B32", SOP1.class, mp4(0x28), "D.u = QuadMask(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_QUADMASK_B64", SOP1.class, mp4(0x29), "D.u = QuadMask(S0.u)", "SCC = 1 if result is non-zero");
		s.add("S_MOVRELS_B32", SOP1.class, mp4(0x2A), "SGPR[D.u] = SGPR[S0.u + M0.u]");
		s.add("S_MOVRELS_B64", SOP1.class, mp4(0x2B), "SGPR[D.u] = SGPR[S0.u + M0.u]");
		s.add("S_MOVRELD_B32", SOP1.class, mp4(0x2C), "SGPR[D.u + M0.u] = SGPR[S0.u]");
		s.add("S_MOVRELD_B64", SOP1.class, mp4(0x2D), "SGPR[D.u + M0.u] = SGPR[S0.u]");
		s.add("S_CBRANCH_JOIN", SOP1.class, mp4(0x2E));

		s.add("S_ABS_I32", SOP1.class, mp4(0x30), "D.i = abs(S0.i)", "SCC = 1 if result is non-zero");

		if (s.processor.has(SubtargetFeature.VolcanicIslands))
			s.add("S_SET_GPR_IDX_IDX", SOP1.class, 0x32, "M0[7:0] = S0.U[7:0]",
					"Modify the index used in vector GPR indexing");
		else if (s.processor.has(SubtargetFeature.SouthernIslands))
			s.add("S_MOV_FED_B32", SOP1.class, 0x35, "D.u = S0.u", "Introduce EDC double error upon write");
	}
}
