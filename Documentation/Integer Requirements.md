# Integer Data Type Requirements and Semantics in Tan-1

## Declaration
Integers can be declared using the `const` or `var` keywords. The value of the integer is initialized to the value of the expression in the declaration. The integer has the same type as that expression.

## Literal Representation
An integer literal in Tan-1 is defined by the token `integerLiteral → [0..9]+`. This means that an integer is a sequence of one or more digits from 0 to 9. Note that Tan-1 doesn't seem to support negative integer literals directly.

## Memory Consumption
Integer variables consume 4 bytes of memory.

## Operations
- The unary operators `+` and `-` can be applied to integers.
- Binary arithmetic operations such as `+`, `-`, `*`, and `/` can be performed on two integer operands. Note that the division operation truncates the result towards zero.
- Binary comparison operations such as `<`, `<=`, `==`, `!=`, `>`, and `>=` can be performed on two integer operands. The result of a comparison operation is a boolean.

## Overflow and Underflow
An integer literal may have a numeric interpretation that exceeds what can be represented. In such cases, it should be reported as an error. For instance, an integer 1234567890987654321 is too large to be represented as an ordinary integer, so it should be reported as an error. To detect an integer being too large, you’ll have to catch a `NumberFormatException` thrown by `Integer.parseInt`.

## Casting
- Integer may be cast to character (by using the bottom 7 bits of the integer as the character).
- Integer may be cast to floating.
- Integer can be cast to boolean (zero yields false, nonzero yields true).
- Integer can be cast to itself.
- No other casts to integer are allowed.

## Scope
If declared within a block statement, the integer variable has a local scope and can only be accessed within that block. If the same identifier is used in an inner block, it will hide the variable of the same name in the outer block.
