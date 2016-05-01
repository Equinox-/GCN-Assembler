package com.pi.gcn;

import com.pi.gcn.proc.Processor;
import com.pi.gcn.proc.SubtargetFeature;

public class MultiOp {
	private final int southern_sea_islands;
	private final int volcanic_islands;

	public MultiOp(int southern_sea_islands, int volcanic_islands) {
		this.southern_sea_islands = southern_sea_islands;
		this.volcanic_islands = volcanic_islands;
	}

	public int opcodeFor(Processor p) {
		if (p.has(SubtargetFeature.VolcanicIslands))
			return volcanic_islands;
		else if (p.has(SubtargetFeature.SeaIslands) || p.has(SubtargetFeature.SouthernIslands))
			return southern_sea_islands;
		else
			throw new IllegalArgumentException("No opcodes for " + p);
	}

	public int CI() {
		return southern_sea_islands;
	}

	public int VI() {
		return volcanic_islands;
	}
}
