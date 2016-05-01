package com.pi.kernel;

import java.nio.ByteBuffer;

public class ControlDirectives {
	public static final int SIZE = 128;
	// amd_enabled_control_directive64_t
	public long enabled_control_directives;
	public short enable_break_exceptions;
	public short enable_detect_exceptions;
	public int max_dynamic_group_size;
	public long max_flat_grid_size;
	public int max_flat_workgroup_size;
	public byte required_dim;
	public final byte[] reserved1 = new byte[3];
	public final long[] required_grid_size = new long[3];
	public final int[] required_workgroup_size = new int[3];

	public final byte[] reserved2 = new byte[60];

	public void read(ByteBuffer b) {
		enabled_control_directives = b.getLong();
		enable_break_exceptions = b.getShort();
		enable_detect_exceptions = b.getShort();
		max_dynamic_group_size = b.getInt();
		max_flat_grid_size = b.getLong();
		max_flat_workgroup_size = b.getInt();
		required_dim = b.get();

		b.get(reserved1);
		for (int i = 0; i < required_grid_size.length; i++)
			required_grid_size[i] = b.getLong();
		for (int i = 0; i < required_workgroup_size.length; i++)
			required_workgroup_size[i] = b.getInt();
		b.get(reserved2);
	}

	public void write(ByteBuffer b) {
		b.putLong(enabled_control_directives);
		b.putShort(enable_break_exceptions);
		b.putShort(enable_detect_exceptions);
		b.putInt(max_dynamic_group_size);
		b.putLong(max_flat_grid_size);
		b.putInt(max_flat_workgroup_size);
		b.put(required_dim);
		b.put(reserved1);
		for (int i = 0; i < required_grid_size.length; i++)
			b.putLong(required_grid_size[i]);
		for (int i = 0; i < required_workgroup_size.length; i++)
			b.putInt(required_workgroup_size[i]);
		b.put(reserved2);
	}
}
