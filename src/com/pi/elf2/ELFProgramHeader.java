package com.pi.elf2;

import java.nio.ByteBuffer;

import com.pi.elf2.ELFHeader.EIClass;

public class ELFProgramHeader {
	public int type;
	public int flags;
	public long offset;
	public long vaddr;
	public long paddr;
	public long filesz;
	public long memsz;
	public long align;

	public ELFComponent component;

	private final ELFFile file;

	public ELFProgramHeader(ELFFile f) {
		this.file = f;
	}

	public int length() {
		return 2 * 4 + 6 * file.ptrSize();
	}

	public void read(ByteBuffer b) {
		type = b.getInt();
		if (file.header.ei_class == EIClass.ARCH_32) {
			offset = file.getPtr(b);
			vaddr = file.getPtr(b);
			paddr = file.getPtr(b);
			filesz = b.getInt();
			memsz = b.getInt();
			flags = b.getInt();
		} else {
			flags = b.getInt();
			offset = file.getPtr(b);
			vaddr = file.getPtr(b);
			paddr = file.getPtr(b);
			filesz = file.getPtr(b);
			memsz = file.getPtr(b);
		}
		align = file.getPtr(b);

		if (type == ELFProgramHeaderType.NOTE.value) {
			component = new ELFNoteTable(this, file);
			component.offset = offset;
			component.size = Math.max(filesz, memsz); // hacky
		}
	}

	public void write(ByteBuffer b) {
		if (type != ELFProgramHeaderType.NOTE.value)
			component = null;
		else {
			offset = component.offset;
			memsz = filesz = component.size;
		}
		b.putInt(type);
		if (file.header.ei_class == EIClass.ARCH_32) {
			file.putPtr(b, offset);
			file.putPtr(b, vaddr);
			file.putPtr(b, paddr);
			file.putPtr(b, filesz);
			file.putPtr(b, memsz);
			b.putInt(flags);
		} else {
			b.putInt(flags);
			file.putPtr(b, offset);
			file.putPtr(b, vaddr);
			file.putPtr(b, paddr);
			file.putPtr(b, filesz);
			file.putPtr(b, memsz);
		}
		file.putPtr(b, align);
	}

	public enum ELFProgramHeaderType {
		NULL(0),
		LOAD(1),
		DYNAMIC(2),
		INTERP(3),
		NOTE(4),
		SHLIB(5),
		PHDR(6),
		LOOS(0x60000000),
		HIOS(0x6FFFFFFF),
		LOPROC(0x70000000),
		HIPROC(0x7FFFFFFF);
		public final int value;

		public static ELFProgramHeaderType lookup(int v) {
			for (ELFProgramHeaderType c : values())
				if (c.value == v)
					return c;
			throw new RuntimeException("Bad value: " + v);
		}

		private ELFProgramHeaderType(int v) {
			this.value = (int) v;
		}
	}
}
