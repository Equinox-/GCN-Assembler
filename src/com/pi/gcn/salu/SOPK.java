package com.pi.gcn.salu;

import java.nio.ByteBuffer;
import java.util.List;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MCOperand;
import com.pi.gcn.base.MC_32_Lit;
import com.pi.gcn.base.RefEncoding;
import com.pi.gcn.data.ComputeData;
import com.pi.gcn.data.GenDest;

public class SOPK extends MC_32_Lit {
	public static final OpcodeLayout LAYOUT = new OpcodeLayout(28, 0b1011, 23, 28);

	private static final int[] DEST = { 16, 23 };

	private static final int[] SIMM = { 0, 16 };

	public static boolean valid(ByteBuffer b) {
		if (SOP1.valid(b) || SOPC.valid(b) || SOPP.valid(b))
			return false;
		final int insn = b.getInt(b.position());
		return (insn >>> 28) == 0b1011;
	}

	public SOPK(ByteBuffer insn) {
		super(insn);
	}

	public SOPK(int opcode) {
		super((int) LAYOUT.make(opcode));
	}

	@Override
	public String format() {
		return "simm dest";
	}

	protected int internalDest() {
		return (int) get(DEST);
	}

	public GenDest dest() {
		return (GenDest) MCOperand.decodeRef7(internalDest());
	}

	@Override
	public void decode(List<String> s) {
		if (s.size() != 2)
			throw new IllegalArgumentException("Must have 2 args");
		int sv = Integer.parseInt(s.get(0));
		if (sv > 0xFFFF || sv < 0)
			throw new IllegalArgumentException("simm must be between [0, " + 0xFFFF + "]");
		simm((short) sv);
		dest((GenDest) RefEncoding.decode(s.get(1)));
	}

	@Override
	public void encodeInternal(List<String> out) {
		out.add(Integer.toString(simm() & 0xFFFF));
		out.add(RefEncoding.encode(dest()));
	}

	@Override
	public int opcode() {
		return (int) get(LAYOUT.op);
	}

	@Override
	public void run(ComputeData data) {
	}

	public short simm() {
		return (short) get(SIMM);
	}

	public void dest(GenDest s) {
		set(DEST, MCOperand.encodeRef(s));
	}

	public void simm(short s) {
		set(SIMM, s);
	}

	@Override
	public boolean hasLiteral() {
		return false;
	}
}
