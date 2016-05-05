package com.pi.elf2.amd;

import com.pi.gcn.proc.Processor;

public enum AMDMachine {
	/** < R600 GPU ISA */
	CAL_TARGET_600(0),
	/** < RV610 GPU ISA */
	CAL_TARGET_610(1),
	/** < RV630 GPU ISA */
	CAL_TARGET_630(2),
	/** < RV670 GPU ISA */
	CAL_TARGET_670(3),
	/** < R700 class GPU ISA */
	CAL_TARGET_7XX(4),
	/** < RV770 GPU ISA */
	CAL_TARGET_770(5),
	/** < RV710 GPU ISA */
	CAL_TARGET_710(6),
	/** < RV730 GPU ISA */
	CAL_TARGET_730(7),
	/** < CYPRESS GPU ISA */
	CAL_TARGET_CYPRESS(8),
	/** < JUNIPER GPU ISA */
	CAL_TARGET_JUNIPER(9),
	/** < REDWOOD GPU ISA */
	CAL_TARGET_REDWOOD(10),
	/** < CEDAR GPU ISA */
	CAL_TARGET_CEDAR(11),
	/** < SUMO GPU ISA */
	CAL_TARGET_SUMO(12),
	/** < SUPERSUMO GPU ISA */
	CAL_TARGET_SUPERSUMO(13),
	/** < WRESTLER GPU ISA */
	CAL_TARGET_WRESTLER(14),
	/** < CAYMAN GPU ISA */
	CAL_TARGET_CAYMAN(15),
	/** < KAUAI GPU ISA */
	CAL_TARGET_KAUAI(16),
	/** < BARTS GPU ISA */
	CAL_TARGET_BARTS(17),
	/** < TURKS GPU ISA */
	CAL_TARGET_TURKS(18),
	/** < CAICOS GPU ISA */
	CAL_TARGET_CAICOS(19),
	/** < TAHITI GPU ISA */
	CAL_TARGET_TAHITI(20, Processor.TAHITI),
	/** < PITCAIRN GPU ISA */
	CAL_TARGET_PITCAIRN(21, Processor.PITCAIRN),
	/** < CAPE VERDE GPU ISA */
	CAL_TARGET_CAPEVERDE(22, Processor.VERDE),
	/** < DEVASTATOR GPU ISA */
	CAL_TARGET_DEVASTATOR(23),
	/** < SCRAPPER GPU ISA */
	CAL_TARGET_SCRAPPER(24),
	/** < OLAND GPU ISA */
	CAL_TARGET_OLAND(25, Processor.OLAND),
	/** < BONAIRE GPU ISA */
	CAL_TARGET_BONAIRE(26, Processor.BONAIRE),
	/** < KALINDI GPU ISA */
	CAL_TARGET_KALINDI(29),
	/** CPU without SSE3 */
	CPU_NO_SSE3(2002 - 1001),
	/** CPU with SSE3 */
	CPU_YES_SSE3(2003 - 1001);
	public final short value;
	public final Processor proc;

	public static AMDMachine lookup(short v) {
		for (AMDMachine c : values())
			if (c.value == v)
				return c;
		throw new RuntimeException("Bad value: " + v);
	}

	private AMDMachine(int v) {
		this(v, null);
	}

	private AMDMachine(int v, Processor p) {
		this.value = (short) (v + 1001);
		this.proc = p;
	}
}