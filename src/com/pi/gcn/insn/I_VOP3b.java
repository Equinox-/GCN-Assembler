package com.pi.gcn.insn;

import com.pi.gcn.valu.VOP3b;
public class I_VOP3b {
	public static void register(InstructionSet s) {
		/* Begin VOP3b */
		s.add("V_DIV_SCALE_F32", VOP3b.class, 0x1E0);
		s.add("V_DIV_SCALE_F64", VOP3b.class, 0x1E1);
		/* End VOP3b */
	}
}
