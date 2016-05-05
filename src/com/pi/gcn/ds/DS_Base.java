package com.pi.gcn.ds;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.base.MC_64;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenDest;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.VGPR;

public class DS_Base extends MC_64 {
	private final DS_Layout layout;

	public DS_Base(ByteBuffer b, DS_Layout layout) {
		super(b);
		this.layout = layout;
	}

	public DS_Base(long insn, DS_Layout layout) {
		super(insn);
		this.layout = layout;
	}

	public GenSrc addrSrc() {
		return new VGPR((int) get(layout.ADDR_SRC));
	}

	public GenSrc data0() {
		return new VGPR((int) get(layout.DATA0));
	}

	public GenSrc data1() {
		return new VGPR((int) get(layout.DATA1));
	}

	public GenDest dest() {
		return new VGPR((int) get(layout.DEST));
	}

	public boolean GDS() {
		return get(layout.GDS);
	}

	public int offset0() {
		return (int) get(layout.OFFSET0);
	}

	public int offset1() {
		return (int) get(layout.OFFSET1);
	}

	@Override
	public int opcode() {
		return (int) get(layout.encoding.op);
	}

	@Override
	public void run(ComputeData data) {
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
}
