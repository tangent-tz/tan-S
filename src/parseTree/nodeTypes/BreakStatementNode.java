package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import logging.TanLogger;
import parseTree.ParseNodeVisitor;
import tokens.Token;


public class BreakStatementNode extends LoopFlowDisruptorNode {
    public BreakStatementNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.BREAK));
    }
    
    
    //////////////////////////////////////////////////////////////
    //accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visit(this);
    }

    
    
    ////////////////////////////////////////////////////////////
    // error reporting
    @Override
    public void noAssociatedLoopError() {
        TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer.breakStatementNode");
        Token token = getToken();
        log.severe("break statement used with no associated loop at: " + token.getLocation());
    }
}
