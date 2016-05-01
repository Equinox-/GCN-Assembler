package com.pi.gcn.insn;

import com.pi.gcn.valu.VOP1;

public class I_VOP1 {
	public static void register(InstructionSet s) {
		/* Begin VOP1 */
		s.add("V_BFREV_B32", VOP1.class, 0x2C, "D.u[31:0] = S0.u[0:31]", "Bitfield reverse");
		s.add("V_CEIL_F16", VOP1.class, 0x45, "D.f16 = ceil(S0.f16)");
		s.add("V_CEIL_F32", VOP1.class, 0x1D, "D.f = ceil(S0.f)");
		s.add("V_CEIL_F64", VOP1.class, 0x18, "D.d = ceil(S0.d)");
		s.add("V_CLREXCP", VOP1.class, 0x35, "Clear wave's SIMD exception state");
		s.add("V_COS_F16", VOP1.class, 0x4A, "D.f16 = cos(S0.f16 * 2 * PI)", "-256 < S0.f16 < 256");
		s.add("V_COS_F32", VOP1.class, 0x2A, "D.f = cos(S0.f * 2 * PI)", "-256 < S0.f < 256");
		s.add("V_CVT_F16_F32", VOP1.class, 0xA, "D.f16 = flt32_to_flt16(S0.f)");
		s.add("V_CVT_F16_I16", VOP1.class, 0x3A, "D.f16 = int16_to_flt16(S0.i16)");
		s.add("V_CVT_F16_U16", VOP1.class, 0x39, "D.f16 = uint16_to_flt16(S0.u16)");
		s.add("V_CVT_F32_F16", VOP1.class, 0xB, "D.f = flt16_to_flt32(S0.f16)");
		s.add("V_CVT_F32_F64", VOP1.class, 0xF, "D.f = (float) S0.d");
		s.add("V_CVT_F32_I32", VOP1.class, 0x5, "D.f = (float) S0.i");
		s.add("V_CVT_F32_U32", VOP1.class, 0x6, "D.f = (float) S0.u");
		s.add("V_CVT_F32_UBYTE0", VOP1.class, 0x11, "D.f = UINT2FLT(S0.u[7:0])");
		s.add("V_CVT_F32_UBYTE1", VOP1.class, 0x12, "D.f = UINT2FLT(S0.u[15:8])");
		s.add("V_CVT_F32_UBYTE2", VOP1.class, 0x13, "D.f = UINT2FLT(S0.u[23:16])");
		s.add("V_CVT_F32_UBYTE3", VOP1.class, 0x14, "D.f = UINT2FLT(S0.u[31:24])");
		s.add("V_CVT_F64_F32", VOP1.class, 0x10, "D.d = (double) S0.f");
		s.add("V_CVT_F64_I32", VOP1.class, 0x4, "D.d = (double) S0.i");
		s.add("V_CVT_F64_U32", VOP1.class, 0x16, "D.d = (double) S0.u");
		s.add("V_CVT_FLR_I32_F32", VOP1.class, 0xD, "D.i = (int)floor(S0.f)");
		s.add("V_CVT_I16_F16", VOP1.class, 0x3C, "D.i16 = flt16_to_int16(S0.f16)");
		s.add("V_CVT_I32_F32", VOP1.class, 0x8, "D.i = (int) S0.f", "inf=max_int, NaN=0");
		s.add("V_CVT_I32_F64", VOP1.class, 0x3, "D.i = (int) S0.d", "inf=max_int, NaN=0");
		s.add("V_CVT_OFF_F32_I4", VOP1.class, 0xE, "signed S0[3:0] -> [-5, 0.4374]");
		s.add("V_CVT_RPI_I32_F32", VOP1.class, 0xC, "D.i = (int)floor(S0.f + 0.5)");
		s.add("V_CVT_U16_F16", VOP1.class, 0x3B, "D.u16 = flt16_to_uint16(S.f16)");
		s.add("V_CVT_U32_F32", VOP1.class, 0x7, "D.u = (uint) S0.f", "inf=max_int, NaN=0");
		s.add("V_CVT_U32_F64", VOP1.class, 0x15, "D.u = (uint) S0.d", "inf=max_int, NaN=0");
		s.add("V_EXP_F32", VOP1.class, 0x20, "D.f = 2 ** S0.f");
		s.add("V_EXP_F16", VOP1.class, 0x41, "D.f16 = 2 ** S0.f16");
		s.add("V_EXP_LEGACY_F32", VOP1.class, 0x4B, "D.f = pow(2, S0.f)");
		s.add("V_FFBH_I32", VOP1.class, 0x2F, "D.u = position of first bit different from sign in S0 from MSB");
		s.add("V_FFBH_U32", VOP1.class, 0x2D, "D.u = position of first bit=1 in S0 from MSB");
		s.add("V_FFBL_B32", VOP1.class, 0x2E, "D.u = position of first bit=1 in S0 from LSB");
		s.add("V_FLOOR_F16", VOP1.class, 0x44, "D.f16 = floor(S0.f16)");
		s.add("V_FLOOR_F32", VOP1.class, 0x1F, "D.f = floor(S0.f)");
		s.add("V_FLOOR_F64", VOP1.class, 0x1A, "D.d = floor(S0.d)");
		s.add("V_FRACT_F16", VOP1.class, 0x48, "D.f16 = S0.f16 - floor(S0.f16)");
		s.add("V_FRACT_F32", VOP1.class, 0x1B, "D.f = S0.f - floor(S0.f)");
		s.add("V_FRACT_F64", VOP1.class, 0x32, "D.d = S0.d - floor(S0.d)");
		s.add("V_FREXP_EXP_I16_F16", VOP1.class, 0x43, "D.i16 = exponent(S0.f16)", "inf,NaN=0");
		s.add("V_FREXP_EXP_I32_F32", VOP1.class, 0x33, "D.i = exponent(S0.f)", "inf,NaN=0");
		s.add("V_FREXP_EXP_I32_F64", VOP1.class, 0x30, "D.i = exponent(S0.d)", "inf,NaN=0");
		s.add("V_FREXP_MANT_F16", VOP1.class, 0x42, "D.f16 = mantissa(S0.f16)", "inf,NaN=S0.f16");
		s.add("V_FREXP_MANT_F32", VOP1.class, 0x34, "D.f = mantissa(S0.f)", "inf,NaN=S0.f");
		s.add("V_FREXP_MANT_F64", VOP1.class, 0x31, "D.d = mantissa(S0.d)", "inf,NaN=S0.d");
		s.add("V_LOG_F16", VOP1.class, 0x40, "D.f = ApproxLog2(S0.f16)");
		s.add("V_LOG_F32", VOP1.class, 0x21, "D.f = ApproxLog2(S0.f)");
		s.add("V_LOG_LEGACY_F32", VOP1.class, 0x4C, "D.f = log2(S0.f)");
		s.add("V_MOV_B32", VOP1.class, 0x1, "D.u = S0.u");
		s.add("V_MOVRELD_B32", VOP1.class, 0x36, "VGPR[D.u + M0.u] = VGPR[S0.u]");
		s.add("V_MOVRELS_B32", VOP1.class, 0x37, "VGPR[D.u] = VGPR[S0.u + M0.u]");
		s.add("V_MOVRELSD_B32", VOP1.class, 0x38, "VGPR[D.u + M0.u] = VGPR[S0.u + M0.u]");
		s.add("V_NOP", VOP1.class, 0x0, "Do nothing");
		s.add("V_NOT_B32", VOP1.class, 0x2B, "D.u = ~S0.u");
		s.add("V_RCP_F16", VOP1.class, 0x3D, "D.f16 = 1.0/S0.f16");
		s.add("V_RCP_F32", VOP1.class, 0x22, "D.f = 1.0/S0.f");
		s.add("V_RCP_F64", VOP1.class, 0x25, "D.d = 1.0/S0.d");
		s.add("V_RCP_IFLAG_F32", VOP1.class, 0x23, "D.f = 1.0 / S0.f", "Raises DIV_BY_ZERO");
		s.add("V_READFIRSTLANE_B32", VOP1.class, 0x2, "Copies LDS or VGPR S0 into SGPR D");
		s.add("V_RNDNE_F16", VOP1.class, 0x47, "D.f16 = round_nearest_even(S0.f16)");
		s.add("V_RNDNE_F32", VOP1.class, 0x1E, "D.f = round_nearest_even(S0.f)");
		s.add("V_RNDNE_F64", VOP1.class, 0x19, "D.d = round_nearest_even(S0.d)");
		s.add("V_RSQ_F16", VOP1.class, 0x3F, "D.f16 = 1/sqrt(S0.f16)");
		s.add("V_RSQ_F32", VOP1.class, 0x24, "D.f = 1/sqrt(S0.f)");
		s.add("V_RSQ_F64", VOP1.class, 0x26, "D.d = 1/sqrt(S0.d)");
		s.add("V_SIN_F16", VOP1.class, 0x49, "D.f16 = sin(S0.f16 * 2 * PI)", "-256 < S0.f16 < 256");
		s.add("V_SIN_F32", VOP1.class, 0x29, "D.f = sin(S0.f * 2 * PI)", "-256 < S0.f < 256");
		s.add("V_SQRT_F16", VOP1.class, 0x2E, "D.f16 = sqrt(S0.f16)");
		s.add("V_SQRT_F32", VOP1.class, 0x27, "D.f = sqrt(S0.f)");
		s.add("V_SQRT_F64", VOP1.class, 0x28, "D.d = sqrt(S0.d)");
		s.add("V_TRUNC_F16", VOP1.class, 0x46, "D.f16 = trunc(S0.f16)", "Round-to-zero semantics");
		s.add("V_TRUNC_F32", VOP1.class, 0x1C, "D.f = trunc(S0.f)", "Round-to-zero semantics");
		s.add("V_TRUNC_F64", VOP1.class, 0x17, "D.d = trunc(S0.d)", "Round-to-zero semantics");
		/* End VOP1 */
	}
}
