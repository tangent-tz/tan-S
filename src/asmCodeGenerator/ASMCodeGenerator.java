package asmCodeGenerator;

import java.awt.*;
import java.util.*;
import java.util.List;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.operators.SimpleCodeGenerator;
import asmCodeGenerator.runtime.FrameStack;
import asmCodeGenerator.runtime.MemoryManager;
import asmCodeGenerator.runtime.RunTime;
import static asmCodeGenerator.Macros.*;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import parseTree.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
import semanticAnalyzer.types.Array;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;
import symbolTable.Binding;
import symbolTable.Scope;

import javax.sound.sampled.DataLine;

import static asmCodeGenerator.codeStorage.ASMCodeFragment.CodeType.*;
import static asmCodeGenerator.codeStorage.ASMOpcode.*;

// do not call the code generator if any errors have occurred during analysis.
public class ASMCodeGenerator {
	ParseNode root;

	public static ASMCodeFragment generate(ParseNode syntaxTree) {
		ASMCodeGenerator codeGenerator = new ASMCodeGenerator(syntaxTree);
		return codeGenerator.makeASM();
	}
	public ASMCodeGenerator(ParseNode root) {
		super();
		this.root = root;
	}

	public ASMCodeFragment makeASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		code.append( RunTime.getEnvironment() );
		//code.append( setUpFrameStack());
		code.append( frameVariableBlockASM());
		code.append( functionASM());
		code.append( globalVariableBlockASM() );
		code.append( mainASM() );
		code.append( MemoryManager.codeForAfterApplication() );
		return code;
	}
	private ASMCodeFragment globalVariableBlockASM() {
		assert root.hasScope();
		Scope scope = root.getScope();
		int globalBlockSize = scope.getAllocatedSize();

		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		code.add(DLabel, RunTime.GLOBAL_MEMORY_BLOCK);
		code.add(DataZ, globalBlockSize);
		return code;
	}

	private ASMCodeFragment frameVariableBlockASM() {
		assert root.hasScope();
		Scope scope = root.getScope();
		int frameBlockSize = scope.getAllocatedSize();

		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		code.add(DLabel, RunTime.FRAME_MEMORY_BLOCK);
		code.add(DataZ, frameBlockSize);
		return code;
	}

	// function ASM ------------------------------------------------------
	private ASMCodeFragment functionASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
		
		//1. do the first pass to generate function labels and setup byte consumption:
		for(int i=0; i < root.nChildren()-1; i++) {
			FunctionNode node = (FunctionNode) root.child(i);
			
			String functionNameString = node.getChildNode_functionName().getToken().getLexeme();
			Labeller labeller = new Labeller("subr-" + functionNameString);
			String functionStartLabel = labeller.newLabel("start");
			node.setASMLabel(functionStartLabel);

			
			int totalByteConsumption = node.getScope().getAllocatedSize();
			node.setAllocatedSize(totalByteConsumption);
		}
		//-------------------------------------------------------------------------------------
		
		//2. do the second pass to append each function code one by one:
		for(int i=0; i < root.nChildren()-1; i++) {
			code.append(functionCode(i));
			code.add(	Return);
		}
		return code;
	}
	private ASMCodeFragment functionCode(int i) {
		CodeVisitor visitor = new CodeVisitor();
		root.child(i).accept(visitor);
		return visitor.removeRootCode(root.child(i));
	}

	// frame stack ------------------------------------------------------
