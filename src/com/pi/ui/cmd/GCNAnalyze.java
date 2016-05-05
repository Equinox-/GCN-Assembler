package com.pi.ui.cmd;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.pi.gcn.base.MC;
import com.pi.gcn.data.GenSrc;
import com.pi.gcn.data.SGPR;
import com.pi.gcn.data.VGPR;
import com.pi.gcn.genmem.DS;
import com.pi.gcn.genmem.FLAT;
import com.pi.gcn.salu.SALU;
import com.pi.gcn.smem.SMEM_Base;
import com.pi.gcn.valu.VOP1;
import com.pi.gcn.valu.VOP2;
import com.pi.gcn.valu.vop3.VOP3_Base;
import com.pi.gcn.valu.vop3.VOP3b_Base;
import com.pi.kernel.ComputePgmResourceRegister;

public class GCNAnalyze {
	public static void main(String[] args) {
		GCN_State state = new GCN_State(new File(Operate.BASE + ".bin"));
		KernelStats stats = analyze(state.getISA());
		System.out.println("ISA size is " + (state.code.length / 1024) + " KB\t" + state.code.length + " bytes");
		System.out.println("I say: ");
		System.out.println(stats);
		System.out.println("They say: ");
		System.out.println("SGPR: " + state.kc.reserved_sgpr_count + "," + state.kc.reserved_sgpr_first + ","
				+ state.kc.wavefront_sgpr_count);
		System.out.println("VGPR: " + state.kc.reserved_vgpr_count + "," + state.kc.reserved_vgpr_first + ","
				+ state.kc.workitem_vgpr_count);
		System.out.println("Resource register says: ");
		Map<ComputePgmResourceRegister, Long> mapping = ComputePgmResourceRegister
				.decodeMap(state.kc.compute_pgm_resource_registers);
		System.out.println("SGPR: " + mapping.get(ComputePgmResourceRegister.SGPRS));
		System.out.println("VGPR: " + mapping.get(ComputePgmResourceRegister.VGPRS));
	}

	public static KernelStats analyze(List<MC> code) {
		KernelStats s = new KernelStats();
		for (MC m : code) {
			if (m instanceof SALU) {
				try {
					s.use(((SALU) m).src0(), 1);
				} catch (Exception e) {
				}
				try {
					s.use(((SALU) m).src1(), 1);
				} catch (Exception e) {
				}
			} else if (m instanceof VOP1) {
				s.use(((VOP1) m).src0(), 1);
				s.use(((VOP1) m).vDest(), 1);
			} else if (m instanceof VOP2) {
				s.use(((VOP2) m).src0(), 1);
				s.use(((VOP2) m).src1(), 1);
				s.use(((VOP2) m).dest(), 1);
			} else if (m instanceof VOP3_Base) {
				s.use(((VOP3_Base) m).src0(), 1);
				s.use(((VOP3_Base) m).src1(), 1);
				s.use(((VOP3_Base) m).src2(), 1);
				s.use(((VOP3_Base) m).vDest(), 1);
				if (m instanceof VOP3b_Base)
					s.use(((VOP3b_Base) m).sDest(), 1);
			} else if (m instanceof SMEM_Base) {
				s.use(((SMEM_Base) m).data(), 1);
				s.use(((SMEM_Base) m).base(), 1);
				if (!((SMEM_Base) m).IMM())
					s.use(((SMEM_Base) m).offset_SGPR(), 1);
			} else if (m instanceof FLAT) {
				s.use(((FLAT) m).addrSource(), 1);
				s.use(((FLAT) m).dest(), 1);
				s.use(((FLAT) m).srcData(), 1);
			} else if (m instanceof DS) {
				s.use(((DS) m).addrSrc(), 1);
				s.use(((DS) m).data0(), 1);
				s.use(((DS) m).data1(), 1);
			}
		}
		return s;
	}

	private static class KernelStats {
		public final boolean[] vgprs_in_use = new boolean[1024];
		public final boolean[] sgprs_in_use = new boolean[1024];

		public void use(GenSrc s, int count) {
			if (s instanceof SGPR) {
				SGPR ss = (SGPR) s;
				for (int i = 0; i < count; i++)
					sgprs_in_use[i + ss.sgpr & 0xFF] = true;
			} else if (s instanceof VGPR) {
				VGPR vv = (VGPR) s;
				for (int i = 0; i < count; i++)
					vgprs_in_use[i + vv.vgpr & 0xFF] = true;
			}
		}

		private static String usage(boolean[] bts) {
			int lf = -1;
			StringBuilder s = new StringBuilder();
			for (int i = 0; i < bts.length; i++) {
				if (!bts[i]) {
					int count = i - lf - 1;
					if (count > 0) {
						s.append(lf + 1);
						if (count > 1)
							s.append('-').append(i - 1);
						s.append(',');
					}
					lf = i;
				}
			}
			return s.toString();
		}

		@Override
		public String toString() {
			return "SGPR: " + usage(sgprs_in_use) + "\nVGPR: " + usage(vgprs_in_use);
		}
	}
}
