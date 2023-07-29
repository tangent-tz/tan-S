package lexicalAnalyzer;

import inputHandler.TextLocation;
import tokens.LextantToken;
import tokens.Token;


public enum Keyword implements Lextant {
	CONST("const"),
	VAR("var"),
	PRINT("print"),
	TRUE("true"),
	FALSE("false"),
	MAIN("main"),
	BOOL("bool"),
	CHAR("char"),
	STRING("string"),
	INT("int"),
	FLOAT("float"),
	WHILE("while"),
	IF("if"),
	ELSE("else"),
	NEW("new"),
	LENGTH("length"),
	BREAK("break"),
	CONTINUE("continue"),
	FOR("for"),
	FROM("from"),
	TO("to"),
	SUBR("subr"),
	PROGRAM("program.keyword.lexicalAnalyzer"),
	PARAMETER_LIST("paramList.keyword.lexicalAnalyzer"), 
	PARAMETER_SPECIFICATION("paramSpecs.keyword.lexicalAnalyzer"),
	VOID("void"),
	RETURN("return"), 
	CALL("call"), 
	FUNCTION_INVOCATION("functionInvocation.keyword.lexicalAnalyzer"),
	FUNCTION_EXPRESSION_LIST("functionExpressionList.keyword.lexicalAnalyzer"),
	NULL_KEYWORD("");

	private String lexeme;
	private Token prototype;
	
	
	private Keyword(String lexeme) {
		this.lexeme = lexeme;
		this.prototype = LextantToken.make(TextLocation.nullInstance(), lexeme, this);
	}
	public String getLexeme() {
		return lexeme;
	}
	public Token prototype() {
		return prototype;
	}
	
	public static Keyword forLexeme(String lexeme) {
		for(Keyword keyword: values()) {
			if(keyword.lexeme.equals(lexeme)) {
				return keyword;
			}
		}
		return NULL_KEYWORD;
	}
	public static boolean isAKeyword(String lexeme) {
		return forLexeme(lexeme) != NULL_KEYWORD;
	}

	public static boolean isATypeKeyword(String lexeme) {
		Keyword k = forLexeme(lexeme);
		return k == BOOL ||
				k == CHAR ||
				k == STRING ||
				k == INT ||
				k == FLOAT;
	}
	
	/*   the following hashtable lookup can replace the serial-search implementation of forLexeme() above. It is faster but less clear. 
	private static LexemeMap<Keyword> lexemeToKeyword = new LexemeMap<Keyword>(values(), NULL_KEYWORD);
	public static Keyword forLexeme(String lexeme) {
		return lexemeToKeyword.forLexeme(lexeme);
	}
	*/
}
