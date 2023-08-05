package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class FunctionNode extends ParseNode {
    public FunctionNode(Token token) {
        super(token); 
    }
    public FunctionNode(ParseNode node) {
        super(node); 
    }

    ////////////////////////////////////////////////////////////
    // no attributes
    public ParseNode getChildNode_returnType() {
        return child(0);
    }
    public ParseNode getChildNode_functionName() {
        return child(1); 
    }
    public ParseNode getChildNode_paramList() {
        return child(2); 
    }
    public ParseNode getChildNode_functionBody() {
        return child(3);
    }

    public void setASMLabel(String label) {
        IdentifierNode functionNameNode = (IdentifierNode) getChildNode_functionName();
        functionNameNode.setFunctionLabel(label);
    }
    
    
    ////////////////////////////////////////////////////////////
    // convenience factory
    public static ParseNode withChildren(Token token, ParseNode returnType, ParseNode functionName, ParseNode paramList, ParseNode functionBody) {  
        FunctionNode node = new FunctionNode(token);
        node.appendChild(returnType);
        node.appendChild(functionName);
        node.appendChild(paramList); 
        node.appendChild(functionBody);
        
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
