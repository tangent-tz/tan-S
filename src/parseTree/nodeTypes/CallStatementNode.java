package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class CallStatementNode extends ParseNode {
    public CallStatementNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.CALL));
    }


    
    ///////////////////////////////////////////////////////////
    // convenience factory
    public static CallStatementNode withChild(Token token, ParseNode functionInvocation) {
        CallStatementNode node = new CallStatementNode(token); 
        node.appendChild(functionInvocation);
        return node; 
    }
    
    
    
    ////////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
    
}
