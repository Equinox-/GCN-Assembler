package com.pi.gcn.valu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.VGPR;

public class VOP3a extends VOP3 {
	private static final int[] ABS = { 8, 11 };

	public VOP3a(ByteBuffer insn) {
		super(insn);
	}

	public VOP3a(int opcode) {
		super(opcode);
	}

	public int abs() {
		return (int) get(ABS);
	}

	// take abs of n-th operand
	public boolean abs(int op) {
		assert op >= 0 && op < 3;
		return get(ABS[0] + op);
	}

	public void abs(int op, boolean b) {
		assert op >= 0 && op < 3;
		set(ABS[0] + op, b);
	}

	@Override
	public void decode(List<String> s) {
		for (int i = 0; i < 3; i++)
			abs(i, Utils.removeIC(s, "ABS" + i));
		decodeOpts(s);

		if (s.size() != 4)
			throw new IllegalArgumentException();

		vDest((VGPR) RefEncoding.decode(s.get(0)));
		src0((GenSrc) RefEncoding.decode(s.get(1)));
		src1((GenSrc) RefEncoding.decode(s.get(2)));
		src2((GenSrc) RefEncoding.decode(s.get(3)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(RefEncoding.encode(vDest()));
		out.add(RefEncoding.encode(src0()));
		out.add(RefEncoding.encode(src1()));
		out.add(RefEncoding.encode(src2()));

		for (int i = 0; i < 3; i++)
			if (abs(i))
				out.add("ABS" + i);
		encodeOpts(out);
	}
	
	@Override
	public String format() {
		return "dest src0 src1 src2";
	}
}
