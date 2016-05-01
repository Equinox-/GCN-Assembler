package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.data.ComputeData;

public class SOP1 extends SALU {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(23, 0b101111101, 8, 16);

	private static final int[] DEST = { 16, 23 };

	private static final int[] SRC0 = { 0, 8 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SOP1(ByteBuffer insn) {
		super(insn);
	}

	public SOP1(int opcode) {
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
	protected int internalDest() {
		return (int) get(DEST);
	}

	@Override
	protected int internalSrc0() {
		return (int) get(SRC0);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
