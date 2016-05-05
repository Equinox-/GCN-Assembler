package com.pi.ui.cmd;

import java.io.File;

import com.pi.FileUtil;
import com.pi.gcn.encode.Mem_Lines;

public class GCNDecompile {
	public static void main(String[] args) {
		GCN_State state = new GCN_State(new File(Operate.BASE + ".bin"));
		String s = Mem_Lines.memToFile(state.insnSet, state.getISA(), true, false);
		FileUtil.write(Operate.BASE + "_output.gcn", s);
		System.out.println(s);
	}
}
