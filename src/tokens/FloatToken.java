package tokens;

import inputHandler.LocatedChar;
import inputHandler.Locator;
import logging.TanLogger;

public class FloatToken extends TokenImp {
    private double value;

    protected FloatToken(Locator locator, String lexeme) {
        super(locator, lexeme);
    }
    protected void setValue(double value) {
        this.value = value;
    }
    public double getValue() {
        return value;
    }

    public static FloatToken make(Locator locator, String lexeme) {
        FloatToken result = new FloatToken(locator, lexeme);
        try {
            double value = Double.parseDouble(lexeme);
            if (value == Double.POSITIVE_INFINITY) {
                lexicalError((LocatedChar) locator);
            }
            result.setValue(value);
        } catch (NumberFormatException e) {
            lexicalError((LocatedChar) locator);
        }
        return result;
    }

    @Override
    protected String rawString() {
        return "float, " + value;
    }


    /////////////////////////////////////////////////////////
    // Error reporting

    private static void lexicalError(LocatedChar ch) {
        TanLogger log = TanLogger.getLogger("compiler.tokens.FloatToken");
        log.severe("Lexical error: FLOAT value invalid: " + ch);
    }

}
