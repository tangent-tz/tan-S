package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.LextantToken;
import tokens.Token;

public class TargetableArrayReferenceNode extends OperatorNode {
    public TargetableArrayReferenceNode(Token token) {
        super(token); 
        assert(token instanceof LextantToken); 
    }
    
    public TargetableArrayReferenceNode(ParseNode node) {
        super(node); 
    }


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static ParseNode withChildren(Token token, ParseNode ...children) {
        TargetableArrayReferenceNode node = new TargetableArrayReferenceNode(token); 
        for(ParseNode child: children) {
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
