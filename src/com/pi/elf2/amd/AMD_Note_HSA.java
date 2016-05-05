package com.pi.elf2.amd;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public enum AMD_Note_HSA {
	HSA_CODE_OBJECT_VERSION(1, new String[] { "major", "minor" }, new int[] { 4, 4 }),
	HSA_HSAIL(2, new String[] { "major", "minor", "profile", "machine", "default_flt_round" },
			new int[] { 4, 4, 1, 1, 1 }),
	HSA_ISA(3, new String[] { "vendor_name_size", "architecture_name_size", "major", "minor", "stepping" },
			new int[] { 2, 2, 4, 4, 4 }),
	HSA_PRODUCER(4, new String[] { "producer_name_size", "reserved", "producer_major", "producer_minor" },
			new int[] { 2, 2, 4, 4 }),
	HSA_PRODUCER_OPTIONS(5, new String[] { "producer_options_size" }, new int[] { 2 }),
	HSA_EXTENSION(6, new String[0], new int[0]),
	HSA_HLDEBUG_DEBUG(101, new String[0], new int[0]),
	HSA_HLDEBUG_TARGET(102, new String[0], new int[0]);

	public final int value;

	public static AMD_Note_HSA lookup(int v) {
		for (AMD_Note_HSA c : values())
			if (c.value == v)
				return c;
		throw new RuntimeException("Bad value: " + v);
	}

	private final String[] keys;
	private final int[] sizeof;

	private AMD_Note_HSA(int v, String[] keys, int[] sizes) {
		this.value = (int) v;
		this.keys = keys;
		this.sizeof = sizes;
	}

	public Map<String, Object> decode(ByteBuffer b) {
		Map<String, Object> mp = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			long val = 0;
			switch (sizeof[i]) {
			case 1:
				val = b.get() & 0xFF;
				break;
			case 2:
				val = b.getShort() & 0xFFFF;
				break;
			case 4:
				val = b.getInt() & 0xFFFFFFFFl;
				break;
			case 8:
				val = b.getLong();
				break;
			default:
				throw new IllegalArgumentException("Bad size");
			}
			mp.put(keys[i], val);
		}
		for (int i = 0; i < keys.length; i++)
			if (keys[i].endsWith("_size")) {
				String strn = keys[i].substring(0, keys[i].length() - 5);
				int count = ((Number) mp.get(keys[i])).intValue();
				count = Math.min(count, b.remaining());
				byte[] bts = new byte[count];
				b.get(bts);
				mp.put(strn, new String(bts).trim());
			}
		return mp;
	}
}