package com.pi.gcn.smem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_32_Lit;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.SGPR;

public class SMRD extends MC_32_Lit implements SMEM_Base {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(27, 0b11000, 22, 27);

	private static final int[] BASE = { 9, 15 };

	private static final int[] DATA = { 15, 22 };

	private static final int[] OFFSET = { 0, 8 };
	public static final int IMM = 8;

	public static boolean valid(ByteBuffer b) {
		long bb = b.getLong(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SMRD(ByteBuffer b) {
		super(b);
	}

	public SMRD(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	@Override
	public SGPR base() {
		return new SGPR((int) get(BASE));
	}

	@Override
	public void base(SGPR b) {
		set(BASE, b.sgpr & 0xFF);
	}

	@Override
	public SGPR data() {
		return new SGPR((int) get(DATA));
	}

	@Override
	public void data(SGPR d) {
		set(DATA, d.sgpr & 0xFF);
	}

	@Override
	public void decode(List<String> tokens) {
		SMEM_Common.decode(this, tokens);
	}

	@Override
	public void encodeInternal(List<String> out) {
		SMEM_Common.encode(this, out);
	}

	@Override
	public String format() {
		return SMEM_Common.FORMAT;
	}

	@Override
	public boolean IMM() {
		return get(IMM);
	}

	@Override
	public void IMM(boolean b) {
		if (IMM() != b) {
			if (b) {
				// Becoming IMM:
				set(OFFSET, literal);
			} else {
				// Becoming literal
				literal = (int) get(OFFSET);
				set(OFFSET, 0xFF);
			}
		}
		set(IMM, b);
	}

	@Override
	public int offset_raw() {
		if (IMM())
			return (int) get(OFFSET);
		else
			return literal;
	}

	@Override
	public void offset_raw(int r) {
		if (IMM())
			set(OFFSET, r);
		else {
			literal = r;
			set(OFFSET, 0xFF);
		}
	}

	@Override
	public boolean hasLiteral() {
		return !IMM();
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
