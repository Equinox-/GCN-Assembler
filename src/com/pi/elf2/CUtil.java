package com.pi.elf2;

public class CUtil {

	public static long align(long n, long to) {
		long rem = n % to;
		if (rem != 0)
			n += to - rem;
		return n;
	}

	public static String cstr(byte[] data, int offset) {
		if (data.length == 0)
			return "";
		int i = offset;
		while (data[i] != '\0')
			i++;
		return new String(data, offset, i - offset);
	}

	public static byte[] pad(byte[] data, long len) {
		byte[] out = new byte[(int) len];
		System.arraycopy(data, 0, out, 0, data.length);
		return out;
	}
}
