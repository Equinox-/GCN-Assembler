package com.pi;

import java.util.Iterator;
import java.util.List;

public class Utils {
	public static boolean removeIC(List<String> tokens, String token) {
		boolean found = false;
		for (Iterator<String> it = tokens.iterator(); it.hasNext();) {
			if (it.next().equalsIgnoreCase(token)) {
				found = true;
				it.remove();
			}
		}
		return found;
	}

	public static int indexOfAny(String s, String of, int from) {
		int at = -1;
		for (int c = 0; c < of.length(); c++) {
			int me = s.indexOf(of.charAt(c), from);
			if (me != -1 && (at == -1 || me < at))
				at = me;
		}
		return at;
	}

	public static int lastIndexOfAny(String s, String of, int from) {
		int at = -1;
		for (int c = 0; c < of.length(); c++) {
			int me = s.lastIndexOf(of.charAt(c), from);
			if (me != -1 && (at == -1 || me > at))
				at = me;
		}
		return at;
	}

	public static String padNumeral(String s, int n) {
		while (s.length() < n)
			s = "0" + s;
		return s;
	}

	public static String padString(String r, int n) {
		StringBuilder s = new StringBuilder(n);
		s.append(r);
		while (s.length() < n)
			s.append(' ');
		return s.toString();
	}
}
