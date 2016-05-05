package com.pi.gcn.smem;

import java.util.List;

import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.SGPR;

public class SMEM_Common {
	public static final String FORMAT = "data base offset";
	
	public static void decode(SMEM_Base bs, List<String> tokens) {
		if (tokens.size() != 3)
			throw new IllegalArgumentException("Needs 3 tokens");
		bs.data((SGPR) RefEncoding.decode(tokens.get(0)));
		bs.base((SGPR) RefEncoding.decode(tokens.get(1)));
		try {
			bs.offset_SGPR((SGPR) RefEncoding.decode(tokens.get(2)));
		} catch (Exception e) {
			bs.offset_MEM(Integer.parseInt(tokens.get(2)));
		}
	}

	public static void encode(SMEM_Base bs, List<String> out) {
		out.add(RefEncoding.encode(bs.data()));
		out.add(RefEncoding.encode(bs.base()));
		if (bs.IMM()) {
			out.add("0x" + Integer.toString(bs.offset_MEM(), 16));
		} else {
			out.add(RefEncoding.encode(bs.offset_SGPR()));
		}
	}
}
