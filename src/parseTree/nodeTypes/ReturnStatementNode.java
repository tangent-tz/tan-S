package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;


public class ReturnStatementNode extends ParseNode {
    public ReturnStatementNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.RETURN)); 
    }

    //////////////////////////////////////////////////////////////
    //accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
    
    ///////////////////////////////////////////////////////////////
    // convenience factory
    public static ReturnStatementNode withChild(Token token, ParseNode returnedExpression) {
        ReturnStatementNode node = new ReturnStatementNode(token); 
        node.appendChild(returnedExpression);
        return node; 
    }
    
}
