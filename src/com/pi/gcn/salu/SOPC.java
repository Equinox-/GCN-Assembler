package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;

public class SOPC extends SALU {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(23, 0b101111110, 16, 23);

	private static final int[] SRC1 = { 8, 16 };

	private static final int[] SRC0 = { 0, 8 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SOPC(ByteBuffer insn) {
		super(insn);
	}

	public SOPC(int opcode) {
		super(LAYOUT.make(opcode));
	}

	@Override
	public void internalSrc0(int s) {
		set(SRC0, s);
	}
	@Override
	public void internalSrc1(int s) {
		set(SRC1, s);
	}
	
	@Override
	public void decode(List<String> s) {
		if (s.size() != 2)
			throw new IllegalArgumentException("Must have 2 args");
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		src1((GenSrc) RefEncoding.decode(s.get(1)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(src1()));
	}

	@Override
	public String format() {
		return "src0 src1";
	}

	@Override
	protected int internalSrc0() {
		return (int) get(SRC0);
	}

	@Override
	protected int internalSrc1() {
		return (int) get(SRC1);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
