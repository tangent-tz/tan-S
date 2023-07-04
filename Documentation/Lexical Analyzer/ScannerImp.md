The `ScannerImp` class we've posted is an abstract implementation of the `Scanner` interface. It's responsible for scanning through input from a `PushbackCharStream` and breaking it up into `Token` objects.

Here's a breakdown of the class and its methods:

- `private Token nextToken;` - This holds the next token that will be returned by the `next()` method. It's initialized in the constructor and updated each time `next()` is called.

- `protected final PushbackCharStream input;` - This is the input stream that the scanner is reading from. It's a `PushbackCharStream`, which is a type of input stream that allows characters to be "pushed back" into the stream after they've been read.

- `protected abstract Token findNextToken();` - This is an abstract method that must be implemented by any concrete subclass of `ScannerImp`. Its job is to find and return the next token in the input stream.

- `public ScannerImp(PushbackCharStream input)` - This is the class constructor. It initializes the `input` field with the provided input stream and immediately calls `findNextToken()` to initialize `nextToken` with the first token from the input.

- `public boolean hasNext()` - This method checks if there are more tokens to read from the input. It returns `false` if `nextToken` is a `NullToken`, which indicates the end of the input.

- `public Token next()` - This method returns the next token from the input. It also updates `nextToken` by calling `findNextToken()` again.

- `public void remove()` - This method is part of the `Iterator` interface, but is not supported in this implementation. If called, it will throw an `UnsupportedOperationException`.

In order to use `ScannerImp`, we would need to extend it and provide an implementation for `findNextToken()`. The implementation of `findNextToken()` would contain the logic for identifying and creating the appropriate `Token` objects based on the contents of the input stream.

Sure, let's illustrate a use case with a simplified scanner that identifies integers and plus symbols in an input stream. This is a simple and unrealistic scenario, but it will help to illustrate how `ScannerImp` works.

First, we will create two classes `IntegerToken` and `PlusToken` which extend `Token`. The `IntegerToken` class will hold an integer value, and `PlusToken` can just be an empty class to represent the '+' symbol.

```java
package tokens;

public class IntegerToken extends Token {
    private int value;

    public IntegerToken(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

public class PlusToken extends Token {
    // Empty class to represent '+' symbol
}
```

Now, we will create our `SimpleMathScanner` that extends `ScannerImp` and implements the `findNextToken()` method:

```java
package lexicalAnalyzer;

import inputHandler.PushbackCharStream;
import inputHandler.TextLocation;
import inputHandler.LocatedChar;
import tokens.IntegerToken;
import tokens.PlusToken;
import tokens.NullToken;
import tokens.Token;

public class SimpleMathScanner extends ScannerImp {
    public SimpleMathScanner(PushbackCharStream input) {
        super(input);
    }

    @Override
    protected Token findNextToken() {
        if (!input.hasNext()) {
            return new NullToken();
        }

        LocatedChar character = input.next();
        if (Character.isDigit(character.getCharacter())) {
            return new IntegerToken(Character.getNumericValue(character.getCharacter()));
        } else if (character.getCharacter() == '+') {
            return new PlusToken();
        }

        // Ignore other characters
        return findNextToken();
    }
}
```

Finally, here is how we might use `SimpleMathScanner` to scan a string of text:

```java
PushbackCharStream input = new PushbackCharStream("1 + 2 + 3");
Scanner scanner = new SimpleMathScanner(input);

while (scanner.hasNext()) {
    Token token = scanner.next();
    // Do something with the token...
}
```

In this example, `SimpleMathScanner` will produce an `IntegerToken` for each digit and a `PlusToken` for each '+' symbol. Other characters are ignored. 

Please note, this example is highly simplified for illustration. In a realistic scenario, the scanner would need to handle many more types of tokens and edge cases.
