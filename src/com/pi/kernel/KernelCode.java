package com.pi.kernel;

import java.nio.ByteBuffer;

public class KernelCode {
	public static final int SIZE = 128 + ControlDirectives.SIZE;

	public int amd_kernel_code_version_major;
	public int amd_kernel_code_version_minor;
	public amd_machine_kind_t amd_machine_kind;
	public short amd_machine_version_major;
	public short amd_machine_version_minor;
	public short amd_machine_version_stepping;
	public long kernel_code_entry_byte_offset;
	public long kernel_code_prefetch_byte_offset;
	public long kernel_code_prefetch_byte_size;
	public long max_scratch_backing_memory_byte_size;
	@Bitfield(decoder = ComputePgmResourceRegister.class)
	public long compute_pgm_resource_registers;
	@Bitfield(decoder = KernelCodeProperties.class)
	public int kernel_code_properties;

	public int workitem_private_segment_byte_size;
	public int workgroup_group_segment_byte_size;
	public int gds_segment_byte_size;
	public long kernarg_segment_byte_size;
	public int workgroup_fbarrier_count;
	public short wavefront_sgpr_count;
	public short workitem_vgpr_count;
	public short reserved_vgpr_first;
	public short reserved_vgpr_count;
	public short reserved_sgpr_first;
	public short reserved_sgpr_count;
	public short debug_wavefront_private_segment_offset_sgpr;
	public short debug_private_segment_buffer_sgpr;
	public byte kernarg_segment_alignment;
	public byte group_segment_alignment;
	public byte private_segment_alignment;
	public byte wavefront_size;
	public int call_convention;
	public final byte[] reserved1 = new byte[12];
	public long runtime_loader_kernel_symbol;
	public final ControlDirectives control_directives = new ControlDirectives();

	public void read(ByteBuffer b) {
		amd_kernel_code_version_major = b.getInt();
		amd_kernel_code_version_minor = b.getInt();
		amd_machine_kind = amd_machine_kind_t.values()[b.getShort()];
		amd_machine_version_major = b.getShort();
		amd_machine_version_minor = b.getShort();
		amd_machine_version_stepping = b.getShort();
		kernel_code_entry_byte_offset = b.getLong();
		kernel_code_prefetch_byte_offset = b.getLong();
		kernel_code_prefetch_byte_size = b.getLong();
		max_scratch_backing_memory_byte_size = b.getLong();
		compute_pgm_resource_registers = b.getLong();
		kernel_code_properties = b.getInt();

		workitem_private_segment_byte_size = b.getInt();
		workgroup_group_segment_byte_size = b.getInt();
		gds_segment_byte_size = b.getInt();
		kernarg_segment_byte_size = b.getLong();
		workgroup_fbarrier_count = b.getInt();
		wavefront_sgpr_count = b.getShort();
		workitem_vgpr_count = b.getShort();
		reserved_vgpr_first = b.getShort();
		reserved_vgpr_count = b.getShort();
		reserved_sgpr_first = b.getShort();
		reserved_sgpr_count = b.getShort();
		debug_wavefront_private_segment_offset_sgpr = b.getShort();
		debug_private_segment_buffer_sgpr = b.getShort();
		kernarg_segment_alignment = b.get();
		group_segment_alignment = b.get();
		private_segment_alignment = b.get();
		wavefront_size = b.get();
		call_convention = b.getInt();
		b.get(reserved1);
		runtime_loader_kernel_symbol = b.getLong();
		control_directives.read(b);
	}

	public void write(ByteBuffer b) {
		b.putInt(amd_kernel_code_version_major);
		b.putInt(amd_kernel_code_version_minor);
		b.putShort((short) amd_machine_kind.ordinal());
		b.putShort(amd_machine_version_major);
		b.putShort(amd_machine_version_minor);
		b.putShort(amd_machine_version_stepping);
		b.putLong(kernel_code_entry_byte_offset);
		b.putLong(kernel_code_prefetch_byte_offset);
		b.putLong(kernel_code_prefetch_byte_size);
		b.putLong(max_scratch_backing_memory_byte_size);
		b.putLong(compute_pgm_resource_registers);
		b.putInt(kernel_code_properties);

		b.putInt(workitem_private_segment_byte_size);
		b.putInt(workgroup_group_segment_byte_size);
		b.putInt(gds_segment_byte_size);
		b.putLong(kernarg_segment_byte_size);
		b.putInt(workgroup_fbarrier_count);
		b.putShort(wavefront_sgpr_count);
		b.putShort(workitem_vgpr_count);
		b.putShort(reserved_vgpr_first);
		b.putShort(reserved_vgpr_count);
		b.putShort(reserved_sgpr_first);
		b.putShort(reserved_sgpr_count);
		b.putShort(debug_wavefront_private_segment_offset_sgpr);
		b.putShort(debug_private_segment_buffer_sgpr);
		b.put(kernarg_segment_alignment);
		b.put(group_segment_alignment);
		b.put(private_segment_alignment);
		b.put(wavefront_size);
		b.putInt(call_convention);
		b.put(reserved1);
		b.putLong(runtime_loader_kernel_symbol);
		control_directives.write(b);
	}

	public static enum amd_machine_kind_t {
		AMD_MACHINE_KIND_UNDEFINED,
		AMD_MACHINE_KIND_AMDGPU
	}
}
