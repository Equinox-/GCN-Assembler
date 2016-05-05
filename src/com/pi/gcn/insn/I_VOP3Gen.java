package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.base.MC;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.valu.VOP1;
import com.pi.gcn.valu.VOP2;
import com.pi.gcn.valu.VOPC;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_SCI;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_VI;
import com.pi.gcn.valu.vop3.VOP3_Base;
import com.pi.gcn.valu.vop3.VOP3a_Base;

public class I_VOP3Gen {
	private static void map(InstructionSet rs, MultiOp offset, Class<? extends VOP3_Base> vp, Class<? extends MC> base) {
		for (Instruction s : rs.values(base)) {
			rs.add(s.name() + "_3", vp, offset.opcodeFor(rs.processor) + s.opcode(), s.document());
		}
	}

	public static void generate(InstructionSet s) {
		Class<? extends VOP3a_Base> vop3AFormat = s.processor.has(SubtargetFeature.VolcanicIslands) ? VOP3a_VI.class
				: VOP3a_SCI.class;
		map(s, new MultiOp(0, 0), vop3AFormat, VOPC.class);
		map(s, new MultiOp(0x100, 0x100), vop3AFormat, VOP2.class);
		map(s, new MultiOp(0x180, 0x140), vop3AFormat, VOP1.class);
	}
}
