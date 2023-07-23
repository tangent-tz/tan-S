package semanticAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import logging.TanLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
import semanticAnalyzer.types.Array;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;
import symbolTable.Binding;
import symbolTable.Scope;
import tokens.FloatToken;
import tokens.IntegerToken;
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

		if(node.child(0) instanceof IdentifierNode) {
			typeCheckAssignment_Identifier(node);
		}
		else if (node.child(0) instanceof TargetableArrayReferenceNode) {
			typeCheckAssignment_ArrayReference(node);
		}
	}

	private void typeCheckAssignment_Identifier(AssignmentStatementNode node) {
		List<Type> childTypes = new ArrayList<>();

		for (int i = 0; i < node.nChildren(); i++) {
			ParseNode child = node.child(i);
			childTypes.add(child.getType());
		}

		IdentifierNode identifier = (IdentifierNode) node.child(0);
		ParseNode expression = node.child(1);

		Type identifierType = identifier.getType();
		Type expressionType = expression.getType();


		int isPromotable = promotableTypesAssignment(childTypes); // check if we can promote

		if(!(expressionType.equivalent(identifierType)) && isPromotable !=0 && !isIdentifier(expression)) { // are lvalue and rvalue not same type, is it promotable, is rValue not identifier node,
			if (isPromotable == 1) { // promote char to int
				promoteCharacter(node);
				visitLeave(node);
			} else if (isPromotable == 2) { //promote int to float
				promoteInteger(node);
				visitLeave(node);
			}
		}
		else if (isPromotable == 0 && !(expressionType.equivalent(identifierType))){ //
			logError("types do not match in AssignmentStatement"); //
			return;
		}
		if (identifier.getBinding().isConstant()) {
			logError("re-assignment to a const identifier, previously declared at location: " + identifier.getBinding().getLocation());
			return;
		}

		node.setType(identifierType);
	}

	private void typeCheckAssignment_ArrayReference(AssignmentStatementNode node) {
		List<Type> childTypes = Arrays.asList(node.child(0).getType(), node.child(1).getType());

		TargetableArrayReferenceNode target = (TargetableArrayReferenceNode) (node.child(0));
		ParseNode expression = node.child(1);

		Type targetType = target.getType();
		Type expressionType = expression.getType();

		if (!(expressionType.equivalent(targetType))) {
			if (promotableTypesAssignment(childTypes) != 0) {
				if (promotableTypesAssignment(childTypes) == 1) {
					promoteCharacter(node);
				} else if (promotableTypesAssignment(childTypes) == 2) {
					promoteInteger(node);
				}
				visitLeave(node);
			} else {
				logError("types do not match in AssignmentStatement");
				return;
			}
		}

		node.setType(targetType);
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

	@Override
	public void visitLeave(IfStatementNode node) {
		if(node.child(0) instanceof ErrorNode) {
			node.setType(PrimitiveType.ERROR);
			return;
		}
		
		ParseNode ifCondition = node.child(0); 
		Type conditionType = ifCondition.getType(); 
		
		if(conditionType != PrimitiveType.BOOLEAN) {
			logError("Condition in if-statement at " + node.getToken().getLocation() + " is not a boolean expression");
			node.setType(PrimitiveType.ERROR);
			return; 
		}
		
		node.setType(PrimitiveType.NO_TYPE);
	}

	@Override
	public void visitLeave(WhileNode node) {
		ParseNode condition = node.child(0);

		if (condition.getType() != PrimitiveType.BOOLEAN) {
			logError("Condition in while loop at " + node.getToken().getLocation() + " is not a boolean expression");
			node.setType(PrimitiveType.ERROR);
		} else {
			node.setType(PrimitiveType.NO_TYPE);
		}
	}

	@Override
	public void visitLeave(ForNode node) {
		ParseNode condition = node.child(0);
		Type conditionType = condition.child(0).getType();

		if (conditionType != PrimitiveType.INTEGER) {
			logError("Condition in for loop at " + node.getToken().getLocation() + " is not a integer value");
			node.setType(PrimitiveType.ERROR);
		} else {
			node.setType(PrimitiveType.NO_TYPE);
		}
	}


	///////////////////////////////////////////////////////////////////////////
	// expressions
	@Override
	public void visitLeave(OperatorNode node) {
		List<Type> childTypes =  new ArrayList<>();

		for(int i=0; i < node.nChildren(); i++) {
			ParseNode child = node.child(i);
			childTypes.add(child.getType());
		}
		assert 1 <= node.nChildren() && node.nChildren() <= 2;

		Lextant operator = operatorFor(node);
		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);

		if(signature.accepts(childTypes)) {
			node.setType(signature.resultType().concreteType());
			node.setSignature(signature); 
		}
		else if(operator != Punctuator.INDEXING && operator != Keyword.LENGTH && childTypes.get(0) instanceof PrimitiveType && promotableTypes(childTypes) != 0) {
			if (promotableTypes(childTypes) == 1) {
				promoteCharacter(node);
			} else if (promotableTypes(childTypes) == 2) {
				promoteInteger(node);
			}
			visitLeave(node);
		}
		else {
			typeCheckError(node, childTypes);
			node.setType(PrimitiveType.ERROR);
		}
	}

	public int promotableTypes(List<Type> childTypes) {
		if(childTypes.size() != 2) {
			return 0;
		}
		Type type1 = childTypes.get(0);
		Type type2 = childTypes.get(1);

		if(type1 == PrimitiveType.FLOAT && type2 == PrimitiveType.INTEGER) {
			return 2;
		}
		else if(type1 == PrimitiveType.INTEGER && type2 == PrimitiveType.FLOAT) {
			return 2;
		}
		else if(type1 == PrimitiveType.CHARACTER && type2 == PrimitiveType.FLOAT) {
			return 1;
		}
		else if(type1 == PrimitiveType.FLOAT && type2 == PrimitiveType.CHARACTER) {
			return 1;
		}
		else if(type1 == PrimitiveType.CHARACTER && type2 == PrimitiveType.INTEGER) {
			return 1;
		}
		else if(type1 == PrimitiveType.INTEGER && type2 == PrimitiveType.CHARACTER) {
			return 1;
		}
		return 0;
	}

	public int promotableTypesAssignment(List<Type> childTypes) {
		if(childTypes.size() != 2) {
			return 0;
		}
		Type type1 = childTypes.get(0);
		Type type2 = childTypes.get(1);

		if(type1 == PrimitiveType.FLOAT && type2 == PrimitiveType.INTEGER) {
			return 2;
		}
		else if(type1 == PrimitiveType.FLOAT && type2 == PrimitiveType.CHARACTER) {
			return 1;
		}
		else if(type1 == PrimitiveType.INTEGER && type2 == PrimitiveType.CHARACTER) {
			return 1;
		}
		return 0;
	}

	private void promoteInteger(ParseNode node) {
		for (int i = 0; i < node.nChildren(); i++) {
			ParseNode child = node.child(i);
			if (child.getType() == PrimitiveType.INTEGER && child.nChildren() == 0) {
				Token test = FloatToken.make(child.getToken().getLocation(), child.getToken().getLexeme());
				FloatConstantNode floatNode = new FloatConstantNode(test);
				floatNode.setType(PrimitiveType.FLOAT);
				node.setType(PrimitiveType.FLOAT);
				node.replaceChild(child, floatNode);
			} else if (child.nChildren() > 0) { // If the child has its own children, recursively promote integers within it.
				promoteInteger(child);
			}
		}
	}

	private void promoteCharacter(ParseNode node) {
		for (int i = 0; i < node.nChildren(); i++) {
			ParseNode child = node.child(i);
			if (child.getType() == PrimitiveType.CHARACTER  && child.nChildren() == 0) {
				int test1 =((CharacterNode) child).getValue();
				Token test = IntegerToken.make(child.getToken().getLocation(), Integer.toString(test1));
				IntegerConstantNode integerNode = new IntegerConstantNode(test);
				integerNode.setType(PrimitiveType.INTEGER);
				node.setType(PrimitiveType.INTEGER);
				node.replaceChild(child, integerNode);
			} else if (child.nChildren() > 0) { // If the child has its own children, recursively promote integers within it.
				promoteCharacter(child);
			}
		}
	}

	private Lextant operatorFor(OperatorNode node) {
		LextantToken token = (LextantToken) node.getToken();
		return token.getLextant();
	}
	
	
	
	///////////////////////////////////////////////////////////
	// array
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
				promoteLevelFlag = -1;
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


	@Override
	public void visitLeave(ArrayNode node) {
		assert(node.nChildren() >= 1); 

		if(emptyArrayCreation(node)) {
			handleEmptyArrayCreation(node);
			return;
		}
		
		
		List<Type> childTypes = new ArrayList<>();
		
		for(int i=0; i < node.nChildren(); i++) {
			childTypes.add(node.child(i).getType());
		}
		boolean isPromotable = promoteCandidateArray(node);
		int highestLevel = checkHighestPromotableArray(node);

		if(highestLevel == 2 && isPromotable){
			node.setType(new Array(PrimitiveType.FLOAT, node.nChildren()));
		}
		else if(highestLevel == 1 && isPromotable){
			node.setType(new Array(PrimitiveType.INTEGER, node.nChildren()));
		}
		else if(highestLevel == -1 && isPromotable){
			logError("types do not match in array creation");
		}
		else{
			node.setType(new Array(childTypes.get(0), node.nChildren()));
		}

		
		//todo: check child types => if different types => try promoting to one unified type
	}
	
	private boolean emptyArrayCreation(ArrayNode node) {
		return node.child(0) instanceof ArrayTypeNode; 
	}
	private void handleEmptyArrayCreation(ArrayNode node) {
		Array arrayType = (Array) (node.child(0).getType());
		Type sizeType = node.child(1).getType();

		if(sizeType != PrimitiveType.INTEGER) {
			typeCheckError(node, Arrays.asList(arrayType, sizeType));
			node.setType(PrimitiveType.ERROR);
			return;
		}
		node.setType(arrayType);		
	}
	
	
	@Override
	public void visitLeave(ArrayTypeNode node) {
		assert(node.nChildren() == 1); 
		
		Type innerType = node.child(0).getType(); 
		node.setType(new Array(innerType));
	}

	@Override
	public void visitLeave(TargetableArrayReferenceNode node) {
		assert node.nChildren() == 2;

		List<Type> childTypes = Arrays.asList(node.child(0).getType(), node.child(1).getType());

		Lextant operator = operatorFor(node);
		FunctionSignature signature = FunctionSignatures.signature(operator, childTypes);

		if(signature.accepts(childTypes)) {
			node.setType(signature.resultType().concreteType());
			node.setSignature(signature);
		}
		else {
			typeCheckError(node, childTypes);
			node.setType(PrimitiveType.ERROR);
		}
	}
	
	
	

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
		if(token.isLextant(Punctuator.OPEN_BRACKETS)) {
			logError("empty array creation: array length specification needs to be of type INTEGER, but currently having type " + operandTypes.get(1) + ", at: " + token.getLocation());
			return;
		}

		logError("operator " + token.getLexeme() + " not defined for types " 
				 + operandTypes  + " at " + token.getLocation());	
	}
	private void logError(String message) {
		TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer");
		log.severe(message);
	}

	private boolean isIdentifier(ParseNode node){
		return node instanceof IdentifierNode;
	}
}