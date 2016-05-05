package com.pi.gcn.proc;

import static com.pi.gcn.proc.ProcessorClass.FullSpeedModel;
import static com.pi.gcn.proc.ProcessorClass.QuarterSpeedModel;
import static com.pi.gcn.proc.SubtargetFeature.FastFMAF32;
import static com.pi.gcn.proc.SubtargetFeature.ISAVersion7_0_0;
import static com.pi.gcn.proc.SubtargetFeature.ISAVersion7_0_1;
import static com.pi.gcn.proc.SubtargetFeature.ISAVersion8_0_0;
import static com.pi.gcn.proc.SubtargetFeature.ISAVersion8_0_1;
import static com.pi.gcn.proc.SubtargetFeature.ISAVersion8_0_3;
import static com.pi.gcn.proc.SubtargetFeature.LDSBankCount16;
import static com.pi.gcn.proc.SubtargetFeature.LDSBankCount32;
import static com.pi.gcn.proc.SubtargetFeature.SGPRInitBug;
import static com.pi.gcn.proc.SubtargetFeature.SeaIslands;
import static com.pi.gcn.proc.SubtargetFeature.SouthernIslands;
import static com.pi.gcn.proc.SubtargetFeature.VolcanicIslands;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Processor {
	private static final Map<String, Processor> nameToProc = new HashMap<>();

	public static Processor valueOf(String name) {
		return nameToProc.get(name.toUpperCase());
	}

	// Taken from the LLVM spec
	// Southern Islands
	public static final Processor SI = new Processor(FullSpeedModel, SouthernIslands, FastFMAF32);
	public static final Processor TAHITI = new Processor(FullSpeedModel, SouthernIslands, FastFMAF32);
	public static final Processor VERDE = new Processor(QuarterSpeedModel, SouthernIslands);
	public static final Processor PITCAIRN = new Processor(QuarterSpeedModel, SouthernIslands);
	public static final Processor OLAND = new Processor(QuarterSpeedModel, SouthernIslands);
	public static final Processor HAINAN = new Processor(QuarterSpeedModel, SouthernIslands);

	// Sea Islands
	public static final Processor BONAIRE = new Processor(QuarterSpeedModel, SeaIslands, LDSBankCount32,
			ISAVersion7_0_0);
	public static final Processor KABINI = new Processor(QuarterSpeedModel, SeaIslands, LDSBankCount16);
	public static final Processor KAVERI = new Processor(QuarterSpeedModel, SeaIslands, LDSBankCount32,
			ISAVersion7_0_0);
	public static final Processor HAWAII = new Processor(FullSpeedModel, SeaIslands, LDSBankCount32, ISAVersion7_0_1);
	public static final Processor MULLINS = new Processor(QuarterSpeedModel, SeaIslands, LDSBankCount16);

	// Volcanic Islands
	public static final Processor TONGA = new Processor(QuarterSpeedModel, VolcanicIslands, SGPRInitBug,
			ISAVersion8_0_0, LDSBankCount32);
	public static final Processor ICELAND = new Processor(QuarterSpeedModel, VolcanicIslands, SGPRInitBug,
			ISAVersion8_0_0, LDSBankCount32);
	public static final Processor CARRIZO = new Processor(QuarterSpeedModel, VolcanicIslands, ISAVersion8_0_1,
			LDSBankCount32);
	public static final Processor FIJI = new Processor(QuarterSpeedModel, VolcanicIslands, ISAVersion8_0_3,
			LDSBankCount32);
	public static final Processor STONEY = new Processor(QuarterSpeedModel, VolcanicIslands, ISAVersion8_0_1,
			LDSBankCount16);

	static {
		// Register fields:
		int count = 0;
		for (Field f : Processor.class.getFields()) {
			if ((f.getModifiers() & Modifier.STATIC) == Modifier.STATIC && f.getType().equals(Processor.class)) {
				try {
					Processor p = (Processor) f.get(null);
					p.cacheName = f.getName().toLowerCase();
					nameToProc.put(p.cacheName.toUpperCase(), p);
					count++;
				} catch (Exception e) {
				}
			}
		}
		if (count != 16) {
			throw new IllegalStateException("Failed to register processors");
		}
	}

	private String cacheName;
	private final EnumSet<SubtargetFeature> features;
	public final ProcessorClass speed;

	private Processor(ProcessorClass speed, SubtargetFeature... features) {
		this.speed = speed;
		this.features = EnumSet.copyOf(Arrays.asList(features));
	}

	public Set<SubtargetFeature> features() {
		return Collections.unmodifiableSet(features);
	}

	public boolean has(SubtargetFeature f) {
		return features.contains(f);
	}

	public String name() {
		return cacheName;
	}
}
