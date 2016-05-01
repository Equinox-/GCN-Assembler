package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFSectionTable extends ELFComponent {
	public ELFSectionHeader[] table;

	protected ELFSectionTable(Object creator, ELFFile f) {
		super(creator, f);
	}

	@Override
	public void prewrite() {
		size = table.length * file.header.e_shentsize;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		int base = b.position();

		table = new ELFSectionHeader[(int) (size / file.header.e_shentsize)];
		for (int i = 0; i < table.length; i++) {
			table[i] = new ELFSectionHeader(file);
			b.position(base + i * file.header.e_shentsize);
			table[i].read(b);
		}
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		int base = b.position();
		for (int i = 0; i < table.length; i++) {
			b.position(base + i * file.header.e_shentsize);
			table[i].write(b);
		}
	}

}
