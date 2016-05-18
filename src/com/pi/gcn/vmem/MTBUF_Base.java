package com.pi.gcn.vmem;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.base.RefEncoding;
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

	private static final int GLC = 14;

	private static final int IDXEN = 13;

	private static final int OFFEN = 12;

	private static final int SLC = 54;

	private static final int TFE = 55;

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
	public void data(VGPR v) {
		set(DATA, v.vgpr & 0xFF);
	}

	public TBUF_DataFormat dataFormat() {
		return TBUF_DataFormat.values()[(int) get(DATA_FORMAT)];
	}
	
	public void dataFormat(TBUF_DataFormat f) {
		set(DATA_FORMAT, f.ordinal());
	}

	@Override
	public void decode(List<String> s) {
		offen(Utils.removeIC(s, "OFFEN"));
		idxen(Utils.removeIC(s, "IDXEN"));
		GLC(Utils.removeIC(s, "GLC"));
		SLC(Utils.removeIC(s, "SLC"));
		TFE(Utils.removeIC(s, "TFE"));
	}

	@Override
	public void encodeInternal(List<String> s) {
		s.add(Utils.encodeHex(offset()));
		s.add(dataFormat().name());
		s.add(numFormat().name());
		s.add(RefEncoding.encode(addrSrc()));
		s.add(RefEncoding.encode(data()));
		s.add(RefEncoding.encode(resourceConstantSource()));
		s.add(RefEncoding.encode(baseOffset()));

		if (offen())
			s.add("OFFEN");
		if (idxen())
			s.add("IDXEN");
		if (GLC())
			s.add("GLC");
		if (SLC())
			s.add("SLC");
		if (TFE())
			s.add("TFE");
	}

	@Override
	public String format() {
		return "offset dataFormat numFormat addrSrc data rscSrc baseOff";
	}

	public boolean GLC() {
		return get(GLC);
	}

	public void GLC(boolean b) {
		set(GLC, b);
	}

	public boolean idxen() {
		return get(IDXEN);
	}

	public void idxen(boolean b) {
		set(IDXEN, b);
	}

	public TBUF_NumberFormat numFormat() {
		return TBUF_NumberFormat.values()[(int) get(NUM_FORMAT)];
	}

	public void numFormat(TBUF_NumberFormat f) {
		set(NUM_FORMAT, f.ordinal());
	}

	public boolean offen() {
		return get(OFFEN);
	}

	public void offen(boolean b) {
		set(OFFEN, b);
	}

	public int offset() {
		return (int) get(OFFSET);
	}

	public void offset(int v) {
		set(OFFSET, v);
	}

	public SGPR resourceConstantSource() {
		return new SGPR((int) get(RES_CONST_SRC));
	}

	public void resourceConstantSource(SGPR s) {
		set(RES_CONST_SRC, s.sgpr & 0xFF);
	}

	@Override
	public void run(ComputeData data) {
	}

	public boolean SLC() {
		return get(SLC);
	}
	
	public void SLC(boolean b) {
		set(SLC, b);
	}

	public boolean TFE() {
		return get(TFE);
	}
	
	public void TFE(boolean b) {
		set(TFE, b);
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
