package com.pi.gcn.base;

import java.util.HashMap;
import java.util.Map;

import com.pi.gcn.data.BadRef;
import com.pi.gcn.data.GenRef;
import com.pi.gcn.data.InlineFloat;
import com.pi.gcn.data.InlineInt;
import com.pi.gcn.data.LiteralConstant;
import com.pi.gcn.data.ROregister;
import com.pi.gcn.data.RWregister;
import com.pi.gcn.data.Register32;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;

public class RefEncoding {
	private static final Map<String, GenRef> cache = new HashMap<>();

	static {
		for (Register32 r : Register32.values())
			if (r.rw)
				encode(new RWregister(r));
			else
				encode(new ROregister(r));
		encode(InlineInt.ZERO);
		for (InlineInt i : InlineInt.POSITIVE)
			encode(i);
		for (InlineInt i : InlineInt.NEGATIVE)
			encode(i);
		encode(InlineFloat.IF_P5);
		encode(InlineFloat.IF_NP5);
		encode(InlineFloat.IF_1);
		encode(InlineFloat.IF_N1);
		encode(InlineFloat.IF_2);
		encode(InlineFloat.IF_N2);
		encode(InlineFloat.IF_4);
		encode(InlineFloat.IF_N4);
	}

	public static GenRef decode(String s) {
		String sl = s.toLowerCase();
		// This will always hit register, inline ints, and inline floats.
		GenRef cst = cache.get(sl);
		if (cst != null)
			return cst;
		if (sl.startsWith("v"))
			return new VGPR(Integer.parseInt(s.substring(1)));
		else if (sl.startsWith("s"))
			return new SGPR(Integer.parseInt(s.substring(1)));
		else if (sl.startsWith("0x"))
			return new LiteralConstant((int) Long.parseLong(s.substring(2), 16));
		else if (sl.startsWith("br_"))
			return new BadRef(Integer.parseInt(s.substring(3)));
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends GenRef> T decode(String s, Class<T> clazz) {
		Object o = decode(s);
		if (!clazz.isInstance(o))
			throw new IllegalArgumentException("\"" + s + "\" isn't a valid " + clazz.getSimpleName());
		return (T) o;
	}

	public static String encode(GenRef r) {
		String s = encodeInternal(r);
		String sl = s.toLowerCase();
		if (!cache.containsKey(sl))
			cache.put(sl, r);
		return s;
	}

	private static String encodeInternal(GenRef r) {
		if (r instanceof VGPR)
			return "v" + (((VGPR) r).vgpr & 0xFF);
		else if (r instanceof SGPR)
			return "s" + (((SGPR) r).sgpr & 0xFF);
		else if (r instanceof InlineInt)
			return "" + ((InlineInt) r).constant;
		else if (r instanceof InlineFloat)
			return "" + ((InlineFloat) r).constant;
		else if (r instanceof ROregister)
			return ((ROregister) r).register.name();
		else if (r instanceof RWregister)
			return ((RWregister) r).register.name();
		else if (r instanceof LiteralConstant)
			return "0x" + Integer.toHexString(((LiteralConstant) r).constant);
		else if (r instanceof BadRef)
			return "br_" + ((BadRef) r).code;
		return null;
	}
}
