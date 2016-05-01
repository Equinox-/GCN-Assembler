package com.pi.gcn.insn;

import com.pi.gcn.vmem.MUBUF;

public class I_MUBUF {
	public static void register(InstructionSet s) {
		/* Begin MUBUF */
		/* LOAD FORMAT */
		s.add("BUFFER_LOAD_FORMAT_X", MUBUF.class, 0x0);
		s.add("BUFFER_LOAD_FORMAT_XY", MUBUF.class, 0x1);
		s.add("BUFFER_LOAD_FORMAT_XYZ", MUBUF.class, 0x2);
		s.add("BUFFER_LOAD_FORMAT_XYZW", MUBUF.class, 0x3);
		/* STORE FORMAT */
		s.add("BUFFER_STORE_FORMAT_X", MUBUF.class, 0x4);
		s.add("BUFFER_STORE_FORMAT_XY", MUBUF.class, 0x5);
		s.add("BUFFER_STORE_FORMAT_XYZ", MUBUF.class, 0x6);
		s.add("BUFFER_STORE_FORMAT_XYZW", MUBUF.class, 0x7);
		/* LOAD */
		s.add("BUFFER_LOAD_FORMAT_D16_X", MUBUF.class, 0x8);
		s.add("BUFFER_LOAD_FORMAT_D16_XY", MUBUF.class, 0x9);
		s.add("BUFFER_LOAD_FORMAT_D16_XYZ", MUBUF.class, 0xA);
		s.add("BUFFER_LOAD_FORMAT_D16_XYZW", MUBUF.class, 0xB);
		s.add("BUFFER_LOAD_UBYTE", MUBUF.class, 0x10);
		s.add("BUFFER_LOAD_SBYTE", MUBUF.class, 0x11);
		s.add("BUFFER_LOAD_USHORT", MUBUF.class, 0x12);
		s.add("BUFFER_LOAD_SSHORT", MUBUF.class, 0x13);
		s.add("BUFFER_LOAD_DWORD", MUBUF.class, 0x14);
		s.add("BUFFER_LOAD_DWORDX2", MUBUF.class, 0x15);
		s.add("BUFFER_LOAD_DWORDX3", MUBUF.class, 0x16);
		s.add("BUFFER_LOAD_DWORDX4", MUBUF.class, 0x17);
		/* STORE */
		s.add("BUFFER_STORE_FORMAT_D16_X", MUBUF.class, 0xC);
		s.add("BUFFER_STORE_FORMAT_D16_XY", MUBUF.class, 0xD);
		s.add("BUFFER_STORE_FORMAT_D16_XYZ", MUBUF.class, 0xE);
		s.add("BUFFER_STORE_FORMAT_D16_XYZW", MUBUF.class, 0xF);
		s.add("BUFFER_STORE_BYTE", MUBUF.class, 0x18);
		s.add("BUFFER_STORE_SHORT", MUBUF.class, 0x1A);
		s.add("BUFFER_STORE_DWORD", MUBUF.class, 0x1C);
		s.add("BUFFER_STORE_DWORDX2", MUBUF.class, 0x1D);
		s.add("BUFFER_STORE_DWORDX3", MUBUF.class, 0x1E);
		s.add("BUFFER_STORE_DWORDX4", MUBUF.class, 0x1F);
		s.add("BUFFER_STORE_LDS_DWORD", MUBUF.class, 0x3D);
		/* Cache Invalidation */
		s.add("BUFFER_WBINVL1", MUBUF.class, 0x2E);
		s.add("BUFFER_WBINVL1_VOL", MUBUF.class, 0x3F);
		/* Atomic */
		s.add("BUFFER_ATOMIC_SWAP", MUBUF.class, 0x40);
		s.add("BUFFER_ATOMIC_CMPSWAP", MUBUF.class, 0x41);
		s.add("BUFFER_ATOMIC_ADD", MUBUF.class, 0x42);
		s.add("BUFFER_ATOMIC_SUB", MUBUF.class, 0x43);
		s.add("BUFFER_ATOMIC_SMIN", MUBUF.class, 0x44);
		s.add("BUFFER_ATOMIC_UMIN", MUBUF.class, 0x45);
		s.add("BUFFER_ATOMIC_SMAX", MUBUF.class, 0x46);
		s.add("BUFFER_ATOMIC_UMAX", MUBUF.class, 0x47);
		s.add("BUFFER_ATOMIC_AND", MUBUF.class, 0x48);
		s.add("BUFFER_ATOMIC_OR", MUBUF.class, 0x49);
		s.add("BUFFER_ATOMIC_XOR", MUBUF.class, 0x4A);
		s.add("BUFFER_ATOMIC_INC", MUBUF.class, 0x4B);
		s.add("BUFFER_ATOMIC_DEC", MUBUF.class, 0x4C);
		s.add("BUFFER_ATOMIC_SWAP_X2", MUBUF.class, 0x60);
		s.add("BUFFER_ATOMIC_CMPSWAP_X2", MUBUF.class, 0x61);
		s.add("BUFFER_ATOMIC_ADD_X2", MUBUF.class, 0x62);
		s.add("BUFFER_ATOMIC_SUB_X2", MUBUF.class, 0x63);
		s.add("BUFFER_ATOMIC_SMIN_X2", MUBUF.class, 0x64);
		s.add("BUFFER_ATOMIC_UMIN_X2", MUBUF.class, 0x65);
		s.add("BUFFER_ATOMIC_SMAX_X2", MUBUF.class, 0x66);
		s.add("BUFFER_ATOMIC_UMAX_X2", MUBUF.class, 0x67);
		s.add("BUFFER_ATOMIC_AND_X2", MUBUF.class, 0x68);
		s.add("BUFFER_ATOMIC_OR_X2", MUBUF.class, 0x69);
		s.add("BUFFER_ATOMIC_XOR_X2", MUBUF.class, 0x6A);
		s.add("BUFFER_ATOMIC_INC_X2", MUBUF.class, 0x6B);
		s.add("BUFFER_ATOMIC_DEC_X2", MUBUF.class, 0x6C);
		/* End MUBUF */
	}
}
