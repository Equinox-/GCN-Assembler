package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.genmem.FLAT;

public class I_FLAT {
	public static void register(InstructionSet s) {
		/* Begin FLAT */
		/* LOAD */
		s.add("FLAT_LOAD_UBYTE", FLAT.class, new MultiOp(0x8, 0x10));
		s.add("FLAT_LOAD_SBYTE", FLAT.class, new MultiOp(0x9,0x11));
		s.add("FLAT_LOAD_USHORT", FLAT.class, new MultiOp(0xA, 0x12));
		s.add("FLAT_LOAD_SSHORT", FLAT.class, new MultiOp(0xB, 0x13));
		s.add("FLAT_LOAD_DWORD", FLAT.class, new MultiOp(0xC, 0x14));
		s.add("FLAT_LOAD_DWORDX2", FLAT.class, new MultiOp(0xD, 0x15));
		s.add("FLAT_LOAD_DWORDX4", FLAT.class, new MultiOp(0xE, 0x17));
		s.add("FLAT_LOAD_DWORDX3", FLAT.class, new MultiOp(0xF, 0x16));
		/* STORE */
		s.add("FLAT_STORE_BYTE", FLAT.class, 0x18);
		s.add("FLAT_STORE_SHORT", FLAT.class, 0x1A);
		s.add("FLAT_STORE_DWORD", FLAT.class, 0x1C);
		s.add("FLAT_STORE_DWORDX2", FLAT.class, 0x1D);
		s.add("FLAT_STORE_DWORDX4", FLAT.class, new MultiOp(0x1E, 0x1F));
		s.add("FLAT_STORE_DWORDX3", FLAT.class, new MultiOp(0x1F, 0x1E));
		/* ATOMIC */
		s.add("FLAT_ATOMIC_SWAP", FLAT.class, new MultiOp(0x30, 0x40));
		s.add("FLAT_ATOMIC_CMPSWAP", FLAT.class, new MultiOp(0x31, 0x41));
		s.add("FLAT_ATOMIC_ADD", FLAT.class, new MultiOp(0x32, 0x42));
		s.add("FLAT_ATOMIC_SUB", FLAT.class, new MultiOp(0x33, 0x43));
		s.add("FLAT_ATOMIC_SMIN", FLAT.class, new MultiOp(0x35, 0x44));
		s.add("FLAT_ATOMIC_UMIN", FLAT.class, new MultiOp(0x36, 0x45));
		s.add("FLAT_ATOMIC_SMAX", FLAT.class, new MultiOp(0x37, 0x46));
		s.add("FLAT_ATOMIC_UMAX", FLAT.class, new MultiOp(0x38, 0x47));
		s.add("FLAT_ATOMIC_AND", FLAT.class, new MultiOp(0x39, 0x48));
		s.add("FLAT_ATOMIC_OR", FLAT.class, new MultiOp(0x3A, 0x49));
		s.add("FLAT_ATOMIC_XOR", FLAT.class, new MultiOp(0x3B, 0x4A));
		s.add("FLAT_ATOMIC_INC", FLAT.class, new MultiOp(0x3C, 0x4B));
		s.add("FLAT_ATOMIC_DEC", FLAT.class, new MultiOp(0x3D, 0x4C));
		s.add("FLAT_ATOMIC_SWAP_X2", FLAT.class, new MultiOp(0x50, 0x60));
		s.add("FLAT_ATOMIC_CMPSWAP_X2", FLAT.class, new MultiOp(0x51, 0x61));
		s.add("FLAT_ATOMIC_ADD_X2", FLAT.class, new MultiOp(0x52, 0x62));
		s.add("FLAT_ATOMIC_SUB_X2", FLAT.class, new MultiOp(0x53, 0x63));
		s.add("FLAT_ATOMIC_SMIN_X2", FLAT.class, new MultiOp(0x55, 0x64));
		s.add("FLAT_ATOMIC_UMIN_X2", FLAT.class, new MultiOp(0x56, 0x65));
		s.add("FLAT_ATOMIC_SMAX_X2", FLAT.class, new MultiOp(0x57, 0x66));
		s.add("FLAT_ATOMIC_UMAX_X2", FLAT.class, new MultiOp(0x58, 0x67));
		s.add("FLAT_ATOMIC_AND_X2", FLAT.class, new MultiOp(0x59, 0x68));
		s.add("FLAT_ATOMIC_OR_X2", FLAT.class, new MultiOp(0x5A, 0x69));
		s.add("FLAT_ATOMIC_XOR_X2", FLAT.class, new MultiOp(0x5B, 0x6A));
		s.add("FLAT_ATOMIC_INC_X2", FLAT.class, new MultiOp(0x5C, 0x6B));
		s.add("FLAT_ATOMIC_DEC_X2", FLAT.class, new MultiOp(0x5D, 0x6C));
		/* End FLAT */
	}
}
