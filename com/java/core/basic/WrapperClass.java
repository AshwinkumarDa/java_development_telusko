public class WrapperClass {
    public static void main(String[] args) {
        
        // BOXING - Primitive - assigning primitive value to Object type from Integer = int
        // UN-BOXING - assiging Object type to Primitive type.
        // AUTO - Boxing, AUTO-Unboxing = assigning value without converting.

        // boxing
        int primitiveNum7 = 7; // primitive type
        Integer integerNum9 = 9; // Unboxing: setting value to Intefer reference variable.


       // Integer integerNum8 = new Integer(8); // Deprecated: Creating Integer reference type object

        // Integer integerBoxing7 = new Integer(primitiveNum7); //  Deprecated: Boxing: Initializing Integer class instance with primitive variable `primitiveNum7`

        Integer integerFromPrimitiveNum7 = primitiveNum7; // Auto boxing, compiler will rewrite `Integer.valueOf(primitiveNum7)`; converts from primitive to Object type.

        int intFromInteger = integerNum9.intValue(); // Unboxing - converting Object to primitive int type and setting it to IntegerToInt;

        // Converting to int from String
        String str = "12";
        // System.out.println(str * 3); //error: bad operand types for binary operator '*'
        int num12FromString = Integer.parseInt(str); // convert String to int
        System.out.println(num12FromString * 3);

    }
}
