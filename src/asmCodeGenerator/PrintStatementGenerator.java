package asmCodeGenerator;

import static asmCodeGenerator.Macros.*;
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


import java.lang.reflect.AccessibleObject;

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

		convertToStringIfBoolean(node.getType());
		convertToStringValueIfString(node.getType());
		code.add(PushD, format);
		code.add(Printf);
	}

	private void isMultiDimension(String baseArray){
		loadAddress(baseArray);
		code.add(PushI, 4);
		code.add(Add);
		code.add(LoadI);

		code.add(PushI, 2);
		code.add(Subtract);
	}

	private void createAndSaveAddressToLabel(String label){
		code.add(DLabel, label);
		code.add(DataZ, 4);
		code.add(PushD, label);
		code.add(Exchange);
		code.add(StoreI);
	}

	private void loadAddress(String baseArray){
		code.add(PushD, baseArray);
		code.add(LoadI);
	}

	private void appendArrayPrintCode(ParseNode node){
		Labeller labeller = new Labeller("arrayPrinter");
		String baseArray = labeller.newLabel("baseAddress");
		String checkMultiExit = labeller.newLabel("isMultiExit");
		String joinLabel = labeller.newLabel("join");
		Type type = node.getType().getSubtype();

		code.append(visitor.removeValueCode(node));
		createAndSaveAddressToLabel(baseArray);

		isMultiDimension(checkMultiExit);
		code.add(JumpNeg, "print-OneD-Array");




		code.add(Label, "print-OneD-Array");
		printOneDimensional(baseArray, type);


	}

	private void printOneDimensional(String baseAddress, Type subtype){

		appendArrayFormatterPrintCode(ARRAY_FORMATTER_OPEN_BRACKET);
		code.add(PushI, 0);
		code.add(Label, "startLoop");
		code.add(Duplicate);
		getLength(baseAddress);
		code.add(Subtract);
		code.add(JumpFalse, "exit");

		printElements(baseAddress, subtype);

		code.add(PushI, 1);
		code.add(Add);

		printDelimiter(baseAddress);
		code.add(Jump, "startLoop");
		code.add(Label, "exit");
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_CLOSE_BRACKET);

	}

	private void printDelimiter(String baseAddress){
		//Needs, loop count on top of stack to work, loop starts from 1 to <= array size
		code.add(Duplicate);//[1,1]
		getLength(baseAddress);//[1,1,3]
		code.add(Subtract);//[1,-2]
		code.add(JumpTrue, "printDelimiter");
		code.add(Jump, "delimiterExit");
		code.add(Label, "printDelimiter");
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_DELIMITER);
		code.add(Label, "delimiterExit");
	}

	private void printElements(String baseAddress, Type subtype) {
		code.add(Duplicate);//[1,1]
		loadAddress(baseAddress);//[1,1, address]
		code.add(PushI, 16); //[1,1,address, 16];
		code.add(Add);//[1,1,address+16]
		code.add(Exchange);//[1,address+16,1]
		code.add(PushI, subtype.getSize());//[1,address+16,1,4]
		code.add(Multiply);//[1,address+16,4]
		code.add(Add);
		turnAddressIntoValue(subtype);
		String format = printFormat(subtype);
		convertToStringIfBoolean(subtype);
		convertToStringValueIfString(subtype);
		code.add(PushD, format);
		code.add(Printf);
	}

	private void getLength(String baseAddress){
		loadAddress(baseAddress);
		code.add(PushI,12);
		code.add(Add);
		code.add(LoadI);
	}

	private void turnAddressIntoValue(Type type) {
		if(type == PrimitiveType.INTEGER) {
			code.add(LoadI);
		}
		else if(type == ReferenceType.STRING) {
			code.add(LoadI);
		}
		else if(type == PrimitiveType.FLOAT) {
			code.add(LoadF);
		}
		else if(type == PrimitiveType.BOOLEAN) {
			code.add(LoadC);
		}
		else if(type == PrimitiveType.CHARACTER) {
			code.add(LoadC);
		}
		else if(type instanceof Array) {
			code.add(LoadI);
		}
		else {
			assert false : "appendArrayPrintCode: cannot turn address into value."; 
		}
	}
	
	private void appendArrayFormatterPrintCode(char c) {
		String format = printFormat(PrimitiveType.CHARACTER);
		
		if(c == ARRAY_FORMATTER_CLOSE_BRACKET) {
			appendArraySpacingPrintCode();
		}
		
		code.add(PushI, c); 
		code.add(PushD, format); 
		code.add(Printf);

		if(c != ARRAY_FORMATTER_CLOSE_BRACKET) {
			appendArraySpacingPrintCode();
		}
	}
	private void appendArraySpacingPrintCode() {
		String format = printFormat(PrimitiveType.CHARACTER);
		code.add(PushI, 32);
		code.add(PushD, format);
		code.add(Printf);
	}
	
	
	private void convertToStringIfBoolean(Type type) {
		if(type != PrimitiveType.BOOLEAN) {
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

	private void convertToStringValueIfString(Type type) {
		if (type != ReferenceType.STRING) {
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
