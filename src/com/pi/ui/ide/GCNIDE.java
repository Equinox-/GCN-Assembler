package com.pi.ui.ide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument.AttributeUndoableEdit;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.pi.FileUtil;
import com.pi.Utils;
import com.pi.cl.TestCL;
import com.pi.gcn.base.MC;
import com.pi.gcn.encode.Lines_Mem;
import com.pi.gcn.encode.Mem_Lines;
import com.pi.gcn.insn.Instruction;
import com.pi.gcn.insn.InstructionSet;
import com.pi.gcn.proc.Processor;
import com.pi.ui.cmd.GCN_State;

public class GCNIDE extends JFrame {
	public static final String STYLE_COMMENT = "comment";
	public static final String STYLE_OPCODE = "opcode";
	public static final Color COLOR_OPCODE = new Color(128, 0, 128);
	public static final float CODE_SIZE = 14;
	public static final float HELP_SIZE = 12;
	private static final Dimension OPCODE_HELPER_SIZE = new Dimension(150, 100);

	private static final InstructionSet instruction = new InstructionSet(Processor.valueOf("tonga"));

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		GCNIDE ide = new GCNIDE();
		ide.setVisible(true);

	}

	private static JMenuItem makeMenuItem(String name, Runnable f, KeyStroke stroke) {
		JMenuItem m = new JMenuItem(name);
		m.addActionListener((ActionEvent e) -> {
			f.run();
		});
		if (stroke != null)
			m.setAccelerator(stroke);
		return m;
	}

	private JMenuBar makeMenuBar() {
		JMenuBar m = new JMenuBar();
		{
			JMenu file = new JMenu("File");
			file.add(makeMenuItem("New", this::makeNew, KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK)));
			file.add(makeMenuItem("Open", this::open, KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK)));
			file.add(makeMenuItem("Save", this::save, KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)));
			file.add(makeMenuItem("Save As", this::saveAs,
					KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK)));
			m.add(file);
		}
		{
			JMenu compile = new JMenu("Binary");
			compile.add(makeMenuItem("Decompile", this::decompile,
					KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK)));
			compile.add(makeMenuItem("Compile", this::createBinary,
					KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK)));
			compile.add(makeMenuItem("Compile Into", this::createBinaryInto,
					KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.SHIFT_MASK | InputEvent.CTRL_MASK)));
			compile.add(
					makeMenuItem("Run", this::runBinary, KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK)));
			m.add(compile);
		}
		return m;
	}

	private File opened;
	private final JTextPane text;
	private final JTextArea lines;

	private final JScrollPane opcode_helper;
	private final JList<String> opcodes;

	private final JTextArea description;

	private void updateOpcodes(String sofar) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Instruction i : instruction.values())
			if (i.name().startsWith(sofar.toUpperCase()))
				model.addElement(i.name());
		opcodes.setModel(model);
	}

	private void modifyTip(int where) {
		try {
			String ever = this.text.getDocument().getText(0, this.text.getDocument().getLength());
			int sot = Utils.lastIndexOfAny(ever, " \t\n", where == 0 ? 0 : where - 1) + 1;
			int eot = Utils.indexOfAny(ever, " \t\n", where);
			if (eot == -1)
				eot = ever.length();
			int sol = sot == 0 ? 0 : (ever.lastIndexOf('\n', sot - 1) + 1);
			int eol = ever.indexOf('\n', sot);
			if (eol == -1)
				eol = ever.length();

			this.description.setVisible(false);
			this.opcode_helper.setVisible(false);
			if (sot == sol) {
				Rectangle r = this.text.modelToView(sot);
				this.opcode_helper.setLocation(r.x + 1, r.y + r.height + 2);
				this.opcode_helper.setVisible(true);
				String token = ever.substring(sot, eot);
				updateOpcodes(token);
				if (this.opcodes.getHeight() < OPCODE_HELPER_SIZE.getHeight())
					this.opcode_helper.setSize((int) OPCODE_HELPER_SIZE.getWidth(), this.opcodes.getHeight());
				else
					this.opcode_helper.setSize(OPCODE_HELPER_SIZE);
				this.opcode_helper.getVerticalScrollBar().setValue(0);
			} else {
				String line = ever.substring(sol, eol);
				int tok = Utils.indexOfAny(line, " \t", 0);
				if (tok == -1)
					tok = line.length();
				int comment = line.indexOf("//");
				if (comment == -1)
					comment = line.length() + 1;
				if (tok < comment) {
					String opcode = line.substring(0, tok);
					Instruction isn = instruction.valueOf(opcode);
					if (isn != null) {
						try {
							String format = isn.createBlank().format();
							description.setVisible(true);
							StringBuilder b = new StringBuilder();
							b.append(isn.name()).append(" ").append(format);
							for (String s : isn.document())
								b.append("\n").append(s);
							description.setText(b.toString());
							Rectangle r = this.text.modelToView(sol);
							description.setLocation(r.x + 1, r.y + r.height + 2);
							description.setSize(description.getPreferredSize());
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public GCNIDE() {
		super.setSize(1280, 720);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setJMenuBar(makeMenuBar());
		super.setLayout(new GridLayout(1, 1));
		this.text = new JTextPane();
		this.text.setFont(new Font(Font.MONOSPACED, 0, (int) CODE_SIZE));
		Style base = this.text.getStyle(StyleContext.DEFAULT_STYLE);
		Style comment = this.text.addStyle(STYLE_COMMENT, base);
		comment.addAttribute(StyleConstants.Foreground, new Color(0, 128, 0));
		Style opcode = this.text.addStyle(STYLE_OPCODE, base);
		opcode.addAttribute(StyleConstants.Foreground, COLOR_OPCODE);
		this.text.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				modifyTip(e.getDot());
			}
		});

		this.opcodes = new JList<>();
		this.opcodes.setForeground(COLOR_OPCODE);
		this.opcodes.setFont(text.getFont().deriveFont(HELP_SIZE));
		this.opcode_helper = new JScrollPane(opcodes);
		this.opcode_helper.setSize(OPCODE_HELPER_SIZE);
		this.opcode_helper.setVisible(false);
		this.text.add(this.opcode_helper);

		this.description = new JTextArea();
		this.description.setVisible(false);
		this.description.setOpaque(true);
		this.description.setFont(text.getFont().deriveFont(HELP_SIZE));
		this.description.setWrapStyleWord(true);
		this.description.setEditable(false);
		this.description.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.text.add(description);

		this.lines = new JTextArea();
		this.lines.setFont(this.text.getFont());
		this.lines.setBackground(Color.LIGHT_GRAY);
		this.lines.setEditable(false);

		JPanel tmp = new JPanel(new BorderLayout());
		tmp.add(this.text);
		JScrollPane jsp = new JScrollPane(tmp);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setRowHeaderView(lines);
		this.add(jsp);

		File f = new File("test/test1-Tonga.bin");
		opened = null;
		GCN_State state = new GCN_State(f);

		setSource(Mem_Lines.memToFile(state.insnSet, state.getISA(), false, false));
		this.text.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if (e instanceof AttributeUndoableEdit)
					return;
				colorText();
				refreshOffsets();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (e instanceof AttributeUndoableEdit)
					return;
				colorText();
				refreshOffsets();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				if (e instanceof AttributeUndoableEdit)
					return;
				colorText();
				refreshOffsets();
			}
		});
		SwingUtilities.invokeLater(() -> {
			refreshOffsets();
		});
	}

	private File browser(boolean open, String title) {
		JFileChooser di = new JFileChooser();
		di.setDialogTitle(title);
		if (opened != null)
			di.setCurrentDirectory(opened.getParentFile());
		else
			di.setCurrentDirectory(new File("."));
		di.setFileSelectionMode(JFileChooser.FILES_ONLY);
		di.setMultiSelectionEnabled(false);
		int out;
		if (open)
			out = di.showOpenDialog(this);
		else
			out = di.showSaveDialog(this);
		if (out == JFileChooser.APPROVE_OPTION)
			return di.getSelectedFile();
		else
			return null;
	}

	private final AtomicBoolean recolor = new AtomicBoolean();

	private void colorText() {
		recolor.set(true);
		SwingUtilities.invokeLater(() -> {
			if (!recolor.get())
				return;
			StyledDocument doc = this.text.getStyledDocument();
			try {
				String s = doc.getText(0, doc.getLength());
				int sol = 0;
				int eol = -1;
				while (eol < s.length()) {
					sol = eol + 1;
					eol = s.indexOf('\n', sol);
					if (eol == -1)
						eol = s.length();

					int comment = s.indexOf("//", sol);
					if (comment >= eol)
						comment = -1;

					int end_of_op = Utils.indexOfAny(s, " \t", sol);
					if (end_of_op >= eol)
						end_of_op = -1;

					doc.setCharacterAttributes(sol, eol - sol, doc.getStyle(StyleContext.DEFAULT_STYLE), true);
					if (comment != -1)
						doc.setCharacterAttributes(comment, eol - comment, doc.getStyle(STYLE_COMMENT), true);
					if (end_of_op != -1 && (comment == -1 || end_of_op < comment))
						doc.setCharacterAttributes(sol, end_of_op - sol, doc.getStyle(STYLE_OPCODE), true);
					else if (end_of_op == -1 && comment == -1)
						doc.setCharacterAttributes(sol, eol, doc.getStyle(STYLE_OPCODE), true);
				}
			} catch (BadLocationException e) {
			}
			recolor.set(false);
		});
	}

	private void setSource(String s) {
		this.text.setText(s);
		lines.setText("");
	}

	private void makeNew() {
		setSource("");
		opened = null;
	}

	private void open() {
		File f = browser(true, "Open");
		if (f != null) {
			opened = f;
			setSource(new String(FileUtil.read(f.getAbsolutePath())));
		}
	}

	private void save() {
		if (opened == null)
			saveAs();
		else
			FileUtil.write(opened.getAbsolutePath(), text.getText());
	}

	private void saveAs() {
		File f = browser(false, "Save");
		if (f != null) {
			opened = f;
			save();
		}
	}

	private void decompile() {
		File f = browser(true, "Open Binary");
		if (f != null) {
			opened = null;
			GCN_State state = new GCN_State(f);
			setSource(Mem_Lines.memToFile(state.insnSet, state.getISA(), false, false));
		}
	}

	private void refreshOffsets() {
		try {
			String src = text.getText();
			List<MC> mc = Lines_Mem.fileToMem(instruction, src, true);
			String[] offsets = new String[mc.size()];
			int len = 0;
			int off = 0;
			for (int i = 0; i < offsets.length; i++) {
				offsets[i] = Integer.toHexString(off);
				off += mc.get(i).sizeof();
				len = Math.max(len, offsets[i].length());
			}
			StringBuilder out = new StringBuilder(offsets.length * (len + 2));
			for (int i = 0; i < offsets.length; i++)
				out.append(Utils.padNumeral(offsets[i], len)).append(" \n");
			this.lines.setText(out.toString());
		} catch (Throwable e) {
		}
	}

	private File templateBinary, outputBinary;

	private void createBinary() {
		if (templateBinary == null) {
			File f = browser(true, "Template Binary");
			if (f != null)
				templateBinary = f;
		}
		if (templateBinary != null) {
			GCN_State state = new GCN_State(templateBinary);
			String src = text.getText();
			List<MC> mc = Lines_Mem.fileToMem(instruction, src, true);
			state.setISA(mc);
			if (outputBinary == null) {
				File out = browser(false, "Output Binary");
				if (out != null)
					outputBinary = out;
			}
			if (outputBinary != null)
				state.write(outputBinary);
		}
	}

	private void createBinaryInto() {
		templateBinary = null;
		outputBinary = null;
		createBinary();
	}

	private void runBinary() {
		createBinary();
		TestCL.main(new String[] { outputBinary.getAbsolutePath() });
	}
}
