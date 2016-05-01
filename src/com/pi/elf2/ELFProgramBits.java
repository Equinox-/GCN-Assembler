package com.pi.elf2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.pi.elf2.ELFHeader.EIEndian;

public class ELFProgramBits extends ELFComponent {
	public byte[] progbits;

	protected ELFProgramBits(Object creator, ELFFile f) {
		super(creator, f);
	}

	public ByteBuffer buffer() {
		return ByteBuffer.wrap(progbits)
				.order(file.header.ei_data == EIEndian.LITTLE ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
	}

	@Override
	public void prewrite() {
		size = progbits.length;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		progbits = new byte[(int) size];
		b.get(progbits);
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		b.put(progbits);
	}
}
