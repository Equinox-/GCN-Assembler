package com.pi.gcn.encode;

import java.util.ArrayList;
import java.util.List;

import com.pi.gcn.base.MC;
import com.pi.gcn.insn.Instruction;
import com.pi.gcn.insn.InstructionSet;

public class Lines_Mem {
	public static List<MC> fileToMem(InstructionSet insnSet, String file, boolean ignoreDecodeError) {
		return linesToMem(insnSet, file.split("(\r|)\n"), ignoreDecodeError);
	}

	public static List<MC> linesToMem(InstructionSet insnSet, String[] lines, boolean ignoreDecodeError) {
		List<MC> code = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			int cm = line.indexOf("//");
			if (cm >= 0)
				line = line.substring(0, cm).trim();
			String[] chu = line.split("[ \t]");
			List<String> base = new ArrayList<>(chu.length);
			for (String s : chu)
				base.add(s);
			MC mc = null;
			try {
				Instruction set = insnSet.valueOf(chu[0].toUpperCase());
				base.remove(0);
				mc = set.createBlank();
			} catch (Exception e) {
				boolean good = false;
				try {
					Class<?> clazz = Class.forName(chu[0]);
					int opcode = Integer.parseInt(chu[1]);
					mc = (MC) clazz.getConstructor(int.class).newInstance(opcode);
					base.remove(1);
					base.remove(0);
					good = true;
				} catch (Exception ex) {
				}
				if (!good)
					throw new RuntimeException(e);
			}
			if (mc != null) {
				try {
					mc.decode(base);
				} catch (Exception e) {
					if (!ignoreDecodeError)
						throw e;
				}
				code.add(mc);
			}
		}
		return code;
	}
}