//	private ASMCodeFragment setUpFrameStack() {
//		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);
//		code.add(DLabel, RunTime.FRAME_POINTER); 
//		code.add(DataZ, 4); 
//		code.add(DLabel, RunTime.STACK_POINTER); 
//		code.add(DataZ, 4); 
//		code.add(DLabel, RunTime.PREV_FRAME_POINTER); 
//		code.add(DataZ, 4); 
//		
//		//initialize frame pointer 
//		code.add(PushD, RunTime.FRAME_POINTER); 
//		code.add(Memtop); 
//		code.add(StoreI); 
//		
//		//initialize stack pointer
//		code.add(PushD, RunTime.STACK_POINTER); 
//		code.add(Memtop); 
//		code.add(StoreI);
//		
//		//initialize previous frame pointer 
//		code.add(PushD, RunTime.PREV_FRAME_POINTER);
//		code.add(Memtop);
//		code.add(StoreI); 
//		
//		return code; 
//	}
	
	
	// main ASM ------------------------------------------------------
	private ASMCodeFragment mainASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

		code.add(    Label, RunTime.MAIN_PROGRAM_LABEL);
		code.append( mainCode());
		code.add(    Halt );

		return code;
	}
	private ASMCodeFragment mainCode() {
		CodeVisitor visitor = new CodeVisitor();
		
		int mainIndex = root.nChildren()-1; 
		root.child(mainIndex).accept(visitor);
		return visitor.removeRootCode(root.child(mainIndex));
	}
	


	protected class CodeVisitor extends ParseNodeVisitor.Default {
		private Map<ParseNode, ASMCodeFragment> codeMap;
		ASMCodeFragment code;

		public CodeVisitor() {
			codeMap = new HashMap<ParseNode, ASMCodeFragment>();
		}

		////////////////////////////////////////////////////////////////////
        // Make the field "code" refer to a new fragment of different sorts.
		private void newAddressCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_ADDRESS);
			codeMap.put(node, code);
		}
		private void newValueCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_VALUE);
			codeMap.put(node, code);
		}
		private void newVoidCode(ParseNode node) {
			code = new ASMCodeFragment(GENERATES_VOID);
			codeMap.put(node, code);
		}

	    ////////////////////////////////////////////////////////////////////
        // Get code from the map.
		private ASMCodeFragment getAndRemoveCode(ParseNode node) {
			ASMCodeFragment result = codeMap.get(node);
			codeMap.remove(node);
			return result;
		}
	    public  ASMCodeFragment removeRootCode(ParseNode tree) {
			return getAndRemoveCode(tree);
		}
		ASMCodeFragment removeValueCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			makeFragmentValueCode(frag, node);
			return frag;
		}

		private ASMCodeFragment getAndRemoveCodeArray(ParseNode node, int level) {
			ASMCodeFragment result = codeMap.get(node);
			codeMap.remove(node);
			if(node.getType() ==PrimitiveType.INTEGER && level == 2 && !(node instanceof IdentifierNode)){
				result.add(ConvertF);
			}
			else if(node.getType() ==PrimitiveType.CHARACTER && level == 2 && !(node instanceof IdentifierNode)){
				result.add(ConvertF);
			}
			return result;
		}

		ASMCodeFragment removeValueCodeArray(ParseNode node, int level) {
			ASMCodeFragment frag = getAndRemoveCodeArray(node, level);
			makeFragmentValueCodeArray(frag, node, level);
			return frag;
		}

		private void makeFragmentValueCodeArray(ASMCodeFragment code, ParseNode node, int level) {
			assert !code.isVoid();

			if(code.isAddress()) {
				turnAddressIntoValueArray(code, node, level);
			}
		}

		private void turnAddressIntoValueArray(ASMCodeFragment code, ParseNode node, int level) {
			if(node.getType() == PrimitiveType.INTEGER) {
				code.add(LoadI);
				if(level == 2){
					code.add(ConvertF);
				}
			}
			else if(node.getType() == ReferenceType.STRING) {
				code.add(LoadI);
			}
			else if(node.getType() == PrimitiveType.FLOAT) {
				code.add(LoadF);
			}
			else if(node.getType() == PrimitiveType.BOOLEAN) {
				code.add(LoadC);
			}
			else if(node.getType() == PrimitiveType.CHARACTER) {
				code.add(LoadC);
				if(level == 2){
					code.add(ConvertF);
				}
			}
			else if(node.getType() instanceof Array) {
				code.add(LoadI);
			}
			else {
				assert false : "node " + node;
			}
			code.markAsValue();
		}

		private ASMCodeFragment removeAddressCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			assert frag.isAddress();
			return frag;
		}
		ASMCodeFragment removeVoidCode(ParseNode node) {
			ASMCodeFragment frag = getAndRemoveCode(node);
			assert frag.isVoid();
			return frag;
		}

	    ////////////////////////////////////////////////////////////////////
        // convert code to value-generating code.
		private void makeFragmentValueCode(ASMCodeFragment code, ParseNode node) {
			assert !code.isVoid();

			if(code.isAddress()) {
				turnAddressIntoValue(code, node);
			}
		}
		private void turnAddressIntoValue(ASMCodeFragment code, ParseNode node) {
			if(node.getType() == PrimitiveType.INTEGER) {
				code.add(LoadI);
			}
			else if(node.getType() == ReferenceType.STRING) {
				code.add(LoadI);
			}
			else if(node.getType() == PrimitiveType.FLOAT) {
				code.add(LoadF);
			}
			else if(node.getType() == PrimitiveType.BOOLEAN) {
				code.add(LoadC);
			}
			else if(node.getType() == PrimitiveType.CHARACTER) {
				code.add(LoadC);
			}
			else if(node.getType() instanceof Array) {
				code.add(LoadI);
			}
			else {
				assert false : "node " + node;
			}
			code.markAsValue();
		}

	    ////////////////////////////////////////////////////////////////////
        // ensures all types of ParseNode in given AST have at least a visitLeave
		public void visitLeave(ParseNode node) {
			assert false : "node " + node + " not handled in ASMCodeGenerator";
		}



		///////////////////////////////////////////////////////////////////////////
		// constructs larger than statements
		public void visitLeave(ProgramNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		
		
		//////////////////////////////////////////////////////////////////////////
		// functions
		
		// main function -------------------------------------------
		public void visitLeave(MainFunctionNode node) {
			newVoidCode(node);
			ParseNode mainBlock = node.child(0); 
			ASMCodeFragment mainBlockCode = removeVoidCode(mainBlock);
			code.append(mainBlockCode);
		}
		
		// non-main function ---------------------------------------

		public void visitLeave(FunctionNode node) {
			newVoidCode(node);
			
			String functionStartLabel = node.getASMLabel();
			Type returnType = node.getChildNode_returnType().getType();
			////////////////////////

			ParseNode functionBodyNode = node.getChildNode_functionBody();
			ASMCodeFragment functionBodyCode = removeVoidCode(functionBodyNode);

			code.add(Label, functionStartLabel);
			// the stack: [... returnAddress]
			FrameStack.saveReturnAddress(code);
			code.append(functionBodyCode);
			
			
			//if we reach this point, meaning we did not reach any return statement in the function body
			//this can only be 2 cases:
			if(node.getChildNode_returnType().getType() != PrimitiveType.VOID) {
				//the function fails to return any value even though it is not a void function, so we throw runtime error:
				// stack: [...]
				code.add(Jump, RunTime.MISSING_RETURN_STATEMENT);
			} else {
				//the function is indeed a void function as expected, so we proceed with the return protocol:
				//stack: [...]
				FrameStack.loadReturnAddress(code);			//stack: [... returnAddress]
				FrameStack.restorePointers(code);
			}
			
			//the default 'Return' opcode is appended in the functionASM() above already!
		}
		

		public void visitLeave(CallStatementNode node) {
			newVoidCode(node);
			code.append(removeValueCode(node.child(0)));
			Type returnType = node.child(0).getType(); 
			if(returnType != PrimitiveType.VOID) {
				code.add(Pop);
			}
		}
		public void visitLeave(ReturnStatementNode node) {
			newVoidCode(node);
			if(node.nChildren() == 1) {
				ASMCodeFragment returnedExpression = removeValueCode(node.child(0));
				code.append(returnedExpression);
				// stack: [... returnValue]
				FrameStack.loadReturnAddress(code);        //stack: [... returnValue, returnAddress]
				code.add(Exchange);                        //stack: [...  returnAddress, returnValue]

				FrameStack.restorePointers(code);
				FrameStack.saveReturnValue(code, node.getType());    //stack: [... returnAddress]
				code.add(Return);
			} else {
				//stack: [...]
				FrameStack.loadReturnAddress(code);			//stack: [... returnAddress]
				FrameStack.restorePointers(code);
				code.add(Return); 
			}
		}

		public void visitLeave(FunctionInvocationNode node) {
			newValueCode(node);
			ParseNode functionNameNode = node.getChildNode_functionName();

			//pass arguments into function
			ExpressionListNode expressionListNode = (ExpressionListNode) node.getChildNode_expressionList();
			List<Type> argTypeList = expressionListNode.getChildTypes();
			
			
			//////////////////////////////////////////////
			for(int i=expressionListNode.nChildren()-1; i >= 0; i--) {
				ParseNode expressionNode = expressionListNode.child(i);
				ASMCodeFragment expressionCode = removeValueCode(expressionNode);
				code.append(expressionCode);
			}
			
			//////////////////////////////////////////////
			
			int totalOffset = 0;
			for(int i=0; i < expressionListNode.nChildren(); i++) {
				Type type = argTypeList.get(i);
				totalOffset -= type.getSize();
				FrameStack.passInParameter(code, totalOffset, type);
			}
			
			
			//update stack pointer after all local variables were allocated:
			int offset = -( ((IdentifierNode) functionNameNode).getAllocatedSize() ); 
			FrameStack.moveStackPointerBy(code, offset);
			FrameStack.createDynamicLink(code);
			
			////////////////////////////////
			
			code.append(removeVoidCode(functionNameNode)); //the stack: [... returnAddress]
			
			//after function is executed: it comes back here.
			//stack: [...]
			Type returnType = functionNameNode.getType(); 
			if(returnType != PrimitiveType.VOID) {
				FrameStack.loadReturnValue(code, returnType);
			}
		}
		

		///////////////////////////////////////////////////////////////////////////
		// statements & declarations & assignments & blockStatements

		public void visitLeave(PrintStatementNode node) {
			newVoidCode(node);
			new PrintStatementGenerator(code, this).generate(node);
		}
		public void visit(NewlineNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.NEWLINE_PRINT_FORMAT);
			code.add(Printf);
		}
		public void visit(SpaceNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.SPACE_PRINT_FORMAT);
			code.add(Printf);
		}
		public void visit(TabNode node) {
			newVoidCode(node);
			code.add(PushD, RunTime.TAB_PRINT_FORMAT);
			code.add(Printf);
		}


		public void visitLeave(DeclarationNode node) {
			newVoidCode(node);
			ASMCodeFragment lvalue = removeAddressCode(node.child(0));
			ASMCodeFragment rvalue = removeValueCode(node.child(1));

			code.append(lvalue);
			code.append(rvalue);

			Type type = node.getType();
			code.add(opcodeForStore(type));
		}

		public void visitLeave(AssignmentStatementNode node) {
			newVoidCode(node);
			ASMCodeFragment lvalue = removeAddressCode(node.child(0));
			ASMCodeFragment rvalue = removeValueCode(node.child(1));

			code.append(lvalue);
			code.append(rvalue);
			if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.INTEGER) {
				code.add(ConvertF);
			}
			if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.CHARACTER) {
				code.add(ConvertF);
			}
			Type type = node.getType();
			code.add(opcodeForStore(type));
		}

		public void visitLeave(BlockStatementNode node) {
			newVoidCode(node);
			for(ParseNode child : node.getChildren()) {
				ASMCodeFragment childCode = removeVoidCode(child);
				code.append(childCode);
			}
		}
		
		public void visitLeave(IfStatementNode node) {
			newVoidCode(node);
			ASMCodeFragment ifCondition = removeValueCode(node.child(0)); 
			ASMCodeFragment ifBlock = removeVoidCode(node.child(1)); 
			
			if(node.nChildren() == 3) {
				ASMCodeFragment elseBlock = removeVoidCode(node.child(2));
				generateIfElseCodeFragment(ifCondition, ifBlock, elseBlock);
				return; 
			}
			generateIfCodeFragment(ifCondition, ifBlock);
		}
		private void generateIfCodeFragment(ASMCodeFragment ifCondition, ASMCodeFragment ifBlock) {
			Labeller labeller = new Labeller("if-statement"); 
			String endLabel = labeller.newLabel("end"); 

			code.append(ifCondition);
			code.add(JumpFalse, endLabel); 
			code.append(ifBlock);
			code.add(Label, endLabel);
		}
		private void generateIfElseCodeFragment(ASMCodeFragment ifCondition, ASMCodeFragment ifBlock, ASMCodeFragment elseBlock) {
			Labeller labeller = new Labeller("if-statement");
			String elseBlockLabel = labeller.newLabel("elseBlock"); 
			String endLabel = labeller.newLabel("end");
			
			code.append(ifCondition); 
			code.add(JumpFalse, elseBlockLabel); 
			code.append(ifBlock);
			code.add(Jump, endLabel); 
			code.add(Label, elseBlockLabel); 
			code.append(elseBlock);
			code.add(Label, endLabel); 
		}

		public void visitEnter(WhileNode node) {
			Labeller labeller = new Labeller("while-statement");
			String startLoopLabel = labeller.newLabel("startLoop");
			String endLoopLabel = labeller.newLabel("endLoop");

			node.storeLinkageLabels(startLoopLabel, endLoopLabel);
		}
		public void visitLeave(WhileNode node) {
			newVoidCode(node);
			ASMCodeFragment lvalue = removeValueCode(node.child(0));
			ASMCodeFragment rvalue = removeVoidCode(node.child(1));

			String startLabel = node.getStartLoopLabel();
			String endLabel = node.getEndLoopLabel();

			code.add(Label, startLabel);
			code.append(lvalue);
			code.add(JumpFalse, endLabel);
			code.append(rvalue);
			code.add(Jump, startLabel);
			code.add(Label, endLabel);
		}

		public void visitEnter(ForNode node) {
			Labeller labeller = new Labeller("for-statement");
			String startLabel = labeller.newLabel("-for-start");
			String incrementLabel = labeller.newLabel("-for-increment");
			String endLabel = labeller.newLabel("-for-end");
			
			node.storeLinkageLabels(startLabel, endLabel, incrementLabel);
		}
		public void visitLeave(ForNode node) {
			newVoidCode(node);
			ASMCodeFragment arg1 = removeVoidCode(node.child(0));
			ASMCodeFragment arg2 = removeVoidCode(node.child(1));
			ASMCodeFragment comparison = removeValueCode(node.child(2));
			ASMCodeFragment loopBody = removeVoidCode(node.child(3));
			ASMCodeFragment loopIncrementor = removeVoidCode(node.child(4));
			
			String startLabel = node.getStartLoopLabel();
			String incrementLabel = node.getIncrementLabel();
			String endLabel = node.getEndLoopLabel();

			code.append(arg1);
			code.append(arg2);
			code.add(Label, startLabel);
			code.append(comparison);
			code.add(JumpFalse, endLabel);
			code.append(loopBody);
			code.add(Label, incrementLabel);
			code.append(loopIncrementor);
			code.add(Jump, startLabel);
			code.add(Label, endLabel);
		}

		private ASMOpcode opcodeForStore(Type type) {
			if(type == PrimitiveType.INTEGER) {
				return StoreI;
			}
			else if(type == ReferenceType.STRING) {
				return StoreI;
			}
			else if(type == PrimitiveType.FLOAT) {
				return StoreF;
			}
			else if(type == PrimitiveType.BOOLEAN) {
				return StoreC;
			}
			else if(type == PrimitiveType.CHARACTER) {
				return StoreC;
			}
			else if(type instanceof Array) {
				return StoreI; 
			}
			assert false: "Type " + type + " unimplemented in opcodeForStore()";
			return null;
		}

		///////////////////////////////////////////////////////////////////////////
		// expressions
		public void visitLeave(OperatorNode node) {
			Lextant operator = node.getOperator();

			if(operator == Punctuator.SUBTRACT || operator == Punctuator.ADD || operator == Punctuator.BOOLEAN_NOT || operator == Keyword.LENGTH) {
				if(node.nChildren() == 1)
					visitUnaryOperatorNode(node);
				else
					visitNormalBinaryOperatorNode(node);
			}
			else if(operator == Punctuator.EQUALS ||operator == Punctuator.GREATER||operator == Punctuator.LESSER||operator == Punctuator.NOTEQUALS || operator  == Punctuator.GREATEREQUAL || operator == Punctuator.LESSEREQUAL) {
				visitComparisonOperatorNode(node);
			}
			else if(operator == Punctuator.CAST) {
				visitCastingOperatorNode(node);
			}
			else if(operator == Punctuator.CONDITIONAL_OR || operator == Punctuator.CONDITIONAL_AND) {
				visitConditionalOperatorNode(node); 
			}
			else if(operator == Punctuator.INDEXING) {
				visitArrayIndexingOperatorNode(node); 
			}
			else {
				visitNormalBinaryOperatorNode(node);
			}
		}
		private void visitUnaryOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));

			code.append(arg1);

			FunctionSignature sig = FunctionSignatures.signature(node.getOperator(), Arrays.asList(node.child(0).getType()));
			Object variant = sig.getVariant();
			if(variant instanceof ASMOpcode) {
				code.add((ASMOpcode) variant); 
				return; 
			}

			((SimpleCodeGenerator) variant).generate(code);
		}

		private void visitCastingOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment source = removeValueCode(node.child(1));
			code.append(source);

			generateCastingCodeFragment(node);
		}
		private void generateCastingCodeFragment(OperatorNode node) {
			FunctionSignature castingSignature = FunctionSignatures.signature(Punctuator.CAST, Arrays.asList(node.child(0).getType(), node.child(1).getType()));
			Object castingVariant = castingSignature.getVariant();

			if(castingVariant instanceof ASMOpcode) {
				code.add((ASMOpcode) castingVariant);
				return;
			}

			((SimpleCodeGenerator)castingVariant).generate(code);
		}

		private void visitConditionalOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0)); 
			ASMCodeFragment arg2 = removeValueCode(node.child(1)); 
			
			FunctionSignature sig = FunctionSignatures.signature(node.getOperator(), Arrays.asList(node.child(0).getType(), node.child(1).getType()));
			((SimpleCodeGenerator) sig.getVariant()).generate(code, arg1, arg2);
		}
		
		
		private void visitComparisonOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));
			code.append(arg1);
			if((node.child(0).getType() == PrimitiveType.INTEGER ||node.child(0).getType() == PrimitiveType.CHARACTER) && node.child(1).getType()== PrimitiveType.FLOAT) {
				code.add(ConvertF);
			}
			code.append(arg2);
			if(node.child(0).getType() == PrimitiveType.FLOAT && (node.child(1).getType()== PrimitiveType.INTEGER||node.child(1).getType() == PrimitiveType.CHARACTER)) {
				code.add(ConvertF);
			}


			generateComparisonCodeFragment(node, arg1, arg2);
		}

		private void generateComparisonCodeFragment(OperatorNode node, ASMCodeFragment arg1, ASMCodeFragment arg2) {
			FunctionSignature signature = FunctionSignatures.signature(node.getOperator(), Arrays.asList(node.child(0).getType(), node.child(1).getType()));
			Object variant = signature.getVariant();

			if(variant instanceof ASMOpcode) {
				code.add((ASMOpcode) variant);
				return;
			}
			((SimpleCodeGenerator)variant).generate(node , code);
		}
		
		

		private void visitArrayIndexingOperatorNode(OperatorNode node) {
			Type subType = node.child(0).getType().getSubtype(); 
			
			Labeller labeller = new Labeller("array-indexing"); 
			String baseAddressLabel = labeller.newLabel("baseAddress");
			String indexLabel = labeller.newLabel("index"); 
			
			
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0)); 
			ASMCodeFragment arg2 = removeValueCode(node.child(1)); 

			
			// storing array base address into a temp memory location:
			code.append(arg1); 			// [... baseAddress]
			code.add(DLabel, baseAddressLabel);
			code.add(DataI, 0); //clear 4 bytes with all zeroes
			code.add(PushD, baseAddressLabel);
			code.add(Exchange);
			code.add(StoreI); 			// [...]
			
			
			//storing index i into a temp memory location:
			code.append(arg2);			// [... i]
			code.add(DLabel, indexLabel); 
			code.add(DataI, 0); //clear 4 bytes with all zeroes
			code.add(PushD, indexLabel); 
			code.add(Exchange);
			code.add(StoreI); 			// [...]
			

			// start: index validation -------------------------------------------------------------
			//first, check if i < 0. If yes, throw runtime error. 
			loadIFrom(code, indexLabel);		// [... i]
			code.add(JumpNeg, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS);
			
			//second, check if i >= arrayLength. If yes, throw runtime error.
			loadIFrom(code, indexLabel);		// [... i]

			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 					// [... i, baseAddress]
			code.add(PushI, 12);
			code.add(Add);						// [... i, baseAddress+12]
			code.add(LoadI); 					// [... i, arrayLength]
			
			code.add(Subtract); 				// [... i - arrayLength]
			code.add(Duplicate); 				// [... i - arrayLength, 	i - arrayLength]
			code.add(JumpFalse, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS); 	// [... i - arrayLength]	
			code.add(JumpPos, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS); 		// [...]
			// end : index validation ---------------------------------------------------------------
			
			
			//start accessing the indexed location:
			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 			// [... baseAddress]
			code.add(PushI, Array.HEADER_SIZE);
			code.add(Add);				// [... baseAddress+headerSize]
			loadIFrom(code, indexLabel); 			// [... baseAddress+headerSize, i]

			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 			// [... baseAddress+headerSize,   i,	baseAddress]
			code.add(PushI, 8);
			code.add(Add);
			code.add(LoadI); 		// [... baseAddress+headerSize,   i,   subtypeSize]
			
			code.add(Multiply); 	// [... baseAddress+headerSize,   i*subtypeSize]
			code.add(Add); 			// [... baseAddress+headerSize + i*subtypeSize]
			turnAddressIntoValue(subType);
		}
		
		
		private void visitNormalBinaryOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			code.append(arg1);
			if((node.child(0).getType() == PrimitiveType.INTEGER ||node.child(0).getType() == PrimitiveType.CHARACTER) && node.child(1).getType()== PrimitiveType.FLOAT) {
				code.add(ConvertF);
			}
			code.append(arg2);
			if(node.child(0).getType() == PrimitiveType.FLOAT && (node.child(1).getType()== PrimitiveType.INTEGER||node.child(1).getType() == PrimitiveType.CHARACTER)) {
				code.add(ConvertF);
			}

			if(node.getType() == PrimitiveType.FLOAT) {
				if(node.getOperator() == Punctuator.DIVIDE) {
					Labeller labeller = new Labeller("divide");
					code.add(Duplicate);  // Duplicate the denominator
					code.add(JumpFZero, RunTime.FLOAT_DIVIDE_BY_ZERO_RUNTIME_ERROR);
					code.add(Label, labeller.newLabel("notZero"));
					ASMOpcode opcode = opcodeForFloatOperator(node.getOperator());
					code.add(opcode);

				}
				else {
					ASMOpcode opcode = opcodeForFloatOperator(node.getOperator());
					code.add(opcode);
				}
			}
			else {
				if(node.getOperator() == Punctuator.DIVIDE) {
					Labeller labeller = new Labeller("divide");
					code.add(Duplicate);  // Duplicate the denominator
					code.add(JumpFalse, RunTime.INTEGER_DIVIDE_BY_ZERO_RUNTIME_ERROR);
					code.add(Label, labeller.newLabel("notZero"));
					ASMOpcode opcode = opcodeForIntegerOperator(node.getOperator());
					code.add(opcode);
				}
				else {
					ASMOpcode opcode = opcodeForIntegerOperator(node.getOperator());
					code.add(opcode);
				}
			}
		}

		private ASMOpcode opcodeForIntegerOperator(Lextant lextant) {
			assert(lextant instanceof Punctuator);
			Punctuator punctuator = (Punctuator)lextant;
			switch(punctuator) {
			case SUBTRACT:		return Subtract;
			case ADD: 	   		return Add;
			case MULTIPLY: 		return Multiply;
			case DIVIDE:		return Divide;
			default:
				assert false : "unimplemented operator in opcodeForOperator";
			}
			return null;
		}

		private ASMOpcode opcodeFoUnaryIntegerOperator(Lextant lextant) {
			assert(lextant instanceof Punctuator);
			Punctuator punctuator = (Punctuator)lextant;
			switch(punctuator) {
				case ADD:			return Nop;
				case SUBTRACT:		return Negate;			// (unary subtract only)
				default:
					assert false : "unimplemented operator in opcodeForOperator";
			}
			return null;
		}

		private ASMOpcode opcodeForFloatOperator(Lextant lextant) {
			assert(lextant instanceof Punctuator);
			Punctuator punctuator = (Punctuator)lextant;
			switch(punctuator) {
				case ADD: 	   		return FAdd;
				case SUBTRACT:		return FSubtract;
				case MULTIPLY: 		return FMultiply;
				case DIVIDE:		return FDivide;
				default:
					assert false : "unimplemented operator in opcodeForOperator";
			}
			return null;
		}
		private ASMOpcode opcodeFoUnaryFloatOperator(Lextant lextant) {
			assert(lextant instanceof Punctuator);
			Punctuator punctuator = (Punctuator)lextant;
			switch(punctuator) {
				case ADD:			return Nop;
				case SUBTRACT:		return FNegate;			// (unary subtract only)
				default:
					assert false : "unimplemented operator in opcodeForOperator";
			}
			return null;
		}

		public int checkHighestPromotableArray(ParseNode node) {
			int promoteLevelFlag = 0;

			for (int i = 0; i < node.nChildren(); i++) {
				ParseNode child = node.child(i);
				if (isFloat(child)) {
					promoteLevelFlag = 2;
				} else if (isInteger(child)) {
					promoteLevelFlag = Math.max(promoteLevelFlag, 1);
				} else if (isCharacter(child)) {
					promoteLevelFlag = Math.max(promoteLevelFlag, 0);
				}
			}
			for (int i = 0; i < node.nChildren(); i++){
				ParseNode child = node.child(i);
				if (isBoolean(child)) {
					promoteLevelFlag = 0;
				}
			}
			return promoteLevelFlag;
		}

		public boolean promoteCandidateArray(ParseNode node){
			Type type = node.child(0).getType();
			for (int i = 1; i < node.nChildren(); i++) {
				ParseNode child = node.child(i);
				if(type != child.getType()) {
					return true;
				}
			}
			return false;
		}

		public void updateSubType(ParseNode node, int level){
			for (int i = 0; i < node.nChildren(); i++) {
				ParseNode child = node.child(i);
				if(level == 2)
					child.setType(PrimitiveType.FLOAT);
				}
		}

		public boolean isFloat(ParseNode node){
			return node.getType() == PrimitiveType.FLOAT;
		}
		public boolean isCharacter(ParseNode node){
			return node.getType() == PrimitiveType.CHARACTER;
		}
		public boolean isInteger(ParseNode node){
			return node.getType() == PrimitiveType.INTEGER;
		}
		public boolean isBoolean(ParseNode node){
			return node.getType() == PrimitiveType.BOOLEAN;
		}


		///////////////////////////////////////////////////////////////////////////
		// array
		public void visitLeave(ArrayNode node) {
			boolean promoteCandidate = promoteCandidateArray(node);
			int promoteLevel = 0;
			if(promoteCandidate){
				promoteLevel = checkHighestPromotableArray(node);
			}

			assert(node.nChildren() >= 1);
			
			if(node.child(0) instanceof ArrayTypeNode) {
				generateCodeForEmptyArrayCreation(node);
				return; 
			}
			
			List<ASMCodeFragment> elements = new ArrayList<>();
			for(int i = 0; i < node.nChildren(); i++) {
				ASMCodeFragment child = removeValueCodeArray(node.child(i), promoteLevel);
				elements.add(child);
			}

			int numOfElements = node.nChildren();
			Type subtype = node.getType().getSubtype();
			int subtypeSize = subtype.getSize();
			int totalSize = Array.HEADER_SIZE + (numOfElements * subtypeSize);
			
			Labeller labeller = new Labeller("array");
			String pointerLabel = labeller.newLabel("pointer");

			newValueCode(node);
			// Allocate memory for the array
			code.add(PushI, totalSize);  // memory needed = size * offset
			code.add(Call, MemoryManager.MEM_MANAGER_ALLOCATE);
			
			//Store header data
			code.add(DLabel, pointerLabel);
			code.add(DataZ, Array.SIZE);
			code.add(PushD, pointerLabel);
			code.add(Exchange);
			code.add(StoreI);

			//storing type identifier:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_TYPE_IDENTIFIER_OFFSET); //offset (fixed)
			code.add(Add); 				//base address + offset
			code.add(PushI, 5); //stack: [... addr] -> [... addr 5]
			code.add(StoreI); 			//store 5 into the address

			//storing status:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_STATUS_OFFSET); //offset (fixed)
			code.add(Add); 				//base address + offset
			if(!(subtype instanceof Array)) {
				code.add(PushI, 0);
			}
			else {
				code.add(PushI, 2);
			}
			code.add(StoreI);

			//storing subtype size:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_SUBTYPESIZE_OFFSET);
			code.add(Add);
			code.add(PushI, subtypeSize);
			code.add(StoreI);

			//storing length (number of elements)
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_LENGTH_OFFSET);
			code.add(Add);
			code.add(PushI, numOfElements);
			code.add(StoreI);
			updateSubType(node, promoteLevel);
			
			// Store each element in the array
			for (int i = 0; i < numOfElements; i++) {
				code.add(PushD, pointerLabel);
				code.add(LoadI); 			//loads the base address of the array
				code.add(PushI, Array.HEADER_SIZE);
				code.add(Add);

				code.add(PushI, subtypeSize*i); //offset
				code.add(Add);
				code.append(elements.get(i));
				code.add(opcodeForStore(subtype));
			}

			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
		}
		
		
		private void generateCodeForEmptyArrayCreation(ArrayNode node) {
			ASMCodeFragment arrayLengthCodeFragment = removeValueCode(node.child(1));

			Type subtype = node.getType().getSubtype();
			int subtypeSize = subtype.getSize();
			
			Labeller labeller = new Labeller("array");
			String pointerLabel = labeller.newLabel("pointer");
			String indexLabel = labeller.newLabel("index");
			String loopConditionLabel = labeller.newLabel("loopCondition");
			String endLoopLabel = labeller.newLabel("endLoop"); 
			String zeroesDataLabel = labeller.newLabel("zeroesData");

			
			newValueCode(node);
			// check if number of elements is negative. If negative, throw a runtime error. 
			code.append(new ASMCodeFragment(arrayLengthCodeFragment));
			code.add(JumpNeg, RunTime.ARRAY_NEGATIVE_NUMBER_OF_ELEMENTS); 
			
			// Allocate memory for the array
			code.append(new ASMCodeFragment(arrayLengthCodeFragment));
			code.add(PushI, subtypeSize);
			code.add(Multiply);
			code.add(PushI, Array.HEADER_SIZE); 
			code.add(Add); 
			code.add(Call, MemoryManager.MEM_MANAGER_ALLOCATE);
			
			//Store header data
			code.add(DLabel, pointerLabel);
			code.add(DataZ, Array.SIZE);
			code.add(PushD, pointerLabel);
			code.add(Exchange);
			code.add(StoreI);

			//storing type identifier:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_TYPE_IDENTIFIER_OFFSET); //offset (fixed)
			code.add(Add); 				//base address + offset
			code.add(PushI, 5); //stack: [... addr] -> [... addr 5]
			code.add(StoreI); 			//store 5 into the address
			
			//storing status:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_STATUS_OFFSET); //offset (fixed)
			code.add(Add); 				//base address + offset
			if(!(subtype instanceof Array)) {
				code.add(PushI, 0);
			}
			else {
				code.add(PushI, 2);
			}
			code.add(StoreI);

			//storing subtype size:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_SUBTYPESIZE_OFFSET);
			code.add(Add);
			code.add(PushI, subtypeSize);
			code.add(StoreI);


			//storing length (number of elements)
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_LENGTH_OFFSET);
			code.add(Add);
			code.append(new ASMCodeFragment(arrayLengthCodeFragment));
			code.add(StoreI);


			// LOOP: Store all 0's bits in the array ///////////////////////////////////////////////
			// make zeroes data in memory:
			code.add(DLabel, zeroesDataLabel);
			code.add(DataZ, subtypeSize); 
			
			// declare int i=0:
			code.add(DLabel, indexLabel);
			code.add(DataZ, PrimitiveType.INTEGER.getSize());
			
			
			// while condition: i < arrayLength
			code.add(Label, loopConditionLabel);
			code.add(PushD, indexLabel);
			code.add(LoadI); 		// [... i]

			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_LENGTH_OFFSET);
			code.add(Add);
			code.add(LoadI); 		// [... i arrayLength]
			
			code.add(Subtract);		// [... i - arrayLength]
			code.add(JumpFalse, endLoopLabel);  // [...]
			
			//entering while loop body:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_SIZE);
			code.add(Add);				// [... baseAddress+headerSize]


			code.add(PushD, indexLabel);
			code.add(LoadI); 		// [... baseAddress+headerSize   i]


			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, Array.HEADER_SUBTYPESIZE_OFFSET);
			code.add(Add);
			code.add(LoadI); 		// [... baseAddress+headerSize   i   subtypeSize]
			
			code.add(Multiply);		// [... baseAddress+headerSize   i*subtypeSize]
			code.add(Add); 			// [... baseAddress+headerSize + i*subtypeSize]
			
			code.add(PushD, zeroesDataLabel); 
			turnAddressIntoValue(subtype); 	// [... baseAddress+headerSize + i*subtypeSize   0]
			code.add(opcodeForStore(subtype));
			
			
			//increment i:
			incrementInteger(code, indexLabel);
			code.add(Jump, loopConditionLabel);
			code.add(Label, endLoopLabel);
			
			
			//final step ////////////////
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
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
				assert false : "setting zeroes for empty array creation: cannot turn address into value.";
			}
		}


		@Override
		public void visitLeave(TargetableArrayReferenceNode node) {
			Labeller labeller = new Labeller("array-indexing");
			String baseAddressLabel = labeller.newLabel("baseAddress");
			String indexLabel = labeller.newLabel("index");


			newAddressCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			// storing array base address into a temp memory location:
			code.append(arg1); 			// [... baseAddress]
			code.add(DLabel, baseAddressLabel);
			code.add(DataI, 0); //clear 4 bytes with all zeroes
			code.add(PushD, baseAddressLabel);
			code.add(Exchange);
			code.add(StoreI); 			// [...]


			//storing index i into a temp memory location:
			code.append(arg2);			// [... i]
			code.add(DLabel, indexLabel);
			code.add(DataI, 0); //clear 4 bytes with all zeroes
			code.add(PushD, indexLabel);
			code.add(Exchange);
			code.add(StoreI); 			// [...]


			// start: index validation -------------------------------------------------------------
			//first, check if i < 0. If yes, throw runtime error. 
			loadIFrom(code, indexLabel);		// [... i]
			code.add(JumpNeg, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS);

			//second, check if i >= arrayLength. If yes, throw runtime error.
			loadIFrom(code, indexLabel);		// [... i]

			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 					// [... i, baseAddress]
			code.add(PushI, Array.HEADER_LENGTH_OFFSET);
			code.add(Add);						// [... i, baseAddress+12]
			code.add(LoadI); 					// [... i, arrayLength]

			code.add(Subtract); 				// [... i - arrayLength]
			code.add(Duplicate); 				// [... i - arrayLength, 	i - arrayLength]
			code.add(JumpFalse, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS); 	// [... i - arrayLength]	
			code.add(JumpPos, RunTime.ARRAY_INDEX_OUT_OF_BOUNDS); 		// [...]
			// end : index validation ---------------------------------------------------------------


			//start accessing the indexed location:
			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 			// [... baseAddress]
			code.add(PushI, Array.HEADER_SIZE);
			code.add(Add);				// [... baseAddress+headerSize]
			loadIFrom(code, indexLabel); 			// [... baseAddress+headerSize, i]

			code.add(PushD, baseAddressLabel);
			code.add(LoadI); 			// [... baseAddress+headerSize,   i,	baseAddress]
			code.add(PushI, Array.HEADER_SUBTYPESIZE_OFFSET);
			code.add(Add);
			code.add(LoadI); 		// [... baseAddress+headerSize,   i,   subtypeSize]

			code.add(Multiply); 	// [... baseAddress+headerSize,   i*subtypeSize]
			code.add(Add); 			// [... baseAddress+headerSize + i*subtypeSize]
		}
		
		
		///////////////////////////////////////////////////////////////////////////
		// leaf nodes (ErrorNode not necessary)
		public void visit(BooleanConstantNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue() ? 1 : 0);
		}
		public void visit(IdentifierNode node) {
			if(isFunctionNameBeingDeclared(node) || isParameterBeingDeclared(node)) {
				return;
			}
			if(isFunctionNameBeingInvoked(node)) {
				newVoidCode(node);
				String functionStartLabel = node.getFunctionLabel();
				code.add(Call, functionStartLabel);
				return;
			}

			newAddressCode(node);
			Binding binding = node.getBinding();
			binding.generateAddress(code);
		}
		private boolean isFunctionNameBeingDeclared(IdentifierNode node) {
			ParseNode parent = node.getParent();
			return (parent instanceof FunctionNode) && (node == ((FunctionNode) parent).getChildNode_functionName());
		}
		private boolean isParameterBeingDeclared(IdentifierNode node) {
			ParseNode parent = node.getParent();
			return (parent instanceof ParameterSpecificationNode);
		}
		private boolean isFunctionNameBeingInvoked(IdentifierNode node) {
			ParseNode parent = node.getParent();
			return parent instanceof FunctionInvocationNode;
		}






		public void visit(IntegerConstantNode node) {
			newValueCode(node);

			code.add(PushI, node.getValue());
		}
		public void visit(FloatConstantNode node) {
			newValueCode(node);
			code.add(PushF, node.getValue());
		}
		public void visit(CharacterNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue());
		}
		public void visit(StringConstantNode node) {
			newValueCode(node); 

			String strAddressLabel ="_string_" + StringConstantNode.getCounter() + "_";
			code.add(DLabel, strAddressLabel);
			code.add(DataI, 3);
			code.add(DataI, 9);
			code.add(DataI, node.getValue().length());
			code.add(DataS, node.getValue());
			code.add(PushD, strAddressLabel);
		}
		public void visit(TypeIndicatorNode node) {}
		public void visit(VoidReturnTypeNode node) {}

		public void visit(BreakStatementNode node) {
			if(node.getType() == PrimitiveType.ERROR) {
				return;
			}
			String endLoopLabel = ((LoopNode)(node.getClosestLoopNode())).getEndLoopLabel();

			newVoidCode(node);
			code.add(Jump, endLoopLabel);
		}

		public void visit(ContinueStatementNode node) {
			if(node.getType() == PrimitiveType.ERROR) {
				return;
			}
			
			String targetLabel; 
			LoopNode loopNode = (LoopNode) (node.getClosestLoopNode());
			if(loopNode instanceof WhileNode) {
				targetLabel = loopNode.getStartLoopLabel();
			} else {
				targetLabel = loopNode.getIncrementLabel();
			}
			
			newVoidCode(node);
			code.add(Jump, targetLabel);
		}

	}
}
