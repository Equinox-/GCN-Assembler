package com.pi.gcn.insn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pi.gcn.MultiOp;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC;
import com.pi.gcn.proc.Processor;

public class InstructionSet {
	public static InstructionSet lastCreated;

	private final Map<String, Instruction> instructions = new HashMap<>();

	public final Processor processor;

	public InstructionSet(Processor p) {
		this.processor = p;
		I_FLAT.register(this);
		I_MUBUF.register(this);
		I_SMEM.register(this);
		I_SOP1.register(this);
		I_SOP2.register(this);
		I_SOPC.register(this);
		I_SOPK.register(this);
		I_SOPP.register(this);
		I_VINTRP.register(this);
		I_VOP1.register(this);
		I_VOP2.register(this);
		I_VOP3a.register(this);
		I_VOP3b.register(this);
		I_VOPC.register(this);
		I_DS.register(this);

		I_VOP3Gen.generate(this);
		lastCreated = this;
	}

	public Instruction valueOf(String s) {
		if (!instructions.containsKey(s))
			throw new ArrayIndexOutOfBoundsException(s + " doesn't exist");
		return instructions.get(s);
	}

	public Instruction add(Instruction s) {
		if (instructions.containsKey(s.name))
			throw new IllegalArgumentException("Name exists");
		instructions.put(s.name, s);
		return s;
	}

	public Instruction add(final String name, Class<? extends MC> format, int opcode) {
		return add(name, format, opcode, new String[0]);
	}

	public Instruction add(final String name, Class<? extends MC> format, int opcode, OpcodeLayout layout,
			String... doc) {
		return add(new Instruction(name, format, opcode, layout, doc));
	}

	public Instruction add(final String name, Class<? extends MC> format, int opcode, String... doc) {
		return add(name, format, opcode, layoutFor(format), doc);
	}

	public Instruction add(final String name, Class<? extends MC> format, MultiOp opcode) {
		return add(name, format, opcode, new String[0]);
	}

	public Instruction add(final String name, Class<? extends MC> format, MultiOp opcode, OpcodeLayout layout,
			String... doc) {
		return add(new Instruction(name, format, opcode.opcodeFor(processor), layout, doc));
	}

	public Instruction add(final String name, Class<? extends MC> format, MultiOp opcode, String... doc) {
		return add(name, format, opcode, layoutFor(format), doc);
	}

	public static OpcodeLayout layoutFor(Class<? extends MC> format) {
		try {
			return (OpcodeLayout) format.getField("LAYOUT").get(null);
		} catch (Exception e) {
			throw new RuntimeException("No LAYOUT for " + format.getName(), e);
		}
	}

	public Collection<Instruction> values() {
		return instructions.values();
	}

	public Collection<Instruction> values(Class<? extends MC> type) {
		List<Instruction> l = new ArrayList<>();
		for (Instruction s : values()) {
			if (type.isAssignableFrom(s.format)) {
				l.add(s);
			}
		}
		return l;
	}

	public Instruction find(int insn) {
		for (Instruction s : values())
			if (s.valid(insn))
				return s;
		return null;
	}
}
