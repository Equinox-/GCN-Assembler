package com.pi.gcn.data;

public class ComputeData {
	public int[] sgpr = new int[256];
	public int[] vgpr = new int[256];

	private int[] registers = new int[Register32.values().length];

	public int readRegister(Register32 r) {
		return registers[r.ordinal()];
	}

	public long readRegister(Register64 r) {
		return readRegister(r.low) | (((long) readRegister(r.high)) << 32);
	}

	public void writeRegister(Register32 r, int v) {
		registers[r.ordinal()] = v;
	}

	public void writeRegister(Register64 r, long v) {
		writeRegister(r.low, (int) (v & 0xFFFFFFFF));
		writeRegister(r.high, (int) ((v >> 32) & 0xFFFFFFFF));
	}
}
