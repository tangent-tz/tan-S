package semanticAnalyzer;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import logging.TanLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.LextantToken;
import tokens.Token;

class SemanticAnalysisVisitor extends ParseNodeVisitor.Default {
	@Override
	public void visitLeave(ParseNode node) {
		throw new RuntimeException("Node class unimplemented in SemanticAnalysisVisitor: " + node.getClass());
	}
	
	///////////////////////////////////////////////////////////////////////////
	// constructs larger than statements
	@Override
	public void visitEnter(ProgramNode node) {
		enterProgramScope(node);
	}
	public void visitLeave(ProgramNode node) {
		leaveScope(node);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	// helper methods for scoping.
	private void enterProgramScope(ParseNode node) {
		Scope scope = Scope.createProgramScope();
		node.setScope(scope);
	}
	private void enterSubscope(ParseNode node) {
		Scope baseScope = node.getLocalScope();
		Scope scope = baseScope.createSubscope();
		node.setScope(scope);
	}
	private void leaveScope(ParseNode node) {
		node.getScope().leave();
	}
	private void leaveSubScope(ParseNode node) {
		leaveScope(node);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// statements: declarations, assignmentStatement, blockStatement

	// printStatement
	@Override
	public void visitLeave(PrintStatementNode node) {
	}

	// declaration statement
	@Override
	public void visitLeave(DeclarationNode node) {
		if(node.child(0) instanceof ErrorNode) {
			node.setType(PrimitiveType.ERROR);
			return;
		}
		
		IdentifierNode identifier = (IdentifierNode) node.child(0);
		ParseNode initializer = node.child(1);
		
		Type declarationType = initializer.getType();
		node.setType(declarationType);

		identifier.setType(declarationType);
		Binding.Constancy constancy = (node.getToken().isLextant(Keyword.CONST))? Binding.Constancy.IS_CONSTANT : Binding.Constancy.IS_VARIABLE;
		addBinding(identifier, declarationType, constancy);
	}

	// assignmentStatement
	public void visitLeave(AssignmentStatementNode node) {
		if(node.child(0) instanceof ErrorNode) {
			node.setType(PrimitiveType.ERROR);
			return;
		}

		IdentifierNode identifier = (IdentifierNode) node.child(0);
		ParseNode expression = node.child(1);

		Type identifierType = identifier.getType();
		Type expressionType = expression.getType();

		if(!(expressionType.equals(identifierType))) {
			logError("types do not match in AssignmentStatement");
			return;
		}
		if(identifier.getBinding().isConstant()) {
			logError("re-assignment to a const identifier, previously declared at location: " + identifier.getBinding().getLocation());
			return;
		}
		node.setType(identifierType);
	}

	// blockStatement
	@Override
	public void visitEnter(BlockStatementNode node) {
		enterSubscope(node);
	}

	@Override
	public void visitLeave(BlockStatementNode node) {
		leaveSubScope(node);
	}



	///////////////////////////////////////////////////////////////////////////
	// expressions
	@Override
	public void visitLeave(OperatorNode node) {
		List<Type> childTypes;  
		if(node.nChildren() == 1) {
			ParseNode child = node.child(0);
			childTypes = Arrays.asList(child.getType());
		}
		else {
			assert node.nChildren() == 2;
			ParseNode left  = node.child(0);
			ParseNode right = node.child(1);
			
			childTypes = Arrays.asList(left.getType(), right.getType());		
		}
		
		Lextant operator = operatorFor(node);
		Punctuator operatorAsPunctuator = Punctuator.forLexeme(operator.getLexeme());
		FunctionSignature signature = null;

		if (childTypes.size() == 2) {
			if(operatorAsPunctuator == Punctuator.CAST) {
				signature = FunctionSignatures.signature(operatorAsPunctuator, childTypes);
			}
			else if (node.child(0).getType() == PrimitiveType.INTEGER && node.child(1).getType() == PrimitiveType.INTEGER) {
				signature = FunctionSignature.signatureOfInteger(operator);
			}
			else if(node.child(0).getType() == PrimitiveType.FLOAT && node.child(1).getType() == PrimitiveType.FLOAT){
				signature = FunctionSignature.signatureOfFloat(operator);
			}
			else if(node.child(0).getType() == PrimitiveType.CHARACTER && node.child(1).getType() == PrimitiveType.CHARACTER){
				signature = FunctionSignature.signatureOfChar(operator);
			}
			else if(node.child(0).getType() == PrimitiveType.BOOLEAN && node.child(1).getType() == PrimitiveType.BOOLEAN){
				signature = FunctionSignature.signatureOfBoolean(operator);
			}
			else if(node.child(0).getType() == ReferenceType.STRING && node.child(1).getType() == ReferenceType.STRING){
				signature = FunctionSignature.signatureOfString(operator);
			}
		} else {
			if(node.child(0).getType() == PrimitiveType.INTEGER) {
				signature = FunctionSignature.unarySignatureOfInteger(operator);
			} else {
				signature = FunctionSignature.unarySignatureOfFloat(operator);
			}
		}

		if(signature.accepts(childTypes)) {
			node.setType(signature.resultType());
		}
		else {
			typeCheckError(node, childTypes);
			node.setType(PrimitiveType.ERROR);
		}
	}
	private Lextant operatorFor(OperatorNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}


//	//type casting
//	@Override
//	public void visitLeave(TypeCastingNode node) {
////		if(node.child(0) instanceof ErrorNode) {
////			node.setType(PrimitiveType.ERROR);
////			return;
////		}
////
////		assert node.nChildren() == 2;
////		ParseNode left  = node.child(0);
////		ParseNode right = node.child(1);
////
////		List<Type> childTypes = Arrays.asList(left.getType(), right.getType());
////
////		TypeIndicatorNode typeNode = (TypeIndicatorNode) node.child(0);
////		ParseNode expression = node.child(1);
////
////		Type targetType = typeNode.getValue();
////		Type sourceType = expression.getType();
////
////		Lextant operator = operatorFor(node);
////		FunctionSignature signature = null;
////
////		if(right.getType() == PrimitiveType.BOOLEAN) {
////			signature = FunctionSignature.signatureOfCastingBoolean();
////		}
////		else if(right.getType() == PrimitiveType.CHARACTER) {
////			signature = FunctionSignature.signatureOfCastingCharacter(operator);
////		}
//
//
//	}
//	private Lextant operatorFor(TypeCastingNode node) {
//		LextantToken token = (LextantToken) node.getToken();
//		return token.getLextant();
//	}







	///////////////////////////////////////////////////////////////////////////
	// simple leaf nodes
	@Override
	public void visit(BooleanConstantNode node) {
		node.setType(PrimitiveType.BOOLEAN);
	}
	@Override
	public void visit(ErrorNode node) {
		node.setType(PrimitiveType.ERROR);
	}
	@Override
	public void visit(IntegerConstantNode node) {
		node.setType(PrimitiveType.INTEGER);
	}
	@Override
	public void visit(FloatConstantNode node) {
		node.setType(PrimitiveType.FLOAT);
	}
	@Override
	public void visit(CharacterNode node) {
		node.setType(PrimitiveType.CHARACTER);
	}
	@Override
	public void visit(StringConstantNode node) {
		node.setType(ReferenceType.STRING);
	}
	@Override
	public void visit(NewlineNode node) {
	}
	@Override
	public void visit(SpaceNode node) {
	}
	@Override
	public void visit(TabNode node) {
	}
	@Override
	public void visit(TypeIndicatorNode node) {
		node.setType(node.getValue());
	}

	///////////////////////////////////////////////////////////////////////////
	// IdentifierNodes, with helper methods
	@Override
	public void visit(IdentifierNode node) {
		if(!isBeingDeclared(node)) {
			Binding binding = node.findVariableBinding();
			
			node.setType(binding.getType());
			node.setBinding(binding);
		}
		// else parent DeclarationNode does the processing.
	}
	private boolean isBeingDeclared(IdentifierNode node) {
		ParseNode parent = node.getParent();
		return (parent instanceof DeclarationNode) && (node == parent.child(0));
	}
	private void addBinding(IdentifierNode identifierNode, Type type, Binding.Constancy constancy) {
		Scope scope = identifierNode.getLocalScope();
		Binding binding = scope.createBinding(identifierNode, type, constancy);
		identifierNode.setBinding(binding);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// error logging/printing

	private void typeCheckError(ParseNode node, List<Type> operandTypes) {
		Token token = node.getToken();
		if (token.isLextant(Punctuator.CAST)) {
			logError("casting from " + operandTypes.get(1) + " to " + operandTypes.get(0) + " is not allowed, at " + token.getLocation());
			return;
		}

		logError("operator " + token.getLexeme() + " not defined for types " 
				 + operandTypes  + " at " + token.getLocation());	
	}
	private void logError(String message) {
		TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer");
		log.severe(message);
	}
}