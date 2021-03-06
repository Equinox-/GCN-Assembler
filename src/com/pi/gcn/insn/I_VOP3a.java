package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_SCI;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_VI;
import com.pi.gcn.valu.vop3.VOP3a_Base;

public class I_VOP3a {
	public static void register(InstructionSet s) {
		Class<? extends VOP3a_Base> vop3AFormat = s.processor.has(SubtargetFeature.VolcanicIslands) ? VOP3a_VI.class
				: VOP3a_SCI.class;

		/* Begin VOP3a instructions */
		s.add("V_ADD_F64", vop3AFormat, new MultiOp(0x164, 0x280), "D.d = S0.d + S1.d");
		s.add("V_ALIGNBIT_B32", vop3AFormat, new MultiOp(0x14E, 0x1CE), "D.u = ({S0,S1} >> S2.u[4:0]) & 0xFFFFFFFF",
				"Select 32 bits from 64 bits");
		s.add("V_ALIGNBYTE_B32", vop3AFormat, new MultiOp(0x14F, 0x1CF),
				"D.u = ({S0,S1} >> {8*S2.u[4:0])) & 0xFFFFFFFF", "Select 4 bytes from 8 bytes");
		s.add("V_ASHRREV_I64", vop3AFormat, new MultiOp(0x163, 0x291), "D.u64 = signext(S1.u64) >> S0.u[5:0]",
				"Signed shift");
		s.add("V_BCNT_U32_B32", vop3AFormat, new MultiOp(-1, 0x28B), "D.u = CountOneBits(S0.u) + S1.u");
		s.add("V_BFE_I32", vop3AFormat, new MultiOp(0x149, 0x1C9), "D.i = (S0.i>>S1.u[4:0]) & ((1 << S2.u[4:0]) - 1)",
				"Bitfield extract: [S1, S1+S2] from S0");
		s.add("V_BFE_U32", vop3AFormat, new MultiOp(0x148, 0x1C8), "D.u = (S0.u>>S1.u[4:0]) & ((1 << S2.u[4:0]) - 1)",
				"Bitfield extract: [S1, S1+S2] from S0");
		s.add("V_BFI_B32", vop3AFormat, new MultiOp(0x14A, 0x1CA), "D.u = (S0.u & S1.u) | (~S0.u & S2.u)",
				"Bitfield insert: S0=mask, S1,S2=data");
		s.add("V_BFM_B32", vop3AFormat, new MultiOp(-1, 0x293), "D.u = ((1 << S0.u[4:0]) - 1) << S1.u[4:0]",
				"Bitfield mask: [S1, S1+S0]");
		s.add("V_CUBEID_F32", vop3AFormat, new MultiOp(0x144, 0x1C4), "D.f = cubeface(S0.f, S1.f, S2.f)");
		s.add("V_CUBEMA_F32", vop3AFormat, new MultiOp(0x147, 0x1C7), "D.f = 2*max_mag(S0.f, S1.f, S2.f)");
		s.add("V_CUBESC_F32", vop3AFormat, new MultiOp(0x145, 0x1C5), "D.f = cube_tex_s(S0.f, S1.f, S2.f)");
		s.add("V_CUBETC_F32", vop3AFormat, new MultiOp(0x146, 0x1C6), "D.f = cube_tex_t(S0.f, S1.f, S2.f)");
		s.add("V_CVT_PK_U8_F32", vop3AFormat, new MultiOp(0x15E, 0x1DD), "Float to uint8");
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			s.add("V_CVT_PK_I16_I32", vop3AFormat, 0x298, "DX int32 to int16");
			s.add("V_CVT_PK_U16_U32", vop3AFormat, 0x297, "DX11 uint32 to uint16");
			s.add("V_CVT_PKACCUM_U8_F32", vop3AFormat, 0x1F0);
			s.add("V_CVT_PKNORM_I16_F32", vop3AFormat, 0x294);
			s.add("V_CVT_PKNORM_U16_F32", vop3AFormat, 0x295);
			s.add("V_CVT_PKRTZ_F16_F32", vop3AFormat, 0x296);
			s.add("V_DIV_FIXUP_F16", vop3AFormat, 0x1EF);
			s.add("V_FMA_F16", vop3AFormat, 0x1EE, "D.f16 = S0.f16 * S1.f16 + S2.f16");
		}
		s.add("V_DIV_FIXUP_F32", vop3AFormat, new MultiOp(0x15F, 0x1DE));
		s.add("V_DIV_FIXUP_F64", vop3AFormat, new MultiOp(0x160, 0x1DF));
		s.add("V_DIV_FMAS_F32", vop3AFormat, new MultiOp(0x16F, 0x1E2));
		s.add("V_DIV_FMAS_F64", vop3AFormat, new MultiOp(0x170, 0x1E3));
		s.add("V_FMA_F32", vop3AFormat, new MultiOp(0x14B, 0x1CB), "D.f = S0.f * S1.f + S2.f");
		s.add("V_FMA_F64", vop3AFormat, new MultiOp(0x14C, 0x1CC), "D.d = S0.d * S1.d + S2.d");
		s.add("V_LDEXP_F32", vop3AFormat, new MultiOp(-1, 0x288), "D.f = S0.f * pow(2, S1.i)");
		s.add("V_LDEXP_F64", vop3AFormat, new MultiOp(0x168, 0x284));
		s.add("V_LERP_U8", vop3AFormat, new MultiOp(0x14D, 0x1CD));

