package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ParameterSpecificationNode extends ParseNode {
    public ParameterSpecificationNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.PARAMETER_SPECIFICATION));
    }
    public ParameterSpecificationNode(ParseNode node) {
        super(node);
    }
    
    ///////////////////////////////////////////////////////
    // convenience factory
    public static ParameterSpecificationNode withChildren(Token token, ParseNode paramType, ParseNode paramName) {
        ParameterSpecificationNode node = new ParameterSpecificationNode(token);
        node.appendChild(paramType);
        node.appendChild(paramName);
        return node;
    }
    
    
    
    //////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this); 
        visitChildren(visitor);
        visitor.visitLeave(this); 
    }
    
}
