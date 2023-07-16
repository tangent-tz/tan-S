package asmCodeGenerator;

import static asmCodeGenerator.Macros.incrementInteger;
import static asmCodeGenerator.Macros.loadIFrom;
import static asmCodeGenerator.codeStorage.ASMOpcode.Jump;
import static asmCodeGenerator.codeStorage.ASMOpcode.JumpTrue;
import static asmCodeGenerator.codeStorage.ASMOpcode.Label;
import static asmCodeGenerator.codeStorage.ASMOpcode.Printf;
import static asmCodeGenerator.codeStorage.ASMOpcode.PushD;

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
		String format = printFormat(subtype); 
		Labeller labeller = new Labeller("tempHolder"); 
		String pointerLabel = labeller.newLabel("pointer");
		String indexLabel = labeller.newLabel("index");
		String loopConditionLabel = labeller.newLabel("loopCondition");
		String noNeedDelimiterLabel = labeller.newLabel("noNeedDelimiter");
		String endLoopLabel = labeller.newLabel("endLoop");
		String printPrimitiveTypeLabel = labeller.newLabel("printPrimitive"); 
		String ifConditionDelimiterLabel = labeller.newLabel("ifConditionDelimiter");
		
		code.append(visitor.removeValueCode(node));	//[... startAddressOfArray]
		storeArrayAddressToLabel(pointerLabel);		//[...]
		
		
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_OPEN_BRACKET);
		// START LOOP ////////////////////////////////////////////////////////////
		// print each element in the array:
		// declare int i=0:
		declareIToZero(indexLabel);
		
		// while condition: i < arrayLength
		code.add(Label, loopConditionLabel);
		verifyIndexLessThanArrayLength(indexLabel, pointerLabel); 	// [... i - arrayLength]
		code.add(JumpFalse, endLoopLabel);  // [...]
		
		//entering while loop body:
		loadArrayElementAtIndex(pointerLabel, indexLabel, subtype);		// [... value]
		
		
		//---------------------------------------------------------------------------------------------------------
		// check if subtype is a primitive type or reference type (array). 
		// if subtype is reference type then recursive printing:
		loadArrayStatus(pointerLabel);	// [... value, statusValue]
		code.add(JumpFalse, printPrimitiveTypeLabel);  	// [... value]
		
		//if we reach this point in the instructions, that means we did not jump, that means we detect that the element-to-be-printed is of type Array. 
		//normally, we can just recursively print out this array element. But, we first need to see if this element has been initialized or not (because the parent array may be constructed with the empty array creation).
		code.add(Duplicate); 		// [... value, value]
		code.add(JumpFalse, printPrimitiveTypeLabel);
		
		//if we read this point, that means the element-to-be-printed is an array AND this array has been initialized.
		//so we can safely do the recursive array printing:
		printArrayRecursive(code, ifConditionDelimiterLabel, subtype.getSubtype());
		//---------------------------------------------------------------------------------------------------------
		
		code.add(Label, printPrimitiveTypeLabel); 
		printPrimitiveTypeElement(subtype, format);


		// START : IF CONDITION FOR DELIMITER /////////////////////////////////////////////////////
		// print a delimiter after each element in the array, except for the last element:
		code.add(Label, ifConditionDelimiterLabel);
		printDelimiterIfNotLastElementInArray(indexLabel, pointerLabel, noNeedDelimiterLabel);
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
		
		code.add(PushI, c); 
		code.add(PushD, format); 
		code.add(Printf);

		if(c == ARRAY_FORMATTER_DELIMITER)
			appendArraySpacingPrintCode();
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
			// this format is used only when we are printing an array element that has not been initialized yet. 
			// so its value is just 4 bytes filled with all O's 
			return RunTime.INTEGER_PRINT_FORMAT;
		} else {
			switch((ReferenceType)type) {
				case STRING:    return RunTime.STRING_PRINT_FORMAT;
				default:
					assert false : "Type " + type + " unimplemented in PrintStatementGenerator.printFormat()";
					return "";
			}
		}
	}
	
	
	private void printArrayRecursive(ASMCodeFragment code, String returnLabel, Type subtype) {
		//base case:
		if (subtype == PrimitiveType.NO_TYPE) {
			return; 
		}
		
		String format = printFormat(subtype);
		Labeller labeller = new Labeller("tempHolder");
		String pointerLabel = labeller.newLabel("pointer");
		String indexLabel = labeller.newLabel("index");
		String loopConditionLabel = labeller.newLabel("loopCondition");
		String noNeedDelimiterLabel = labeller.newLabel("noNeedDelimiter");
		String endLoopLabel = labeller.newLabel("endLoop");
		String printPrimitiveTypeLabel = labeller.newLabel("printPrimitive");
		String ifConditionDelimiterLabel = labeller.newLabel("ifConditionDelimiter"); 

		//at this moment, the stack: [... value]
		storeArrayAddressToLabel(pointerLabel);
		
		
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_OPEN_BRACKET);
		// START LOOP ////////////////////////////////////////////////////////////
		// print each element in the array:
		// declare int i=0:
		declareIToZero(indexLabel); 
		

		// while condition: i < arrayLength
		code.add(Label, loopConditionLabel);
		verifyIndexLessThanArrayLength(indexLabel, pointerLabel);	// [... i - arrayLength]
		code.add(JumpFalse, endLoopLabel);  // [...]

		//entering while loop body:
		loadArrayElementAtIndex(pointerLabel, indexLabel, subtype);		// [... value]
		
		//---------------------------------------------------------------------------------------------------------
		// check if subtype is a primitive type or reference type (array). 
		// if subtype is reference type then recursive printing
		loadArrayStatus(pointerLabel);	// [... value, statusValue]
		code.add(JumpFalse, printPrimitiveTypeLabel);  	// [... value]
		printArrayRecursive(code, ifConditionDelimiterLabel, subtype.getSubtype());
		//---------------------------------------------------------------------------------------------------------

		code.add(Label, printPrimitiveTypeLabel);
		printPrimitiveTypeElement(subtype, format);


		// START : IF CONDITION FOR DELIMITER /////////////////////////////////////////////////////
		// print a delimiter after each element in the array, except for the last element:
		code.add(Label, ifConditionDelimiterLabel);
		printDelimiterIfNotLastElementInArray(indexLabel, pointerLabel, noNeedDelimiterLabel);
		code.add(Label, noNeedDelimiterLabel);
		// END : IF CONDITION FOR DELIMITER /////////////////////////////////////////////////////


		//increment i:
		incrementInteger(code, indexLabel);
		code.add(Jump, loopConditionLabel);
		code.add(Label, endLoopLabel);
		// END LOOP ////////////////////////////////////////////////////////////

		appendArrayFormatterPrintCode(ARRAY_FORMATTER_CLOSE_BRACKET);
		code.add(Jump, returnLabel);
	}
	
	
	private void declareIToZero(String indexLabel) {
		code.add(DLabel, indexLabel);
		code.add(DataZ, 4);
		code.add(PushD, indexLabel); 
		code.add(PushI, 0); 
		code.add(StoreI); 
	}
	
	private void verifyIndexLessThanArrayLength(String indexLabel, String pointerLabel) {
		loadIFrom(code, indexLabel);	// [... i]
		loadArrayLength(pointerLabel); 	// [... i arrayLength]
		code.add(Subtract);		// [... i - arrayLength]
	}
	
	private void loadArrayLength(String pointerLabel) {
		loadIFrom(code, pointerLabel); 			//loads the base address of the array
		code.add(PushI, Array.HEADER_LENGTH_OFFSET);
		code.add(Add);
		code.add(LoadI); 						// [... i arrayLength]
	}
	
	private void loadArraySubtypeSize(String pointerLabel) {
		loadIFrom(code, pointerLabel); 			//loads the base address of the array
		code.add(PushI, Array.HEADER_SUBTYPESIZE_OFFSET);
		code.add(Add);
		code.add(LoadI); 						// [... baseAddress+headerSize   i   subtypeSize]
	}
	
	private void loadArrayElementAtIndex(String pointerLabel, String indexLabel, Type subtype) {
		loadIFrom(code, pointerLabel); 			//loads the base address of the array
		code.add(PushI, Array.HEADER_SIZE);
		code.add(Add);							// [... baseAddress+headerSize]

		loadIFrom(code, indexLabel); 			// [... baseAddress+headerSize   i]
		loadArraySubtypeSize(pointerLabel); 	// [... baseAddress+headerSize   i   subtypeSize]

		code.add(Multiply);						// [... baseAddress+headerSize   i*subtypeSize]
		code.add(Add); 							// [... baseAddress+headerSize + i*subtypeSize]

		turnAddressIntoValue(subtype);			// [... value]
	}
	
	private void loadArrayStatus(String pointerLabel) {
		loadIFrom(code, pointerLabel); 			//loads the base address of the array [... value, baseAddress]
		code.add(PushI, Array.HEADER_STATUS_OFFSET);
		code.add(Add); 							//base address + offset
		code.add(LoadI); 						// [... value, statusValue]
	}
	
	private void printPrimitiveTypeElement(Type subtype, String format) {
		//currently, the stack: [... value]
		convertToStringIfBoolean(subtype);
		convertToStringValueIfString(subtype);
		code.add(PushD, format);
		code.add(Printf);
	}
	
	private void printDelimiterIfNotLastElementInArray(String indexLabel, String pointerLabel, String noNeedDelimiterLabel) {
		// if condition: (i != arrayLength-1)
		loadIFrom(code, indexLabel);  			// [... i]
		loadArrayLength(pointerLabel); 			// [... i arrayLength]

		code.add(PushI, 1);  			// [... i, arrayLength, 1]
		code.add(Subtract); 					// [... i, arrayLength - 1]
		code.add(Subtract); 					// [... i - (arrayLength - 1)]
		
		code.add(JumpFalse, noNeedDelimiterLabel);
		appendArrayFormatterPrintCode(ARRAY_FORMATTER_DELIMITER);
	}
	
	private void storeArrayAddressToLabel(String pointerLabel) {
		//currently, the stack: [... startAddressOfArray]
		code.add(DLabel, pointerLabel);
		code.add(DataI, 0); //clear 4 bytes with all zeroes
		code.add(PushD, pointerLabel);
		code.add(Exchange);
		code.add(StoreI);
	}
}
































