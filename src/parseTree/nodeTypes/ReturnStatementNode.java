package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import logging.TanLogger;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;


public class ReturnStatementNode extends ParseNode {
    private FunctionNode parentFunctionNode;
    
    
    public ReturnStatementNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.RETURN)); 
        parentFunctionNode = null; 
    }

    ////////////////////////////////////////////////////////////
    // Speciality functions    
    public FunctionNode findParentFunctionNode() {
        for(ParseNode node: pathToRoot()) {
            if(node instanceof FunctionNode) {
                parentFunctionNode = (FunctionNode) node; 
                return parentFunctionNode; 
            }
        }
        noAssociatedFunctionError();
        return parentFunctionNode; 
    }
    
    public FunctionNode getParentFunctionNode() {
        return parentFunctionNode; 
    }
    
    private void noAssociatedFunctionError() {
        TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer.returnStatementNode");
        Token token = getToken();
        log.severe("return statement used with no associated function definition at: " + token.getLocation());
    }
    
    
    
    
    //////////////////////////////////////////////////////////////
    //accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
    
    
    ///////////////////////////////////////////////////////////////
    // convenience factory
    public static ReturnStatementNode withChild(Token token, ParseNode returnedExpression) {
        ReturnStatementNode node = new ReturnStatementNode(token); 
        node.appendChild(returnedExpression);
        return node; 
    }
    
}
