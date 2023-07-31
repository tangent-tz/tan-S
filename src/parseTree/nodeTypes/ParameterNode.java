package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ParameterNode extends ParseNode {

    public ParameterNode(Token token) {
        super(token);
    }

    public ParameterNode(ParseNode node) {
        super(node);
    }

    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }

    public static ParameterNode withChildren(Token token, ParseNode... children) {
        ParameterNode node = new ParameterNode(token);
        for (ParseNode child : children) {
            node.appendChild(child);
        }
        return node;
    }
}
