package com.pi.gcn.vmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;

public abstract class MTBUF_Base extends MC_64 {
	private static final int[] OFFSET = { 0, 12 };

	private static final int[] DATA_FORMAT = { 19, 23 };

	private static final int[] NUM_FORMAT = { 23, 26 };

	private static final int[] ADDR_SRC = { 32, 40 };

	private static final int[] DATA = { 40, 48 };

	private static final int[] RES_CONST_SRC = { 48, 53 };

	private static final int[] BASE_OFFSET = { 56, 64 };

	public MTBUF_Base(ByteBuffer insn) {
		super(insn);
	}

	public MTBUF_Base(long insn) {
		super(insn);
	}

	public VGPR addrSrc() {
		return new VGPR((int) get(ADDR_SRC));
	}

	public GenSrc baseOffset() {
		int ref = (int) get(BASE_OFFSET);
		if (ref >= 209)
			return null;
		return (GenSrc) MCOperand.decodeRef8(ref);
	}

	public VGPR data() {
		return new VGPR((int) get(DATA));
	}

	public TBUF_DataFormat dataFormat() {
		return TBUF_DataFormat.values()[(int) get(DATA_FORMAT)];
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

	public boolean GLC() {
		return get(14);
	}

	public boolean idxen() {
		return get(13);
	}

	public TBUF_NumberFormat numFormat() {
		return TBUF_NumberFormat.values()[(int) get(NUM_FORMAT)];
	}

	public boolean offen() {
		return get(12);
	}

	public int offset() {
		return (int) get(OFFSET);
	}

	public SGPR resourceConstantSource() {
		return new SGPR((int) get(RES_CONST_SRC));
	}

	@Override
	public void run(ComputeData data) {
	}

	public boolean SLC() {
		return get(54);
	}

	public boolean TFE() {
		return get(55);
	}

	public static enum TBUF_DataFormat {
		INVALID,
		F8,
		F16,
		F8_8,
		F32,
		F16_16,
		F10_11_11,
		RESERVED_7,
		F10_10_10_2,
		F2_10_10_10,
		F8_8_8_8,
		F32_32,
		F16_16_16_16,
		F32_32_32,
		F32_32_32_32,
		RESERVED_15
	}

	public static enum TBUF_NumberFormat {
		UNORM,
		SNORM,
		USCALED,
		SSCALED,
		UINT,
		SINT,
		RESERVED,
		FLOAT
	}
}