		if (s.processor.has(SubtargetFeature.SouthernIslands) || s.processor.has(SubtargetFeature.SeaIslands)) {
			s.add("V_LSHL_B64", vop3AFormat, 0x161, "D = S0.u << S1.u[4:0]");
			s.add("V_LSHR_B64", vop3AFormat, 0x162, "D = S0.u >> S1.u[4:0]");
		}
		s.add("V_MAD_F32", vop3AFormat, new MultiOp(0x141, 0x1C1), "D.f = S0.f * S1.f + S2.f");
		s.add("V_MAD_I32_I24", vop3AFormat, new MultiOp(0x142, 0x1C2), "D.i = S0.i[23:0] * S1.i[23:0] + S2.i");
		s.add("V_MAD_I64_I32", vop3AFormat, new MultiOp(0x177, 0x1E9), "D.i64 = S0.i32 * S1.i32 + S2.i64", "VCC = Overflow bit");
		s.add("V_MAD_LEGACY_F32", vop3AFormat, new MultiOp(0x140, 0x1C0), "D.f = S0.f * S1.f + S2.f", "DX9 rules");
		s.add("V_MAD_U32_U24", vop3AFormat, new MultiOp(0x143, 0x1C3), "D.u = S0.u[23:0] * S1.u[23:0] + S2.u[31:0]");
		s.add("V_MAD_U64_U32", vop3AFormat, new MultiOp(0x176, 0x1E8), "D.u64 = S0.u32 * S1.u32 + S2.u64", "VCC = Overflow bit");
		
