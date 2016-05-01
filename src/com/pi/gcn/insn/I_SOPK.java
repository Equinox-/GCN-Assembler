package com.pi.gcn.insn;

import com.pi.gcn.salu.SOPK;
import com.pi.gcn.salu.SOPK_ForcedLiteral;

public class I_SOPK {
	public static void register(InstructionSet s) {
		/* Begin SOPK */
		s.add("S_ADDK_I32", SOPK.class, 0xE, "D.i = D.i + signext(SIMM16)", "SCC = signed overflow");
		s.add("S_CBRANCH_I_FORK", SOPK.class, 0x10);
		s.add("S_CMOVK_I32", SOPK.class, 0x1, "if (SCC) D.i = signext(SIMM16); else NOP");
		s.add("S_CMPK_EQ_I32", SOPK.class, 0x2, "SCC = (D.i == signext(SIMM16))");
		s.add("S_CMPK_EQ_U32", SOPK.class, 0x8, "SCC = (D.u == SIMM16)");
		s.add("S_CMPK_GE_I32", SOPK.class, 0x5, "SCC = (D.i >= signext(SIMM16))");
		s.add("S_CMPK_GE_U32", SOPK.class, 0xB, "SCC = (D.u >= SIMM16)");
		s.add("S_CMPK_GT_I32", SOPK.class, 0x4, "SCC = (D.i > signext(SIMM16))");
		s.add("S_CMPK_GT_U32", SOPK.class, 0xA, "SCC = (D.u > SIMM16)");
		s.add("S_CMPK_LE_I32", SOPK.class, 0x7, "SCC = (D.i <= signext(SIMM16))");
		s.add("S_CMPK_LE_U32", SOPK.class, 0xD, "D.u = SCC = (D.u <= SIMM16)");
		s.add("S_CMPK_LG_I32", SOPK.class, 0x3, "SCC = (D.i != signext(SIMM16))");
		s.add("S_CMPK_LG_U32", SOPK.class, 0x9, "SCC = (D.u != SIMM16)");
		s.add("S_CMPK_LT_I32", SOPK.class, 0x6, "SCC = (D.i < signext(SIMM16))");
		s.add("S_CMPK_LT_U32", SOPK.class, 0xC, "SCC = (D.u < SIMM16)");
		s.add("S_GETREG_B32", SOPK.class, 0x11, "D.u = hardware register");
		s.add("S_MOVK_I32", SOPK.class, 0x0, "D.i = signext(SIMM16)");
		s.add("S_MULK_I32", SOPK.class, 0xF, "D.i = D.i * signext(SIMM16)", "SCC = overflow");
		s.add("S_SETREG_B32", SOPK.class, 0x12, "hardware register = D.u");
		s.add("S_SETREG_IMM32_B32", SOPK_ForcedLiteral.class, 0x14, "SIMM16 = [size[4:0], offset[4:0], hwRegId[5:0]]");
		/* End SOPK */
	}
}
