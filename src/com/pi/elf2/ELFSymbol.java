package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFSymbol {
	public static final int SIZEOF_x32 = 4 + 4 + 4 + 1 + 1 + 2;
	public static final int SIZEOF_x64 = 4 + 8 + 8 + 1 + 1 + 2;

	public int st_name;
	public long st_value;
	public long st_size;
	public byte st_info;
	public byte st_other;
	public short st_shndx;

	public void read(boolean x64, ByteBuffer b) {
		st_name = b.getInt();
		if (x64) {
			st_info = b.get();
			st_other = b.get();
			st_shndx = b.getShort();
			st_value = b.getLong();
			st_size = b.getLong();
		} else {
			st_value = b.getInt();
			st_size = b.getInt();
			st_info = b.get();
			st_other = b.get();
			st_shndx = b.getShort();
		}
	}

	public void write(boolean x64, ByteBuffer b) {
		b.putInt(st_name);
		if (x64) {
			b.put(st_info).put(st_other);
			b.putShort(st_shndx);
			b.putLong(st_value).putLong(st_size);
		} else {
			b.putInt((int) st_value).putInt((int) st_size);
			b.put(st_info).put(st_other);
			b.putShort(st_shndx);
		}
	}
}
