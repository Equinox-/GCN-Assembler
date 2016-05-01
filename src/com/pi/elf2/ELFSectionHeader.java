package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFSectionHeader {
	public int s_name;
	public ELFSectionType s_type;
	public long s_flags;
	public long s_address;
	public int s_link;
	public int s_info;
	public long s_addralign;
	public long s_entsize;

	public ELFComponent component;

	private final ELFFile file;

	public ELFSectionHeader(ELFFile f) {
		this.file = f;
	}

	public int length() {
		return 4 * 4 + 6 * file.ptrSize();
	}

	public void read(ByteBuffer b) {
		s_name = b.getInt();
		s_type = ELFSectionType.lookup(b.getInt());
		s_flags = file.getPtr(b);
		s_address = file.getPtr(b);
		long s_offset = file.getPtr(b);
		long s_size = file.getPtr(b);
		s_link = b.getInt();
		s_info = b.getInt();
		s_addralign = file.getPtr(b);
		s_entsize = file.getPtr(b);

		switch (s_type) {
		case STRTAB:
			component = new ELFStringTable(this, file);
			break;
		case SYMTAB:
			component = new ELFSymbolTable(this, file);
			break;
		case PROGBITS:
			component = new ELFProgramBits(this, file);
			break;
		case NOTE:
			component = new ELFNoteTable(this, file);
			break;
		case NULL:
		default:
			component = new ELFGenData(this,file);
			break;
		}
		component.offset = s_offset;
		component.size = s_size;
	}

	public void write(ByteBuffer b) {
		b.putInt(s_name);
		b.putInt(s_type.value);
		file.putPtr(b, s_flags);
		file.putPtr(b, s_address);
		file.putPtr(b, component.offset);
		file.putPtr(b, component.size);
		b.putInt(s_link);
		b.putInt(s_info);
		file.putPtr(b, s_addralign);
		file.putPtr(b, s_entsize);
	}

	public static enum ELFSectionType {
		NULL(0),
		PROGBITS(0x1),
		SYMTAB(0x2),
		STRTAB(0x3),
		RELA(0x4),
		HASH(0x5),
		DYNAMIC(0x6),
		NOTE(0x7),
		NOBITS(0x8),
		REL(0x9),
		SHLIB(0xA),
		DYNSYM(0xB);
		public final int value;

		public static ELFSectionType lookup(int v) {
			for (ELFSectionType c : values())
				if (c.value == v)
					return c;
			throw new RuntimeException("Bad value: " + v);
		}

		private ELFSectionType(int v) {
			this.value = (int) v;
		}
	}
}
