package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_32;
import com.pi.gcn.data.ComputeData;

public class VOPC extends MC_32 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(25, 0b0111110, 17, 25);

	private static final int[] SRC0 = { 0, 9 };

	private static final int[] VSRC1 = { 9, 17 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public VOPC(ByteBuffer insn) {
		super(insn);
	}

	public VOPC(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
	}

	@Override
	public void encodeInternal(List<String> out) {
	}

	public int internalSrc0() {
		return (int) get(SRC0);
	}

	public int internalVSrc1() {
		return (int) get(VSRC1);
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
