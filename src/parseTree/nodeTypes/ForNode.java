package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import tokens.LextantToken;
import tokens.Token;

public class ForNode extends ParseNode {
    public ForNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.FOR));
    }
    public ForNode(ParseNode node) {
        super(node);
    }

    ///////////////////////////////////////////////////////////
// accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }


    public static ForNode withChildren(Token token, ParseNode... children) {
        ForNode node = new ForNode(token);

        for (ParseNode child : children) {
            node.appendChild(child);
        }

        return node;
    }

}
