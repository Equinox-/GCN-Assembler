package com.pi.gcn.base;

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

public class MCOperand {

	public static final int LITERAL_CONSTANT = 255;

	public static GenRef decodeRef7(int src) {
		if (src < 0)
			return null;
		if (src <= 101)
			return new SGPR(src);
		switch (src) {
		case 102:
			return new RWregister(Register32.FLAT_SCRATCH_LO); // FLAT_SCR_LO
		case 103:
			return new RWregister(Register32.FLAT_SCRATCH_HI); // FLAT_SCR_HI
		case 104:
			return null; // XNACK_MASK_LO
		case 105:
			return null; // XNACK_MASK_HI
		case 106:
			return new RWregister(Register32.VCC_LO);
		case 107:
			return new RWregister(Register32.VCC_HI);
		case 108:
			return new RWregister(Register32.TBA_LO); // TBA_LO
		case 109:
			return new RWregister(Register32.TBA_HI); // TBA_HI
		case 110:
			return new RWregister(Register32.TMA_LO); // TMA_LO
		case 111:
			return new RWregister(Register32.TMA_HI); // TMA_HI
		default:
		}
		if (src <= 123)
			return null; // Trap handlers [112-123] -> [ttmp0-ttmp11]
		switch (src) {
		case 124:
			return new RWregister(Register32.M0);
		case 125:
			return null; // reserved
		case 126:
			return new RWregister(Register32.EXEC_LO);
		case 127:
			return new RWregister(Register32.EXEC_HI);
		default:
		}
		return null;
	}

	public static GenRef decodeRef8(int src) {
		if (src < 128)
			return decodeRef7(src);
		if (src == 128)
			return InlineInt.ZERO;
		if (src <= 192)
			return InlineInt.POSITIVE[src - 129];
		if (src <= 208)
			return InlineInt.NEGATIVE[src - 193];
		switch (src) {
		case 240:
			return InlineFloat.IF_P5;
		case 241:
			return InlineFloat.IF_NP5;
		case 242:
			return InlineFloat.IF_1;
		case 243:
			return InlineFloat.IF_N1;
		case 244:
			return InlineFloat.IF_2;
		case 245:
			return InlineFloat.IF_N2;
		case 246:
			return InlineFloat.IF_4;
		case 247:
			return InlineFloat.IF_N4;
		case 248:
			return InlineFloat.IF_INVERSE_2PI;
		default:
		}
		if (src <= 250)
			return new BadRef(src); // reserved
		switch (src)
		{
		case 251:
			return new ROregister(Register32.VCCZ);
		case 252:
			return new ROregister(Register32.EXECZ);
		case 253:
			return new ROregister(Register32.SCC);
		case 254:
			return new BadRef(src); // reserved
		default:
			return null; // failure
		}
	}

	public static GenRef decodeRef9(int src) {
		if (src < 256)
			return decodeRef8(src);
		if (src <= 511)
			return new VGPR(src - 256);
		return null;
	}

	public static int encodeRef(GenRef ref) {
		if (ref instanceof SGPR)
			return ((SGPR) ref).sgpr & 0xFF;
		else if (ref instanceof VGPR)
			return 256 + (((VGPR) ref).vgpr & 0xFF);
		else if (ref instanceof LiteralConstant)
			return LITERAL_CONSTANT;
		else if (ref instanceof ROregister || ref instanceof RWregister) {
			Register32 reg = null;
			if (ref instanceof ROregister)
				reg = ((ROregister) ref).register;
			else
				reg = ((RWregister) ref).register;
			switch (reg) {
			case VCC_LO:
				return 106;
			case VCC_HI:
				return 107;
			case M0:
				return 124;
			case EXEC_LO:
				return 126;
			case EXEC_HI:
				return 127;
			case VCCZ:
				return 251;
			case EXECZ:
				return 252;
			case SCC:
				return 253;
			default:
				throw new IllegalArgumentException("Can't encode refcode for " + reg);
			}
		} else if (ref instanceof InlineInt) {
			if (ref == InlineInt.ZERO)
				return 128;
			for (int i = 0; i < InlineInt.POSITIVE.length; i++)
				if (ref == InlineInt.POSITIVE[i])
					return 129 + i;
			for (int i = 0; i < InlineInt.NEGATIVE.length; i++)
				if (ref == InlineInt.NEGATIVE[i])
					return 193 + i;
		} else if (ref instanceof InlineFloat) {
			if (ref == InlineFloat.IF_P5)
				return 240;
			else if (ref == InlineFloat.IF_NP5)
				return 241;
			else if (ref == InlineFloat.IF_1)
				return 242;
			else if (ref == InlineFloat.IF_N1)
				return 243;
			else if (ref == InlineFloat.IF_2)
				return 244;
			else if (ref == InlineFloat.IF_N2)
				return 245;
			else if (ref == InlineFloat.IF_4)
				return 246;
			else if (ref == InlineFloat.IF_N4)
				return 247;
		}else if (ref instanceof BadRef) {
			return ((BadRef) ref).code;
		}
		throw new IllegalArgumentException("Can't encode refcode for " + ref);
	}
}
