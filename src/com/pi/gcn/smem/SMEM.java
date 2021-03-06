package com.pi.gcn.smem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.SGPR;

public class SMEM extends MC_64 implements SMEM_Base {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b110000, 18, 26);

	private static final int[] BASE = { 0, 6 };

	private static final int[] DATA = { 6, 12 };

	private static final int[] OFFSET = { 32, 52 };

	public static final int GLC = 16;
	public static final int IMM = 17;

	public static boolean valid(ByteBuffer b) {
		long bb = b.getLong(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public SMEM(ByteBuffer b) {
		super(b);
	}

	public SMEM(int opcode) {
		super(LAYOUT.make(opcode));
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
		GLC(Utils.removeIC(tokens, "GLC"));
		SMEM_Common.decode(this, tokens);
	}

	@Override
	public void encodeInternal(List<String> out) {
		SMEM_Common.encode(this, out);
		if (GLC())
			out.add("GLC");
	}

	@Override
	public String format() {
		return SMEM_Common.FORMAT;
	}

	public boolean GLC() {
		return get(GLC);
	}

	public void GLC(boolean b) {
		set(GLC, b);
	}

	@Override
	public boolean IMM() {
		return get(IMM);
	}

	@Override
	public void IMM(boolean b) {
		set(IMM, b);
	}

	@Override
	public int offset_raw() {
		return (int) get(OFFSET);
	}

	@Override
	public void offset_raw(int r) {
		set(OFFSET, r);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
