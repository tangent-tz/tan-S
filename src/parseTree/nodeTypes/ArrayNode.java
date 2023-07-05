package parseTree.nodeTypes;

import lexicalAnalyzer.Punctuator;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ArrayNode extends ParseNode {
    public ArrayNode(Token token) {
        super(token); 
        assert(token.isLextant(Punctuator.OPEN_BRACKETS)); 
    }
    public ArrayNode(ParseNode node) {
        super(node); 
    }

    
    ///////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}
