package com.pi.gcn.insn;

import com.pi.gcn.MultiOp;
import com.pi.gcn.proc.SubtargetFeature;
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
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			s.add("BUFFER_LOAD_FORMAT_D16_X", MUBUF.class, 0x8);
			s.add("BUFFER_LOAD_FORMAT_D16_XY", MUBUF.class, 0x9);
			s.add("BUFFER_LOAD_FORMAT_D16_XYZ", MUBUF.class, 0xA);
			s.add("BUFFER_LOAD_FORMAT_D16_XYZW", MUBUF.class, 0xB);
		}
		s.add("BUFFER_LOAD_UBYTE", MUBUF.class, new MultiOp(0x8, 0x10));
		s.add("BUFFER_LOAD_SBYTE", MUBUF.class, new MultiOp(0x9, 0x11));
		s.add("BUFFER_LOAD_USHORT", MUBUF.class, new MultiOp(0xA, 0x12));
		s.add("BUFFER_LOAD_SSHORT", MUBUF.class, new MultiOp(0x0B, 0x13));
		s.add("BUFFER_LOAD_DWORD", MUBUF.class, new MultiOp(0x0C, 0x14));
		s.add("BUFFER_LOAD_DWORDX2", MUBUF.class, new MultiOp(0x0D, 0x15));
		s.add("BUFFER_LOAD_DWORDX3", MUBUF.class, new MultiOp(-1, 0x16));
		s.add("BUFFER_LOAD_DWORDX4", MUBUF.class, new MultiOp(0x0E, 0x17));
		/* STORE */
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			s.add("BUFFER_STORE_FORMAT_D16_X", MUBUF.class, 0xC);
			s.add("BUFFER_STORE_FORMAT_D16_XY", MUBUF.class, 0xD);
			s.add("BUFFER_STORE_FORMAT_D16_XYZ", MUBUF.class, 0xE);
			s.add("BUFFER_STORE_FORMAT_D16_XYZW", MUBUF.class, 0xF);
		}
		s.add("BUFFER_STORE_BYTE", MUBUF.class, 0x18);
		s.add("BUFFER_STORE_SHORT", MUBUF.class, 0x1A);
		s.add("BUFFER_STORE_DWORD", MUBUF.class, 0x1C);
		s.add("BUFFER_STORE_DWORDX2", MUBUF.class, 0x1D);
		s.add("BUFFER_STORE_DWORDX3", MUBUF.class, new MultiOp(-1, 0x1E));
		s.add("BUFFER_STORE_DWORDX4", MUBUF.class, new MultiOp(0x1E, 0x1F));

		/* Atomic */
		s.add("BUFFER_ATOMIC_SWAP", MUBUF.class, new MultiOp(0x30, 0x40));
		s.add("BUFFER_ATOMIC_CMPSWAP", MUBUF.class, new MultiOp(0x31, 0x41));
		s.add("BUFFER_ATOMIC_ADD", MUBUF.class, new MultiOp(0x32, 0x42));
		s.add("BUFFER_ATOMIC_SUB", MUBUF.class, new MultiOp(0x33, 0x43));
		if (s.processor.has(SubtargetFeature.SouthernIslands))
			s.add("BUFFER_ATOMIC_RSUB", MUBUF.class, 0x34);
		s.add("BUFFER_ATOMIC_SMIN", MUBUF.class, new MultiOp(0x35, 0x44));
		s.add("BUFFER_ATOMIC_UMIN", MUBUF.class, new MultiOp(0x36, 0x45));
		s.add("BUFFER_ATOMIC_SMAX", MUBUF.class, new MultiOp(0x37, 0x46));
		s.add("BUFFER_ATOMIC_UMAX", MUBUF.class, new MultiOp(0x38, 0x47));
		s.add("BUFFER_ATOMIC_AND", MUBUF.class, new MultiOp(0x39, 0x48));
		s.add("BUFFER_ATOMIC_OR", MUBUF.class, new MultiOp(0x3A, 0x49));
		s.add("BUFFER_ATOMIC_XOR", MUBUF.class, new MultiOp(0x3B, 0x4A));

		s.add("BUFFER_ATOMIC_INC", MUBUF.class, new MultiOp(0x3C, 0x4B));
		s.add("BUFFER_ATOMIC_DEC", MUBUF.class, new MultiOp(0x3D, 0x4C));
		if (s.processor.has(SubtargetFeature.SouthernIslands) || s.processor.has(SubtargetFeature.SeaIslands)) {
			s.add("BUFFER_ATOMIC_FCMPSWAP", MUBUF.class, 0x3E);
			s.add("BUFFER_ATOMIC_FMIN", MUBUF.class, 0x3F);
			s.add("BUFFER_ATOMIC_FMAX", MUBUF.class, 0x40);
		}
		s.add("BUFFER_ATOMIC_SWAP_X2", MUBUF.class, new MultiOp(0x50, 0x60));
		s.add("BUFFER_ATOMIC_CMPSWAP_X2", MUBUF.class, new MultiOp(0x51, 0x61));
		s.add("BUFFER_ATOMIC_ADD_X2", MUBUF.class, new MultiOp(0x52, 0x62));
		s.add("BUFFER_ATOMIC_SUB_X2", MUBUF.class, new MultiOp(0x53, 0x63));
		s.add("BUFFER_ATOMIC_SMIN_X2", MUBUF.class, new MultiOp(0x55, 0x64));
		s.add("BUFFER_ATOMIC_UMIN_X2", MUBUF.class, new MultiOp(0x56, 0x65));
		s.add("BUFFER_ATOMIC_SMAX_X2", MUBUF.class, new MultiOp(0x57, 0x66));
		s.add("BUFFER_ATOMIC_UMAX_X2", MUBUF.class, new MultiOp(0x58, 0x67));
		s.add("BUFFER_ATOMIC_AND_X2", MUBUF.class, new MultiOp(0x59, 0x68));
		s.add("BUFFER_ATOMIC_OR_X2", MUBUF.class, new MultiOp(0x5A, 0x69));
		s.add("BUFFER_ATOMIC_XOR_X2", MUBUF.class, new MultiOp(0x5B, 0x6A));
		s.add("BUFFER_ATOMIC_INC_X2", MUBUF.class, new MultiOp(0x5C, 0x6B));
		s.add("BUFFER_ATOMIC_DEC_X2", MUBUF.class, new MultiOp(0x5D, 0x6C));
		if (s.processor.has(SubtargetFeature.SouthernIslands) || s.processor.has(SubtargetFeature.SeaIslands)) {
			s.add("BUFFER_ATOMIC_FCMPSWAP_X2", MUBUF.class, 0x5E);
			s.add("BUFFER_ATOMIC_FMIN_X2", MUBUF.class, 0x5F);
			s.add("BUFFER_ATOMIC_FMAX_X2", MUBUF.class, 0x60);
		}
		/* End MUBUF */

		/* Cache Invalidation */
		s.add("BUFFER_WBINVL1", MUBUF.class, new MultiOp(0x71, 0x2E));
		s.add("BUFFER_WBINVL1_VOL", MUBUF.class, new MultiOp(0x70, 0x3F));

		if (s.processor.has(SubtargetFeature.VolcanicIslands))
			s.add("BUFFER_STORE_LDS_DWORD", MUBUF.class, 0x3D);
	}
}
