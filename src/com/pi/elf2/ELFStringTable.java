package com.pi.elf2;

import java.nio.ByteBuffer;

public class ELFStringTable extends ELFComponent {
	private String table;

	protected ELFStringTable(Object creator, ELFFile f) {
		super(creator, f);
	}

	public int add(String s) {
		int sc = table.indexOf(s + "\0");
		if (sc >= 0)
			return sc;
		sc = table.length();
		table += s + "\0";
		return sc;
	}

	public String get(int offset) {
		return this.table.substring(offset, this.table.indexOf('\0', offset));
	}

	public void prewrite() {
		size = table.getBytes().length;
	}

	@Override
	public void readInternal(ByteBuffer b) {
		byte[] tmp = new byte[(int) size];
		b.get(tmp);
		table = new String(tmp);
	}

	@Override
	public void writeInternal(ByteBuffer b) {
		b.put(table.getBytes());
	}
}