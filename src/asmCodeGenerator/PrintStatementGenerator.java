package asmCodeGenerator;

import static asmCodeGenerator.Macros.incrementInteger;
import static asmCodeGenerator.Macros.loadIFrom;
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

		convertToStringIfBoolean(node.getType());
		convertToStringValueIfString(node.getType());
		code.add(PushD, format);
		code.add(Printf);
	}
	
	private void appendArrayPrintCode(ParseNode node) {
		if(!(node.getType() instanceof Array)) {
			return;
		}
		
		Type subtype = node.getType().getSubtype();
		

		int header_typeIdentifier_byteConsumption = 4;
		int header_status_byteConsumption = 4;
		int header_subtypeSize_byteConsumption = 4;
		int header_length_byteConsumption = 4;
		
		int headerSize = header_typeIdentifier_byteConsumption
				+ header_status_byteConsumption
				+ header_subtypeSize_byteConsumption
				+ header_length_byteConsumption;
		
		String format = printFormat(subtype); 
		Labeller labeller = new Labeller("tempHolder"); 
		String pointerLabel = labeller.newLabel("pointer");
		String indexLabel = labeller.newLabel("index");
		String loopConditionLabel = labeller.newLabel("loopCondition");
		String noNeedDelimiterLabel = labeller.newLabel("noNeedDelimiter");
		String endLoopLabel = labeller.newLabel("endLoop");
		
		
		code.append(visitor.removeValueCode(node));
		
		code.add(DLabel, pointerLabel);
		code.add(DataI, 0); //clear 4 bytes with all zeroes
		code.add(PushD, pointerLabel); 
		code.add(Exchange); 
		code.add(StoreI); 
		
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_OPEN_BRACKET);

		
		// START LOOP ////////////////////////////////////////////////////////////
		// print each element in the array:
		// declare int i=0:
		code.add(DLabel, indexLabel);
		code.add(DataZ, 4);


		// while condition: i < arrayLength
		code.add(Label, loopConditionLabel);
		code.add(PushD, indexLabel);
		code.add(LoadI); 		// [... i]

		code.add(PushD, pointerLabel);
		code.add(LoadI); 			//loads the base address of the array
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI); 		// [... i arrayLength]

		code.add(Subtract);		// [... i - arrayLength]
		code.add(JumpFalse, endLoopLabel);  // [...]

		//entering while loop body:
		code.add(PushD, pointerLabel);
		code.add(LoadI); 			//loads the base address of the array
		code.add(PushI, headerSize);
		code.add(Add);				// [... baseAddress+headerSize]


		code.add(PushD, indexLabel);
		code.add(LoadI); 		// [... baseAddress+headerSize   i]


		code.add(PushD, pointerLabel);
		code.add(LoadI); 			//loads the base address of the array
		code.add(PushI, 8);
		code.add(Add);
		code.add(LoadI); 		// [... baseAddress+headerSize   i   subtypeSize]

		code.add(Multiply);		// [... baseAddress+headerSize   i*subtypeSize]
		code.add(Add); 			// [... baseAddress+headerSize + i*subtypeSize]
		
		turnAddressIntoValue(subtype);	// [... value]
		convertToStringIfBoolean(subtype);
		convertToStringValueIfString(subtype);
		code.add(PushD, format);
		code.add(Printf);


		// START : IF CONDITION FOR DELIMITER /////////////////////////////////////////////////////
		// print a delimiter after each element in the array, except for the last element:
		// if condition: (i != arrayLength-1)
		loadIFrom(code, indexLabel);  // [... i]

		code.add(PushD, pointerLabel);
		code.add(LoadI); 			//loads the base address of the array
		code.add(PushI, 12);
		code.add(Add);
		code.add(LoadI); 		// [... i arrayLength]
		
		code.add(PushI, 1);  	// [... i, arrayLength, 1]
		code.add(Subtract); 			// [... i, arrayLength - 1]
		
		code.add(Subtract); 			// [... i - (arrayLength - 1)]
		code.add(JumpFalse, noNeedDelimiterLabel);
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_DELIMITER);
		
		code.add(Label, noNeedDelimiterLabel);
		// END : IF CONDITION FOR DELIMITER /////////////////////////////////////////////////////
		
		
		//increment i:
		incrementInteger(code, indexLabel);
		code.add(Jump, loopConditionLabel);
		code.add(Label, endLoopLabel);
		// END LOOP ////////////////////////////////////////////////////////////
		
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_CLOSE_BRACKET);
		
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
