package com.pi.gcn.salu;

import java.nio.ByteBuffer;

import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_32_Lit;
import com.pi.gcn.data.GenDest;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.LiteralConstant;

public abstract class SALU extends MC_32_Lit {
	public SALU(ByteBuffer insn) {
		super(insn);
	}

	public SALU(long op) {
		super((int) op);
	}

	public GenDest dest() {
		int dest = internalDest();
		if (dest > 127)
			return null;
		return (GenDest) MCOperand.decodeRef7(dest);
	}

	@Override
	public boolean hasLiteral() {
		int src0 = 0;
		int src1 = 0;
		try {
			src0 = internalSrc0();
		} catch (UnsupportedOperationException e) {
		}
		try {
			src1 = internalSrc1();
		} catch (UnsupportedOperationException e) {
		}
		return src0 == MCOperand.LITERAL_CONSTANT || src1 == MCOperand.LITERAL_CONSTANT;
	}

	protected int internalDest() {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support dest");
	}

	protected int internalSrc0() {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support src1");
	}

	protected int internalSrc1() {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support src2");
	}

	@Override
	public abstract int opcode();

	public short simm() {
		throw new UnsupportedOperationException(getClass().getSimpleName() + " does not support simm");
	}

	public GenSrc src0() {
		int src = internalSrc0();
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef8(src);
	}

	public GenSrc src1() {
		int src = internalSrc1();
		if (src == MCOperand.LITERAL_CONSTANT)
			return new LiteralConstant(literal);
		return (GenSrc) MCOperand.decodeRef8(src);
	}
}
