package com.pi.elf2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ELFHeader {
	private static final byte[] MAGIC = { 0x7f, 0x45, 0x4c, 0x46 };

	public EIClass ei_class; // arch

	public EIEndian ei_data; // endianess

	public byte ei_version;
	public byte ei_osabi;
	public byte ei_abiversion;
	public final byte[] ei_pad = new byte[7];
	public short e_type;
	public short e_machine;
	public int e_version;
	public long e_entry; // ptr length - see ei_class
	public long e_phoff;

	public long e_shoff;
	public int e_flags;
	public short e_ehsize;

	public short e_phentsize;
	public short e_phnum;
	public short e_shentsize;
	public short e_shnum;
	public short e_shstrndx;

	public void clDefaults() {
		ei_data = EIEndian.LITTLE;
		ei_osabi = 0; // ELFOSABI_NONE
		ei_abiversion = 0;
		e_type = 0; // ET_NONE
		e_version = 1; // EV_CURRENT
		e_entry = 0;
		e_phoff = 0;
		e_flags = 0;
		e_phentsize = 0;
		e_phnum = 0;
	}

	public void read(ByteBuffer buff) {
		for (int i = 0; i < MAGIC.length; ++i)
			if (MAGIC[i] != buff.get())
				throw new IllegalArgumentException("Bad magic");
		ei_class = EIClass.lookup(buff.get());
		ei_data = EIEndian.lookup(buff.get());
		buff.order(ei_data == EIEndian.BIG ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
		ei_version = buff.get();
		ei_osabi = buff.get();
		ei_abiversion = buff.get();
		buff.get(ei_pad);
		e_type = buff.getShort();
		e_machine = buff.getShort();
		e_version = buff.getInt();
		if (ei_class == EIClass.ARCH_64) {
			e_entry = buff.getLong();
			e_phoff = buff.getLong();
			e_shoff = buff.getLong();
		} else {
			e_entry = buff.getInt();
			e_phoff = buff.getInt();
			e_shoff = buff.getInt();
		}
		e_flags = buff.getInt();
		e_ehsize = buff.getShort();
		e_phentsize = buff.getShort();
		e_phnum = buff.getShort();
		e_shentsize = buff.getShort();
		e_shnum = buff.getShort();
		e_shstrndx = buff.getShort();
	}

	public void write(ByteBuffer b) {
		b.put(MAGIC);
		b.put(ei_class.value);
		b.put(ei_data.value);
		b.order(ei_data == EIEndian.BIG ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
		b.put(ei_version);
		b.put(ei_osabi);
		b.put(ei_abiversion);
		b.put(ei_pad);
		b.putShort(e_type);
		b.putShort(e_machine);
		b.putInt(e_version);
		if (ei_class == EIClass.ARCH_64) {
			b.putLong(e_entry);
			b.putLong(e_phoff);
			b.putLong(e_shoff);
		} else {
			b.putInt((int) e_entry);
			b.putInt((int) e_phoff);
			b.putInt((int) e_shoff);
		}
		b.putInt(e_flags);
		b.putShort(e_ehsize);
		b.putShort(e_phentsize);
		b.putShort(e_phnum);
		b.putShort(e_shentsize);
		b.putShort(e_shnum);
		b.putShort(e_shstrndx);
	}

	public static enum EIClass {
		ARCH_32(1),
		ARCH_64(2);
		public final byte value;

		public static EIClass lookup(byte v) {
			for (EIClass c : values())
				if (c.value == v)
					return c;
			throw new RuntimeException("Bad value: " + v);
		}

		private EIClass(int v) {
			this.value = (byte) v;
		}
	}

	public static enum EIEndian {
		LITTLE(1, ByteOrder.LITTLE_ENDIAN),
		BIG(2, ByteOrder.BIG_ENDIAN);
		public final byte value;
		public final ByteOrder order;

		public static EIEndian lookup(byte v) {
			for (EIEndian c : values())
				if (c.value == v)
					return c;
			throw new RuntimeException("Bad value: " + v);
		}

		private EIEndian(int v, ByteOrder o) {
			this.value = (byte) v;
			this.order = o;
		}
	}
}
