package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;


public class VoidReturnTypeNode extends ParseNode {
    public VoidReturnTypeNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.VOID)); 
    }
    public VoidReturnTypeNode(ParseNode node) {
        super(node); 
    }


    //////////////////////////////////////////////////////////////
    //accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visit(this); 
    }
    
    
}
