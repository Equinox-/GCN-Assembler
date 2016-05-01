package com.pi.elf2;

import java.nio.ByteBuffer;

import com.pi.elf2.ELFHeader.EIClass;

public class ELFSymbolTable extends ELFComponent {
	public ELFSymbol[] table;

	protected ELFSymbolTable(Object creator, ELFFile f) {
		super(creator, f);
	}

	@Override
	public void prewrite() {
		boolean x64 = super.file.header.ei_class == EIClass.ARCH_64;
		int sizeof = x64 ? ELFSymbol.SIZEOF_x64 : ELFSymbol.SIZEOF_x32;
		if (creator instanceof ELFSectionHeader) {
			ELFSectionHeader ss = (ELFSectionHeader) creator;
			sizeof = (int) Math.max(sizeof, ss.s_entsize);
			ss.s_entsize = sizeof;
		}

		size = sizeof * table.length;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		boolean x64 = super.file.header.ei_class == EIClass.ARCH_64;
		int sizeof = x64 ? ELFSymbol.SIZEOF_x64 : ELFSymbol.SIZEOF_x32;
		if (creator instanceof ELFSectionHeader) {
			ELFSectionHeader ss = (ELFSectionHeader) creator;
			sizeof = (int) Math.max(sizeof, ss.s_entsize);
		}
		table = new ELFSymbol[b.remaining() / sizeof];
		int pos = b.position();
		for (int i = 0; i < table.length; i++) {
			table[i] = new ELFSymbol();
			b.position(pos + sizeof * i);
			table[i].read(x64, b);
		}
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		boolean x64 = super.file.header.ei_class == EIClass.ARCH_64;
		int sizeof = x64 ? ELFSymbol.SIZEOF_x64 : ELFSymbol.SIZEOF_x32;
		if (creator instanceof ELFSectionHeader) {
			ELFSectionHeader ss = (ELFSectionHeader) creator;
			sizeof = (int) Math.max(sizeof, ss.s_entsize);
			ss.s_entsize = sizeof;
		}

		int pos = b.position();
		for (int i = 0; i < table.length; i++) {
			b.position(pos + sizeof * i);
			table[i].write(x64, b);
		}
	}
}