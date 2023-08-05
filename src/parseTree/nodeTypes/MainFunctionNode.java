package parseTree.nodeTypes;

import com.sun.tools.javac.Main;
import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import tokens.Token;

public class MainFunctionNode extends FunctionNode {
    private String asmLabel;

    public MainFunctionNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.MAIN));
    }
    public MainFunctionNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    // attributes
    @Override
    public void setASMLabel(String label) {
        this.asmLabel = label;
    }
    public String getAsmLabel() {
        return asmLabel;
    }


    ///////////////////////////////////////////////////////////
    // boilerplate for visitors
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }
}