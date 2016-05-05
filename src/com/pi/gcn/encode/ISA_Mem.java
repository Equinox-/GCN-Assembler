package com.pi.gcn.encode;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pi.Utils;
import com.pi.gcn.OpcodeLayout;
import com.pi.gcn.base.MC;
import com.pi.gcn.ds.DS;
import com.pi.gcn.genmem.EXP;
import com.pi.gcn.genmem.FLAT;
import com.pi.gcn.genmem.MIMG;
import com.pi.gcn.insn.Instruction;
import com.pi.gcn.insn.InstructionSet;
import com.pi.gcn.salu.SOP1;
import com.pi.gcn.salu.SOP2;
import com.pi.gcn.salu.SOPC;
import com.pi.gcn.salu.SOPK;
import com.pi.gcn.salu.SOPP;
import com.pi.gcn.smem.SMEM;
import com.pi.gcn.valu.VINTRP;
import com.pi.gcn.valu.VOP1;
import com.pi.gcn.valu.VOP2;
import com.pi.gcn.valu.VOPC;
import com.pi.gcn.valu.vop3.VOP3.VOP3a_VI;
import com.pi.gcn.vmem.MTBUF;
import com.pi.gcn.vmem.MUBUF;

public class ISA_Mem {
	@SuppressWarnings("rawtypes")
	private static final Class[] TYPES = { SOPP.class, SOPC.class, SOP1.class, SOPK.class, SOP2.class, VINTRP.class,
			VOP1.class, VOPC.class, SMEM.class, MUBUF.class, MTBUF.MTBUF_VI.class, DS.class, MIMG.class, FLAT.class, EXP.class,
			VOP3a_VI.class, VOP2.class };

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void debug(ByteBuffer b) {
		for (Class c : TYPES) {
			try {
				int insn = b.getInt(b.position());
				OpcodeLayout lay = InstructionSet.layoutFor(c);
				if (lay.matches(insn)) {
					System.err.println("\tMatches " + c + " with opcode=" + lay.opcode(insn) + "(0x"
							+ Integer.toHexString(lay.opcode(insn)) + ")");
				}
			} catch (Exception e) {
			}
		}
	}

	public static List<MC> isaToMem(InstructionSet insnSet, ByteBuffer isa) {
		List<MC> microcode = new ArrayList<>();

		int lop = 0;
		while (isa.remaining() >= 4) {
			int op = isa.position();

			int insn = isa.getInt(op);
			Instruction set = insnSet.find(insn);
			MC code = set != null ? set.create(isa) : null;
			if (code == null) {
				if (!microcode.isEmpty()) {
					MC mc = microcode.get(microcode.size() - 1);
					System.err.println("Previous " + mc.getClass().getSimpleName() + " with opcode=" + mc.opcode()
							+ "(0x" + Integer.toHexString(mc.opcode()) + ")\t@\t0x" + Integer.toHexString(lop) + " ("
							+ (op - lop) + " ago) ");
					System.err.println("\t" + Arrays.toString(mc.encode(insnSet)));
				}
				int pos = isa.position();
				System.err.println("Starting at 0x" + Integer.toHexString(op));
				for (int p = 0; p >= 0; --p) {
					System.err.println(Integer.toHexString(pos + p * 4) + "\t"
							+ Utils.padNumeral(Integer.toHexString(isa.getInt(pos + p * 4)), 8));
					isa.position(pos + p * 4);
					debug(isa);
				}
				isa.position(pos + 4);
				// throw new RuntimeException("Failed to decode");
			}
			if (code != null) {
				microcode.add(code);
			}
			lop = op;
		}
		return microcode;
	}
}
