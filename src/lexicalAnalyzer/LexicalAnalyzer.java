package lexicalAnalyzer;


import logging.TanLogger;

import inputHandler.InputHandler;
import inputHandler.LocatedChar;
import inputHandler.LocatedCharStream;
import inputHandler.PushbackCharStream;
import tokens.*;
import static lexicalAnalyzer.PunctuatorScanningAids.*;

public class LexicalAnalyzer extends ScannerImp implements Scanner {
	public static LexicalAnalyzer make(String filename) {
		InputHandler handler = InputHandler.fromFilename(filename);
		PushbackCharStream charStream = PushbackCharStream.make(handler);
		return new LexicalAnalyzer(charStream);
	}

	public LexicalAnalyzer(PushbackCharStream input) {
		super(input);
	}

	
	//////////////////////////////////////////////////////////////////////////////
	// Token-finding main dispatch	

	@Override
	protected Token findNextToken() {
		LocatedChar ch = nextNonWhitespaceChar();
		if(ch.isDigit()) {
			return scanNumber(ch);
		}
		else if(ch.isIdentifierStarter()) {
			return scanIdentifier(ch);
		}
		else if(ch.getCharacter() == LexicalMacros.HASH_TAG && isPunctuatorStart(ch)) {
			scanComment();
			return findNextToken();
		}
		else if(ch.getCharacter() == LexicalMacros.PERCENT && isPunctuatorStart(ch)) {
				return scanCharacterOct(ch);
		}
		else if(isPunctuatorStart(ch)) {
			return PunctuatorScanner.scan(ch, input);
		}
		else if(ch.isCharacterWrapper()) {
			return scanCharacterSingle(ch);
		}
		else if(ch.isStringWrapper()) {
			return scanString(ch);
		}
		else if(isEndOfInput(ch)) {
			return NullToken.make(ch);
		}
		else {
			lexicalError(ch);
			return findNextToken();
		}
	}


