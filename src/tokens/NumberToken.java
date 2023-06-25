//package tokens;
//
//import inputHandler.LocatedChar;
//import inputHandler.Locator;
//import logging.TanLogger;
//
//public class NumberToken extends TokenImp {
//	protected Number value;
//
//	protected NumberToken(Locator locator, String lexeme) {
//		super(locator, lexeme);
//	}
//	protected void setValue(Number value) {
//		this.value = value;
//	}
//	public Number getValue() {
//		return value;
//	}
//
//	public static NumberToken make(Locator locator, String lexeme, boolean isFloat) {
//		NumberToken result = new NumberToken(locator, lexeme);
//		if(isFloat) {
//			try {
//				Number floatValue = Double.parseDouble(lexeme);
//				if(floatValue.equals(Double.POSITIVE_INFINITY)) {
//					lexicalError((LocatedChar) locator, "floating");
//				}
//				result.setValue(floatValue);
//				return result;
//			} catch (Exception e) {
//				lexicalError((LocatedChar) locator, "floating");
//			}
//		}
//
//		try {
//			result.setValue(Integer.parseInt(lexeme));
//		} catch (NumberFormatException e) {
//			lexicalError((LocatedChar) locator, "integer");
//		}
//		return result;
//	}
//
//	@Override
//	protected String rawString() {
//		return "number, " + value;
//	}
//
//	//////////////////////////////////////////////////////////////////////////////
//	// Error-reporting
//
//	private static void lexicalError(LocatedChar ch, String type) {
//		TanLogger log = TanLogger.getLogger("compiler.tokens.NumberToken");
//		log.severe("Lexical error: " + type + " value invalid: " + ch);
//	}
//}
