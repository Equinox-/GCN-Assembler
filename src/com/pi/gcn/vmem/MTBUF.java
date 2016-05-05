package com.pi.gcn.vmem;

import java.nio.ByteBuffer;

import com.pi.gcn.OpcodeLayout;

public class MTBUF {
	public static class MTBUF_VI extends MTBUF_Base {
		public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b111010, 15, 19);

		public static boolean valid(ByteBuffer b) {
			int bb = b.getInt(b.position());
			return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
		}

		public MTBUF_VI(ByteBuffer insn) {
			super(insn);
		}

		public MTBUF_VI(int opcode) {
			super(LAYOUT.make(opcode));
		}

		@Override
		public int opcode() {
			return (int) get(LAYOUT.op);
		}
	}

	public static class MTBUF_SCI extends MTBUF_Base {
		public static final OpcodeLayout LAYOUT = new OpcodeLayout(26, 0b111010, 16, 19);

		public static boolean valid(ByteBuffer b) {
			int bb = b.getInt(b.position());
			return (bb >>> LAYOUT.idShift) == LAYOUT.idValue;
		}

		public MTBUF_SCI(ByteBuffer insn) {
			super(insn);
		}

		public MTBUF_SCI(int opcode) {
			super(LAYOUT.make(opcode));
		}

		@Override
		public int opcode() {
			return (int) get(LAYOUT.op);
		}
	}
}
