package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64_Lit;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.LiteralConstant;
import com.pi.gcn.data.VGPR;

public abstract class VOP3 extends MC_64_Lit {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b110100, 16, 26);

	private static final int[] OMOD = { 59, 61 };

	private static final int[] NEG = { 61, 64 };

	private static final int[] VDEST = { 0, 8 };

	private static final int[] SRC0 = { 32, 41 };

	private static final int[] SRC1 = { 41, 50 };

	private static final int[] SRC2 = { 50, 59 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public VOP3(ByteBuffer insn) {
		super(insn);
	}

	public VOP3(int opcode) {
		super(LAYOUT.make(opcode));
	}

	private void checkLitUpdate(GenSrc s) {
		if (s instanceof LiteralConstant)
			literal = ((LiteralConstant) s).constant;
	}

	// Clamp output to [0,1]
	public boolean clamp() {
		return get(15);
	}

	public void clamp(boolean b) {
		set(15, b);
	}

	protected void decodeOpts(List<String> s) {
		for (int i = 0; i < 3; i++)
			neg(i, Utils.removeIC(s, "NEG" + i));
		clamp(Utils.removeIC(s, "CLAMP"));

		omod(VOP3_OMOD.NONE);
		for (VOP3_OMOD v : VOP3_OMOD.values())
			if (Utils.removeIC(s, v.name()))
				omod(v);
	}

	protected void encodeOpts(List<String> out) {
		if (clamp())
			out.add("CLAMP");
		for (int i = 0; i < 3; i++)
			if (neg(i))
				out.add("NEG" + i);

		VOP3_OMOD mod = omod();
		if (mod != VOP3_OMOD.NONE)
			out.add(mod.name());
	}

	@Override
	public boolean hasLiteral() {
		return internalSrc0() == MCOperand.LITERAL_CONSTANT || internalSrc1() == MCOperand.LITERAL_CONSTANT
				|| internalSrc2() == MCOperand.LITERAL_CONSTANT;
	}

	private int internalSrc0() {
		return (int) get(SRC0);
	}

	private int internalSrc1() {
		return (int) get(SRC1);
	}

	private int internalSrc2() {
		return (int) get(SRC2);
	}
	public int neg() {
		return (int) get(NEG);
	}
	// Negate the n-th operand.
	public boolean neg(int op) {
		assert op >= 0 && op < 3;
		return get(NEG[0] + op);
	}

	public void neg(int op, boolean b) {
		assert op >= 0 && op < 3;
		set(NEG[0] + op, b);
	}

	public VOP3_OMOD omod() {
		return VOP3_OMOD.values()[(int) get(OMOD)];
	}

	public void omod(VOP3_OMOD v) {
		set(OMOD, v.ordinal());
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
		// TODO Auto-generated method stub

	}

	public GenSrc src0() {
		int src = internalSrc0();
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src0(GenSrc s) {
		checkLitUpdate(s);
		set(SRC0, MCOperand.encodeRef(s));
	}

	public GenSrc src1() {
		int src = internalSrc1();
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src1(GenSrc s) {
		checkLitUpdate(s);
		set(SRC1, MCOperand.encodeRef(s));
	}

	public GenSrc src2() {
		int src = internalSrc2();
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src2(GenSrc s) {
		checkLitUpdate(s);
		set(SRC2, MCOperand.encodeRef(s));
	}

	public VGPR vDest() {
		return new VGPR((int) get(VDEST));
	}

	public void vDest(VGPR v) {
		set(VDEST, v.vgpr & 0xFF);
	}

	public static enum VOP3_OMOD {
		NONE(1),
		MUL_BY_2(2),
		MUL_BY_4(4),
		DIV_BY_2(1 / 2f);
		public final float k;

		private VOP3_OMOD(float k) {
			this.k = k;
		}
	}
}
