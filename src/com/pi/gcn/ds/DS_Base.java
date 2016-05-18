package com.pi.gcn.ds;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.base.MC_64;
import com.pi.gcn.base.RefEncoding;
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

	public void addrSrc(VGPR d) {
		set(layout.DATA1, d.vgpr & 0xFF);
	}

	public GenSrc data0() {
		return new VGPR((int) get(layout.DATA0));
	}

	public void data0(VGPR d) {
		set(layout.DATA0, d.vgpr & 0xFF);
	}

	public GenSrc data1() {
		return new VGPR((int) get(layout.DATA1));
	}

	public void data1(VGPR d) {
		set(layout.DATA1, d.vgpr & 0xFF);
	}

	public GenDest dest() {
		return new VGPR((int) get(layout.DEST));
	}

	public void dest(VGPR d) {
		set(layout.DEST, d.vgpr & 0xFF);
	}

	public boolean GDS() {
		return get(layout.GDS);
	}

	public void GDS(boolean b) {
		set(layout.GDS, b);
	}

	public int offset0() {
		return (int) get(layout.OFFSET0);
	}

	public void offset0(int v) {
		set(layout.OFFSET0, v);
	}

	public int offset1() {
		return (int) get(layout.OFFSET1);
	}

	public void offset1(int v) {
		set(layout.OFFSET1, v);
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
		GDS(Utils.removeIC(s, "GDS"));
		
		if (s.size() != 6) throw new IllegalArgumentException();
		offset0(Utils.decodeHex(s.get(0)));
		offset1(Utils.decodeHex(s.get(1)));
		addrSrc((VGPR) RefEncoding.decode(s.get(2)));
		data0((VGPR) RefEncoding.decode(s.get(3)));
		data1((VGPR) RefEncoding.decode(s.get(4)));
		dest((VGPR) RefEncoding.decode(s.get(5)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(Utils.encodeHex(offset0()));
		out.add(Utils.encodeHex(offset1()));
		out.add(RefEncoding.encode(addrSrc()));
		out.add(RefEncoding.encode(data0()));
		out.add(RefEncoding.encode(data1()));
		out.add(RefEncoding.encode(dest()));
		if (GDS())
			out.add("GDS");
	}

	@Override
	public String format() {
		return "off0 off1 addr data0 data1 dest";
	}
}
