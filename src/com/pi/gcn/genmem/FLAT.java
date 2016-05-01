package com.pi.gcn.genmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.VGPR;

public class FLAT extends MC_64 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b110111, 18, 25);

	private static final int[] ADDR_SRC = { 32, 40 };

	private static final int[] SRC_DATA = { 40, 48 };

	private static final int[] DEST = { 56, 64 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public FLAT(ByteBuffer insn) {
		super(insn);
	}

	public FLAT(int opcode) {
		super(LAYOUT.make(opcode));
	}

	public VGPR addrSource() {
		return new VGPR((int) get(ADDR_SRC));
	}

	public void addrSource(VGPR v) {
		set(ADDR_SRC, v.vgpr & 0xFF);
	}

	@Override
	public void decode(List<String> tokens) {
		GLC(Utils.removeIC(tokens, "GLC"));
		SLC(Utils.removeIC(tokens, "SLC"));
		TFE(Utils.removeIC(tokens, "TFE"));

		if (tokens.size() != 3)
			throw new IllegalArgumentException("Bad tokens remaining");

		addrSource(RefEncoding.decode(tokens.get(0), VGPR.class));
		srcData(RefEncoding.decode(tokens.get(1), VGPR.class));
		dest(RefEncoding.decode(tokens.get(2), VGPR.class));

	}

	@Override
	public String format() {
		return "addrSrc srcData dest";
	}

	public VGPR dest() {
		return new VGPR((int) get(DEST));
	}

	public void dest(VGPR v) {
		set(DEST, v.vgpr & 0xFF);
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(addrSource()));
		out.add(RefEncoding.encode(srcData()));
		out.add(RefEncoding.encode(dest()));
		if (GLC())
			out.add("GLC");
		if (SLC())
			out.add("SLC");
		if (TFE())
			out.add("TFE");
	}

	public boolean GLC() {
		return get(16);
	}

	public void GLC(boolean b) {
		set(16, b);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}

	public boolean SLC() {
		return get(17);
	}

	public void SLC(boolean b) {
		set(17, b);
	}

	public VGPR srcData() {
		return new VGPR((int) get(SRC_DATA));
	}

	public void srcData(VGPR v) {
		set(SRC_DATA, v.vgpr & 0xFF);
	}

	public boolean TFE() {
		return get(55);
	}

	public void TFE(boolean b) {
		set(55, b);
	}
}
