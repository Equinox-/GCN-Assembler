package com.pi.gcn.encode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import com.pi.gcn.base.MC;

public class Mem_ISA {
	public static void memToISA(List<MC> gcn, ByteBuffer isa) {
		for (MC m : gcn)
			m.write(isa);
	}

	public static byte[] memToISA(List<MC> gcn, ByteOrder order) {
		// Up to two longs per instruction.
		ByteBuffer tmp = ByteBuffer.allocate(gcn.size() * 2 * 8).order(order);
		memToISA(gcn, tmp);
		tmp.flip();
		byte[] out = new byte[tmp.limit()];
		tmp.get(out);
		return out;
	}
}
