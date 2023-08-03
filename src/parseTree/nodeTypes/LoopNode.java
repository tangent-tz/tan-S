package parseTree.nodeTypes;

import parseTree.ParseNode;
import tokens.Token;

import java.util.ArrayList;

public class LoopNode extends ParseNode {
    private ArrayList<String> ASMLinkageLabels = new ArrayList<>();
    
    public LoopNode(Token token) {
        super(token);
    }
    public LoopNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    // Speciality functions
    public void storeLinkageLabels(String... labels) {
        for(String label: labels) {
            ASMLinkageLabels.add(label);
        }
    }
    public String getStartLoopLabel() {
        return ASMLinkageLabels.get(0);
    }
    public String getEndLoopLabel() {
        return ASMLinkageLabels.get(1);
    }
    public String getIncrementLabel() {
        return ASMLinkageLabels.get(2);
    }

}
