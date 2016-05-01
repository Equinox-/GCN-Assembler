package com.pi.kernel;

public enum KernelCodeProperties {
	ENABLE_SGPR_PRIVATE_SEGMENT_BUFFER(0, 1),
	ENABLE_SGPR_DISPATCH_PTR(1, 1),
	ENABLE_SGPR_QUEUE_PTR(2, 1),
	ENABLE_SGPR_KERNARG_SEGMENT_PTR(3, 1),
	ENABLE_SGPR_DISPATCH_ID(4, 1),
	ENABLE_SGPR_FLAT_SCRATCH_INIT(5, 1),
	ENABLE_SGPR_PRIVATE_SEGMENT_SIZE(6, 1),
	ENABLE_SGPR_GRID_WORKGROUP_COUNT_X(7, 1),
	ENABLE_SGPR_GRID_WORKGROUP_COUNT_Y(8, 1),
	ENABLE_SGPR_GRID_WORKGROUP_COUNT_Z(9, 1),
//	RESERVED1(10, 6),
	ENABLE_ORDERED_APPEND_GDS(16, 1),
	PRIVATE_ELEMENT_SIZE(17, 2),
	IS_PTR64(19, 1),
	IS_DYNAMIC_CALLSTACK(20, 1),
	IS_DEBUG_ENABLED(21, 1),
	IS_XNACK_ENABLED(22, 1),
//	RESERVED2(23, 9)
	;

	private final int offset, width, mask;

	private KernelCodeProperties(int offset, int width) {
		this.offset = offset;
		this.width = width;
		this.mask = ((1 << width) - 1) << offset;
	}

	public static String decode(int v) {
		StringBuilder out = new StringBuilder();
		for (KernelCodeProperties p : values()) {
			int k = (v & p.mask) >>> p.offset;
			if (p.width != 1) {
				out.append(p.name()).append("=").append(k).append(", ");
			} else if (k > 0) {
				out.append(p.name()).append(", ");
			}
		}
		return out.toString();
	}
}
