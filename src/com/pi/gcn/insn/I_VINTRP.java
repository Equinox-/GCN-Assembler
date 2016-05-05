package com.pi.gcn.insn;

import com.pi.gcn.valu.VINTRP;
public class I_VINTRP {
	public static void register(InstructionSet s) {
		/* Begin VINTRP */
		s.add("V_INTERP_MOV_F32", VINTRP.class, 0x2);
		s.add("V_INTERP_P1_F32", VINTRP.class, 0x0);
		s.add("V_INTERP_P2_F32", VINTRP.class, 0x1);
//		s.add("V_INTERP_P1LL_F16", VOP3a.class, 0x274);
//		s.add("V_INTERP_P1LV_F16", VOP3a.class, 0x275);
//		s.add("V_INTERP_P2_F16", VOP3a.class, 0x276);
		/* End VINTRP */
	}
}
