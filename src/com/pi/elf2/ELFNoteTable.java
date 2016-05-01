package com.pi.elf2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ELFNoteTable extends ELFComponent {
	public ELFNote[] table;

	protected ELFNoteTable(Object creator, ELFFile f) {
		super(creator, f);
	}

	@Override
	public void prewrite() {
		long size = 0;
		for (ELFNote n : table)
			size += n.sizeof(file);
		this.size = size;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		List<ELFNote> n = new ArrayList<>();
		while (b.hasRemaining()) {
			ELFNote note = new ELFNote();
			note.read(file, b);
			n.add(note);
		}
		table = n.toArray(new ELFNote[n.size()]);
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		for (int i = 0; i < table.length; i++)
			table[i].write(file, b);
	}

}
