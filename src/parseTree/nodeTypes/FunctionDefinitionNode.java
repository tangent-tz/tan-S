package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class FunctionDefinitionNode extends ParseNode {
    private ParseNode returnTypeNode; // Add return type node

    public FunctionDefinitionNode(Token token) {
        super(token);
    }

    public FunctionDefinitionNode(ParseNode node) {
        super(node);
    }

    // Adding getter and setter for return type
    public ParseNode getReturnTypeNode() {
        return this.returnTypeNode;
    }

    public void setReturnTypeNode(ParseNode returnTypeNode) {
        this.returnTypeNode = returnTypeNode;
    }

    ////////////////////////////////////////////////////////////
    // boilerplate for visitors

    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }

    public static FunctionDefinitionNode withChildren(Token token, ParseNode returnTypeNode, ParseNode... children) {
        FunctionDefinitionNode node = new FunctionDefinitionNode(token);
        node.setReturnTypeNode(returnTypeNode); // set return type node

        for (ParseNode child : children) {
            node.appendChild(child);
        }
        return node;
    }
}