	private LocatedChar nextNonWhitespaceChar() {
		LocatedChar ch = input.next();
		while(ch.isWhitespace()) {
			ch = input.next();
		}
		return ch;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Integer lexical analysis	

	private Token scanNumber(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(firstChar.getCharacter());
		boolean isFloat = appendWholeNumbers(buffer);
		if (isFloat) appendFloatingSequence(buffer);
		return NumberToken.make(firstChar, buffer.toString(), isFloat);
	}
	private boolean appendWholeNumbers(StringBuffer buffer) {
		LocatedChar c = input.next();
		while(c.isDigit()) {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		if(c.getCharacter() != LexicalMacros.PERIOD) {
			input.pushback(c);
			return false;
		}
		else{
			buffer.append(c.getCharacter());
			return true;
		}
	}

	private void appendFloatingSequence(StringBuffer buffer){
		boolean digitPhase = false;
		boolean exponentPhase = false;
		boolean exponentSignPhase = false;
		boolean exponentDigitPhase = false;
		LocatedChar c = input.next();

		while(c.isDigit()){
			buffer.append(c.getCharacter());
			c = input.next();
			digitPhase = true;
		}
		if(!digitPhase) lexicalError(c);

		if((c.getCharacter() == LexicalMacros.E_NOTATION_LOWER || c.getCharacter() == LexicalMacros.E_NOTATION_UPPER) && digitPhase){
			buffer.append(c.getCharacter());
			c = input.next();
			exponentPhase = true;
		}

		if((c.getCharacter() == LexicalMacros.ADD || c.getCharacter() == LexicalMacros.SUBTRACT) && exponentPhase){
			buffer.append(c.getCharacter());
			c = input.next();
			exponentSignPhase = true;
		}
		if(!exponentSignPhase && exponentPhase) lexicalError(c);

		while(c.isDigit() && exponentSignPhase){
			buffer.append(c.getCharacter());
			c = input.next();
			exponentDigitPhase = true;
		}
		if(!exponentDigitPhase && exponentSignPhase) lexicalError(c);
		input.pushback(c);
	}

	private boolean validNumber(String numberString){
		String regexFloat = "^[0-9]+\\.[0-9]+$";
		String regexScientific = "^[0-9]+\\.[0-9]+[eE][+-][0-9]+$";
		String regexInt= "^[0-9]+$";
		return numberString.matches(regexFloat) || numberString.matches(regexScientific) || numberString.matches(regexInt);
	}

	//////////////////////////////////////////////////////////////////////////////
	// Identifier and keyword lexical analysis	

	private Token scanIdentifier(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(firstChar.getCharacter());
		appendSubsequentIdentifierCharacters(buffer);

		String lexeme = buffer.toString();
		if(Keyword.isAKeyword(lexeme)) {
			return LextantToken.make(firstChar, lexeme, Keyword.forLexeme(lexeme));
		}
		else {
			return IdentifierToken.make(firstChar, lexeme);
		}
	}
	private void appendSubsequentIdentifierCharacters(StringBuffer buffer) {
		LocatedChar c = input.next();
		while (c.isIdentifierSubsequent()) {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		input.pushback(c);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	// Punctuator lexical analysis	
	// old method left in to show a simple scanning method.
	// current method is the algorithm object PunctuatorScanner.java

	@SuppressWarnings("unused")
	private Token oldScanPunctuator(LocatedChar ch) {
		
		switch(ch.getCharacter()) {
		case '*':
			return LextantToken.make(ch, "*", Punctuator.MULTIPLY);
		case '+':
			return LextantToken.make(ch, "+", Punctuator.ADD);
		case '-':
			return LextantToken.make(ch, "-", Punctuator.SUBTRACT);
		case '>':
			return LextantToken.make(ch, ">", Punctuator.GREATER);
		case ':':
			if(ch.getCharacter()=='=') {
				return LextantToken.make(ch, ":=", Punctuator.ASSIGN);
			}
			else {
				lexicalError(ch);
				return(NullToken.make(ch));
			}
		case ',':
			return LextantToken.make(ch, ",", Punctuator.PRINT_SEPARATOR);
		case ';':
			return LextantToken.make(ch, ";", Punctuator.TERMINATOR);
		default:
			lexicalError(ch);
			return(NullToken.make(ch));
		}
	}

	

	//////////////////////////////////////////////////////////////////////////////
	// Character-classification routines specific to tan scanning.	

	private boolean isPunctuatorStart(LocatedChar lc) {
		char c = lc.getCharacter();
		return isPunctuatorStartingCharacter(c);
	}

	private boolean isEndOfInput(LocatedChar lc) {
		return lc == LocatedCharStream.FLAG_END_OF_INPUT;
	}


	//////////////////////////////////////////////////////////////////////////////
	// Comment lexical analysis
	private void scanComment() {
		LocatedChar c = input.next();
		while (!(c.getCharacter() == LexicalMacros.HASH_TAG || c.getCharacter() == LexicalMacros.NEXT_LINE )) {
			c = input.next();
		}
	}


	//////////////////////////////////////////////////////////////////////////////
	// Character lexical analysis

	private Token scanCharacterSingle(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		LocatedChar c = input.next();
		if (c.isPrintableAsciiChar()) {
			buffer.append(c.getCharacter());
		} else {
			lexicalError(c);
			return NullToken.make(c);
		}

		c = input.next();
		if (!c.isCharacterWrapper()) {
			lexicalError(c);
			return NullToken.make(c);
		}

		return CharacterToken.make(firstChar, buffer.toString());
	}

	private Token scanCharacterOct(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		LocatedChar c = input.next();
		int octCount = 0;
		while(octCount < LexicalMacros.OCTAL_MAX) {
			if(c.isDigit()) {
				buffer.append(c.getCharacter());
				octCount++;
				c = input.next();
			} else {
				lexicalError(c);
				return NullToken.make(firstChar);
			}
		}
		input.pushback(c);
		return CharacterToken.make(firstChar, buffer.toString());
	}

	//////////////////////////////////////////////////////////////////////////////
	// String lexical analysis
	private Token scanString(LocatedChar firstChar) {
		StringBuffer buffer = new StringBuffer();
		LocatedChar c = input.next();
		while(c.isValidStringChar()) {
			buffer.append(c.getCharacter());
			c = input.next();
		}
		if(!c.isStringWrapper()) {
			lexicalError(c);
			return NullToken.make(firstChar);
		}
		return StringToken.make(firstChar, buffer.toString());
	}


	//////////////////////////////////////////////////////////////////////////////
	// Error-reporting	

	private void lexicalError(LocatedChar ch) {
		TanLogger log = TanLogger.getLogger("compiler.lexicalAnalyzer");
		log.severe("Lexical error: invalid character " + ch);
	}

}
