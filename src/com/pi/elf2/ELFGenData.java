package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFGenData extends ELFComponent {
	public byte[] data;

	protected ELFGenData(Object creator, ELFFile f) {
		super(creator, f);
	}

	@Override
	public void prewrite() {
		size = data.length;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		data = new byte[(int) size];
		b.get(data);
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		b.put(data);
	}
}
