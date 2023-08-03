package semanticAnalyzer;

import logging.TanLogger;
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
import tokens.Token;

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


    @Override
    public void visitEnter(FunctionNode node) {
        //todo:implement scope for this
        enterParameterScope(node);
    }
    private void enterParameterScope(ParseNode node) {
        Scope baseScope = node.getLocalScope(); 
        Scope scope = baseScope.createParameterScope(); 
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

        addBindingForFunctionIdentifier(node, functionName, returnType, functionSignature);
    }
    private void addBindingForFunctionIdentifier(FunctionNode functionNode, IdentifierNode identifierNode, Type type, FunctionSignature functionSignature) {
        Scope scope = functionNode.getLocalScope();
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
        if(node.getChildNode_paramType() instanceof ErrorNode) {
            node.setType(PrimitiveType.ERROR);
            return;
        }
        
        ParseNode paramTypeNode = node.getChildNode_paramType(); 
        Type paramType = paramTypeNode.getType();
        if(paramType == PrimitiveType.VOID) {
            invalidVoidUsage(paramTypeNode);
            node.setType(PrimitiveType.ERROR);
            return;
        }
        
        IdentifierNode paramName = (IdentifierNode) node.getChildNode_paramName();
        paramName.setType(paramType);
        addBinding(paramName, paramType, Binding.Constancy.IS_CONSTANT); 
        
        node.setType(paramType);
    }
    private void addBinding(IdentifierNode identifierNode, Type type, Binding.Constancy constancy) {
        Scope scope = identifierNode.getLocalScope();
        Binding binding = scope.createBinding(identifierNode, type, constancy);
        identifierNode.setBinding(binding);
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



    ///////////////////////////////////////////////////////////////////////////
    // error logging/printing
    private void invalidVoidUsage(ParseNode voidTypeNode) {
        Token voidToken = voidTypeNode.getToken(); 
        logError("Invalid '" + voidToken.getLexeme() + "' usage, at: " + voidToken.getLocation() + 
                " (type '" + voidToken.getLexeme() + "' can only be used as a return type of a function).");
    }
    private void logError(String message) {
        TanLogger log = TanLogger.getLogger("compiler.semanticAnalyzer");
        log.severe(message);
    }
    
    
}
