package com.pi.gcn.valu.vop3;

import com.pi.gcn.OpcodeLayout;

public class VOP3_Layout {
	// Opcode
	public final OpcodeLayout encode;
	// Shared
	public final int[] OMOD, NEG, VDEST, SRC0, SRC1, SRC2;
	public final int CLAMP;
	// VOP3a
	public final int[] ABS;
	// VOP3b
	public final int[] SDEST;

	private VOP3_Layout(OpcodeLayout layout, int[] VDEST, int CLAMP, int[] SRC0, int[] SRC1, int[] SRC2, int[] OMOD,
			int[] NEG, int[] ABS, int[] SDEST) {
		this.encode = layout;
		this.OMOD = OMOD;
		this.CLAMP = CLAMP;
		this.NEG = NEG;
		this.VDEST = VDEST;
		this.SRC0 = SRC0;
		this.SRC1 = SRC1;
		this.SRC2 = SRC2;
		this.ABS = ABS;
		this.SDEST = SDEST;
	}

	public static final VOP3_Layout LAYOUT_VI = new VOP3_Layout(new OpcodeLayout(26, 0b110100, 16, 26),
			new int[] { 0, 8 }, 15, new int[] { 32, 41 }, new int[] { 41, 50 }, new int[] { 50, 59 },
			new int[] { 59, 61 }, new int[] { 61, 64 }, new int[] { 8, 11 }, new int[] { 8, 15 });

	public static final VOP3_Layout LAYOUT_SCI = new VOP3_Layout(new OpcodeLayout(26, 0b110100, 17, 26),
			new int[] { 0, 8 }, 11, new int[] { 32, 40 }, new int[] { 40, 49 }, new int[] { 49, 58 },
			new int[] { 58, 60 }, new int[] { 60, 63 }, new int[] { 8, 11 }, new int[] { 8, 15 });
}
