/*
 * ============================================================
 *  JAVA ANNOTATIONS REFERENCE
 *  Description, purpose and one example for each annotation.
 * ============================================================
 */
public class JavaAnnotations {

    /*
     * ------------------------------------------------------------
     *  CORE JAVA ANNOTATIONS (java.lang)
     * ------------------------------------------------------------
     */

    /*
     * 1. @Override
     *    Description : Marks a method that overrides a superclass or
     *                  interface method. The compiler checks this.
     *    Purpose     : Catches mistakes such as misspelled method names
     *                  or wrong parameter types.
     *    Example     :
     *        class Animal {
     *            void speak() { System.out.println("..."); }
     *        }
     *        class Dog extends Animal {
     *            @Override
     *            void speak() { System.out.println("Woof"); }
     *        }
     */

    /*
     * 2. @Deprecated
     *    Description : Marks an element (class, method, field) that should
     *                  no longer be used, usually because a better option exists.
     *    Purpose     : Warns developers at compile time and documents the
     *                  replacement. Takes optional since and forRemoval attributes.
     *    Example     :
     *        @Deprecated(since = "9", forRemoval = true)
     *        public void oldMethod() { }
     */

    /*
     * 3. @SuppressWarnings
     *    Description : Tells the compiler to suppress specific warnings
     *                  for the annotated element.
     *    Purpose     : Silences warnings you have reviewed and accepted, such as
     *                  unchecked casts, so real warnings stay visible.
     *    Example     :
     *        @SuppressWarnings("unchecked")
     *        List<String> names = (List<String>) obj;
     */

    /*
     * 4. @FunctionalInterface
     *    Description : Marks an interface with exactly one abstract method,
     *                  so it can be used as a lambda target.
     *    Purpose     : Makes the compiler fail if a second abstract method is
     *                  added, which would break lambda usage.
     *    Example     :
     *        @FunctionalInterface
     *        interface Greeter {
     *            String greet(String name);
     *        }
     *        Greeter g = name -> "Hello, " + name;
     */

    /*
     * 5. @SafeVarargs
     *    Description : Asserts that a varargs method or constructor does not
     *                  perform unsafe operations on its generic varargs parameter.
     *    Purpose     : Suppresses the "possible heap pollution" warning when the
     *                  method is known to be safe. Only for static, final, or
     *                  private methods and constructors.
     *    Example     :
     *        @SafeVarargs
     *        static <T> List<T> listOf(T... items) {
     *            return Arrays.asList(items);
     *        }
     */

    /*
     * 6. @Serial (Java 14+)
     *    Description : Marks fields and methods related to Java serialization,
     *                  such as serialVersionUID, writeObject and readObject.
     *    Purpose     : Lets the compiler verify that serialization members have
     *                  the correct signatures.
     *    Example     :
     *        public class User implements Serializable {
     *            @Serial
     *            private static final long serialVersionUID = 1L;
     *        }
     */

    /*
     * 7. @Native (Java 9+)
     *    Description : Marks a constant field that native (C/C++) code may reference.
     *    Purpose     : Signals that the constant is used outside Java, so tools
     *                  can find and keep it.
     *    Example     :
     *        @Native public static final int MAX_BUFFER = 1024;
     */

    /*
     * ------------------------------------------------------------
     *  META-ANNOTATIONS (java.lang.annotation)
     *  Applied to other annotations to control how they behave.
     * ------------------------------------------------------------
     */

    /*
     * 8. @Retention
     *    Description : Specifies how long an annotation is kept:
     *                  SOURCE  - discarded by the compiler
     *                  CLASS   - kept in bytecode, not at runtime (default)
     *                  RUNTIME - available via reflection
     *    Purpose     : Makes annotations readable at runtime, as frameworks need.
     *    Example     :
     *        @Retention(RetentionPolicy.RUNTIME)
     *        @interface Info { String value(); }
     */

    /*
     * 9. @Target
     *    Description : Restricts where an annotation can be applied, such as
     *                  methods, fields, classes or parameters.
     *    Purpose     : Prevents misuse, for example putting a method-only
     *                  annotation on a class.
     *    Example     :
     *        @Target({ElementType.METHOD, ElementType.TYPE})
     *        @interface Audited { }
     */

    /*
     * 10. @Documented
     *     Description : Includes the annotation in the generated Javadoc.
     *     Purpose     : Makes the annotation visible in API documentation.
     *     Example     :
     *         @Documented
     *         @interface Experimental { }
     */

    /*
     * 11. @Inherited
     *     Description : Makes a class annotation inherited by subclasses.
     *                   Has no effect on methods or fields.
     *     Purpose     : Lets subclasses pick up class-level configuration
     *                   from a parent class.
     *     Example     :
     *         @Inherited
     *         @interface Entity { }
     *         @Entity class Base { }
     *         class Child extends Base { }   // Child is also @Entity
     */

    /*
     * 12. @Repeatable (Java 8+)
     *     Description : Allows the same annotation to be applied more than
     *                   once to the same element.
     *     Purpose     : Lets you write repeated annotations without manually
     *                   wrapping them in a container.
     *     Example     :
     *         @Repeatable(Schedules.class)
     *         @interface Schedule { String day(); }
     *
     *         @interface Schedules { Schedule[] value(); }
     *
     *         @Schedule(day = "Mon")
     *         @Schedule(day = "Wed")
     *         class Meeting { }
     */

    /*
     * ------------------------------------------------------------
     *  COMMON THIRD-PARTY ANNOTATIONS (not part of Java SE)
     * ------------------------------------------------------------
     */

    /*
     * @Test (JUnit)        : Marks a test method.
     *     @Test void addsNumbers() { assertEquals(4, 2 + 2); }
     *
     * @Autowired (Spring)  : Asks Spring to inject a dependency.
     *     @Autowired private UserService userService;
     *
     * @Entity (JPA)        : Marks a class as a database table.
     *     @Entity public class Customer { @Id Long id; }
     *
     * @Getter / @Setter (Lombok) : Generates getter and setter methods.
     *     @Getter @Setter class Person { String name; }
     */
}
