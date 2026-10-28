// jshell on terminal

class Hello {

    // Every variable need a variable and type.
    // We can't change type of a variable.
    // Type conversion and casting - lower range can assigned to bigger range. e.g. short value assigned to long . long = short, implicit conversion
    // casting explicit conversion byte = (byte) int;
    //// float f = 5.6f
    /// int x = (int) f;
    /// here we loose floating point values in variable x.
    /// 


    public static void main(String a[]) {
        System.out.print("Hello World!");
        System.out.print(3 + 5);
        System.out.print(8+7);

        System.out.println("Hello World!");
        System.out.println(3 + 5);
        System.out.println(8+7);

        int num = 3;
        System.out.println(num);

        int num1 = 3;
        int num2 = 5;
        System.out.println(num1 + num2);

        int result = num1 + num2;
        System.out.println(result);

        int num3 = 9;
        byte by = 127;
        short sh = 558;
        long l = 5854l;

        float f = 5.8f;
        double d = 5.8;
        
        char c = 'k'; // literals

        boolean b = true;
        
        // literals

        int num4 = 0b101; // binary 
        System.out.println("Num4: " + num4);

        int num5 = 0x7E; // Hexadecimal
        System.out.println("Num5: " + num5);

        int num6 = 100_000_000;
        System.out.println(num6);

        double num7 = 56; //integer number 56 will get converted to double automatically.
        System.out.print(num7);

        double num8 = 12e10;
        System.out.println(num8);

        // boolean num9 = 1; // This line gives error -incompatible types: int cannot be converted to boolean.
        // System.out.println(num9);

        char c1 = 'a';
        System.out.println(c1);
        c1++;
        System.out.println(c1);
    }
}

// DataType - Primitive - 
// Integer - int (4 bytes), long (8 bytes), short (2 bytes), byte (1 byte)
// Float- (4 bytes), double ( 8 bytes), default is double, float has limited precisions. e.g. float num = 5.6f, float num = 5.6 (it will give error, 5.6 is double type) 
// Character - 2 bytes UNICODE
// Boolean - true or false, e.g. bool is_true = true
//
//
 