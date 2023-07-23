package tokens;

import inputHandler.Locator;

public class IdentifierToken extends TokenImp {
	public IdentifierToken(Locator locator, String lexeme) {
		super(locator, lexeme.intern());
	}
	
	public static IdentifierToken make(Locator locator, String lexeme) {
		IdentifierToken result = new IdentifierToken(locator, lexeme);
		return result;
	}


	@Override
	protected String rawString() {
		return "identifier, " + getLexeme();
	}
}
