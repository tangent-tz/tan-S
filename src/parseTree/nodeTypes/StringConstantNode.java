package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.StringToken;
import tokens.Token;

public class StringConstantNode extends ParseNode {
    private static int counter = 0;

    public StringConstantNode(Token token) {
        super(token);
        assert(token instanceof StringToken);
        counter++;
    }
    public StringConstantNode(ParseNode node) {
        super(node);
    }

    //////////////////////////////////////////////////////////////
    //attributes

    public String getValue() {
        return stringToken().getValue();
    }
    public StringToken stringToken() {
        return (StringToken)token;
    }
    public static int getCounter() {
        return counter--;
    }

    //////////////////////////////////////////////////////////////
    //accept a visitor

    public void accept(ParseNodeVisitor visitor) {
        visitor.visit(this);
    }

}
