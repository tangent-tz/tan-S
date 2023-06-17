package asmCodeGenerator;

import java.util.*;

import asmCodeGenerator.codeStorage.ASMCodeFragment;
import asmCodeGenerator.codeStorage.ASMOpcode;
import asmCodeGenerator.operators.SimpleCodeGenerator;
import asmCodeGenerator.runtime.RunTime;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import parseTree.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
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
//		code.append( MemoryManager.codeForAfterApplication() );

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
			assert false: "Type " + type + " unimplemented in opcodeForStore()";
			return null;
		}


		///////////////////////////////////////////////////////////////////////////
		// expressions
		public void visitLeave(OperatorNode node) {
			Lextant operator = node.getOperator();

			if(operator == Punctuator.SUBTRACT || operator == Punctuator.ADD) {
				if(node.nChildren() == 1)
					visitUnaryOperatorNode(node);
				else
					visitNormalBinaryOperatorNode(node);
			}
			else if(operator == Punctuator.EQUALS) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.BOOLEAN && node.child(1).getType() == PrimitiveType.BOOLEAN) {
					visitComparisonEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonEqualFloatOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == ReferenceType.STRING && node.child(1).getType() == ReferenceType.STRING) {
					visitComparisonEqualStringOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.GREATER) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonGreaterIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonGreaterIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonGreaterFloatOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.LESSER) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonLesserIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonLesserIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonLesserFloatOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.NOTEQUALS) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonNotEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonNotEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.BOOLEAN && node.child(1).getType() == PrimitiveType.BOOLEAN) {
					visitComparisonNotEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonNotEqualFloatOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == ReferenceType.STRING && node.child(1).getType() == ReferenceType.STRING) {
					visitComparisonNotEqualStringOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.GREATEREQUAL) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonGreatEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonGreatEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonGreatEqualFloatOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.LESSEREQUAL) {
				if(node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
					visitComparisonLessEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER) {
					visitComparisonLessEqualIntegerOperatorNode(node, operator);
				}
				else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT) {
					visitComparisonLessEqualFloatOperatorNode(node, operator);
				}
			}
			else if(operator == Punctuator.CAST) {
				visitCastingOperatorNode(node);
			}
			else {
				visitNormalBinaryOperatorNode(node);
			}
		}
		private void visitComparisonGreaterIntegerOperatorNode(OperatorNode node,
															   Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpPos, trueLabel);
			code.add(Jump, falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonEqualIntegerOperatorNode(OperatorNode node,
															 Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpFalse, trueLabel);
			code.add(Jump,falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonEqualFloatOperatorNode(OperatorNode node,
															 Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFZero, trueLabel);
			code.add(Jump,falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonGreaterFloatOperatorNode(OperatorNode node,
												 Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFPos, trueLabel);
			code.add(Jump, falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}

		private void visitComparisonLesserIntegerOperatorNode(OperatorNode node,
															 Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpNeg, trueLabel);
			code.add(Jump, falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonLesserFloatOperatorNode(OperatorNode node,
															  Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFNeg, trueLabel);
			code.add(Jump, falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonNotEqualIntegerOperatorNode(OperatorNode node,
															Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpFalse, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitComparisonNotEqualFloatOperatorNode(OperatorNode node,
																Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFZero, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}

		private void visitComparisonGreatEqualIntegerOperatorNode(OperatorNode node,
															  Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpNeg, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}
		private void visitComparisonGreatEqualFloatOperatorNode(OperatorNode node,
																  Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFNeg, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}
		private void visitComparisonLessEqualIntegerOperatorNode(OperatorNode node,
																  Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpPos, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}
		private void visitComparisonLessEqualFloatOperatorNode(OperatorNode node,
																Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(FSubtract);

			code.add(JumpFPos, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}
		private void visitComparisonEqualStringOperatorNode(OperatorNode node,
															 Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpFalse, trueLabel);
			code.add(Jump,falseLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);
		}
		private void visitComparisonNotEqualStringOperatorNode(OperatorNode node,
																Lextant operator) {

			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			Labeller labeller = new Labeller("compare");

			String startLabel = labeller.newLabel("arg1");
			String arg2Label  = labeller.newLabel("arg2");
			String subLabel   = labeller.newLabel("sub");
			String trueLabel  = labeller.newLabel("true");
			String falseLabel = labeller.newLabel("false");
			String joinLabel  = labeller.newLabel("join");

			newValueCode(node);
			code.add(Label, startLabel);
			code.append(arg1);
			code.add(Label, arg2Label);
			code.append(arg2);
			code.add(Label, subLabel);
			code.add(Subtract);

			code.add(JumpFalse, falseLabel);
			code.add(Jump, trueLabel);

			code.add(Label, trueLabel);
			code.add(PushI, 1);
			code.add(Jump, joinLabel);
			code.add(Label, falseLabel);
			code.add(PushI, 0);
			code.add(Jump, joinLabel);
			code.add(Label, joinLabel);

		}
		private void visitUnaryOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));

			code.append(arg1);
			if(node.getType() == PrimitiveType.FLOAT) {
				ASMOpcode opcode = opcodeFoUnaryFloatOperator(node.getOperator());
				code.add(opcode);
			}
			else {
				ASMOpcode opcode = opcodeFoUnaryIntegerOperator(node.getOperator());
				code.add(opcode);
			}
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

		private void visitNormalBinaryOperatorNode(OperatorNode node) {
			newValueCode(node);
			ASMCodeFragment arg1 = removeValueCode(node.child(0));
			ASMCodeFragment arg2 = removeValueCode(node.child(1));

			code.append(arg1);
			code.append(arg2);

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
