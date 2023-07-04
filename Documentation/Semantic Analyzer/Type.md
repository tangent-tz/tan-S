The `Type` interface defines the behavior for all types in the language. This includes both primitive types like `int` or `float`, and reference types like `String`. Any class that implements this interface will need to provide definitions for these methods. Here's what each method is intended to do:

- `getSize()`: This method should return the size of an instance of the type, in bytes. For example, an `int` might have a size of 4 bytes, and a `float` might have a size of 8 bytes. The size information is useful for many parts of the compiler, such as determining how much space to allocate for a variable of a certain type, or deciding how to align data in memory.

- `infoString()`: This method should return a string that provides information about the type. This could be used in error messages or other output from the compiler. Unlike `toString()`, which returns a string representation of the object, `infoString()` can be used to provide a more concise or customized description of the type.

Both `PrimitiveType` and `ReferenceType` classes are implementing this interface and providing their own definitions for these two methods.