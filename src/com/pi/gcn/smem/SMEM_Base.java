package com.pi.gcn.smem;

import com.pi.gcn.data.SGPR;

public interface SMEM_Base {
	/**
	 * <em>true</em> if offset is a memory offset, <em>false</em> if offset is a
	 * SGPR with a DWORD memory offset.
	 */
	public boolean IMM();

	public void IMM(boolean b);

	public SGPR base();

	public void base(SGPR r);

	public SGPR data();

	public void data(SGPR s);

	// Don't TOUCH
	// ALWAYS change mode, then read
	public int offset_raw();

	// Don't TOUCH
	// ALWAYS change mode, then read
	public void offset_raw(int v);

	public default int offset_MEM() {
		if (!IMM())
			throw new IllegalStateException("This is not a IMM instruction.  Use _SGPR methods.");
		return offset_raw();
	}

	public default void offset_MEM(int val) {
		IMM(true);
		offset_raw(val);
	}

	public default SGPR offset_SGPR() {
		if (IMM())
			throw new IllegalArgumentException("This is a IMM instruction.  Use _MEM methods.");
		return new SGPR(offset_raw());
	}

	public default void offset_SGPR(SGPR r) {
		IMM(true);
		offset_raw(r.sgpr & 0xFF);
	}
}
