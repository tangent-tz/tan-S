package parser;

import java.util.Arrays;

import logging.TanLogger;
import parseTree.*;
import parseTree.nodeTypes.*;
import tokens.*;
import lexicalAnalyzer.Keyword;
import lexicalAnalyzer.Lextant;
import lexicalAnalyzer.Punctuator;
import lexicalAnalyzer.Scanner;


public class Parser {
	private Scanner scanner;
	private Token nowReading;
	private Token previouslyRead;
	
	public static ParseNode parse(Scanner scanner) {
		Parser parser = new Parser(scanner);
		return parser.parse();
	}
	public Parser(Scanner scanner) {
		super();
		this.scanner = scanner;
	}
	
	public ParseNode parse() {
		readToken();
		return parseProgram();
	}

	////////////////////////////////////////////////////////////
	// parse program (the largest unit)
	private ParseNode parseProgram() {
		if(!startsProgram(nowReading)) {
			return syntaxErrorNode("program"); 
		}
		Token programToken = LextantToken.make(nowReading.getLocation(), Keyword.PROGRAM.getLexeme(), Keyword.PROGRAM);
		ParseNode programNode = new ProgramNode(programToken); 
		
		while(startsFunction(nowReading)) {
			ParseNode functionNode = parseFunction(); 
			programNode.appendChild(functionNode);
		}
		ParseNode mainFunctionNode = parseMainFunction(); 
		programNode.appendChild(mainFunctionNode);
		
		return programNode;
	}
	private boolean startsProgram(Token token) {
		return startsFunction(token) || startsMainFunction(token); 
 	}

	///////////////////////////////////////////////////////////
	// parse a non-main function (direct child of the program node)
	private ParseNode parseFunction() {
		if(!startsFunction(nowReading)) {
			return syntaxErrorNode("subr-function");
		}
		Token functionToken = nowReading;
		readToken();
		
		ParseNode returnType = parseReturnType(); 
		ParseNode functionName = parseIdentifier(); 
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode paramList = parseParameterList();
		expect(Punctuator.CLOSE_PARENTHESIS); 
		ParseNode functionBody = parseBlockStatement();
		
		return FunctionNode.withChildren(functionToken, returnType, functionName, paramList, functionBody);
	}
	private boolean startsFunction(Token token) {
		return token.isLextant(Keyword.SUBR); 
	}
	private ParseNode parseReturnType() {
		if(!startsReturnType(nowReading)) {
			return syntaxErrorNode("function-return-type");
		}
		if(startsPrimitiveType(nowReading)) {
			return parseTypeIndicator();
		}
		if(startsArrayType(nowReading)) {
			return parseArrayType(); 
		}
		return parseVoidReturnType();
	}
	private boolean startsReturnType(Token token) {
		return startsPrimitiveType(token) ||
				startsArrayType(token) ||
				startsVoidType(token);
	}
	private boolean startsVoidType(Token token) {
		return token.isLextant(Keyword.VOID);
	}
	private ParseNode parseTypeIndicator() {
		if(!startsPrimitiveType(nowReading)) {
			return syntaxErrorNode("primitive-type-indicator");
		}
		Token typeToken = nowReading;
		readToken();
		return new TypeIndicatorNode(typeToken);
	}
	private ParseNode parseVoidReturnType() {
		if(!startsVoidType(nowReading)) {
			return syntaxErrorNode("void-return-type");
		}
		Token typeToken = nowReading;
		readToken();
		return new VoidReturnTypeNode(typeToken);		
	}
	private ParseNode parseParameterList() {
		if(!startsParameterList(nowReading)) {
			return syntaxErrorNode("function-parameter-list");
		}
		
		ParseNode paramListNode = new ParameterListNode(LextantToken.make(nowReading.getLocation(), Keyword.PARAMETER_LIST.getLexeme(), Keyword.PARAMETER_LIST));
		while(startsParameterSpecification(nowReading)) {
			paramListNode.appendChild(parseParameterSpecification());
			if(nowReading.isLextant(Punctuator.CLOSE_PARENTHESIS)){
				return paramListNode;
			}
			expect(Punctuator.COMMA);
		}
		return paramListNode;
	}
	private boolean startsParameterList(Token token) {
		return startsParameterSpecification(token) ||
				token.isLextant(Punctuator.CLOSE_PARENTHESIS); 
	}
	
