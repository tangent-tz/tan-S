package parseTree.nodeTypes;

import com.sun.tools.javac.Main;
import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class MainFunctionNode extends FunctionNode {
    public MainFunctionNode(Token token) {
        super(token); 
        assert(token.isLextant(Keyword.MAIN)); 
    }
    public MainFunctionNode(ParseNode node) {
        super(node); 
    }

    ////////////////////////////////////////////////////////////
    // no attributes


    ///////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}
