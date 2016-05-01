package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_32;
import com.pi.gcn.data.ComputeData;

public class SOPP extends MC_32 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(23, 0b101111111, 16, 23);

	private static final int[] SIMM = { 0, 16 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SOPP(ByteBuffer insn) {
		super(insn);
	}

	public SOPP(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
		int sv = Integer.parseInt(s.get(0));
		if (sv > 0xFFFF || sv < 0)
			throw new IllegalArgumentException("simm must be between [0, " + 0xFFFF + "]");
		simm((short) sv);
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(Integer.toString(simm() & 0xFFFF));
	}

	@Override
	public String format() {
		return "simm";
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}

	public short simm() {
		return (short) get(SIMM);
	}

	public void simm(short s) {
		set(SIMM, s);
	}
}
