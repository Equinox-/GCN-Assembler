package com.pi.gcn.valu;

import java.nio.ByteBuffer;

public class VOP2_ForcedLiteral extends VOP2 {

	public VOP2_ForcedLiteral(ByteBuffer b) {
		super(b);
	}

	public VOP2_ForcedLiteral(int opcode) {
		super(opcode);
	}

	@Override
	public boolean hasLiteral() {
		return true;
	}
}
