package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;

public class VOP3b extends VOP3 {
	private static final int[] SDEST = { 8, 15 };

	public VOP3b(ByteBuffer insn) {
		super(insn);
	}

	public VOP3b(int opcode) {
		super(opcode);
	}

	@Override
	public void decode(List<String> s) {
		decodeOpts(s);

		if (s.size() != 5)
			throw new IllegalArgumentException();

		vDest((VGPR) RefEncoding.decode(s.get(0)));
		sDest((SGPR) RefEncoding.decode(s.get(1)));
		src0((GenSrc) RefEncoding.decode(s.get(2)));
		src1((GenSrc) RefEncoding.decode(s.get(3)));
		src2((GenSrc) RefEncoding.decode(s.get(4)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(vDest()));
		out.add(RefEncoding.encode(sDest()));
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(src1()));
		out.add(RefEncoding.encode(src2()));

		encodeOpts(out);
	}
	
	@Override
	public String format() {
		return "vDest sDest src0 src1 src2";
	}

	public SGPR sDest() {
		return new SGPR((int) get(SDEST));
	}

	public void sDest(SGPR s) {
		set(SDEST, s.sgpr & 0xFF);
	}
}
