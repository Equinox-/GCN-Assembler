package com.pi.gcn.base;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.pi.gcn.data.ComputeData;
import com.pi.gcn.insn.Instruction;
import com.pi.gcn.insn.InstructionSet;

public abstract class MC {
	protected long insn;

	public static boolean getBit(long insn, int bit) {
		return ((insn >>> bit) & 1) != 0;
	}

	public static long getBits(long insn, int low, int high) {
		int size = high - low;
		long mask = ((1 << size) - 1);
		return (insn >>> low) & mask;
	}

	public static long setBits(long insn, int low, int high, long valu) {
		int size = high - low;
		long mask = ((1 << size) - 1);
		return (insn & ~(mask << low)) | ((valu & mask) << low);
	}

	public MC(int insn) {
		this.insn = insn & 0xFFFFFFFFL;
	}

	public MC(long insn) {
		this.insn = insn;
	}

	public abstract void decode(List<String> s);

	public String[] encode(InstructionSet set) {
		List<String> out = new ArrayList<>();
		Instruction insn = instruction(set);
		if (insn == null) {
			out.add(getClass().getName());
			out.add(Integer.toString(opcode()));
		} else {
			out.add(insn.name());
		}
		encodeInternal(out);
		return out.toArray(new String[out.size()]);
	}

	protected abstract void encodeInternal(List<String> out);

	public abstract String format();

	protected boolean get(int bit) {
		return getBit(insn, bit);
	}

	protected long get(int[] p) {
		return getBits(insn, p[0], p[1]);
	}

	public Instruction instruction(InstructionSet set) {
		for (Instruction s : set.values())
			if (s.valid((int) insn))
				return s;
		return null;
	}

	public abstract int opcode();

	public abstract void run(ComputeData data);

	protected void set(int bit, boolean b) {
		long mask = 1L << bit;
		insn = (insn & ~mask) | (b ? mask : 0);
	}

	protected void set(int[] p, int valu) {
		int size = p[1] - p[0];
		long mask = ((1 << size) - 1);
		insn = (insn & ~(mask << p[0])) | ((valu & mask) << p[0]);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String[] tokens = encode(InstructionSet.lastCreated);
		for (int i = 0; i < tokens.length; i++) {
			if (i > 0)
				sb.append(' ');
			sb.append(tokens[i]);
		}
		return sb.toString();
	}

	public abstract void write(ByteBuffer b);
	
	public abstract int sizeof();
}
