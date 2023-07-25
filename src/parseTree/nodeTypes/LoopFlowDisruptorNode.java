package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import tokens.Token;

public class LoopFlowDisruptorNode extends ParseNode {
    private ParseNode closestLoopNode = null;

    public LoopFlowDisruptorNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.BREAK, Keyword.CONTINUE));
    }


    ////////////////////////////////////////////////////////////
    // Speciality functions
    public ParseNode findClosestLoopNode() {
        for(ParseNode node : pathToRoot()) {
            if (node instanceof WhileNode) {
                closestLoopNode = node;
                return closestLoopNode;
            }
        }
        noAssociatedLoopError();
        return closestLoopNode;
    }
    
    public ParseNode getClosestLoopNode() {
        return closestLoopNode;
    }
    
    
    ////////////////////////////////////////////////////////////
    // error reporting
    public void noAssociatedLoopError() {}
}
