package asmCodeGenerator;

import static asmCodeGenerator.codeStorage.ASMOpcode.Jump;
import static asmCodeGenerator.codeStorage.ASMOpcode.JumpTrue;
import static asmCodeGenerator.codeStorage.ASMOpcode.Label;
import static asmCodeGenerator.codeStorage.ASMOpcode.Printf;
import static asmCodeGenerator.codeStorage.ASMOpcode.PushD;

import asmCodeGenerator.runtime.MemoryManager;
import parseTree.ParseNode;
import parseTree.nodeTypes.NewlineNode;
import parseTree.nodeTypes.PrintStatementNode;
import parseTree.nodeTypes.SpaceNode;
import parseTree.nodeTypes.TabNode;
import semanticAnalyzer.types.Array;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;
import asmCodeGenerator.ASMCodeGenerator.CodeVisitor;
import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.runtime.RunTime;


import static asmCodeGenerator.codeStorage.ASMOpcode.*;

public class PrintStatementGenerator {
	private static final char ARRAY_FORMATTER_OPEN_BRACKET = '[';
	private static final char ARRAY_FORMATTER_CLOSE_BRACKET = ']';
	private static final char ARRAY_FORMATTER_DELIMITER = ',';
	
	ASMCodeFragment code;
	ASMCodeGenerator.CodeVisitor visitor;
	
	
	public PrintStatementGenerator(ASMCodeFragment code, CodeVisitor visitor) {
		super();
		this.code = code;
		this.visitor = visitor;
	}

	public void generate(PrintStatementNode node) {
		for(ParseNode child : node.getChildren()) {
			if(child instanceof NewlineNode || child instanceof SpaceNode || child instanceof TabNode) {
				ASMCodeFragment childCode = visitor.removeVoidCode(child);
				code.append(childCode);
			}
			else if (child.getType() instanceof Array) {
				appendArrayPrintCode(child);
			}
			else {
				appendPrintCode(child);
			}
		}
	}

	private void appendPrintCode(ParseNode node) {
		String format = printFormat(node.getType());
		code.append(visitor.removeValueCode(node));

		convertToStringIfBoolean(node);
		convertToStringValueIfString(node);
		code.add(PushD, format);

		code.add(Printf);


	}
	private void appendArrayPrintCode(ParseNode node) {
		if(!(node.getType() instanceof Array)) {
			return;
		}
		
		int size = 3; 
		int subtypeSize = 4; 
		String format = printFormat(PrimitiveType.INTEGER); 
		Labeller labeller = new Labeller("stack-temp"); 
		String baseAddressHolderLabel = labeller.newLabel("baseAddress");
		
		
		code.append(visitor.removeValueCode(node));
		
		code.add(DLabel, baseAddressHolderLabel);
		code.add(DataI, 0); //clear 4 bytes with all zeroes
		code.add(PushD, baseAddressHolderLabel); 
		code.add(Exchange); 
		code.add(StoreI); 
		
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_OPEN_BRACKET);
		for (int i = 0; i < size; i++) {
			// calculate address to load from: baseAddress + (i * offset)
			code.add(PushD, baseAddressHolderLabel); 
			code.add(LoadI);
			code.add(PushI, 16);
			code.add(Add);
			code.add(PushI,  + i*subtypeSize);
			code.add(Add);

			// load the value from the calculated address
			code.add(LoadI); //todo: for now only for integer
			code.add(PushD, format);
			code.add(Printf);
			
			if(i != size-1) {
				//print a delimiter after each element in the array, except for the last element
				appendArrayFormatterPrintCode(ARRAY_FORMATTER_DELIMITER);
			}
		}
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_CLOSE_BRACKET);
		
	}
	private void appendArrayFormatterPrintCode(char c) {
		String format = printFormat(PrimitiveType.CHARACTER);
		
		if(c == ARRAY_FORMATTER_CLOSE_BRACKET) {
			appendArraySpacingPrintCode();
		}
		
		code.add(PushI, c); 
		code.add(PushD, format); 
		code.add(Printf);
		
		appendArraySpacingPrintCode();
	}
	private void appendArraySpacingPrintCode() {
		String format = printFormat(PrimitiveType.CHARACTER);
		code.add(PushI, 32);
		code.add(PushD, format);
		code.add(Printf);
	}
	
	
	
	private void convertToStringIfBoolean(ParseNode node) {
		if(node.getType() != PrimitiveType.BOOLEAN) {
			return;
		}
		
		Labeller labeller = new Labeller("print-boolean");
		String trueLabel = labeller.newLabel("true");
		String endLabel = labeller.newLabel("join");

		code.add(JumpTrue, trueLabel);
		code.add(PushD, RunTime.BOOLEAN_FALSE_STRING);
		code.add(Jump, endLabel);
		code.add(Label, trueLabel);
		code.add(PushD, RunTime.BOOLEAN_TRUE_STRING);
		code.add(Label, endLabel);
	}

	private void convertToStringValueIfString(ParseNode node) {
		if (node.getType() != ReferenceType.STRING) {
			return;
		}

		int offset = 4 + 4 + 4;
		code.add(PushI, offset);
		code.add(Add);
	}


	private static String printFormat(Type type) {
		assert type instanceof PrimitiveType || type instanceof ReferenceType || type instanceof Array;

		if (type instanceof PrimitiveType) {
			switch((PrimitiveType)type) {
				case INTEGER:   return RunTime.INTEGER_PRINT_FORMAT;
				case BOOLEAN:   return RunTime.BOOLEAN_PRINT_FORMAT;
				case FLOAT:     return RunTime.FLOAT_PRINT_FORMAT;
				case CHARACTER: return RunTime.CHARACTER_PRINT_FORMAT;
				default:
					assert false : "Type " + type + " unimplemented in PrintStatementGenerator.printFormat()";
					return "";
			}
		} else if (type instanceof Array) {
			// Assuming that all arrays will use the same print format
			return "";
		} else {
			switch((ReferenceType)type) {
				case STRING:    return RunTime.STRING_PRINT_FORMAT;
				default:
					assert false : "Type " + type + " unimplemented in PrintStatementGenerator.printFormat()";
					return "";
			}
		}
	}
}
