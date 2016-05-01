package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFProgramTable extends ELFComponent {
	public ELFProgramHeader[] table;

	protected ELFProgramTable(Object creator, ELFFile f) {
		super(creator, f);
	}

	@Override
	public void prewrite() {
		size = table.length * file.header.e_phentsize;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		int base = b.position();
		if (size > 0) {
			table = new ELFProgramHeader[(int) (size / file.header.e_phentsize)];
			for (int i = 0; i < table.length; i++) {
				table[i] = new ELFProgramHeader(file);
				b.position(base + i * file.header.e_phentsize);
				table[i].read(b);
			}
		} else {
			table = new ELFProgramHeader[0];
		}
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		int base = b.position();
		for (int i = 0; i < table.length; i++) {
			b.position(base + i * file.header.e_phentsize);
			table[i].write(b);
		}
	}

}