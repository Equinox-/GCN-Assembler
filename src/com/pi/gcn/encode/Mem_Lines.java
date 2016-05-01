package com.pi.gcn.encode;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.base.MC;
import com.pi.gcn.insn.Instruction;
import com.pi.gcn.insn.InstructionSet;

public class Mem_Lines {
	public static String[] memToLines(InstructionSet insnSet, List<MC> mem, boolean document, boolean raw_insn) {
		String[] out = new String[mem.size()];
		int len = 0;
		for (int i = 0; i < mem.size(); i++) {
			out[i] = mem.get(i).toString();
			len = Math.max(len, out[i].length());
		}
		if (raw_insn) {
			int olen = len;
			ByteBuffer tmp = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < mem.size(); i++) {
				tmp.position(0);
				mem.get(i).write(tmp);
				out[i] = Utils.padString(out[i], olen) + " \t // ";
				for (int lk = 0; lk < tmp.position(); lk += 4) {
					if (lk > 0)
						out[i] += ",";
					out[i] += Utils.padNumeral(Integer.toBinaryString(tmp.getInt(lk)), 32);
				}
				len = Math.max(len, out[i].length());
			}
		}
		if (document) {
			int olen = len;
			for (int i = 0; i < mem.size(); i++) {
				out[i] = Utils.padString(out[i], olen) + " \t" + (raw_insn ? "" : "// ") + mem.get(i).format();
				len = Math.max(len, out[i].length());
			}
			olen = len;
			for (int i = 0; i < mem.size(); i++) {
				Instruction s = mem.get(i).instruction(insnSet);
				out[i] = Utils.padString(out[i], olen) + " \t " + (s != null ? Arrays.toString(s.document()) : "????");
				len = Math.max(len, out[i].length());
			}
		}
		return out;
	}

	public static String memToFile(InstructionSet insnSet, List<MC> mem, boolean document, boolean raw_insn) {
		String[] lines = memToLines(insnSet, mem, document, raw_insn);
		StringBuilder out = new StringBuilder(lines.length * 20);
		for (int i = 0; i < lines.length; i++) {
			if (i > 0)
				out.append(System.getProperty("line.separator", "\n"));
			out.append(lines[i]);
		}
		return out.toString();
	}
}
