package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class TypeCastingNode extends ParseNode {
    public TypeCastingNode(Token token) {
        super(token);
        assert(Keyword.isATypeKeyword(token.getLexeme()));
    }

    public TypeCastingNode(ParseNode node) {
        super(node);
    }


    ////////////////////////////////////////////////////////////
    // attributes


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static TypeCastingNode withChild(Token token, ParseNode expressionNode) {
        TypeCastingNode node = new TypeCastingNode(token);
        node.appendChild(expressionNode);
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
