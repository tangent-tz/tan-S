package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class MainNode extends ParseNode {
    public MainNode(Token token) {
        super(token);
    }


    public static MainNode withChildren(Token token, ParseNode... children) {
        MainNode node = new MainNode(token);
        for (ParseNode child : children) {
            node.appendChild(child);
        }
        return node;
    }

    ////////////////////////////////////////////////////////////
    // boilerplate for visitors

    @Override
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}
