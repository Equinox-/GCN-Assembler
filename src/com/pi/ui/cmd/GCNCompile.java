package com.pi.ui.cmd;

import java.io.File;

import com.pi.FileUtil;
import com.pi.gcn.encode.Lines_Mem;

public class GCNCompile {
	public static void main(String[] args) {
		GCN_State state = new GCN_State(new File(Operate.BASE + ".bin"));
		String s = new String(FileUtil.read(Operate.BASE + "_output.gcn"));
		state.setISA(Lines_Mem.fileToMem(state.insnSet, s, false));
		state.write(new File(Operate.BASE + "_output.bin"));
	}
}
