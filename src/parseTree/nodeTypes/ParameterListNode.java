package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ParameterListNode extends ParseNode {
    public ParameterListNode(Token token) {
        super(token);
    }

    ////////////////////////////////////////////////////////////
    // ATTRIBUTES
    // The list of parameters can be accessed via the inherited
    // field: "children".

    ////////////////////////////////////////////////////////////
    // CONVENIENCE METHODS

    public void appendParameter(ParseNode parameter) {
        appendChild(parameter);
    }

    ////////////////////////////////////////////////////////////
    // BOILERPLATE

    @Override
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitor.visitLeave(this);
    }
    public static ParameterListNode withChildren(Token token, ParseNode... children) {
        ParameterListNode node = new ParameterListNode(token);
        for (ParseNode child : children) {
            node.appendChild(child);
        }
        return node;
    }

}
