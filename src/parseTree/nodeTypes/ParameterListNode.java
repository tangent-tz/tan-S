package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.Type;
import tokens.Token;


public class ParameterListNode extends ParseNode {
    private Type[] paramTypes; 
    
    
    public ParameterListNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.PARAMETER_LIST));
    }
    public ParameterListNode(ParseNode node) {
        super(node);
    }
    
    ///////////////////////////////////////////////////////
    // attributes
    public void setParamTypes(Type[] paramTypes) {
        this.paramTypes = paramTypes; 
    }
    public Type[] getParamTypes() {
        return this.paramTypes; 
    }
    
    
    
    
    
    
    /////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
    
}
