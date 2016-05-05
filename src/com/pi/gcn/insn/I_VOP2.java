package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.base.MC;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.valu.VOP2;
import com.pi.gcn.valu.VOP2_ForcedLiteral;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_VI;

public class I_VOP2 {
	private static void registerHalfPrecision(InstructionSet s) {
		s.add("V_ADD_F16", VOP2.class, 0x1F, "D.f16 = S0.f16 + S1.f16");
		s.add("V_SUB_F16", VOP2.class, 0x20, "D.f16 = S0.f16 - S1.f16");
		s.add("V_SUBREV_F16", VOP2.class, 0x21, "D.f16 = S1.f16 - S0.f16");
		s.add("V_MUL_F16", VOP2.class, 0x22, "D.f16 = S0.f16 * S1.f16");
		s.add("V_MAC_F16", VOP2.class, 0x23, "D.f16 = S0.f16 * S1.f16 + D.f16");
		s.add("V_MADMK_F16", VOP2_ForcedLiteral.class, 0x24, "D.f16 = S0.f16 * K.f16 + S1.f16");
		s.add("V_MADAK_F16", VOP2_ForcedLiteral.class, 0x25, "D.f16 = S0.f16 * S1.f16 + K.f16");

		s.add("V_ADD_U16", VOP2.class, 0x26, "D.u16 = S0.u16 + S1.u16");
		s.add("V_SUB_U16", VOP2.class, 0x27, "D.u16 = S0.u16 - S1.u16");
		s.add("V_SUBRREV_U16", VOP2.class, 0x28, "D.u16 = S1.u16 - S0.u16");
		s.add("V_MUL_LO_U16", VOP2.class, 0x29, "D.u16 = S0.u16 * S1.u16");
		s.add("V_LSHLREV_B16", VOP2.class, 0x2A, "D.u[15:0] = S1.u[15:0] << S0.u[3:0]");
		s.add("V_LSHRREV_B16", VOP2.class, 0x2B, "D.u[15:0] = S1.u[15:0] >> S0.u[3:0]");
		s.add("V_ASHRREV_I16", VOP2.class, 0x2C, "D.i[15:0] = signext(S1.i[15:0]) >> S0.i[3:0]");
		s.add("V_MAX_F16", VOP2_ForcedLiteral.class, 0x2D, "D.f16 = max(S0.f16, S1.f16)");
		s.add("V_MIN_F16", VOP2.class, 0x2E, "D.f16 = min(S0.f16, S1.f16)");
		s.add("V_MAX_U16", VOP2.class, 0x2F, "D.u[15:0] = max(S0.u[15:0], S1.u[15:0])");
		s.add("V_MAX_I16", VOP2.class, 0x30, "D.i[15:0] = max(S0.i[15:0], S1.i[15:0])");
		s.add("V_MIN_U16", VOP2.class, 0x31, "D.u[15:0] = min(S0.u[15:0], S1.u[15:0])");
		s.add("V_MIN_I16", VOP2.class, 0x32, "D.i[15:0] = min(S0.i[15:0], S1.i[15:0])");
		s.add("V_LDEXP_F16", VOP2.class, 0x33, "D.f16 = S0.f16 * (2 ** S1.i16)");
	}

	public static void register(InstructionSet s) {
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			registerHalfPrecision(s);
		}
		/* Begin VOP2 */
		s.add("V_CNDMASK_B32", VOP2.class, 0x0, "D.u = VCC ? S1.u : S0.u");
		s.add("V_ADD_F32", VOP2.class, new MultiOp(0x3, 0x1), "D.f = S0.f + S1.f");
		s.add("V_SUB_F32", VOP2.class, new MultiOp(0x4, 0x2), "D.f = S0.f - S1.f");
		s.add("V_SUBREV_F32", VOP2.class, new MultiOp(0x5, 0x3), "D.f = S1.f - S0.f");
		s.add("V_MUL_LEGACY_F32", VOP2.class, new MultiOp(0x7, 0x4), "D.f = S0.f * S1.f", "DX9 Rules");
		s.add("V_MUL_F32", VOP2.class, new MultiOp(0x8, 0x5), "D.f = S0.f * S1.f");
		s.add("V_MUL_I32_I24", VOP2.class, new MultiOp(0x9, 0x6), "D.i = S0.i[23:0] * S1.i[23:0]");
		s.add("V_MUL_HI_I32_I24", VOP2.class, new MultiOp(0xA, 0x7), "D.i = (S0.i[23:0] * S1.i[23:0]) >> 32");
		s.add("V_MUL_U32_U24", VOP2.class, new MultiOp(0xB, 0x8), "D.u = S0.u[23:0] * S1.u[23:0]");
		s.add("V_MUL_HI_U32_U24", VOP2.class, new MultiOp(0xC, 0x9), "D.i = (S0.u[23:0] * S1.u[23:0]) >> 32");
		s.add("V_MIN_F32", VOP2.class, new MultiOp(0xF, 0xA), "D.f = min(S0.f, S1.f)");
		s.add("V_MAX_F32", VOP2.class, new MultiOp(0x10, 0xB), "D.f = max(S0.f, S1.f)");
		s.add("V_MIN_I32", VOP2.class, new MultiOp(0x11, 0xC), "D.i = min(S0.i, S1.i)");
		s.add("V_MAX_I32", VOP2.class, new MultiOp(0x12, 0xD), "D.i = max(S0.i, S1.i)");
		s.add("V_MIN_U32", VOP2.class, new MultiOp(0x13, 0xE), "D.u = min(S0.u, S1.u)");
		s.add("V_MAX_U32", VOP2.class, new MultiOp(0x14, 0xF), "D.u = max(S0.u, S1.u)");

