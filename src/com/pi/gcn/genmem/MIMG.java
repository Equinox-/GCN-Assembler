package com.pi.gcn.genmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;

public class MIMG extends MC_64 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b111100, 18, 25);

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public MIMG(ByteBuffer insn) {
		super(insn);
	}

	public MIMG(int opcode) {
		super(LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
	}

	@Override
	public void encodeInternal(List<String> out) {
	}
	
	@Override
	public String format() {
		return null;
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
