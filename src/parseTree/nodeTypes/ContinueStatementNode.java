package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import logging.TanLogger;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class ContinueStatementNode extends LoopFlowDisruptorNode {
    public ContinueStatementNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.CONTINUE));
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
        TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer.continueStatementNode");
        Token token = getToken();
        log.severe("continue statement used with no associated loop at: " + token.getLocation());
    }
}

