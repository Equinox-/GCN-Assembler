package com.pi.gcn.valu.vop3;

import java.nio.ByteBuffer;

import com.pi.gcn.OpcodeLayout;

public class VOP3 {
	public static class VOP3a_VI extends VOP3a_Base {
		private static final VOP3_Layout LAYOUT_VOP3 = VOP3_Layout.LAYOUT_VI;

		public static final OpcodeLayout LAYOUT = LAYOUT_VOP3.encode;

		public VOP3a_VI(ByteBuffer insn) {
			super(insn, LAYOUT_VOP3);
		}

		public VOP3a_VI(int op) {
			super(LAYOUT.make(op), LAYOUT_VOP3);
		}
	}

	public static class VOP3b_VI extends VOP3b_Base {
		private static final VOP3_Layout LAYOUT_VOP3 = VOP3_Layout.LAYOUT_VI;

		public static final OpcodeLayout LAYOUT = LAYOUT_VOP3.encode;

		public VOP3b_VI(ByteBuffer insn) {
			super(insn, LAYOUT_VOP3);
		}

		public VOP3b_VI(int op) {
			super(LAYOUT.make(op), LAYOUT_VOP3);
		}
	}

	public static class VOP3a_SCI extends VOP3a_Base {
		private static final VOP3_Layout LAYOUT_VOP3 = VOP3_Layout.LAYOUT_SCI;

		public static final OpcodeLayout LAYOUT = LAYOUT_VOP3.encode;

		public VOP3a_SCI(ByteBuffer insn) {
			super(insn, LAYOUT_VOP3);
		}

		public VOP3a_SCI(int op) {
			super(LAYOUT.make(op), LAYOUT_VOP3);
		}
	}

	public static class VOP3b_SCI extends VOP3b_Base {
		private static final VOP3_Layout LAYOUT_VOP3 = VOP3_Layout.LAYOUT_SCI;

		public static final OpcodeLayout LAYOUT = LAYOUT_VOP3.encode;

		public VOP3b_SCI(ByteBuffer insn) {
			super(insn, LAYOUT_VOP3);
		}

		public VOP3b_SCI(int op) {
			super(LAYOUT.make(op), LAYOUT_VOP3);
		}
	}
}
