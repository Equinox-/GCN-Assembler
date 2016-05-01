package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_32_Lit;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.LiteralConstant;
import com.pi.gcn.data.VGPR;

public class VOP2 extends MC_32_Lit {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(31, 0b0, 25, 31);

	private static final int[] SRC0 = { 0, 9 };

	private static final int[] SRC1 = { 9, 17 };

	private static final int[] DEST = { 17, 25 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public VOP2(ByteBuffer insn) {
		super(insn);
	}

	public VOP2(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
		if (s.size() != 3)
			throw new IllegalArgumentException();
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		src1((VGPR) RefEncoding.decode(s.get(1)));
		dest((VGPR) RefEncoding.decode(s.get(2)));
	}

	public VGPR dest() {
		return new VGPR((int) get(DEST));
	}

	public void dest(VGPR v) {
		set(DEST, v.vgpr & 0xFF);
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
	public boolean hasLiteral() {
		return get(SRC0) == MCOperand.LITERAL_CONSTANT || get(SRC1) == MCOperand.LITERAL_CONSTANT;
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}

	public GenSrc src0() {
		int src = (int) get(SRC0);
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src0(GenSrc s) {
		if (s instanceof LiteralConstant)
			literal = ((LiteralConstant) s).constant;
		set(SRC0, MCOperand.encodeRef(s));
	}

	public VGPR src1() {
		return new VGPR((int) get(SRC1));
	}

	public void src1(VGPR r) {
		set(SRC1, r.vgpr & 0xFF);
	}
}
