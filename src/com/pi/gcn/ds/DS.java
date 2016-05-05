package com.pi.gcn.ds;

import java.nio.ByteBuffer;

import com.pi.gcn.OpcodeLayout;

public class DS {
	public static class DS_VI extends DS_Base {
		private static final DS_Layout LAYOUT_DS = DS_Layout.LAYOUT_VI;
		public static final OpcodeLayout LAYOUT = LAYOUT_DS.encoding;

		public DS_VI(ByteBuffer b) {
			super(b, LAYOUT_DS);
		}

		public DS_VI(int op) {
			super(LAYOUT.make(op), LAYOUT_DS);
		}
	}

	public static class DS_SCI extends DS_Base {
		private static final DS_Layout LAYOUT_DS = DS_Layout.LAYOUT_SCI;
		public static final OpcodeLayout LAYOUT = LAYOUT_DS.encoding;

		public DS_SCI(ByteBuffer b) {
			super(b, LAYOUT_DS);
		}

		public DS_SCI(int op) {
			super(LAYOUT.make(op), LAYOUT_DS);
		}
	}
}
