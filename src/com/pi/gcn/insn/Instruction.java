package com.pi.gcn.insn;

import java.nio.ByteBuffer;

import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC;

public class Instruction {
	final Class<? extends MC> format;
	private final int opcode;
	private final OpcodeLayout layout;
	final String name;
	private final String[] documentation;

	Instruction(final String name, Class<? extends MC> format, int opcode, OpcodeLayout layout, String[] doc) {
		this.format = format;
		this.opcode = opcode;
		this.layout = layout;
		this.name = name;
		if (layout.maxOpcode() < opcode)
			throw new RuntimeException(
					format.getName() + " can only handle opcodes up to 0x" + Integer.toHexString(layout.maxOpcode())
							+ "; " + name() + "'s opcode is 0x" + Integer.toHexString(opcode));
		this.documentation = doc;
	}

	public String name() {
		return name;
	}

	public MC create(ByteBuffer isa) {
		try {
			return format.getConstructor(ByteBuffer.class).newInstance(isa);
		} catch (Exception e) {
			return null;
		}
	}

	public MC createBlank() {
		try {
			return format.getConstructor(int.class).newInstance(opcode);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean valid(int insn) {
		return layout.matches(insn, opcode);
	}

	public int opcode() {
		return opcode;
	}

	public String[] document() {
		return documentation;
	}
}
