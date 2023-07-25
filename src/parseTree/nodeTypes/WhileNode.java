package parseTree.nodeTypes;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import lexicalAnalyzer.Keyword;
import tokens.Token;

import java.util.ArrayList;

public class WhileNode extends ParseNode {
    private ArrayList<String> ASMLinkageLabels = new ArrayList<>();
    
    public WhileNode(Token token) {
        super(token);
        assert(token.isLextant(Keyword.WHILE));
    }
    public WhileNode(ParseNode node) {
        super(node);
    }


    ////////////////////////////////////////////////////////////
    // Speciality functions
    public void storeLinkageLabels(String startLoopLabel, String endLoopLabel) {
        ASMLinkageLabels.add(startLoopLabel); 
        ASMLinkageLabels.add(endLoopLabel); 
    }
    
    public String getStartLoopLabel() {
        return ASMLinkageLabels.get(0); 
    }
    
    public String getEndLoopLabel() {
        return ASMLinkageLabels.get(1); 
    }
    
    
    ///////////////////////////////////////////////////////////
    // accept a visitor
    public void accept(ParseNodeVisitor visitor) {
        visitor.visitEnter(this);
        visitChildren(visitor);
        visitor.visitLeave(this);
    }


    public static WhileNode withChildren(Token token, ParseNode... children) {
        WhileNode node = new WhileNode(token);

        for (ParseNode child : children) {
            node.appendChild(child);
        }

        return node;
    }

}
