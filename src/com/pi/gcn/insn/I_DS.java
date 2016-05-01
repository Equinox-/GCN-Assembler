package com.pi.gcn.insn;

import com.pi.gcn.genmem.DS;

public class I_DS {
	public static void register(InstructionSet s) {
		s.add("DS_ADD_U32", DS.class, 0);
		s.add("DS_SUB_U32", DS.class, 1);
		s.add("DS_RSUB_U32", DS.class, 2);
		s.add("DS_INC_U32", DS.class, 3);
		s.add("DS_DEC_U32", DS.class, 4);
		s.add("DS_MIN_I32", DS.class, 5);
		s.add("DS_MAX_I32", DS.class, 6);
		s.add("DS_MIN_U32", DS.class, 7);
		s.add("DS_MAX_U32", DS.class, 8);
		s.add("DS_AND_B32", DS.class, 9);
		s.add("DS_OR_B32", DS.class, 10);
		s.add("DS_XOR_B32", DS.class, 11);
		s.add("DS_MSKOR_B32", DS.class, 12);
		s.add("DS_WRITE_B32", DS.class, 13);
		s.add("DS_WRITE2_B32", DS.class, 14);
		s.add("DS_WRITE2ST64_B32", DS.class, 15);
		s.add("DS_CMPST_B32", DS.class, 16);
		s.add("DS_CMPST_F32", DS.class, 17);
		s.add("DS_MIN_F32", DS.class, 18);
		s.add("DS_MAX_F32", DS.class, 19);
		s.add("DS_NOP Do nothing.", DS.class, 20);
		s.add("DS_ADD_F32", DS.class, 21);
		s.add("DS_WRITE_B8", DS.class, 30);
		s.add("DS_WRITE_B16", DS.class, 31);
		s.add("DS_ADD_RTN_U32", DS.class, 32);
		s.add("DS_SUB_RTN_U32", DS.class, 33);
		s.add("DS_RSUB_RTN_U32", DS.class, 34);
		s.add("DS_INC_RTN_U32", DS.class, 35);
		s.add("DS_DEC_RTN_U32", DS.class, 36);
		s.add("DS_MIN_RTN_I32", DS.class, 37);
		s.add("DS_MAX_RTN_I32", DS.class, 38);
		s.add("DS_MIN_RTN_U32", DS.class, 39);
		s.add("DS_MAX_RTN_U32", DS.class, 40);
		s.add("DS_AND_RTN_B32", DS.class, 41);
		s.add("DS_OR_RTN_B32", DS.class, 42);
		s.add("DS_XOR_RTN_B32", DS.class, 43);
		s.add("DS_MSKOR_RTN_B32", DS.class, 44);
		s.add("DS_WRXCHG_RTN_B32", DS.class, 45);
		s.add("DS_WRXCHG2_RTN_B32", DS.class, 46);
		s.add("DS_WRXCHG2ST64_RTN_B32", DS.class, 47);
		s.add("DS_CMPST_RTN_B32", DS.class, 48);
		s.add("DS_CMPST_RTN_F32", DS.class, 49);
		s.add("DS_MIN_RTN_F32", DS.class, 50);
		s.add("DS_MAX_RTN_F32", DS.class, 51);
		s.add("DS_WRAP_RTN_B32", DS.class, 52);
		s.add("reserved.", DS.class, 53);
		s.add("DS_SWIZZLE_B32", DS.class, 61);
		s.add("DS_READ_B32", DS.class, 54);
		s.add("DS_READ2_B32", DS.class, 55);
		s.add("DS_READ2ST64_B32", DS.class, 56);
		s.add("DS_READ_I8", DS.class, 57);
		s.add("DS_READ_U8", DS.class, 58);
		s.add("DS_READ_I16", DS.class, 59);
		s.add("DS_READ_U16", DS.class, 60);
		s.add("DS_PERMUTE_B32", DS.class, 62);
		s.add("DS_BPERMUTE_B32", DS.class, 63);
		s.add("DS_ADD_U64", DS.class, 64);
		s.add("DS_SUB_U64", DS.class, 65);
		s.add("DS_RSUB_U64", DS.class, 66);
		s.add("DS_INC_U64", DS.class, 67);
		s.add("DS_DEC_U64", DS.class, 68);
		s.add("DS_MIN_I64", DS.class, 69);
		s.add("DS_MAX_I64", DS.class, 70);
		s.add("DS_MIN_U64", DS.class, 71);
		s.add("DS_MAX_U64", DS.class, 72);
		s.add("DS_AND_B64", DS.class, 73);
		s.add("DS_OR_B64", DS.class, 74);
		s.add("DS_XOR_B64", DS.class, 75);
		s.add("DS_MSKOR_B64", DS.class, 76);
		s.add("DS_WRITE_B64", DS.class, 77);
		s.add("DS_WRITE2_B64", DS.class, 78);
		s.add("DS_WRITE2ST64_B64", DS.class, 79);
		s.add("DS_CMPST_B64", DS.class, 80);
		s.add("DS_CMPST_F64", DS.class, 81);
		s.add("DS_MIN_F64", DS.class, 82);
		s.add("DS_MAX_F64", DS.class, 83);
		s.add("DS_ADD_RTN_U64", DS.class, 96);
		s.add("DS_SUB_RTN_U64", DS.class, 97);
		s.add("DS_RSUB_RTN_U64", DS.class, 98);
		s.add("DS_INC_RTN_U64", DS.class, 99);
		s.add("DS_DEC_RTN_U64", DS.class, 100);
		s.add("DS_MIN_RTN_I64", DS.class, 101);
		s.add("DS_MAX_RTN_I64", DS.class, 102);
		s.add("DS_MIN_RTN_U64", DS.class, 103);
		s.add("DS_MAX_RTN_U64", DS.class, 104);
		s.add("DS_AND_RTN_B64", DS.class, 105);
		s.add("DS_OR_RTN_B64", DS.class, 106);
		s.add("DS_XOR_RTN_B64", DS.class, 107);
		s.add("DS_MSKOR_RTN_B64", DS.class, 108);
		s.add("DS_WRXCHG_RTN_B64", DS.class, 109);
		s.add("DS_WRXCHG2_RTN_B64", DS.class, 110);
		s.add("DS_WRXCHG2ST64_RTN_B64", DS.class, 111);
		s.add("DS_CMPST_RTN_B64", DS.class, 112);
		s.add("DS_CMPST_RTN_F64", DS.class, 113);
		s.add("DS_MIN_RTN_F64", DS.class, 114);
		s.add("DS_MAX_RTN_F64", DS.class, 115);
		s.add("DS_READ_B64", DS.class, 118);
		s.add("DS_READ2_B64", DS.class, 119);
		s.add("DS_READ2ST64_B64", DS.class, 120);
		s.add("DS_CONDXCHG32_RTN_B64", DS.class, 126);
		s.add("DS_ADD_SRC2_U32", DS.class, 128);
		s.add("DS_SUB_SRC2_U32", DS.class, 129);
		s.add("DS_RSUB_SRC2_U32", DS.class, 130);
		s.add("DS_INC_SRC2_U32", DS.class, 131);
		s.add("DS_DEC_SRC2_U32", DS.class, 132);
		s.add("DS_MIN_SRC2_I32", DS.class, 133);
		s.add("DS_MAX_SRC2_I32", DS.class, 134);
		s.add("DS_MIN_SRC2_U32", DS.class, 135);
		s.add("DS_MAX_SRC2_U32", DS.class, 136);
		s.add("DS_AND_SRC2_B32", DS.class, 137);
		s.add("DS_OR_SRC2_B32", DS.class, 138);
		s.add("DS_XOR_SRC2_B32", DS.class, 139);
		s.add("DS_WRITE_SRC2_B32", DS.class, 140);
		s.add("DS_MIN_SRC2_F32", DS.class, 146);
		s.add("DS_MAX_SRC2_F32", DS.class, 147);
		s.add("DS_GWS_SEMA_RELEASE_ALL", DS.class, 152);
		s.add("DS_GWS_INIT", DS.class, 153);
		s.add("DS_GWS_SEMA_V", DS.class, 154);
		s.add("DS_GWS_SEMA_BR", DS.class, 155);
		s.add("DS_GWS_SEMA_P", DS.class, 156);
		s.add("DS_GWS_BARRIER", DS.class, 157);
		s.add("DS_CONSUME", DS.class, 189);
		s.add("DS_APPEND", DS.class, 190);
		s.add("DS_ORDERED_COUNT", DS.class, 191);
		s.add("DS_ADD_SRC2_U64", DS.class, 192);
		s.add("DS_SUB_SRC2_U64", DS.class, 193);
		s.add("DS_RSUB_SRC2_U64", DS.class, 194);
		s.add("DS_INC_SRC2_U64", DS.class, 195);
		s.add("DS_DEC_SRC2_U64", DS.class, 196);
		s.add("DS_MIN_SRC2_I64", DS.class, 197);
		s.add("DS_MAX_SRC2_I64", DS.class, 198);
		s.add("DS_MIN_SRC2_U64", DS.class, 199);
		s.add("DS_MAX_SRC2_U64", DS.class, 200);
		s.add("DS_AND_SRC2_B64", DS.class, 201);
		s.add("DS_OR_SRC2_B64", DS.class, 202);
		s.add("DS_XOR_SRC2_B64", DS.class, 203);
		s.add("DS_WRITE_SRC2_B64", DS.class, 204);
		s.add("DS_MIN_SRC2_F64", DS.class, 210);
		s.add("DS_MAX_SRC2_F64", DS.class, 211);
		s.add("DS_WRITE_B96", DS.class, 222);
		s.add("DS_WRITE_B128", DS.class, 223);
		s.add("DS_CONDXCHG32_RTN_B128", DS.class, 253);
		s.add("DS_READ_B96", DS.class, 254);
		s.add("DS_READ_B128", DS.class, 255);
	}
}