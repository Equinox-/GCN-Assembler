package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenDest;
import com.pi.gcn.data.GenSrc;

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
		if (s.size() != 2)
			throw new IllegalArgumentException();
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		dest((GenDest) RefEncoding.decode(s.get(1)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(dest()));
	}

	@Override
	public String format() {
		return "src0 dest";
	}

	@Override
	protected int internalDest() {
		return (int) get(DEST);
	}

	@Override
	protected void internalDest(int v) {
		set(DEST, v);
	}

	@Override
	protected int internalSrc0() {
		return (int) get(SRC0);
	}

	@Override
	protected void internalSrc0(int v) {
		set(SRC0, v);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
