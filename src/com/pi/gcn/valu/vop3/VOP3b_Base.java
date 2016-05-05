package com.pi.gcn.valu.vop3;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;

public abstract class VOP3b_Base extends VOP3_Base {
	public VOP3b_Base(ByteBuffer insn, VOP3_Layout layout) {
		super(insn, layout);
	}

	public VOP3b_Base(long insn, VOP3_Layout layout) {
		super(insn, layout);
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
		return new SGPR((int) get(layout.SDEST));
	}

	public void sDest(SGPR s) {
		set(layout.SDEST, s.sgpr & 0xFF);
	}
}
