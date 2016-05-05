package com.pi.gcn.valu.vop3;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.VGPR;

public abstract class VOP3_Base extends MC_64 {
	protected final VOP3_Layout layout;

	public VOP3_Base(ByteBuffer insn, VOP3_Layout layout) {
		super(insn);
		this.layout = layout;
	}

	public VOP3_Base(long insn, VOP3_Layout layout) {
		super(insn);
		this.layout = layout;
	}

	// Clamp output to [0,1]
	public boolean clamp() {
		return get(layout.CLAMP);
	}

	public void clamp(boolean b) {
		set(layout.CLAMP, b);
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

	private int internalSrc0() {
		return (int) get(layout.SRC0);
	}

	private int internalSrc1() {
		return (int) get(layout.SRC1);
	}

	private int internalSrc2() {
		return (int) get(layout.SRC2);
	}

	public int neg() {
		return (int) get(layout.NEG);
	}

	// Negate the n-th operand.
	public boolean neg(int op) {
		assert op >= 0 && op < 3;
		return get(layout.NEG[0] + op);
	}

	public void neg(int op, boolean b) {
		assert op >= 0 && op < 3;
		set(layout.NEG[0] + op, b);
	}

	public VOP3_OMOD omod() {
		return VOP3_OMOD.values()[(int) get(layout.OMOD)];
	}

	public void omod(VOP3_OMOD v) {
		set(layout.OMOD, v.ordinal());
	}

	@Override
	public void run(ComputeData data) {
		// TODO Auto-generated method stub

	}

	public GenSrc src0() {
		int src = internalSrc0();
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src0(GenSrc s) {
		set(layout.SRC0, MCOperand.encodeRef(s));
	}

	public GenSrc src1() {
		int src = internalSrc1();
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src1(GenSrc s) {
		set(layout.SRC1, MCOperand.encodeRef(s));
	}

	public GenSrc src2() {
		int src = internalSrc2();
		return (GenSrc) MCOperand.decodeRef9(src);
	}

	public void src2(GenSrc s) {
		set(layout.SRC2, MCOperand.encodeRef(s));
	}

	public VGPR vDest() {
		return new VGPR((int) get(layout.VDEST));
	}

	public void vDest(VGPR v) {
		set(layout.VDEST, v.vgpr & 0xFF);
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

	@Override
	public int opcode() {
		return (int) get(layout.encode.op);
	}
}
