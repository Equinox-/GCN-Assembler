package com.pi.gcn.genmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenDest;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.VGPR;

public class DS extends MC_64 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b110110, 17, 25);

	private static final int[] OFFSET0 = { 0, 8 };
	private static final int[] OFFSET1 = { 8, 16 };
	private static final int GDS = 16;
	private static final int[] ADDR_SRC = { 32, 40 };
	private static final int[] DATA0 = { 40, 48 };
	private static final int[] DATA1 = { 48, 56 };
	private static final int[] DEST = { 56, 64 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public DS(ByteBuffer b) {
		super(b);
	}

	public DS(int opcode) {
		super(LAYOUT.make(opcode));
	}

	public GenSrc addrSrc() {
		return new VGPR((int) get(ADDR_SRC));
	}

	public GenSrc data0() {
		return new VGPR((int) get(DATA0));
	}

	public GenSrc data1() {
		return new VGPR((int) get(DATA1));
	}

	@Override
	public void decode(List<String> s) {
	}

	public GenDest dest() {
		return new VGPR((int) get(DEST));
	}

	@Override
	public void encodeInternal(List<String> out) {
	}

	@Override
	public String format() {
		return null;
	}

	public boolean GDS() {
		return get(GDS);
	}

	public int offset0() {
		return (int) get(OFFSET0);
	}

	public int offset1() {
		return (int) get(OFFSET1);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}
}