	private ParseNode parseParameterSpecification() {
		if(!startsParameterSpecification(nowReading)) {
			return syntaxErrorNode("function-parameter-specification");
		}
		
		Token paramSpecsToken = LextantToken.make(nowReading.getLocation(), Keyword.PARAMETER_SPECIFICATION.getLexeme(), Keyword.PARAMETER_SPECIFICATION);
		ParseNode paramType = parseParameterType();
		ParseNode paramIdentifier = parseIdentifier();
		
		return ParameterSpecificationNode.withChildren(paramSpecsToken, paramType, paramIdentifier);
	}
	private boolean startsParameterSpecification(Token token) {
		return startsParameterType(token);
	}
	private ParseNode parseParameterType() {
		if(!startsParameterType(nowReading)) {
			return syntaxErrorNode("function-parameter-type");
		}
		if(startsPrimitiveType(nowReading)) {
			return parseTypeIndicator();
		}
		if(startsVoidType(nowReading)) {
			return parseVoidReturnType();
		}
		return parseArrayType();
	}
	private boolean startsParameterType(Token token) {
		return startsPrimitiveType(token) || 
				startsVoidType(token) || 
				startsArrayType(token); 
	}
	
	
	
	
	
	// parse main function (direct and also the last child of the program node)
	private ParseNode parseMainFunction() {
		if(!startsMainFunction(nowReading)) {
			return syntaxErrorNode("main-function");
		}
		ParseNode mainNode = new MainFunctionNode(nowReading);
		
		expect(Keyword.MAIN);
		ParseNode mainBlock = parseBlockStatement();
		mainNode.appendChild(mainBlock);
		
		if(!(nowReading instanceof NullToken)) {
			return syntaxErrorNode("end of program");
		}
		
		return mainNode;
	}
	private boolean startsMainFunction(Token token) {
		return token.isLextant(Keyword.MAIN);
	}


	
	///////////////////////////////////////////////////////////
	// statements
	// statement-> declaration | assignmentStatement | printStmt | blockStatement
	private ParseNode parseStatement() {
		if(!startsStatement(nowReading)) {
			return syntaxErrorNode("statement");
		}
		if(startsDeclaration(nowReading)) {
			return parseDeclaration();
		}
		if(startsAssignmentStatement(nowReading)) {
			return parseAssignmentStatement();
		}
		if(startsPrintStatement(nowReading)) {
			return parsePrintStatement();
		}
		if(startsBlockStatement(nowReading)) {
			return parseBlockStatement();
		}
		if(startsWhileStatement(nowReading)) {
			return parseWhileStatement();
		}
		if(startsForStatement(nowReading)) {
			return parseForStatement();
		}
		if(startsIfStatement(nowReading)) {
			return parseIfStatement();
		}
		if(startsBreakStatement(nowReading)) {
			return parseBreakStatement();
		}
		if(startsContinueStatement(nowReading)) {
			return parseContinueStatement();
		}
		if(startsReturnStatement(nowReading)) {
			return parseReturnStatement(); 
		}
		if(startsCallStatement(nowReading)) {
			return parseCallStatement(); 
		}
		return syntaxErrorNode("statement");
	}
	private boolean startsStatement(Token token) {
		return startsPrintStatement(token) ||
			   	startsDeclaration(token) ||
				startsAssignmentStatement(token) ||
				startsBlockStatement(token) ||
				startsWhileStatement(token) ||
				startsForStatement(token) ||
				startsIfStatement(token)||
				startsBreakStatement(token) ||
				startsContinueStatement(token) ||
				startsReturnStatement(token) ||
				startsCallStatement(token); 
	}
	
	// printStmt -> PRINT printExpressionList TERMINATOR
	private ParseNode parsePrintStatement() {
		if(!startsPrintStatement(nowReading)) {
			return syntaxErrorNode("print statement");
		}
		ParseNode result = new PrintStatementNode(nowReading);
		
		readToken();
		result = parsePrintExpressionList(result);
		
		expect(Punctuator.TERMINATOR);
		return result;
	}
	private boolean startsPrintStatement(Token token) {
		return token.isLextant(Keyword.PRINT);
	}	

	// This adds the printExpressions it parses to the children of the given parent
	// printExpressionList -> printSeparator* (expression printSeparator+)* expression? (note that this is nullable)

	private ParseNode parsePrintExpressionList(ParseNode parent) {
		if(!startsPrintExpressionList(nowReading)) {
			return syntaxErrorNode("printExpressionList");
		}
		
		while(startsPrintSeparator(nowReading)) {
			parsePrintSeparator(parent);
		}
		while(startsExpression(nowReading)) {
			parent.appendChild(parseExpression());
			if(nowReading.isLextant(Punctuator.TERMINATOR)) {
				return parent;
			}
			do {
				parsePrintSeparator(parent);
			} while(startsPrintSeparator(nowReading));
		}
		return parent;
	}
	private boolean startsPrintExpressionList(Token token) {
		return startsExpression(token) || startsPrintSeparator(token) || token.isLextant(Punctuator.TERMINATOR);
	}

	
	// This adds the printSeparator it parses to the children of the given parent
	// printSeparator -> PRINT_SEPARATOR | PRINT_SPACE | PRINT_NEWLINE 
	
