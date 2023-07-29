package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ParameterListNode extends ParseNode {
    public ParameterListNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.PARAMETER_LIST));
    }
    public ParameterListNode(ParseNode node) {
        super(node);
    }
    
    
    /////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
    
}
