package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.LextantToken;
import tokens.Token;

public class AssignmentStatementNode extends ParseNode {

    public AssignmentStatementNode(Token token) {
        super(token);
        //TODO:dont need a token infront? assert(token.isLextant(Keyword.CONST));
    }

    public AssignmentStatementNode(ParseNode node) {
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
}

