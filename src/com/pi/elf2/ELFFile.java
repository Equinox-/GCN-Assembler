package com.pi.elf2;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.pi.ClazzStr;
import com.pi.elf2.ELFHeader.EIClass;

public class ELFFile {
	public final ELFHeader header = new ELFHeader();
	public final List<ELFComponent> data = new ArrayList<ELFComponent>();
	public ELFSectionTable sections;
	public ELFProgramTable programs;
	public String[] sectionNames;

	public long getPtr(ByteBuffer b) {
		if (header.ei_class == EIClass.ARCH_32)
			return b.getInt();
		else
			return b.getLong();
	}

	public long ptrAlign(long ptr) {
		return CUtil.align(ptr, ptrSize());
	}

	public int ptrSize() {
		return header.ei_class == EIClass.ARCH_32 ? 4 : 8;
	}

	public void putPtr(ByteBuffer b, long l) {
		if (header.ei_class == EIClass.ARCH_32)
			b.putInt((int) l);
		else
			b.putLong(l);
	}

	public void read(ByteBuffer b) {
		data.clear();
		header.read(b);
		programs = new ELFProgramTable(this, this);
		programs.offset = (int) header.e_phoff;
		programs.size = header.e_phentsize * header.e_phnum;
		programs.read(b);
		
//		System.out.println(ClazzStr.stringify("header", header));

		sections = new ELFSectionTable(this, this);
		sections.offset = (int) header.e_shoff;
		sections.size = header.e_shentsize * header.e_shnum;
		sections.read(b);

		for (ELFComponent ec : data)
			if (!(ec instanceof ELFSectionTable) && !(ec instanceof ELFProgramTable)) {
				ec.read(b);
			}
		// Move sections to the end:
		data.remove(sections);
		data.add(sections);
		// Set section names:
		sectionNames = new String[sections.table.length];
		ELFSectionHeader namesHeader = sections.table[header.e_shstrndx];
		ELFStringTable names = (ELFStringTable) namesHeader.component;
		for (int i = 0; i < sections.table.length; i++) {
			sectionNames[i] = names.get(sections.table[i].s_name);
		}
	}

	public ELFSectionHeader section(String name) {
		for (int i = 0; i < sections.table.length; i++)
			if (sectionNames[i].equals(name))
				return sections.table[i];
		return null;
	}

	public int size() {
		int head = header.e_ehsize;
		// Offsets must be padded at multiples of 4:
		for (ELFComponent ec : data) {
			ec.prewrite();
			long realsize = ec.size;
			head = (int) CUtil.align(head, ec.alignment());
			if (ec.size == 0)
				ec.offset = 0;
			else {
				ec.offset = head;
			}
			head += realsize;
		}
		System.out.println();
		return head;
	}

	public void write(ByteBuffer b) {
		// Force alignment
		size();
		header.e_shnum = (short) sections.table.length;
		header.e_shoff = sections.offset;
		header.write(b);
		for (ELFComponent ec : data) {
			ec.write(b);
		}
	}

}
