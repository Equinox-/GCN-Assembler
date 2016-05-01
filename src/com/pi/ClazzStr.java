package com.pi;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pi.elf2.ELFNote;
import com.pi.elf2.amd.AMD_Note;
import com.pi.kernel.Bitfield;
import com.pi.kernel.KernelCodeProperties;

public class ClazzStr {
	public static final Set<Object> open = new HashSet<>();

	public static final String hexEncode(byte[] data) {
		StringBuilder sb = new StringBuilder(data.length * 2);
		for (byte b : data)
			sb.append(Integer.toHexString(b & 0xFF));
		return sb.toString();
	}

	public static String stringify(String prefix, Object o) throws Exception {
		if (o == null)
			return prefix + " = null\n";
		if (!open.add(o))
			return prefix + " -> loop\n";
		try {
			if (o.getClass().isPrimitive() || Enum.class.isAssignableFrom(o.getClass())
					|| Number.class.isAssignableFrom(o.getClass())) {
				return prefix + " = " + o + "\n";
			} else if (o.getClass().equals(String.class)) {
				String tmp = o.toString();
				tmp = tmp.replace("\n", "\\n");
				tmp = tmp.replace("\t", "\\t");
				tmp = tmp.replace("\r", "\\r");
				tmp = tmp.replace("\0", "\\0");
				return prefix + " = \"" + tmp + "\"\n";
			}
			Class<?> c = o.getClass();
			List<Field> fs = new ArrayList<>();
			for (Field f : c.getDeclaredFields())
				fs.add(f);
			for (Field f : c.getSuperclass().getDeclaredFields())
				fs.add(f);
			StringBuilder s = new StringBuilder();
			s.append(prefix + " = " + o.getClass().getSimpleName() + "\n");
			if (o instanceof ELFNote) {
				ELFNote eo = (ELFNote) o;
				if (eo.name.equals("AMD")) {
					try {
						AMD_Note type = AMD_Note.lookup(eo.type);
						s.append(prefix + " = " + type + " -> "
								+ type.decode(ByteBuffer.wrap(eo.desc).order(ByteOrder.LITTLE_ENDIAN)) + "\n");
					} catch (Exception e) {
					}
				}
			}
			for (Field f : fs) {
				if ((f.getModifiers() & Modifier.STATIC) != 0)
					continue;
				f.setAccessible(true);
				String line = prefix + "." + f.getName();
				if (f.getType().equals(byte[].class)) {
					s.append(line + " = byte[" + ((byte[]) f.get(o)).length + "]\n");
					// s.append(line + " = \"" + hexEncode((byte[]) f.get(o)) +
					// "\"\n");
				} else if (f.getType().isArray()) {
					Object obj = f.get(o);
					if (obj == null) {
						s.append(line + " = null\n");
					} else {
						int len = Array.getLength(obj);
						for (int i = 0; i < len; ++i) {
							Object e = Array.get(obj, i);
							String aline = line + "[" + i + "]";
							s.append(stringify(aline, e));
						}
					}
				} else {
					Bitfield bf = f.getAnnotation(Bitfield.class);
					if (bf != null) {
						Method m = bf.decoder().getMethod("decode", f.getType());
						s.append(line + " = " + m.invoke(null, f.get(o)) + "\n");
					}
					s.append(stringify(line, f.get(o)));
				}
			}
			return s.toString();
		} finally {
			open.remove(o);
		}
	}
}
