package com.pi.gcn.insn;

import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.valu.vop3.VOP3.VOP3b_SCI;
import com.pi.gcn.valu.vop3.VOP3.VOP3b_VI;
import com.pi.gcn.valu.vop3.VOP3b_Base;
public class I_VOP3b {
	public static void register(InstructionSet s) {
		Class<? extends VOP3b_Base> vop3BFormat = s.processor.has(SubtargetFeature.VolcanicIslands) ? VOP3b_VI.class
				: VOP3b_SCI.class;
		
		/* Begin VOP3b */
		s.add("V_DIV_SCALE_F32", vop3BFormat, 0x1E0);
		s.add("V_DIV_SCALE_F64", vop3BFormat, 0x1E1);
		/* End VOP3b */
	}
}
