# GCN-Assembler
Java tools to assemble and disassemble AMD GCN ISA.  This tool has only gone through significant tests on the AMD "Tonga" GPU (Most R9 3x GPUs).  This is the "Volcanic Islands" or "ISA 8.0.0" version; this tool may or may not work with other versions; it has only 
been tested with Capeverde and Pitcairn.

## OpenCL Binary Manager
This tool allows the raw GCN code located inside an existing compiled binary to be modified.  It is located in [com.pi.ui.cmd.GCN_State](src/com/pi/ui/cmd/GCN_State.java).

## GCN Disassembler
The command line disassembler is located at [com.pi.ui.cmd.GCNDecompile](src/com/pi/ui/cmd/GCNDecompile.java).  It takes a single-device binary generated by `clCompileProgram` and produces texual assembly from the GCN.

## GCN Assembler
The command line assembler located at [com.pi.ui.cm.GCNCompile](src/com/pi/ui/cmd/GCNCompile.java).  It takes a template binary that contains all the kernel metadata and textual assembly to produce a binary output file for use with `clCreateProgramWithBinary`.

## GCN IDE
A fairly rudimentary IDE for writing GCN that allows the user to see the argument format and other documentation of the AMD ISA.
