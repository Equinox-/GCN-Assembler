package com.pi.gcn;

import com.pi.gcn.base.MC;

public class OpcodeLayout {
	public final int idValue;
	public final int idShift;

	public final int[] op;

	public OpcodeLayout(int id_shift, int id_valu, int op_low, int op_high) {
		this.idValue = id_valu;
		this.idShift = id_shift;
		this.op = new int[] { op_low, op_high };
	}

	public long make(int opcode) {
		return MC.setBits(((long) idValue << (long) idShift), op[0], op[1], opcode);
	}

	public boolean matches(int insn, int opcode) {
		return matches(insn) && opcode(insn) == opcode;
	}

	public boolean matches(int insn) {
		return (insn >>> idShift) == idValue;
	}

	public int maxOpcode() {
		return (1 << (op[1] - op[0])) - 1;
	}

	public int opcode(long insn) {
		return (int) MC.getBits(insn, op[0], op[1]);
	}
}