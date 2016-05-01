package com.pi.gcn.smem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.SGPR;

public class SMEM extends MC_64 {
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

	public SGPR base() {
		return new SGPR((int) get(BASE));
	}

	public void base(SGPR b) {
		set(BASE, b.sgpr & 0xFF);
	}

	public SGPR data() {
		return new SGPR((int) get(DATA));
	}

	public void data(SGPR d) {
		set(DATA, d.sgpr & 0xFF);
	}

	@Override
	public void decode(List<String> tokens) {
		GLC(Utils.removeIC(tokens, "GLC"));
		IMM(Utils.removeIC(tokens,  "IMM"));
		if (tokens.size() != 3)
			throw new IllegalArgumentException("Needs 3 tokens");
		data((SGPR) RefEncoding.decode(tokens.get(0)));
		base((SGPR) RefEncoding.decode(tokens.get(1)));
		try {
			offset(((SGPR) RefEncoding.decode(tokens.get(2))).sgpr & 0xFF);
			IMM(false);
		} catch (Exception e) {
			offset(Integer.parseInt(tokens.get(2)));
			IMM(true);
		}
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(data()));
		out.add(RefEncoding.encode(base()));
		if (IMM()) {
			out.add(Integer.toString(offset()));
		} else {
			out.add(RefEncoding.encode(new SGPR(offset())));
		}
		if (GLC())
			out.add("GLC");
		if (IMM())
			out.add("IMM");
	}
	
	@Override
	public String format() {
		return "data base offset";
	}

	public boolean GLC() {
		return get(GLC);
	}

	public void GLC(boolean b) {
		set(GLC, b);
	}

	public boolean IMM() {
		return get(IMM);
	}

	public void IMM(boolean b) {
		set(IMM, b);
	}

	public int offset() {
		return (int) get(OFFSET);
	}

	public void offset(int v) {
		set(OFFSET, v);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
