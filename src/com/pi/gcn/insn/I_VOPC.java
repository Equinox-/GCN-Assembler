package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.valu.VOPC;

public class I_VOPC {
	private static void reg_16_insn(InstructionSet s) {
		final String[] OP16_DU = { "0", "(S0 < S1)", "(S0 == S1)", "(S0 <= S1)", "(S0 > S1)", "(S0 <> S1)",
				"(S0 >= S1)", "(!isNaN(S0) && !isNaN(S1))", "(!isNaN(S0) || !isNaN(S1))", "!(S0 >= S1)", "!(S0 <> S1)",
				"!(S0 > S1)", "!(S0 <= S1)", "!(S0 == S1)", "!(S0 < S1)", "1" };
		final String[] OP16_CMP = { "F", "LT", "EQ", "LE", "GT", "LG", "GE", "O", "U", "NGE", "NLG", "NGT", "NLE",
				"NEQ", "NLT", "TRU" };

		String[] OP16_SET;
		int[] OP16_SET_OFFSET;
		if (s.processor.has(SubtargetFeature.SeaIslands) || s.processor.has(SubtargetFeature.SouthernIslands)) {
			OP16_SET = new String[] { "V_CMP_{OP16}_F32", "V_CMPX_{OP16}_F32", "V_CMP_{OP16}_F64", "V_CMPX_{OP16}_F64",
					"V_CMPS_{OP16}_F32", "V_CMPSX_{OP16}_F32", "V_CMPS_{OP16}_F64", "V_CMPSX_{OP16}_F64" };
			OP16_SET_OFFSET = new int[] { 0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70 };
		} else {
			OP16_SET = new String[] { "V_CMP_{OP16}_F32", "V_CMPX_{OP16}_F32", "V_CMP_{OP16}_F64",
					"V_CMPX_{OP16}_F64" };
			OP16_SET_OFFSET = new int[] { 0x40, 0x50, 0x60, 0x70 };
		}

		for (int i = 0; i < OP16_SET.length; i++)
			for (int j = 0; j < OP16_CMP.length; j++) {
				final String name = OP16_SET[i].replace("{OP16}", OP16_CMP[j]);
				final int op = OP16_SET_OFFSET[i] + j;
				s.add(name, VOPC.class, op, "D.u = " + OP16_DU[j]);
			}
	}

	private static void reg_8_insn(InstructionSet s) {
		final String[] OP8_DU = { "0", "(S0 < S1)", "(S0 == S1)", "(S0 <= S1)", "(S0 > S1)", "(S0 <> S1)", "(S0 >= S1)",
				"1" };
		final String[] OP8_CMP = { "F", "LT", "EQ", "LE", "GT", "LG", "GE", "TRU" };
		final String[] OP8_SET = { "V_CMP_{OP8}_I32", "V_CMPX_{OP8}_I32", "V_CMP_{OP8}_I64", "V_CMPX_{OP8}_I64",
				"V_CMP_{OP8}_U32", "V_CMPX_{OP8}_U32", "V_CMP_{OP8}_U64", "V_CMPX_{OP8}_U64" };
		final int[] OP8_SET_OFFSET_CI = { 0x80, 0x90, 0xA0, 0xB0, 0xC0, 0xD0, 0xE0, 0xF0 };
		final int[] OP8_SET_OFFSET_VI = { 0xC0, 0xD0, 0xE0, 0xF0, 0xC8, 0xD8, 0xE8, 0xF8 };

		for (int i = 0; i < OP8_SET.length; i++)
			for (int j = 0; j < OP8_CMP.length; j++) {
				final String name = OP8_SET[i].replace("{OP8}", OP8_CMP[j]);
				final int op_CI = OP8_SET_OFFSET_CI[i] + j;
				final int op_VI = OP8_SET_OFFSET_VI[i] + j;
				s.add(name, VOPC.class, new MultiOp(op_CI, op_VI), "D.u = " + OP8_DU[j]);
			}
	}

	public static void register(InstructionSet s) {
		reg_16_insn(s);

		reg_8_insn(s);

		/* Begin colliding VOPC class instructions. */
		s.add("V_CMP_CLASS_F32", VOPC.class, new MultiOp(0x88, 0x10), "D = IEEE numeric class function S1.u performed on S0.f");
		s.add("V_CMPX_CLASS_F32", VOPC.class, new MultiOp(0x98, 0x11), "D = IEEE numeric class function S1.u performed on S0.f", "Write EXEC");
		s.add("V_CMP_CLASS_F64", VOPC.class, new MultiOp(0xA8, 0x12), "D = IEEE numeric class function S1.u performed on S0.f");
		s.add("V_CMPX_CLASS_F64", VOPC.class, new MultiOp(0xB8, 0x13), "D = IEEE numeric class function S1.u performed on S0.f", "Write EXEC");
		/* End colliding VOPC class instructions. */
	}
}
