package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class FunctionDefinitionNode extends ParseNode {

    public FunctionDefinitionNode(Token token) {
        super(token);
    }
    public FunctionDefinitionNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    // no attributes


    ///////////////////////////////////////////////////////////
    // boilerplate for visitors

    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }

    public static FunctionDefinitionNode withChildren(Token token, ParseNode... children) {
        FunctionDefinitionNode node = new FunctionDefinitionNode(token);

        for (ParseNode child : children) {
            node.appendChild(child);
        }
        return node;
    }
}
