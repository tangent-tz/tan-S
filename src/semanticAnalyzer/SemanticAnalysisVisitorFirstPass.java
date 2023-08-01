package semanticAnalyzer;

import parseTree.ParseNode;
import parseTree.ParseNodeVisitor;
import parseTree.nodeTypes.*;
import semanticAnalyzer.signatures.FunctionSignature;
import semanticAnalyzer.signatures.FunctionSignatures;
import semanticAnalyzer.types.Array;
import semanticAnalyzer.types.PrimitiveType;
import semanticAnalyzer.types.Type;
import symbolTable.Binding;
import symbolTable.Scope;

public class SemanticAnalysisVisitorFirstPass extends ParseNodeVisitor.Default {
    
    ///////////////////////////////////////////////////////////////////////////
    // constructs larger than statements
    @Override
    public void visitEnter(ProgramNode node) {
        enterProgramScope(node);
    }
    private void enterProgramScope(ParseNode node) {
        Scope scope = Scope.createProgramScope();
        node.setScope(scope);
    }
    
    
    
    
    //////////////////////////////////////////////////////////////////////////
    // function definition
    @Override
    public void visitLeave(FunctionNode node) {
        IdentifierNode functionName = (IdentifierNode) node.getChildNode_functionName();
        
        Type returnType = node.getChildNode_returnType().getType();
        Type[] paramTypes = ((ParameterListNode) node.getChildNode_paramList()).getParamTypes();

        FunctionSignature functionSignature = new FunctionSignature(paramTypes, returnType);
        new FunctionSignatures(functionName.getToken().getLexeme(), functionSignature);

        addBindingForFunctionIdentifier(functionName, returnType, functionSignature);
    }
    private void addBindingForFunctionIdentifier(IdentifierNode identifierNode, Type type, FunctionSignature functionSignature) {
        Scope scope = identifierNode.getLocalScope();
        Binding binding = scope.createBindingForFunctionIdentifier(identifierNode, type, functionSignature);
        identifierNode.setBinding(binding);
    }
    
    
    
    @Override
    public void visitLeave(ParameterListNode node) {
        Type[] paramTypes = new Type[node.nChildren()]; 
        for(int i=0; i < paramTypes.length; i++) {
            paramTypes[i] = node.child(i).getType(); 
        }
        node.setParamTypes(paramTypes);
    }
    
    @Override 
    public void visitLeave(ParameterSpecificationNode node) {
        Type paramType = node.getChildNode_paramType().getType(); 
        node.setType(paramType);
    }
    
    
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void visitLeave(ArrayTypeNode node) {
        assert(node.nChildren() == 1);

        Type innerType = node.child(0).getType();
        node.setType(new Array(innerType));
    }
    @Override
    public void visit(TypeIndicatorNode node) {
        node.setType(node.getValue());
    }
    @Override 
    public void visit(VoidReturnTypeNode node) {
        node.setType(PrimitiveType.VOID);
    }
    
    
    
    
}
