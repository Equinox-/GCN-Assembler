package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenDest;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.LiteralConstant;

public class SOP2 extends SALU {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(30, 0b10, 23, 30);

	private static final int[] DEST = { 16, 23 };

	private static final int[] SRC1 = { 8, 16 };

	private static final int[] SRC0 = { 0, 8 };

	public static boolean valid(ByteBuffer b) {
		if (SOP1.valid(b) || SOPC.valid(b) || SOPK.valid(b) || SOPP.valid(b))
			return false;
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SOP2(ByteBuffer insn) {
		super(insn);
	}

	public SOP2(int opcode) {
		super(LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
		if (s.size() != 3)
			throw new IllegalArgumentException();
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		src1((GenSrc) RefEncoding.decode(s.get(1)));
		dest((GenDest) RefEncoding.decode(s.get(2)));
	}

	public void dest(GenDest s) {
		set(DEST, MCOperand.encodeRef(s));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(src1()));
		out.add(RefEncoding.encode(dest()));
	}

	@Override
	public String format() {
		return "src0 src1 dest";
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

	public void src0(GenSrc s) {
		if (s instanceof LiteralConstant)
			literal = ((LiteralConstant) s).constant;
		set(SRC0, MCOperand.encodeRef(s));
	}

	public void src1(GenSrc s) {
		if (s instanceof LiteralConstant)
			literal = ((LiteralConstant) s).constant;
		set(SRC1, MCOperand.encodeRef(s));
	}
}
