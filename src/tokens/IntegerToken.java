package tokens;

import inputHandler.LocatedChar;
import inputHandler.Locator;
import logging.TanLogger;

public class IntegerToken extends TokenImp {
    private int value;

    public IntegerToken(Locator locator, String lexeme) {
        super(locator, lexeme);
    }
    protected void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public static IntegerToken make(Locator locator, String lexeme) {
        IntegerToken result = new IntegerToken(locator, lexeme);
        try {
            result.setValue(Integer.parseInt(lexeme));
        } catch (NumberFormatException e) {
            lexicalError((LocatedChar) locator);
        }
        return result;
    }

    @Override
    protected String rawString() {
        return "integer, " + value;
    }


    ////////////////////////////////////////////////////////////////////
    // Error reporting

    private static void lexicalError(LocatedChar ch) {
        TanLogger log = TanLogger.getLogger("compiler.tokens.IntegerToken");
        log.severe("Lexical error: INTEGER value invalid: " + ch);
    }
}
