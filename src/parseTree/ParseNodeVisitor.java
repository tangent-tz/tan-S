package parseTree;

import parseTree.nodeTypes.*;

// Visitor pattern with pre- and post-order visits
public interface ParseNodeVisitor {
	
	// non-leaf nodes: visitEnter and visitLeave
	void visitEnter(OperatorNode node);
	void visitLeave(OperatorNode node);
	
	void visitEnter(BlockStatementNode node);
	void visitLeave(BlockStatementNode node);

	void visitEnter(DeclarationNode node);
	void visitLeave(DeclarationNode node);

	void visitEnter(AssignmentStatementNode node);
	void visitLeave(AssignmentStatementNode node);

	void visitEnter(ParseNode node);
	void visitLeave(ParseNode node);
	
	void visitEnter(PrintStatementNode node);
	void visitLeave(PrintStatementNode node);
	
	void visitEnter(ProgramNode node);
	void visitLeave(ProgramNode node);
	void visitEnter(MainNode node);
	void visitLeave(MainNode node);

	void visitEnter(WhileNode node);
	void visitLeave(WhileNode node);
	void visitEnter(ForNode node);
	void visitLeave(ForNode node);

	void visitEnter(IfStatementNode node);
	void visitLeave(IfStatementNode node);
	
	void visitEnter(ArrayNode node); 
	void visitLeave(ArrayNode node); 
	
	void visitEnter(ArrayTypeNode node); 
	void visitLeave(ArrayTypeNode node);
	
	void visitEnter(TargetableArrayReferenceNode node); 
	void visitLeave(TargetableArrayReferenceNode node);
	void visitEnter(FunctionDefinitionNode node);
	void visitLeave(FunctionDefinitionNode node);



	// leaf nodes: visitLeaf only
	void visit(BooleanConstantNode node);
	void visit(ErrorNode node);
	void visit(IdentifierNode node);
	void visit(IntegerConstantNode node);
	void visit(FloatConstantNode node);
	void visit(CharacterNode node);
	void visit(StringConstantNode node);
	void visit(NewlineNode node);
	void visit(SpaceNode node);
	void visit(TabNode node);
	void visit(TypeIndicatorNode node);
	void visit(BreakStatementNode node);
	void visit(ContinueStatementNode node);
	void visitEnter(ParameterListNode node);
	void visitLeave(ParameterListNode node);




	public static class Default implements ParseNodeVisitor
	{
		public void defaultVisit(ParseNode node) {	}
		public void defaultVisitEnter(ParseNode node) {
			defaultVisit(node);
		}
		public void defaultVisitLeave(ParseNode node) {
			defaultVisit(node);
		}		
		public void defaultVisitForLeaf(ParseNode node) {
			defaultVisit(node);
		}
		
		public void visitEnter(OperatorNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(OperatorNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(DeclarationNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(DeclarationNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(AssignmentStatementNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(AssignmentStatementNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(BlockStatementNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(BlockStatementNode node) {
			defaultVisitLeave(node);
		}				
		public void visitEnter(ParseNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ParseNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(PrintStatementNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(PrintStatementNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ProgramNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ProgramNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(MainNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(MainNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(WhileNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(WhileNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ForNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ForNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(IfStatementNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(IfStatementNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ArrayNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ArrayNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ArrayTypeNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(ArrayTypeNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(TargetableArrayReferenceNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(TargetableArrayReferenceNode node) { defaultVisitLeave(node);}
		public void visitEnter(FunctionDefinitionNode node) {
			defaultVisitEnter(node);
		}
		public void visitLeave(FunctionDefinitionNode node) {
			defaultVisitLeave(node);
		}
		public void visitEnter(ParameterListNode node) {
			defaultVisitEnter(node);
		}

		public void visitLeave(ParameterListNode node) {
			defaultVisitLeave(node);
		}





		public void visit(BooleanConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(ErrorNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(IdentifierNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(IntegerConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(FloatConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(CharacterNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(StringConstantNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(NewlineNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(SpaceNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(TabNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(TypeIndicatorNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(BreakStatementNode node) {
			defaultVisitForLeaf(node);
		}
		public void visit(ContinueStatementNode node) {
			defaultVisitForLeaf(node);
		}

	}
}