		s.add("V_LSHRREV_B32", VOP2.class, new MultiOp(0x16, 0x10), "D.u = S1.u >> S1.u[4:0]");
		s.add("V_ASHRREV_I32", VOP2.class, new MultiOp(0x18, 0x11), "D.i = signext(S1.1) >> S0.i[4:0]");
		if (s.processor.has(SubtargetFeature.SouthernIslands) || s.processor.has(SubtargetFeature.SeaIslands))
			s.add("V_LSHL_B32", VOP2.class, 0x19, "D.u = S0.u << S1.u[4:0]");
		
		s.add("V_LSHLREV_B32", VOP2.class, new MultiOp(0x1A, 0x12), "D.u = S1.u << S0.u[4:0]");
		s.add("V_AND_B32", VOP2.class, new MultiOp(0x1B, 0x13), "D.u = S0.u & S1.u");
		s.add("V_OR_B32", VOP2.class, new MultiOp(0x1C, 0x14), "D.u = S0.u | S1.u");
		s.add("V_XOR_B32", VOP2.class, new MultiOp(0x1D, 0x15), "D.u = S0.u ^ S1.u");
		s.add("V_MAC_F32", VOP2.class, new MultiOp(0x1F, 0x16), "D.f = S0.f * S1.f + D.f");
		s.add("V_MADMK_F32", VOP2_ForcedLiteral.class, new MultiOp(0x20, 0x17), "D.f = S0.f * K + S1.f");
		s.add("V_MADAK_F32", VOP2_ForcedLiteral.class, new MultiOp(0x21, 0x18), "D.f = S0.f * S1.f + K");
		{
			String type = s.processor.has(SubtargetFeature.VolcanicIslands) ? "U" : "I";
			String alias = s.processor.has(SubtargetFeature.VolcanicIslands) ? "I" : "U";
			String typeLower = type.toLowerCase();
			s.add("V_ADD_" + type + "32", VOP2.class, new MultiOp(0x25, 0x19),
					"D." + typeLower + " = S0." + typeLower + " + S1." + typeLower, "VCC = carry out");
			s.alias("V_ADD_" + alias + "32", "V_ADD_" + type + "32");

			s.add("V_SUB_" + type + "32", VOP2.class, new MultiOp(0x26, 0x1A),
					"D." + typeLower + " = S0." + typeLower + " - S1." + typeLower,
					"VCC = S1." + typeLower + " > S0." + typeLower);
			s.alias("V_SUB_" + alias + "32", "V_SUB_" + type + "32");

			s.add("V_SUBREV_" + type + "32", VOP2.class, new MultiOp(0x27, 0x1B),
					"D." + typeLower + " = S1." + typeLower + " - S0." + typeLower,
					"VCC = S0." + typeLower + " > S1." + typeLower);
			s.alias("V_SUBREV_" + alias + "32", "V_SUBREV_" + type + "32");
		}
		s.add("V_ADDC_U32", VOP2.class, new MultiOp(0x28, 0x1C), "D.u = S0.u + S1.u + VCC", "VCC = carry out");
		s.add("V_SUBB_U32", VOP2.class, new MultiOp(0x29, 0x1D), "D.u = S0.u - S1.u - VCC", "VCC = carry out");
		s.add("V_SUBBREV_U32", VOP2.class, new MultiOp(0x2A, 0x1E), "D.u = S1.u - S0.u - VCC", "VCC = carry out");
		/* End VOP2/VOP3a */

		{
			Class<? extends MC> lane = s.processor.has(SubtargetFeature.VolcanicIslands) ? VOP3a_VI.class : VOP2.class;
			s.add("V_READLANE_B32", lane, new MultiOp(0x1, 0x289), "Copy VGPR or LDS (S0) to SGPR (D)");
			s.add("V_WRITELANE_B32", lane, new MultiOp(0x2, 0x28A), "Write value into one VGPR one lane");
		}
	}
}
