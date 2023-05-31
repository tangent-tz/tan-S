package tokens;

import inputHandler.Locator;

public class NumberToken extends TokenImp {
	protected Number value;
	
	protected NumberToken(Locator locator, String lexeme) {
		super(locator, lexeme);
	}
	protected void setValue(Number value) {
		this.value = value;
	}
	public Number getValue() {
		return value;
	}
	
	public static NumberToken make(Locator locator, String lexeme, boolean isFloat) {
		NumberToken result = new NumberToken(locator, lexeme);
		if(isFloat)
			result.setValue(Float.parseFloat(lexeme));
		else
			result.setValue(Integer.parseInt(lexeme));
		return result;
	}
	
	@Override
	protected String rawString() {
		return "number, " + value;
	}
}
