package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.Type;
import tokens.Token;

import java.util.ArrayList;
import java.util.List;

public class ExpressionListNode extends ParseNode {
    private List<Type> childTypes = new ArrayList<>(); 
    
    
    public ExpressionListNode(Token token) {
        super(token); 
    }
    public ExpressionListNode(ParseNode node){
        super(node); 
    }
    
    ///////////////////////////////////////////////////////////
    //attributes
    public void setChildTypes(List<Type> types) {
        childTypes = types; 
    }
    public List<Type> getChildTypes() {
        return childTypes; 
    }

    
    
    ////////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
}
