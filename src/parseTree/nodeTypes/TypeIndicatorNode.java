package parseTree.nodeTypes;

import lexicalAnalyzer.Keyword;
import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.ReferenceType;
import semanticAnalyzer.types.Type;
import tokens.Token;

public class TypeIndicatorNode extends ParseNode {
    public TypeIndicatorNode(Token token) {
        super(token);
        assert(Keyword.isATypeKeyword(token.getLexeme()));
    }
    public TypeIndicatorNode(ParseNode node) {
        super(node);
    }

    ////////////////////////////////////////////////////////////
    //attributes

    public Type getValue() {
        switch(token.getLexeme()) {
            case "bool":
                return PrimitiveType.BOOLEAN;
            case "char":
                return PrimitiveType.CHARACTER;
            case "string":
                return ReferenceType.STRING;
            case "int":
                return PrimitiveType.INTEGER;
            case "float":
                return PrimitiveType.FLOAT;
            default:
                return PrimitiveType.NO_TYPE;
        }
    }

    //////////////////////////////////////////////////////////////
    //accept a visitor

    public void accept(ParseNodeVisitor visitor) {
        visitor.visit(this);
    }
}
