package com.pi.elf2.amd;

import com.pi.elf2.ELFProgramHeader;

public class AMDElf {
	// https://github.com/HSAFoundation/HSA-Runtime-AMD/blob/master/include/amd_hsa_elf.h#L53-55
	public static final int ELF_OSABI_AMDGPU_HSA = 64;
	public static final int ELF_MACHINE_AMDGPU = 224;
	public static final int ELF_ABIVERSION_AMDGPU_HSA = 0;
	
	public static final int ELF_INNER_SYMBOL_INFO_KERNEL = 26;
	
	public static final int ELF_INNER_PROG_TYPE_KERNEL = ELFProgramHeader.ELFProgramHeaderType.LOOS.value + 3;
	
	public static final int ELF_INNER_MACHINE_ATI_CALIMAGE_BINARY = 0x7D;
}
