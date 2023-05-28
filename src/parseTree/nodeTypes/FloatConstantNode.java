package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.NumberToken;
import tokens.Token;

public class FloatConstantNode extends ParseNode {
    public FloatConstantNode(Token token) {
        super(token);
        assert(token instanceof NumberToken);
    }
    public FloatConstantNode(ParseNode node) {
        super(node);
    }

////////////////////////////////////////////////////////////
// attributes

    public double getValue() {
        return (Double)numberToken().getValue();
    }

    public NumberToken numberToken() {
        return (NumberToken)token;
    }

///////////////////////////////////////////////////////////
// accept a visitor

    public void accept(ParseNodeVisitor visitor) {visitor.visit(this);
    }

}
