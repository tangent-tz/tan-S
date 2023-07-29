package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class FunctionInvocationNode extends ParseNode {
    public FunctionInvocationNode(Token token) {
        super(token); 
    }
    public FunctionInvocationNode(ParseNode node) {
        super(node); 
    }
    
    
    //////////////////////////////////////////////////////////////
    // convenience factory
    public static FunctionInvocationNode withChildren(Token token, ParseNode identifier, ParseNode expressionList) {
        FunctionInvocationNode node = new FunctionInvocationNode(token); 
        node.appendChild(identifier);
        node.appendChild(expressionList);
        return node; 
    }
    
    
    
    ////////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this); 
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
}
