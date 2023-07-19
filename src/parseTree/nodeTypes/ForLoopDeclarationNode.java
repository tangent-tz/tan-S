package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import tokens.LextantToken;
import tokens.Token;

public class ForLoopDeclarationNode extends ParseNode {

    public ForLoopDeclarationNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.FOR));
    }

    public ForLoopDeclarationNode(ParseNode node) {
        super(node);
    }


    ////////////////////////////////////////////////////////////
    // attributes

    public Lextant getDeclarationType() {
        return lextantToken().getLextant();
    }
    public LextantToken lextantToken() {
        return (LextantToken)token;
    }


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static DeclarationNode withChildren(Token token, ParseNode declaredName, ParseNode initializer) {
        DeclarationNode node = new DeclarationNode(token);
        node.appendChild(declaredName);
        node.appendChild(initializer);
        return node;
    }


    ///////////////////////////////////////////////////////////
    // boilerplate for visitors

    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }

    public static ForLoopDeclarationNode BinaryOperatorNode(Token token, ParseNode forExpressionScope,ParseNode forBlock) {
        ForLoopDeclarationNode node = new ForLoopDeclarationNode(token);
        node.appendChild(forExpressionScope);
        node.appendChild(forBlock);
        return node;
    }


}
