package com.pi.gcn.insn;

import com.pi.gcn.proc.SubtargetFeature;
import com.pi.gcn.vmem.MTBUF;
import com.pi.gcn.vmem.MTBUF_Base;

public class I_MTBUF {
	public static void register(InstructionSet s) {
		Class<? extends MTBUF_Base> format = s.processor.has(SubtargetFeature.VolcanicIslands) ? MTBUF.MTBUF_VI.class
				: MTBUF.MTBUF_SCI.class;
		/* Begin MTBUF */
		/* LOAD FORMAT */
		s.add("TBUFFER_LOAD_FORMAT_X", format, 0x0);
		s.add("TBUFFER_LOAD_FORMAT_XY", format, 0x1);
		s.add("TBUFFER_LOAD_FORMAT_XYZ", format, 0x2);
		s.add("TBUFFER_LOAD_FORMAT_XYZW", format, 0x3);
		/* STORE FORMAT */
		s.add("TBUFFER_STORE_FORMAT_X", format, 0x4);
		s.add("TBUFFER_STORE_FORMAT_XY", format, 0x5);
		s.add("TBUFFER_STORE_FORMAT_XYZ", format, 0x6);
		s.add("TBUFFER_STORE_FORMAT_XYZW", format, 0x7);
		if (s.processor.has(SubtargetFeature.VolcanicIslands)) {
			s.add("TBUFFER_LOAD_FORMAT_D16_X", format, 0x8);
			s.add("TBUFFER_LOAD_FORMAT_D16_XY", format, 0x9);
			s.add("TBUFFER_LOAD_FORMAT_D16_XYZ", format, 0xA);
			s.add("TBUFFER_LOAD_FORMAT_D16_XYZW", format, 0xB);
			s.add("TBUFFER_STORE_FORMAT_D16_X", format, 0xC);
			s.add("TBUFFER_STORE_FORMAT_D16_XY", format, 0xD);
			s.add("TBUFFER_STORE_FORMAT_D16_XYZ", format, 0xE);
			s.add("TBUFFER_STORE_FORMAT_D16_XYZW", format, 0xF);
		}
	}
}
