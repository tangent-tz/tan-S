package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class TypeCastingNode extends ParseNode {
    public TypeCastingNode(Token token) {
        super(token);
    }
    public TypeCastingNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    // attributes


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static TypeCastingNode withChildren(Token token, ParseNode typeIndicator, ParseNode expression) {
        TypeCastingNode node = new TypeCastingNode(token);
        node.appendChild(typeIndicator);
        node.appendChild(expression);
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