	private void parsePrintSeparator(ParseNode parent) {
		if(!startsPrintSeparator(nowReading)) {
			ParseNode child = syntaxErrorNode("print separator");
			parent.appendChild(child);
			return;
		}
		
		if(nowReading.isLextant(Punctuator.PRINT_NEWLINE)) {
			readToken();
			ParseNode child = new NewlineNode(previouslyRead);
			parent.appendChild(child);
		}
		else if(nowReading.isLextant(Punctuator.PRINT_SPACE)) {
			readToken();
			ParseNode child = new SpaceNode(previouslyRead);
			parent.appendChild(child);
		}
		else if(nowReading.isLextant(Punctuator.PRINT_TAB)) {
			readToken();
			ParseNode child = new TabNode(previouslyRead);
			parent.appendChild(child);
		}
		else if(nowReading.isLextant(Punctuator.PRINT_SEPARATOR)) {
			readToken();
		} 
	}
	private boolean startsPrintSeparator(Token token) {
		return token.isLextant(Punctuator.PRINT_SEPARATOR, Punctuator.PRINT_SPACE, Punctuator.PRINT_NEWLINE, Punctuator.PRINT_TAB);
	}

	
	// declaration -> CONST identifier := expression TERMINATOR
	private ParseNode parseDeclaration() {
		if(!startsDeclaration(nowReading)) {
			return syntaxErrorNode("declaration");
		}
		Token declarationToken = nowReading;
		readToken();
		
		ParseNode identifier = parseIdentifier();
		expect(Punctuator.ASSIGN);
		ParseNode initializer = parseExpression();
		expect(Punctuator.TERMINATOR);
		
		return DeclarationNode.withChildren(declarationToken, identifier, initializer);
	}

	private boolean startsDeclaration(Token token) {
		return token.isLextant(Keyword.CONST) || token.isLextant(Keyword.VAR);
	}


