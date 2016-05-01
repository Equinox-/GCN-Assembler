package com.pi.elf2;

import java.nio.ByteBuffer;

public abstract class ELFComponent {
	protected final ELFFile file;
	public final Object creator;

	public long size, offset;

	protected ELFComponent(Object creator, ELFFile f) {
		this.file = f;
		this.creator = creator;
		this.file.data.add(this);
	}

	public final long alignment() {
		long align = 0;
		if (creator instanceof ELFSectionHeader)
			align = ((ELFSectionHeader) creator).s_addralign;
		if (this instanceof ELFSectionTable || this instanceof ELFProgramTable)
			align = Math.max(file.ptrSize(), align);
		align = Math.max(1, align);
		return align;
	}

	/**
	 * Sets the <em>length</em> for this element from the data.
	 */
	public abstract void prewrite();

	public final void read(ByteBuffer b) {
		int op = b.position();
		int ol = b.limit();
		b.position((int) offset);
		b.limit((int) (offset + size));

		readInternal(b);

		b.limit(ol);
		b.position(op);
	}

	public abstract void readInternal(ByteBuffer b);

	public final void write(ByteBuffer b) {
		int op = b.position();
		int ol = b.limit();
		b.position((int) offset);
		b.limit((int) (offset + size));

		writeInternal(b);

		if (op < b.limit()) {
			b.position(op);
			b.limit(ol);
		} else {
			b.limit(ol);
			b.position(op);
		}
	}

	public abstract void writeInternal(ByteBuffer b);
}
