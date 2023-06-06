package tokens;

import inputHandler.LocatedChar;
import inputHandler.Locator;
import logging.TanLogger;


public class CharacterToken extends TokenImp {
    protected Character value;

    protected CharacterToken(Locator locator, String lexeme) {
        super(locator, lexeme);
    }
    protected void setValue(Character value) {
        this.value = value;
    }
    public Character getValue() {
        return this.value;
    }

    public static CharacterToken make(Locator locator, String lexeme) {
        CharacterToken result = new CharacterToken(locator, lexeme);
        LocatedChar firstChar = (LocatedChar) locator;
        if(firstChar.isCharacterWrapper()) {
            result.setValue(lexeme.charAt(0));
        } else {
            try {
                int charDec = Integer.parseInt(lexeme, 8);
                if(charDec >= 0 && charDec <= 127) {
                    result.setValue((char)charDec);
                } else {
                    lexicalError(firstChar);
                }
            } catch (NumberFormatException e) {
                lexicalError(firstChar);
            }
        }
        return result;
    }

    @Override
    protected String rawString() {
        return "character, " + value;
    }

    //////////////////////////////////////////////////////////////////////////////
    // Error-reporting

    private static void lexicalError(LocatedChar ch) {
        TanLogger log = TanLogger.getLogger("compiler.tokens.CharacterToken");
        log.severe("Lexical error: not a valid ASCII character" + ch);
    }

}
