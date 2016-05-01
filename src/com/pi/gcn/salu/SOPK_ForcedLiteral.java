package com.pi.gcn.salu;

import java.nio.ByteBuffer;

public class SOPK_ForcedLiteral extends SOPK {

	public SOPK_ForcedLiteral(ByteBuffer insn) {
		super(insn);
	}

	public SOPK_ForcedLiteral(int opcode) {
		super(opcode);
	}

	@Override
	public boolean hasLiteral() {
		return true;
	}
}
