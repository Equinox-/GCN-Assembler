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

public class VOPC extends MC_32_Lit {
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
		if (s.size() != 2)
			throw new IllegalArgumentException();
		src0((GenSrc) RefEncoding.decode(s.get(0)));
		vSrc1((VGPR) RefEncoding.decode(s.get(1)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(vSrc1()));
	}

	public GenSrc src0() {
		return (GenSrc) MCOperand.decodeRef8((int) get(SRC0));
	}

	public VGPR vSrc1() {
		return new VGPR((int) get(VSRC1));
	}

	public void src0(GenSrc s) {
		int v = MCOperand.encodeRef(s);
		if (v == MCOperand.LITERAL_CONSTANT)
			literal = ((LiteralConstant) s).constant;
		set(SRC0, v);
	}

	public void vSrc1(VGPR s) {
		set(VSRC1, MCOperand.encodeRef(s));
	}

	@Override
	public String format() {
		return "src0 vSrc1";
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}

	@Override
	public boolean hasLiteral() {
		return get(SRC0) == MCOperand.LITERAL_CONSTANT;
	}
}
