package parser;

import java.util.Arrays;

import logging.TanLogger;
import parseTree.*;
import parseTree.nodeTypes.*;
import semanticAnalyzer.types.Type;
import symbolTable.SymbolTable;
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
	// "program" is the start symbol S
	// S -> MAIN mainBlock
	
	private ParseNode parseProgram() {
		if(!startsProgram(nowReading)) {
			return syntaxErrorNode("program");
		}
		ParseNode program = new ProgramNode(nowReading);
		
		expect(Keyword.MAIN);
		ParseNode mainBlock = parseBlockStatement();
		program.appendChild(mainBlock);
		
		if(!(nowReading instanceof NullToken)) {
			return syntaxErrorNode("end of program");
		}
		
		return program;
	}
	private boolean startsProgram(Token token) {
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
		return syntaxErrorNode("statement");
	}
	private boolean startsStatement(Token token) {
		return startsPrintStatement(token) ||
			   	startsDeclaration(token) ||
				startsAssignmentStatement(token) ||
				startsBlockStatement(token);
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
		ParseNode identifier = parseIdentifier();
		expect(Punctuator.ASSIGN);
		ParseNode newInitializer = parseExpression();
		expect(Punctuator.TERMINATOR);

		Token assignmentNodeToken = Punctuator.ASSIGN.prototype();
		return AssignmentStatementNode.withChildren(assignmentNodeToken, identifier, newInitializer);
	}
	private boolean startsAssignmentStatement(Token token) {
		return startsIdentifier(token);
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



	///////////////////////////////////////////////////////////
	// expressions
	// expr                     -> comparisonExpression
	// comparisonExpression     -> additiveExpression [> additiveExpression]?
	// additiveExpression       -> multiplicativeExpression [+ multiplicativeExpression]*  (left-assoc)
	// multiplicativeExpression -> atomicExpression [MULT atomicExpression]*  (left-assoc)
	// atomicExpression         -> unaryExpression | literal
	// unaryExpression			-> UNARYOP atomicExpression
	// literal                  -> intNumber | identifier | booleanConstant

	// expr  -> comparisonExpression
	private ParseNode parseExpression() {		
		if(!startsExpression(nowReading)) {
			return syntaxErrorNode("expression");
		}
		return parseComparisonExpression();
	}
	private boolean startsExpression(Token token) {
		return startsComparisonExpression(token);
	}

	// comparisonExpression -> additiveExpression [> additiveExpression]?
	private ParseNode parseComparisonExpression() {
		if(!startsComparisonExpression(nowReading)) {
			return syntaxErrorNode("comparison expression");
		}
		
		ParseNode left = parseAdditiveExpression();
		if(nowReading.isLextant(Punctuator.GREATER) || nowReading.isLextant(Punctuator.LESSER) || nowReading.isLextant(Punctuator.GREATEREQUAL) || nowReading.isLextant(Punctuator.LESSEREQUAL) || nowReading.isLextant(Punctuator.EQUALS) || nowReading.isLextant(Punctuator.NOTEQUALS)) {
			Token compareToken = nowReading;
			readToken();
			ParseNode right = parseAdditiveExpression();
			
			return OperatorNode.withChildren(compareToken, left, right);
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
		return parseLiteral();
	}
	private boolean startsAtomicExpression(Token token) {
		return startsLiteral(token) ||
				startsUnaryExpression(token) ||
				startsParenthesesWrappedExpression(token) ||
				startsTypeCastingExpression(token);
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
		return token.isLextant(Punctuator.SUBTRACT);
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
		Token targetTypeToken = nowReading;
		expect(Keyword.BOOL, Keyword.CHAR, Keyword.STRING, Keyword.INT, Keyword.FLOAT);
		expect(Punctuator.GREATER);

		ParseNode expressionNode = parseParenthesesWrappedExpression();
		TypeIndicatorNode typeNode = new TypeIndicatorNode(targetTypeToken);

		return OperatorNode.withChildren(tokenForCasting, typeNode, expressionNode);
	}
	private boolean startsTypeCastingExpression(Token token) {
		return token.isLextant(Punctuator.LESSER);
	}
	
	// literal -> number | character | identifier | booleanConstant | string
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
		return token instanceof NumberToken && ((NumberToken) token).getValue() instanceof Integer;
	}
	private boolean startsFloatLiteral(Token token) {
		return token instanceof NumberToken && ((NumberToken) token).getValue() instanceof Float;
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

