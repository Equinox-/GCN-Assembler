package com.pi.gcn.vmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenRef;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;

public class MUBUF extends MC_64 {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b111000, 18, 25);

	private static final int[] OFFSET = { 0, 12 };

	private static final int OFFEN = 12;

	private static final int IDEXN = 13;

	private static final int GLC = 14;

	private static final int LDS = 15;

	private static final int[] ADDR_SRC = { 32, 40 };

	private static final int[] DATA = { 40, 48 };
	private static final int[] RES_CONST_SRC = { 48, 53 };
	private static final int TFE = 55;

	private static final int[] BASE_OFFSET = { 56, 64 };

	public static boolean valid(ByteBuffer b) {
		int bb = b.getInt(b.position());
		return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
	}

	public MUBUF(ByteBuffer insn) {
		super(insn);
	}

	public MUBUF(int opcode) {
		super(LAYOUT.make(opcode));
	}

	public GenSrc addrSrc() {
		return new VGPR((int) get(ADDR_SRC));
	}

	public GenSrc baseOffset() {
		int ref = (int) get(BASE_OFFSET);
		if (ref >= 209)
			return null;
		return (GenSrc) MCOperand.decodeRef8(ref);
	}

	@Override
	public void decode(List<String> s) {
	}

	@Override
	public void encodeInternal(List<String> out) {
	}

	@Override
	public String format() {
		return null;
	}

	public boolean glc() {
		return get(GLC);
	}

	public boolean idexn() {
		return get(IDEXN);
	}

	public boolean LDS() {
		return get(LDS);
	}

	public boolean offen() {
		return get(OFFEN);
	}

	public int offset() {
		return (int) get(OFFSET);
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	public GenSrc resourceConstantSource() {
		return new SGPR((int) get(RES_CONST_SRC));
	}

	@Override
	public void run(ComputeData data) {
	}

	public boolean SLC() {
		return get(17);
	}

	public boolean TFE() {
		return get(TFE);
	}

	public GenRef vData() {
		return new VGPR((int) get(DATA));
	}
}
