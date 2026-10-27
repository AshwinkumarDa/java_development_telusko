public class Variables {
    
    public static void main(String args[]) {

        // Conversion = automatic(implicit) conversion
        // Casting = Explicit conversion.

         // byte b = 127;
        // byte b = 257; // Error:  Type mismatch: cannot convert from int to byte

        int a = 257;

        // byte k = a; // error: incompatible types: possible lossy conversion from int to byte

        byte k = (byte) a; // error: incompatible types: possible lossy conversion from int to byte 
        // System.out.println(b);

        float f = 5.6f;
        int t = (int) f; // here we loose floating point value (.56) after converting it to int from float.

        System.out.println(a);
        System.out.println(k);
        System.out.println(f);
        System.out.println(t);


        // Type promotion
        byte b1 = 10;
        byte b2 = 30;
        int result = b1 * b2;
        System.out.println(result);


    }
}
