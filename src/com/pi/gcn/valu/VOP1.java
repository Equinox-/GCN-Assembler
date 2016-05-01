package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_32_Lit;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.LiteralConstant;
import com.pi.gcn.data.VGPR;

public class VOP1 extends MC_32_Lit {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(25, 0b0111111, 9, 17);

	private static final int[] SRC0 = { 0, 9 };

	private static final int[] VDEST = { 17, 25 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public VOP1(ByteBuffer insn) {
		super(insn);
	}

	public VOP1(int opcode) {
		super((int)LAYOUT.make(opcode));
	}

	@Override
	public void decode(List<String> s) {
		if (s.size() != 2)
			throw new IllegalArgumentException("Must have 2 arguments");
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		vDest((VGPR) RefEncoding.decode(s.get(1)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(vDest()));
	}
	
	@Override
	public String format() {
		return "src0 dest";
	}

	@Override
	public boolean hasLiteral() {
		return get(SRC0) == MCOperand.LITERAL_CONSTANT;
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

	public VGPR vDest() {
		return new VGPR((int) get(VDEST));
	}

	public void vDest(VGPR v) {
		set(VDEST, v.vgpr & 0xFF);
	}
}
