package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import tokens.Token;


public class WhileNode extends LoopNode {
    public WhileNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.WHILE));
    }
    public WhileNode(ParseNode node) {
        super(node);
    }
    
    
    ///////////////////////////////////////////////////////////
    // accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }


    public static WhileNode withChildren(Token token, ParseNode... children) {
        WhileNode node = new WhileNode(token);

        for (ParseNode child : children) {
            node.appendChild(child);
        }

        return node;
    }

}
