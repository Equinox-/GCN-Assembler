package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.base.MC;
import com.pi.gcn.valu.VOP1;
import com.pi.gcn.valu.VOP2;
import com.pi.gcn.valu.VOP3;
import com.pi.gcn.valu.VOP3a;
import com.pi.gcn.valu.VOPC;

public class I_VOP3Gen {
	private static void map(InstructionSet rs, MultiOp offset, Class<? extends VOP3> vp, Class<? extends MC> base) {
		for (Instruction s : rs.values(base)) {
			rs.add(s.name() + "_3", vp, offset.opcodeFor(rs.processor) + s.opcode(), s.document());
		}
	}

	public static void generate(InstructionSet s) {
		map(s, new MultiOp(0, 0), VOP3a.class, VOPC.class);
		map(s, new MultiOp(0x100, 0x100), VOP3a.class, VOP2.class);
		map(s, new MultiOp(0x180, 0x140), VOP3a.class, VOP1.class);
	}
}
