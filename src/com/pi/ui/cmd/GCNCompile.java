package com.pi.ui.cmd;

import java.io.File;

import com.pi.FileUtil;
import com.pi.cl.Module;
import com.pi.gcn.encode.Lines_Mem;

public class GCNCompile {
	private static final String BASE = "test/" + Module.PREFIX + "-Tonga";

	public static void main(String[] args) {
		GCN_State state = new GCN_State(new File(BASE + ".bin"));
		String s = new String(FileUtil.read(BASE + "2.gcn"));
		state.setISA(Lines_Mem.fileToMem(state.insnSet, s, false));
		state.write(new File(BASE + "2.bin"));
	}
}
