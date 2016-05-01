package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFNote {
	public String name;
	public byte[] desc;
	public int type;

	public void read(ELFFile f, ByteBuffer b) {
		long name_len = CUtil.align(b.getInt(), 4);
		desc = new byte[(int) b.getInt()];
		long desc_len = CUtil.align(desc.length, 4);
		type = b.getInt();
		byte[] name_data = new byte[(int) name_len];
		b.get(name_data);
		byte[] desc_data = new byte[(int) desc_len];
		b.get(desc_data);
		name = CUtil.cstr(name_data, 0);
		System.arraycopy(desc_data, 0, desc, 0, desc.length);
	}

	public long sizeof(ELFFile f) {
		byte[] name_data = name.getBytes();
		long name_len = name_data.length > 0 ? CUtil.align(1 + name_data.length, 4) : 0;
		long desc_len = CUtil.align(desc.length, 4);
		return 3 * 4 + name_len + desc_len;
	}

	public void write(ELFFile f, ByteBuffer b) {
		byte[] name_data = name.getBytes();
		long name_len = name_data.length > 0 ? CUtil.align(1 + name_data.length, 4) : 0;
		long desc_len = CUtil.align(desc.length, 4);
		b.putInt((int) name_data.length + 1);
		b.putInt((int) desc.length);
		b.putInt((int) type);
		b.put(CUtil.pad(name_data, name_len));
		b.put(CUtil.pad(desc, desc_len));
	}
}
