package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.LextantToken;
import tokens.Token;

public class AssignmentStatementNode extends ParseNode {

    public AssignmentStatementNode(Token token) {
        super(token);
        //TODO:check if identifier exist before??
        assert(token.isLextant(Punctuator.ASSIGN));
    }

    public AssignmentStatementNode(ParseNode node) {
        super(node);
    }


    ////////////////////////////////////////////////////////////
    // attributes

//    public Lextant getAssignmentType() {
//        return lextantToken().getLextant();
//    }
//    public LextantToken lextantToken() {
//        return (LextantToken)token;
//    }


    ////////////////////////////////////////////////////////////
    // convenience factory

    public static AssignmentStatementNode withChildren(Token token, ParseNode identifier, ParseNode newInitializer) {
        AssignmentStatementNode node = new AssignmentStatementNode(token);
        node.appendChild(identifier);
        node.appendChild(newInitializer);
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

