package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_32;
import com.pi.gcn.data.ComputeData;

public class VINTRP extends MC_32 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b110010, 16, 18);

	private static final int[] VSRC = { 0, 8 };

	private static final int[] ATTR_CHAN = { 8, 10 };

	private static final int[] ATTR = { 10, 16 };
	private static final int[] VDEST = { 18, 26 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public VINTRP(ByteBuffer insn) {
		super(insn);
	}

	public VINTRP(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	public int attr() {
		return (int) get(ATTR);
	}

	public int attrChan() {
		return (int) get(ATTR_CHAN);
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

	private int internalVDest() {
		return (int) get(VDEST);
	}

	private int internalVSrc() {
		return (int) get(VSRC);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
