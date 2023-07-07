package asmCodeGenerator;

import java.util.*;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.operators.SimpleCodeGenerator;
import asmCodeGenerator.runtime.MemoryManager;
import asmCodeGenerator.runtime.RunTime;
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

		code.append( globalVariableBlockASM() );

		code.append( programASM() );

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
	private ASMCodeFragment programASM() {
		ASMCodeFragment code = new ASMCodeFragment(GENERATES_VOID);

		code.add(    Label, RunTime.MAIN_PROGRAM_LABEL);
		code.append( programCode());
		code.add(    Halt );

		return code;
	}
	private ASMCodeFragment programCode() {
		CodeVisitor visitor = new CodeVisitor();
		root.accept(visitor);
		return visitor.removeRootCode(root);
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

		public void visitLeave(WhileNode node) {
			newVoidCode(node);
			ASMCodeFragment lvalue = removeValueCode(node.child(0));
			ASMCodeFragment rvalue = removeVoidCode(node.child(1));

			Labeller labeller = new Labeller("while-statement");
			String startLabel = labeller.newLabel("-while-start");
			String endLabel = labeller.newLabel("-while-end");

			code.add(Label, startLabel);
			code.append(lvalue);
			code.add(JumpFalse, endLabel);
			code.append(rvalue);
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

			if(operator == Punctuator.SUBTRACT || operator == Punctuator.ADD || operator == Punctuator.BOOLEAN_NOT) {
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
			else {
				visitNormalBinaryOperatorNode(node);
			}
		}
		private void visitUnaryOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));

			code.append(arg1);

			FunctionSignature sig = FunctionSignatures.signature(node.getOperator(), Arrays.asList(node.child(0).getType()));
			ASMOpcode opcode = (ASMOpcode) (sig.getVariant());
			code.add(opcode);
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
		
		
		
		///////////////////////////////////////////////////////////////////////////
		// array
		public void visitLeave(ArrayNode node) {
			List<ASMCodeFragment> elements = new ArrayList<>();
			for(int i = 0; i < node.nChildren(); i++) {
				ASMCodeFragment child = removeValueCode(node.child(i));
				elements.add(child);
			}
			int header_typeIdentifier_byteConsumption = 4;
			int header_status_byteConsumption = 4;
			int header_subtypeSize_byteConsumption = 4;
			int header_length_byteConsumption = 4;
			
			
			int numOfElements = node.nChildren();
			Type subtype = node.getType().getSubtype();
			int subtypeSize = subtype.getSize();

			newValueCode(node);
			int totalSize = (header_typeIdentifier_byteConsumption 
							+ header_status_byteConsumption 
							+ header_subtypeSize_byteConsumption 
							+ header_length_byteConsumption) 
							+ (numOfElements * subtypeSize);
			
			
			Labeller labeller = new Labeller("array"); 
			String pointerLabel = labeller.newLabel("pointer"); 
			
			
			// Allocate memory for the array
			code.add(PushI, totalSize);  // memory needed = size * offset
			code.add(Call, MemoryManager.MEM_MANAGER_ALLOCATE);
			
			
			//Store header data
			code.add(DLabel, pointerLabel); 
			code.add(PushD, pointerLabel); 
			code.add(Exchange); 
			code.add(StoreI); 
			
			
			//storing type identifier:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, 0); //offset (fixed)
			code.add(Add); 				//base address + offset
			code.add(PushI, 5); //stack: [... addr] -> [... addr 5]
			code.add(StoreI); 			//store 5 into the address
			
			//storing status:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, 4); //offset (fixed)
			code.add(Add); 				//base address + offset
			if(subtype instanceof PrimitiveType) {
				code.add(PushI, 0); 
			}
			else {
				code.add(PushI, 2);
			}
			code.add(StoreI); 	
			
			//storing subtype size:
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, 8); 
			code.add(Add); 
			code.add(PushI, subtypeSize);
			code.add(StoreI);

			//storing length (number of elements)
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
			code.add(PushI, 12);
			code.add(Add);
			code.add(PushI, numOfElements);
			code.add(StoreI);

			// Store each element in the array
			for (int i = 0; i < numOfElements; i++) {
				code.add(PushD, pointerLabel);
				code.add(LoadI); 			//loads the base address of the array
				code.add(PushI, 16); 
				code.add(Add); 
				
				code.add(PushI, subtypeSize*i); //offset
				code.add(Add);
				code.append(elements.get(i));
				code.add(opcodeForStore(subtype));
			}
			
			code.add(PushD, pointerLabel);
			code.add(LoadI); 			//loads the base address of the array
		}

		
		
		
		///////////////////////////////////////////////////////////////////////////
		// leaf nodes (ErrorNode not necessary)
		public void visit(BooleanConstantNode node) {
			newValueCode(node);
			code.add(PushI, node.getValue() ? 1 : 0);
		}
		public void visit(IdentifierNode node) {
			newAddressCode(node);
			Binding binding = node.getBinding();

			binding.generateAddress(code);
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
		public void visit(TypeIndicatorNode node) {
		}
	}
}
