package com.pi.gcn.ds;

import com.pi.gcn.OpcodeLayout;

public class DS_Layout {
	public final OpcodeLayout encoding;

	public final int[] OFFSET0;
	public final int[] OFFSET1;
	public final int GDS;
	public final int[] ADDR_SRC;
	public final int[] DATA0;
	public final int[] DATA1;
	public final int[] DEST;

	private DS_Layout(OpcodeLayout enc, int[] off0, int[] off1, int gds, int[] addr, int[] dat0, int[] dat1,
			int[] dest) {
		this.encoding = enc;
		this.OFFSET0 = off0;
		this.OFFSET1 = off1;
		this.GDS = gds;
		this.ADDR_SRC = addr;
		this.DATA0 = dat0;
		this.DATA1 = dat1;
		this.DEST = dest;
	}

	public static final DS_Layout LAYOUT_VI = new DS_Layout(new OpcodeLayout(26, 0b110110, 17, 25), new int[] { 0, 8 },
			new int[] { 8, 16 }, 16, new int[] { 32, 40 }, new int[] { 40, 48 }, new int[] { 48, 56 },
			new int[] { 56, 64 });
	public static final DS_Layout LAYOUT_SCI = new DS_Layout(new OpcodeLayout(26, 0b110110, 18, 26), new int[] { 0, 8 },
			new int[] { 8, 16 }, 17, new int[] { 32, 40 }, new int[] { 40, 48 }, new int[] { 48, 56 },
			new int[] { 56, 64 });
}
