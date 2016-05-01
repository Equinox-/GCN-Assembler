package com.pi.gcn.proc;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Processor {
	private static final Map<String, Processor> processors = new HashMap<>();

	private static void add(String name, SubtargetFeature... features) {
		processors.put(name.toUpperCase(), new Processor(name, features));
	}

	public static Processor valueOf(String name) {
		return processors.get(name.toUpperCase());
	}

	static {
		add("bonaire", SubtargetFeature.SeaIslands, SubtargetFeature.LDSBankCount32, SubtargetFeature.ISAVersion7_0_0);
		add("tonga", SubtargetFeature.VolcanicIslands, SubtargetFeature.SGPRInitBug, SubtargetFeature.ISAVersion8_0_0);
	}

	private final EnumSet<SubtargetFeature> features;
	private final String name;

	private Processor(String name, SubtargetFeature... features) {
		this.name = name;
		this.features = EnumSet.copyOf(Arrays.asList(features));
	}

	public Set<SubtargetFeature> features() {
		return Collections.unmodifiableSet(features);
	}

	public boolean has(SubtargetFeature f) {
		return features.contains(f);
	}

	public String name() {
		return name;
	}
}