	// assignmentStatement -> target := expression TERMINATOR
	private ParseNode parseAssignmentStatement() {
		if(!startsAssignmentStatement(nowReading)) {
			return syntaxErrorNode("assignmentStatement");
		}
		ParseNode target = parseTargetableExpression();
		expect(Punctuator.ASSIGN);
		ParseNode newInitializer = parseExpression();
		expect(Punctuator.TERMINATOR);

		Token assignmentNodeToken = Punctuator.ASSIGN.prototype();
		return AssignmentStatementNode.withChildren(assignmentNodeToken, target, newInitializer);
	}
	private boolean startsAssignmentStatement(Token token) {
		return startsTargetableExpression(token); 
	}
	
	
	private ParseNode parseTargetableExpression() {
		if(!startsTargetableExpression(nowReading)) {
			return syntaxErrorNode("targetable-expression"); 
		}
		
		if(startsIdentifier(nowReading)) {
			return parseIdentifier();
		}
		if(startsTargetableArrayReferenceExpression(nowReading)) {
			return parseTargetableArrayReferenceExpression(); 
		}
		return parseTargetableParenthesesWrappedExpression();
	}
	private boolean startsTargetableExpression(Token token){
		return startsIdentifier(token) ||
				startsTargetableArrayReferenceExpression(token) ||
				startsTargetableParenthesesWrappedExpression(token); 
	}
	
	
	private ParseNode parseTargetableArrayReferenceExpression() {
		if(!startsTargetableArrayReferenceExpression(nowReading)) {
			return syntaxErrorNode("targetable array-reference expression"); 
		}
		readToken();
		
		ParseNode left = parseExpression();
		expect(Punctuator.INDEXING);
		ParseNode right = parseExpression(); 
		expect(Punctuator.CLOSE_BRACKETS);
		
		Token arrayRefToken = Punctuator.INDEXING.prototype();
		return TargetableArrayReferenceNode.withChildren(arrayRefToken, left, right);
		
	}
	private boolean startsTargetableArrayReferenceExpression(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACKETS); 
	}
	
	
	private ParseNode parseTargetableParenthesesWrappedExpression() {
		if(!startsTargetableParenthesesWrappedExpression(nowReading)){
			return syntaxErrorNode("targetable parenthesized expression"); 
		}
		
		readToken();
		ParseNode target = parseTargetableExpression(); 
		expect(Punctuator.CLOSE_PARENTHESIS);
		
		return target; 
	}
	private boolean startsTargetableParenthesesWrappedExpression(Token token) {
		return token.isLextant(Punctuator.OPEN_PARENTHESIS);  
	}
	
	

	// block statement -> { statement* }
	private ParseNode parseBlockStatement() {
		if(!startsBlockStatement(nowReading)) {
			return syntaxErrorNode("blockStatement");
		}
		ParseNode codeBlock = new BlockStatementNode(nowReading);
		expect(Punctuator.OPEN_BRACE);

		while(startsStatement(nowReading)) {
			ParseNode statement = parseStatement();
			codeBlock.appendChild(statement);
		}
		expect(Punctuator.CLOSE_BRACE);
		return codeBlock;
	}
	private boolean startsBlockStatement(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACE);
	}


	private ParseNode parseWhileStatement() {
		if(!startsWhileStatement(nowReading)) {
			return syntaxErrorNode("whileStatement");
		}

		Token whileToken = nowReading; // Save the while token for creating the WhileNode later
		readToken(); // Consume the "while" keyword
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode condition = parseExpression();
		expect(Punctuator.CLOSE_PARENTHESIS);

		ParseNode whileBlock = parseBlockStatement();

		return WhileNode.withChildren(whileToken, condition, whileBlock);
	}
	private boolean startsWhileStatement(Token token) {
		return token.isLextant(Keyword.WHILE);
	}

	private ParseNode parseForStatement() {
		if(!startsForStatement(nowReading)) {
			return syntaxErrorNode("forStatement");
		}
		Token forToken = nowReading;
		readToken();
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode identifier = parseIdentifier();
		expect(Keyword.FROM);
		ParseNode initializerFrom = parseExpression();
		expect(Keyword.TO);
		ParseNode initializerTo = parseExpression();
		expect(Punctuator.CLOSE_PARENTHESIS);
		ParseNode innerCodeBlock = parseBlockStatement();


		ParseNode loopIterator = DeclarationNode.withChildren(Keyword.VAR.prototype(), identifier, initializerFrom);
		ParseNode loopBoundary = DeclarationNode.withChildren(Keyword.VAR.prototype(), new IdentifierNode(IdentifierToken.make(nowReading.getLocation(), "upperfor")), initializerTo);
		ParseNode comparison = OperatorNode.withChildren(Punctuator.LESSEREQUAL.prototype(), new IdentifierNode(identifier.getToken()), new IdentifierNode(IdentifierToken.make(nowReading.getLocation(), "upperfor")));

		IntegerConstantNode incrementValue = new IntegerConstantNode(IntegerToken.make(nowReading.getLocation(), "1"));
		ParseNode increment = OperatorNode.withChildren(Punctuator.ADD.prototype(), new IdentifierNode(identifier.getToken()), incrementValue);
		ParseNode assign = AssignmentStatementNode.withChildren(Punctuator.ASSIGN.prototype(), new IdentifierNode(identifier.getToken()), increment);

		return ForNode.withChildren(forToken, loopIterator, loopBoundary,comparison, innerCodeBlock, assign);
	}

	private boolean startsForStatement(Token token) {
		return token.isLextant(Keyword.FOR);
	}

	private ParseNode parseIfStatement() {
		if(!startsIfStatement(nowReading)) {
			return syntaxErrorNode("ifStatement");
		}
		Token ifToken = nowReading;
		readToken();
		ParseNode ifCondition = parseParenthesesWrappedExpression();
		ParseNode ifBlock = parseBlockStatement();
		if(nowReading.isLextant(Keyword.ELSE)){
			readToken();
			ParseNode elseBlock = parseBlockStatement();
			return IfStatementNode.withChildren(ifToken, ifCondition, ifBlock, elseBlock);
		}
		return IfStatementNode.withChildren(ifToken, ifCondition, ifBlock);
	}
	private boolean startsIfStatement(Token token) {
		return token.isLextant(Keyword.IF);
	}


	private ParseNode parseReturnStatement() {
		if(!startsReturnStatement(nowReading)) {
			return syntaxErrorNode("return-statement"); 
		}
		Token returnToken = nowReading; 
		readToken();
		
		if(nowReading.isLextant(Punctuator.TERMINATOR)) {
			readToken();
			return new ReturnStatementNode(returnToken); 
		}
		
		ParseNode returnedExpression = parseExpression(); 
		expect(Punctuator.TERMINATOR);
		return ReturnStatementNode.withChild(returnToken, returnedExpression);
	}
	private boolean startsReturnStatement(Token token) {
		return token.isLextant(Keyword.RETURN); 
	}
	
	
	private ParseNode parseCallStatement() {
		if(!startsCallStatement(nowReading)) {
			return syntaxErrorNode("call-statement"); 
		}
		Token callToken = nowReading; 
		readToken();
		ParseNode functionInvocation = parseFunctionInvocationExpression();
		expect(Punctuator.TERMINATOR);
		return CallStatementNode.withChild(callToken, functionInvocation);
	}
	private boolean startsCallStatement(Token token) {
		return token.isLextant(Keyword.CALL); 
	}
	
	
	private ParseNode parseBreakStatement() {
		if(!startsBreakStatement(nowReading)) {
			return syntaxErrorNode("breakStatement");
		}
		Token breakToken = nowReading;
		readToken();
		expect(Punctuator.TERMINATOR);

		return new BreakStatementNode(breakToken);
	}
	private boolean startsBreakStatement(Token token) {
		return token.isLextant(Keyword.BREAK);
	}


	private ParseNode parseContinueStatement() {
		if(!startsContinueStatement(nowReading)){
			return syntaxErrorNode("continueStatement");
		}
		Token continueToken = nowReading;
		readToken();
		expect(Punctuator.TERMINATOR);

		return new ContinueStatementNode(continueToken);
	}
	private boolean startsContinueStatement(Token token) {
		return token.isLextant(Keyword.CONTINUE);
	}


	///////////////////////////////////////////////////////////
	// expressions
	// expr                     -> booleanORExpression
	// booleanORExpression		-> booleanANDExpression [|| booleanANDExpression]*	(left-assoc + short-circuit)
	// booleanANDExpression 	-> comparisonExpression [&& comparisonExpression]*	(left-assoc + short-circuit)
	// comparisonExpression     -> additiveExpression [> additiveExpression]* 	(left-assoc)
	// additiveExpression       -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	// multiplicativeExpression -> atomicExpression [MULT atomicExpression]*  (left-assoc)
	// atomicExpression         -> unaryExpression | literal
	// unaryExpression			-> UNARYOP atomicExpression
	// literal                  -> intNumber | identifier | booleanConstant


	private ParseNode parseExpression() {		
		if(!startsExpression(nowReading)) {
			return syntaxErrorNode("expression");
		}
		return parseConditionalOrExpression();
	}
	private boolean startsExpression(Token token) {
		return startsConditionalOrExpression(token);
	}

	
	
	private ParseNode parseConditionalOrExpression() {
		if(!startsConditionalOrExpression(nowReading)) {
			return syntaxErrorNode("conditional-or expression"); 
		}
		
		ParseNode left = parseConditionalAndExpression(); 
		while(nowReading.isLextant(Punctuator.CONDITIONAL_OR)) {
			Token orToken = nowReading; 
			readToken();
			ParseNode right = parseConditionalAndExpression(); 
			
			left = OperatorNode.withChildren(orToken, left, right); 
		}
		return left;
	}
	private boolean startsConditionalOrExpression(Token token) {
		return startsConditionalAndExpression(token); 
	}
	
	
	
	private ParseNode parseConditionalAndExpression(){
		if(!startsConditionalAndExpression(nowReading)) {
			return syntaxErrorNode("conditional-and expression");
		}

		ParseNode left = parseComparisonExpression();
		while(nowReading.isLextant(Punctuator.CONDITIONAL_AND)) {
			Token andToken = nowReading;
			readToken();
			ParseNode right = parseComparisonExpression();

			left = OperatorNode.withChildren(andToken, left, right);
		}
		return left;
	}

	private boolean startsConditionalAndExpression(Token token) {
		return startsComparisonExpression(token);
	}

	
	
	// comparisonExpression -> additiveExpression [> additiveExpression]*
	private ParseNode parseComparisonExpression() {
		if(!startsComparisonExpression(nowReading)) {
			return syntaxErrorNode("comparison expression");
		}
		
		ParseNode left = parseAdditiveExpression();
		while(nowReading.isLextant(Punctuator.GREATER, Punctuator.LESSER, Punctuator.GREATEREQUAL, Punctuator.LESSEREQUAL, Punctuator.EQUALS, Punctuator.NOTEQUALS)) {
			Token compareToken = nowReading;
			readToken();
			ParseNode right = parseAdditiveExpression();
			
			left = OperatorNode.withChildren(compareToken, left, right);
		}
		return left;
	}
	private boolean startsComparisonExpression(Token token) {
		return startsAdditiveExpression(token);
	}

	// additiveExpression -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	private ParseNode parseAdditiveExpression() {
		if(!startsAdditiveExpression(nowReading)) {
			return syntaxErrorNode("additiveExpression");
		}
		
		ParseNode left = parseMultiplicativeExpression();
		while(nowReading.isLextant(Punctuator.ADD, Punctuator.SUBTRACT)) {
			Token additiveToken = nowReading;
			readToken();
			ParseNode right = parseMultiplicativeExpression();
			
			left = OperatorNode.withChildren(additiveToken, left, right);
		}
		return left;
	}
	private boolean startsAdditiveExpression(Token token) {
		return startsMultiplicativeExpression(token);
	}	

	// multiplicativeExpression -> atomicExpression [MULT atomicExpression]*  (left-assoc)
	private ParseNode parseMultiplicativeExpression() {
		if(!startsMultiplicativeExpression(nowReading)) {
			return syntaxErrorNode("multiplicativeExpression");
		}
		
		ParseNode left = parseAtomicExpression();
		while(nowReading.isLextant(Punctuator.MULTIPLY) || nowReading.isLextant(Punctuator.DIVIDE)) {
			Token multiplicativeToken = nowReading;
			readToken();
			ParseNode right = parseAtomicExpression();
			
			left = OperatorNode.withChildren(multiplicativeToken, left, right);
		}
		return left;
	}

	private boolean startsMultiplicativeExpression(Token token) {
		return startsAtomicExpression(token);
	}
	
	// atomicExpression         -> unaryExpression | literal | parentheses-wrapped-expression
	private ParseNode parseAtomicExpression() {
		if(!startsAtomicExpression(nowReading)) {
			return syntaxErrorNode("atomic expression");
		}
		if(startsUnaryExpression(nowReading)) {
			return parseUnaryExpression();
		}
		if(startsParenthesesWrappedExpression(nowReading)) {
			return parseParenthesesWrappedExpression();
		}
		if(startsTypeCastingExpression(nowReading)) {
			return parseTypeCastingExpression();
		}
		if(startsPopulatedArrayCreationExpression(nowReading)) {
			return parsePopulatedArrayCreationExpression(); 
		}
		if(startsEmptyArrayCreationExpression(nowReading)) {
			return parseEmptyArrayCreationExpression(); 
		}
		if(startsFunctionInvocationExpression(nowReading)) {
			//IMPORTANT: check and parse function-invocation-expression ALWAYS have to go BEFORE parse-literal
			//because: parse-function-invocation-expression will handle an identifier-literal case for us,
			//if parse-literal is processed first, then parse-literal cannot handle function-invocation-expression.
			return parseFunctionInvocationExpression(); 
		}
		return parseLiteral();
	}
	private boolean startsAtomicExpression(Token token) {
		return startsUnaryExpression(token) ||
				startsParenthesesWrappedExpression(token) ||
				startsTypeCastingExpression(token) ||
				startsPopulatedArrayCreationExpression(token) ||
				startsEmptyArrayCreationExpression(token) ||
				startsFunctionInvocationExpression(token) ||
				startsLiteral(token); 
	}

	// unaryExpression			-> UNARYOP atomicExpression
	private ParseNode parseUnaryExpression() {
		if(!startsUnaryExpression(nowReading)) {
			return syntaxErrorNode("unary expression");
		}
		Token operatorToken = nowReading;
		readToken();
		ParseNode child = parseAtomicExpression();
		
		return OperatorNode.withChildren(operatorToken, child);
	}
	private boolean startsUnaryExpression(Token token) {
		return token.isLextant(Punctuator.SUBTRACT, Punctuator.ADD, Punctuator.BOOLEAN_NOT, Keyword.LENGTH);
	}


	//parentheses wrapped expression 	-> ( expression )
	private ParseNode parseParenthesesWrappedExpression() {
		if(!startsParenthesesWrappedExpression(nowReading)) {
			return syntaxErrorNode("parentheses-wrapped expression");
		}
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode result = parseExpression();
		expect(Punctuator.CLOSE_PARENTHESIS);
		return result;
	}
	private boolean startsParenthesesWrappedExpression(Token token) {
		return token.isLextant(Punctuator.OPEN_PARENTHESIS);
	}


	// type casting expression -> 	<type>(expression)
	private ParseNode parseTypeCastingExpression() {
		if(!startsTypeCastingExpression(nowReading)) {
			return syntaxErrorNode("type casting expression");
		}

		LextantToken tokenForCasting = LextantToken.make(nowReading.getLocation(), Punctuator.CAST.getLexeme(), Punctuator.CAST);
		expect(Punctuator.LESSER);

		ParseNode typeNode;
		if(startsPrimitiveTypeKeyword(nowReading)) {
			Token targetTypeToken = nowReading;
			readToken();
			typeNode = new TypeIndicatorNode(targetTypeToken);
		} else {
			typeNode = parseArrayType();
		}

		expect(Punctuator.GREATER);
		ParseNode expressionNode = parseParenthesesWrappedExpression();

		return OperatorNode.withChildren(tokenForCasting, typeNode, expressionNode);
	}
	private boolean startsTypeCastingExpression(Token token) {
		return token.isLextant(Punctuator.LESSER);
	}
	private boolean startsPrimitiveTypeKeyword(Token token) {
		return token.isLextant(Keyword.BOOL, Keyword.CHAR, Keyword.STRING, Keyword.INT, Keyword.FLOAT);
	}
	
	
	// array: populated creation ==========================================================================
	private ParseNode parsePopulatedArrayCreationExpression() {
		if(!startsPopulatedArrayCreationExpression(nowReading)) {
			return syntaxErrorNode("populated-array-creation expression");
		}
		Token arrayToken = nowReading; 
		readToken();
		
		ParseNode arrayNode = new ArrayNode(arrayToken); 
		arrayNode = parseArrayExpressionList(arrayNode);
		expect(Punctuator.CLOSE_BRACKETS);
		return arrayNode;
	}
	private boolean startsPopulatedArrayCreationExpression(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACKETS);
	}
	
	private ParseNode parseArrayExpressionList(ParseNode parent) {
		if(!startsArrayExpressionList(nowReading)) {
			return syntaxErrorNode("populated-array-creation expression list cannot be empty");
		}
		parent.appendChild(parseExpression());
		
		//------------------------------------------------------------
		if(startsArrayIndexingExpression(nowReading)) {
			return parseArrayIndexingExpression(nowReading, parent.child(0)); 
		}

		//------------------------------------------------------------
		
		while(nowReading.isLextant(Punctuator.COMMA)) {
			readToken();
			parent.appendChild(parseExpression());
		}
		return parent; 
	}
	private boolean startsArrayExpressionList(Token token) {
		return startsExpression(token);
	}

	
	// array: indexing ==========================================================================
	private ParseNode parseArrayIndexingExpression(Token token, ParseNode leftChild) {
		Token indexingToken = LextantToken.make(token.getLocation(), Punctuator.INDEXING.getLexeme(), Punctuator.INDEXING);
		readToken();
		
		ParseNode rightChild = parseExpression();
		return OperatorNode.withChildren(indexingToken, leftChild, rightChild);
	}
	
	private boolean startsArrayIndexingExpression(Token token) {
		return token.isLextant(Punctuator.INDEXING);
	}
	
	
	// array: empty creation ==========================================================================
	private ParseNode parseEmptyArrayCreationExpression() {
		if(!startsEmptyArrayCreationExpression(nowReading)) {
			return syntaxErrorNode("empty array creation"); 
		}
		
		Token arrayToken = LextantToken.make(nowReading.getLocation(), Punctuator.OPEN_BRACKETS.getLexeme(), Punctuator.OPEN_BRACKETS); 
		readToken();
		
		ParseNode arrayTypeNode = parseArrayType(); 
		ParseNode arraySizeExpression = parseParenthesesWrappedExpression(); 
		
		ParseNode arrayNode = new ArrayNode(arrayToken); 
		arrayNode.appendChild(arrayTypeNode);
		arrayNode.appendChild(arraySizeExpression);
		
		return arrayNode;
	}
	private boolean startsEmptyArrayCreationExpression(Token token) {
		return token.isLextant(Keyword.NEW); 
	}
	
	private ParseNode parseArrayType() {
		if(!startsArrayType(nowReading)) {
			return syntaxErrorNode("arrayType");
		}
		
		ParseNode result = new ArrayTypeNode(nowReading); 
		readToken();
		
		ParseNode innerType; 
		if(startsPrimitiveType(nowReading)) {
			innerType = new TypeIndicatorNode(nowReading);
			readToken();
		} else {
			innerType = parseArrayType();
		}
		result.appendChild(innerType);
		
		expect(Punctuator.CLOSE_BRACKETS);
		return result;
	}
	private boolean startsArrayType(Token token) {
		return token.isLextant(Punctuator.OPEN_BRACKETS); 
	}
	private boolean startsPrimitiveType(Token token) {
		return Keyword.isATypeKeyword(token.getLexeme()); 
	}


 	
	// function invocation ==================================================================================
	private ParseNode parseFunctionInvocationExpression() {
		if(!startsFunctionInvocationExpression(nowReading)) {
			return syntaxErrorNode("function-invocation-expression");
		}
		
		Token funcInvocToken = LextantToken.make(nowReading.getLocation(), Keyword.FUNCTION_INVOCATION.getLexeme(), Keyword.FUNCTION_INVOCATION); 
		ParseNode identifier = parseIdentifier(); 
		if(!nowReading.isLextant(Punctuator.OPEN_PARENTHESIS)) {
			return identifier; 
		}
		
		expect(Punctuator.OPEN_PARENTHESIS);
		ParseNode expressionList = parseExpressionList();
		expect(Punctuator.CLOSE_PARENTHESIS);
		return FunctionInvocationNode.withChildren(funcInvocToken, identifier, expressionList);
	}
	private boolean startsFunctionInvocationExpression(Token token) {
		return startsIdentifier(token); 
	}
	
	private ParseNode parseExpressionList() {
		Token tokenForExpressionListNode = LextantToken.make(nowReading.getLocation(), Keyword.FUNCTION_EXPRESSION_LIST.getLexeme(), Keyword.FUNCTION_EXPRESSION_LIST);
		ParseNode expressionListNode = new ExpressionListNode(tokenForExpressionListNode); 
		
		while(startsExpression(nowReading)) {
			ParseNode funcArg = parseExpression(); 
			expressionListNode.appendChild(funcArg);
			if(nowReading.isLextant(Punctuator.CLOSE_PARENTHESIS)) {
				return expressionListNode; 
			}
			expect(Punctuator.COMMA); 
		}
		return expressionListNode; 
	}
	

	

	
	// literal -> number | character | identifier | booleanConstant | string ================================
	private ParseNode parseLiteral() {
		if(!startsLiteral(nowReading)) {
			return syntaxErrorNode("literal");
		}
		if(startsIntLiteral(nowReading)) {
			return parseIntLiteral();
		}
		if(startsFloatLiteral(nowReading)) {
			return parseFloatLiteral();
		}
		if(startsCharacterLiteral(nowReading)) {
			return parseCharacterLiteral();
		}
		if(startsIdentifier(nowReading)) {
			return parseIdentifier();
		}
		if(startsBooleanLiteral(nowReading)) {
			return parseBooleanLiteral();
		}
		if(startsStringLiteral(nowReading)) {
			return parseStringLiteral();
		}

		return syntaxErrorNode("literal");
	}
	private boolean startsLiteral(Token token) {
		return startsIntLiteral(token) ||
				startsCharacterLiteral(token) ||
				startsIdentifier(token) ||
				startsBooleanLiteral(token) ||
				startsFloatLiteral(token) ||
				startsStringLiteral(token);
	}

	// number (literal)
	private ParseNode parseIntLiteral() {
		if(!startsIntLiteral(nowReading)) {
			return syntaxErrorNode("integer constant");
		}
		readToken();
		return new IntegerConstantNode(previouslyRead);
	}
	private ParseNode parseFloatLiteral() {
		if(!startsFloatLiteral(nowReading)) {
			return syntaxErrorNode("float constant");
		}
		readToken();
		return new FloatConstantNode(previouslyRead);
	}
	private boolean startsIntLiteral(Token token) {
		return token instanceof IntegerToken;
	}
	private boolean startsFloatLiteral(Token token) {
		return token instanceof FloatToken;
	}


	// character (literal)
	private ParseNode parseCharacterLiteral() {
		if(!startsCharacterLiteral(nowReading)) {
			return syntaxErrorNode("character");
		}
		readToken();
		return new CharacterNode(previouslyRead);
	}
	private boolean startsCharacterLiteral(Token token) {
		return token instanceof CharacterToken;
	}


	// identifier (terminal)
	private ParseNode parseIdentifier() {
		if(!startsIdentifier(nowReading)) {
			return syntaxErrorNode("identifier");
		}
		readToken();
		return new IdentifierNode(previouslyRead);
	}
	private boolean startsIdentifier(Token token) {
		return token instanceof IdentifierToken;
	}

	// boolean literal
	private ParseNode parseBooleanLiteral() {
		if(!startsBooleanLiteral(nowReading)) {
			return syntaxErrorNode("boolean constant");
		}
		readToken();
		return new BooleanConstantNode(previouslyRead);
	}
	private boolean startsBooleanLiteral(Token token) {
		return token.isLextant(Keyword.TRUE, Keyword.FALSE);
	}


	// string literal
	private ParseNode parseStringLiteral() {
		if(!startsStringLiteral(nowReading)) {
			return syntaxErrorNode("string constant");
		}
		readToken();
		return new StringConstantNode(previouslyRead);
	}
	private boolean startsStringLiteral(Token token) {
		return token instanceof StringToken;
	}


	private void readToken() {
		previouslyRead = nowReading;
		nowReading = scanner.next();
	}	
	
	// if the current token is one of the given lextants, read the next token.
	// otherwise, give a syntax error and read next token (to avoid endless looping).
	private void expect(Lextant ...lextants ) {
		if(!nowReading.isLextant(lextants)) {
			syntaxError(nowReading, "expecting " + Arrays.toString(lextants));
		}
		readToken();
	}	
	private ErrorNode syntaxErrorNode(String expectedSymbol) {
		syntaxError(nowReading, "expecting " + expectedSymbol);
		ErrorNode errorNode = new ErrorNode(nowReading);
		readToken();
		return errorNode;
	}
	private void syntaxError(Token token, String errorDescription) {
		String message = "" + token.getLocation() + " " + errorDescription;
		error(message);
	}
	private void error(String message) {
		TanLogger log = TanLogger.getLogger("compiler.Parser");
		log.severe("syntax error: " + message);
	}	
}

