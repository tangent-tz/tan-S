package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;


public class ProgramNode extends ParseNode {
	public ProgramNode(Token token) {
		super(token);
		assert(token.isLextant(Keyword.PROGRAM));
	}
	public ProgramNode(ParseNode node) {
		super(node);
	}

	////////////////////////////////////////////////////////////
	// attributes
	public ParseNode getChildNode_Main() {
		assert(nChildren() >= 1);
		return child(nChildren()-1);
	}
	
	///////////////////////////////////////////////////////////
	// boilerplate for visitors
	
	public void accept(ParseNodeVisitor visitor) {
		visitor.visitEnter(this);
		visitChildren(visitor);
		visitor.visitLeave(this);
	}
}
