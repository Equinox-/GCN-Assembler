package com.pi.ui.cmd;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

import com.pi.FileUtil;
import com.pi.elf2.ELFFile;
import com.pi.elf2.ELFNoteTable;
import com.pi.elf2.ELFProgramBits;
import com.pi.elf2.ELFSectionHeader;
import com.pi.elf2.ELFStringTable;
import com.pi.elf2.ELFSymbol;
import com.pi.elf2.ELFSymbolTable;
import com.pi.elf2.amd.AMDElf;
import com.pi.elf2.amd.AMDMachine;
import com.pi.elf2.amd.AMD_Note_HSA;
import com.pi.gcn.base.MC;
import com.pi.gcn.encode.ISA_Mem;
import com.pi.gcn.encode.Mem_ISA;
import com.pi.gcn.insn.InstructionSet;
import com.pi.gcn.proc.Processor;
import com.pi.gcn.proc.SubtargetFeature;
import com.pi.kernel.KernelCode;

public class GCN_State {
	public byte[] code;
	private final ELFFile elf, exe;
	public final KernelCode kc;
	private final ELFProgramBits exeData, hsaData;

	public final InstructionSet insnSet;

	public GCN_State(File f) {
		byte[] data = FileUtil.read(f.getAbsolutePath());
		// Load binary ELF
		elf = new ELFFile();
		elf.read(ByteBuffer.wrap(data));

		// Load executable ELF
		exeData = ((ELFProgramBits) elf.section(".text").component);
		exe = new ELFFile();
		exe.read(exeData.buffer());
		FileUtil.write("test/inner-in.bin", exeData.progbits);

		if (exe.header.e_machine == AMDElf.ELF_INNER_MACHINE_ATI_CALIMAGE_BINARY) {
			Processor matching = AMDMachine.lookup(elf.header.e_machine).proc;
			insnSet = new InstructionSet(matching);
			System.out.println("Decompiling for " + matching.name());

			hsaData = (ELFProgramBits) exe.section(".text").component;
			ByteBuffer isa = hsaData.buffer();
			code = new byte[isa.remaining()];
			isa.get(code);

			// Read the kernel spec from the notes?
			kc = new KernelCode();
		} else {
			// Decide how many kernels we have:
			{
				ELFSectionHeader symsec = exe.section(".symtab");
				ELFSectionHeader strsec = exe.section(".strtab");
				if (symsec != null && strsec != null) {
					int kernelCount = 0;
					ELFSymbolTable symtab = (ELFSymbolTable) symsec.component;
					ELFStringTable strtab = (ELFStringTable) strsec.component;
					for (int i = 0; i < symtab.table.length; i++) {
						ELFSymbol sym = symtab.table[i];
						String name = strtab.get(sym.st_name);
						if (name.startsWith("&__OpenCL_") && name.endsWith("_kernel")) {
							// It's a kernel.
							// String actualName = name.substring(10,
							// name.length() - 7);
							kernelCount++;
						}
					}
					if (kernelCount != 1)
						System.err.println(
								"You have less or more than one kernel in this binary.  Strange behavior may arise.");
				}
			}

			// We need to decide on what GPU we are using. I couldn't find
			// exactly where this was but I did find the ISA version: best
			// chance is to use that
			{
				// Default to TONGA: it's the most common HSA based one right
				// now
				Processor matching = Processor.TONGA;

				ELFNoteTable notes = (ELFNoteTable) exe.section(".note").component;

				Map<String, Object> hsa_isa_note = null;
				for (int i = 0; i < notes.table.length; i++) {
					AMD_Note_HSA hs = AMD_Note_HSA.lookup(notes.table[i].type);
					if (hs == AMD_Note_HSA.HSA_ISA) {
						try {
							hsa_isa_note = hs
									.decode(ByteBuffer.wrap(notes.table[i].desc).order(exe.header.ei_data.order));
						} catch (Exception e) {
						}
					}
				}
				if (hsa_isa_note != null) {
					boolean ignoreStepping = false;
					int major = ((Number) hsa_isa_note.get("major")).intValue();
					int minor = ((Number) hsa_isa_note.get("minor")).intValue();
					int stepping = ((Number) hsa_isa_note.get("stepping")).intValue();
					primary: while (true) {
						for (Processor p : Processor.values()) {
							for (SubtargetFeature fet : p.features()) {
								if (fet.isISAVersion && fet.major == major && fet.minor == minor
										&& (ignoreStepping || fet.stepping == stepping)) {
									matching = p;
									break primary;
								}
							}
						}
						if (ignoreStepping)
							break;
						else
							ignoreStepping = true;
					}
				}
				System.out.println("Decompiling for " + matching.name());
				insnSet = new InstructionSet(matching);
			}

			// Load HSA data
			hsaData = ((ELFProgramBits) exe.section(".hsatext").component);
			ByteBuffer isa = hsaData.buffer();

			// Read the kernel spec
			kc = new KernelCode();
			kc.read(isa);
			// System.out.println(ClazzStr.stringify("kc", kc));
			if (kc.kernel_code_entry_byte_offset != KernelCode.SIZE)
				throw new UnsupportedOperationException("Can't place ISA at " + kc.kernel_code_entry_byte_offset);

			code = new byte[isa.remaining()];
			isa.get(code);
		}
	}

	public ByteOrder order() {
		return exe.header.ei_data.order;
	}

	public List<MC> getISA() {
		return ISA_Mem.isaToMem(insnSet, ByteBuffer.wrap(code).order(order()));
	}

	public void setISA(List<MC> mc) {
		code = Mem_ISA.memToISA(mc, order());
	}

	public void write(File f) {
		// Grab HSA data pointer
		ELFProgramBits hsaData = ((ELFProgramBits) exe.section(".hsatext").component);

		hsaData.progbits = new byte[code.length + KernelCode.SIZE];
		ByteBuffer hsa = ByteBuffer.wrap(hsaData.progbits).order(order());
		kc.write(hsa);
		hsa.put(code);

		// Adjust program sizes:
		ELFSymbolTable syms = (ELFSymbolTable) exe.section(".symtab").component;
		for (int i = 0; i < syms.table.length; i++) {
			if (syms.table[i].st_info == AMDElf.ELF_INNER_SYMBOL_INFO_KERNEL) {
				syms.table[i].st_size = hsaData.progbits.length;
			}
		}
		for (int i = 0; i < exe.programs.table.length; i++) {
			if (exe.programs.table[i].type == AMDElf.ELF_INNER_PROG_TYPE_KERNEL) {
				exe.programs.table[i].memsz = exe.programs.table[i].filesz = hsaData.progbits.length;
			}
		}

		ByteBuffer exeOut = ByteBuffer.wrap(exeData.progbits = new byte[exe.size()]);
		exe.write(exeOut);
		FileUtil.write("test/inner-out.bin", exeData.progbits);

		ByteBuffer elfOut = ByteBuffer.wrap(new byte[elf.size()]);
		elf.write(elfOut);
		FileUtil.write(f.getAbsolutePath(), elfOut);
	}
}
