# Java Method References

In Java, a **method reference is a shorthand syntax used to write highly readable lambda expressions**. Introduced in Java 8, it uses the double colon (`::`) operator to point directly to an existing method or constructor without executing it immediately. 

Method references only work when the lambda expression simply forwards all parameters directly to an existing method (known as a "pass-through" call) and must match the signature of a **functional interface** (an interface with exactly one abstract method).

---

## The Four Kinds of Method References

| Type | Syntax | Equivalent Lambda Expression |
| :--- | :--- | :--- |
| **1. Reference to a Static Method** | `ContainingClass::staticMethodName` | `(args) -> ContainingClass.staticMethodName(args)` |
| **2. Reference to an Instance Method of a Particular Object** | `containingObject::instanceMethodName` | `(args) -> containingObject.instanceMethodName(args)` |
| **3. Reference to an Instance Method of an Arbitrary Object** | `ContainingType::methodName` | `(obj, args) -> obj.methodName(args)` |
| **4. Reference to a Constructor** | `ClassName::new` | `(args) -> new ClassName(args)` |

---

## Code Examples

### 1. Reference to a Static Method
Replaces a lambda expression that calls a utility or standard static method.
```java
// Lambda expression
Function<String, Integer> lambda = (s) -> Integer.parseInt(s);

// Method reference equivalent
Function<String, Integer> methodRef = Integer::parseInt;
```

### 2. Reference to an Instance Method of a Particular Object
Points to a method belonging to an object instance that you have already created.
```java
String searchString = "Java Programming";

// Lambda expression
Predicate<String> lambda = (s) -> searchString.contains(s);

// Method reference equivalent
Predicate<String> methodRef = searchString::contains;
```

### 3. Reference to an Instance Method of an Arbitrary Object of a Particular Type
This type is often used when working with streams or sorting. The first parameter of the functional interface acts as the target object on which the method is called.
```java
List<String> names = Arrays.asList("apple", "banana", "cherry");

// Lambda expression
names.forEach(s -> System.out.println(s.toUpperCase()));

// Method reference equivalent (Using ClassName::methodName)
// Here, toUpperCase() is run on each arbitrary string passed through the stream.
names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println); // (System.out::println is Type #2)
```

### 4. Reference to a Constructor
Maps directly to the `new` keyword to instantiate collections, arrays, or custom objects.
```java
// Lambda expression
Supplier<List<String>> lambda = () -> new ArrayList<>();

// Method reference equivalent
Supplier<List<String>> constructorRef = ArrayList::new;
```

## Why use them?
* **Zero Boilerplate:** They abstract away explicit argument lists (`x -> ...`).
* **Readability:** Code looks like natural language expressions (e.g., `Objects::nonNull`).
* **Tool Support:** Modern IDEs will automatically flag valid lambdas and offer to convert them to method references.