		s.add("V_MAX_F64", vop3AFormat, new MultiOp(0x167, 0x283), "D.d = max(S0.d, S1.d)");
		s.add("V_MAX3_F32", vop3AFormat, new MultiOp(0x154, 0x1D3), "D.f = max(S0.f, S1.f, S2.f)");
		s.add("V_MAX3_I32", vop3AFormat, new MultiOp(0x155, 0x1D4), "D.i = max(S0.i, S1.i, S2.i)");
		s.add("V_MAX3_U32", vop3AFormat, new MultiOp(0x156, 0x1D5), "D.u = max(S0.u, S1.u, S2.u)");
		s.add("V_MED3_F32", vop3AFormat, new MultiOp(0x157, 0x1D6), "D.f = median(S0.f, S1.f, S2.f)");
		s.add("V_MED3_I32", vop3AFormat, new MultiOp(0x158, 0x1D7), "D.i = median(S0.i, S1.i, S2.i)");
		s.add("V_MED3_U32", vop3AFormat, new MultiOp(0x159, 0x1D8), "D.u = median(S0.u, S1.u, S2.u)");
		s.add("V_MIN_F64", vop3AFormat, new MultiOp(0x166, 0x282), "D.d = min(S0.d, S1.d)");
		s.add("V_MIN3_F32", vop3AFormat, new MultiOp(0x151, 0x1D0), "D.f = min(S0.f, S1.f, S2.f)");
		s.add("V_MIN3_I32", vop3AFormat, new MultiOp(0x152, 0x1D1), "D.i = min(S0.i, S1.i, S2.i)");
		s.add("V_MIN3_U32", vop3AFormat, new MultiOp(0x153, 0x1D2), "D.u = min(S0.u, S1.u, S2.u)");
		s.add("V_MQSAD_PK_U16_U8", vop3AFormat, new MultiOp(0x173, 0x1E6));
		s.add("V_MQSAD_U32_U8", vop3AFormat, new MultiOp(0x175, 0x1E7));
		s.add("V_MSAD_U8", vop3AFormat, new MultiOp(0x171, 0x1E4));
		s.add("V_MUL_F64", vop3AFormat, new MultiOp(0x165, 0x281), "D.d = S0.d * S1.d");
		s.add("V_MUL_HI_I32", vop3AFormat, new MultiOp(0x16C, 0x287), "D.i = (S0.i * S1.i) >> 32");
		s.add("V_MUL_HI_U32", vop3AFormat, new MultiOp(0x16A, 0x286), "D.u = (S0.u * S1.u) >> 32");
		s.add("V_MUL_LO_U32", vop3AFormat, new MultiOp(0x169, 0x285), "D.u = S0.u * S1.u");
		if (s.processor.has(SubtargetFeature.VolcanicIslands))
			s.alias("V_MUL_LO_I32", "V_MUL_LO_U32");
		else
			s.add("V_MUL_LO_I32", vop3AFormat, 0x16B, "D.i = S0.i * S1.i");
		
		if (s.processor.has(SubtargetFeature.SouthernIslands) || s.processor.has(SubtargetFeature.SeaIslands)) {
			s.add("V_MULLIT_F32", vop3AFormat, 0x150, "D.f = S0.f * S1.f", "4 channel mult");
		}

		s.add("V_QSAD_PK_U16_U8", vop3AFormat, new MultiOp(0x172, 0x1E5));
		s.add("V_SAD_HI_U8", vop3AFormat, new MultiOp(0x15B, 0x1DA));
		s.add("V_SAD_U8", vop3AFormat, new MultiOp(0x15A, 0x1D9));
		s.add("V_SAD_U16", vop3AFormat, new MultiOp(0x15C, 0x1DB));
		s.add("V_SAD_U32", vop3AFormat, new MultiOp(0x15D, 0x1DC));
		s.add("V_TRIG_PREOP_F64", vop3AFormat, new MultiOp(0x174, 0x292));
		
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			s.add("V_LSHLREV_B64", vop3AFormat, 0x28F, "D.u64 = S1.u64 << S0.u[5:0]");
			s.add("V_LSHLRREV_B64", vop3AFormat, 0x290, "D.u64 = S1.u64 >> S0.u[5:0]");
			s.add("V_MAD_F16", vop3AFormat, 0x1EA, "D.f16 = S0.f16 * S1.f16 + S2.f16");
			s.add("V_MAD_I16", vop3AFormat, 0x1EC, "D.i16 = S0.i16 * S1.i16 + S2.i16");
			s.add("V_MAD_U16", vop3AFormat, 0x1EB, "D.u16 = S0.u16 * S1.u16 + S2.u16");
			s.add("V_MBCNT_LO_U32_B32", vop3AFormat, 0x28C, "D.u = CountOneBits(S0.u & ThreadMask) + S1.u");
			s.add("V_PERM_B32", vop3AFormat, 0x1ED, "Byte permute");
		}
		/* End VOP3a */
	}
}
