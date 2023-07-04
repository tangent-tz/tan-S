package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class IfStatementNode extends ParseNode {
    public IfStatementNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.IF));
    }
    
    public IfStatementNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    // convenience factory
    
    public static IfStatementNode withChildren(Token token, ParseNode ...children) {
        IfStatementNode node = new IfStatementNode(token);
        for (ParseNode child: children) {
            node.appendChild(child);
        }
        return node; 
    }

    ///////////////////////////////////////////////////////////
    // boilerplate for visitors
    
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this); 
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}
