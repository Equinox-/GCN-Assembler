package com.pi;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;

public class FileUtil {
	public static byte[] read(String path) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			FileInputStream fin = new FileInputStream(path);
			{
				byte[] temp = new byte[512];
				int c;
				while ((c = fin.read(temp)) > 0)
					out.write(temp, 0, c);
				fin.close();
				out.close();
			}
			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void write(String path, byte[] data) {
		try {
			FileOutputStream fout = new FileOutputStream(path);
			fout.write(data);
			fout.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void write(String path, String data) {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(path));
			w.write(data);
			w.close();
		} catch (Exception e) {
		}
	}

	public static void write(String path, ByteBuffer data) {
		byte[] dd = new byte[data.capacity()];
		data.position(0);
		data.get(dd);
		write(path, dd);
	}
}
