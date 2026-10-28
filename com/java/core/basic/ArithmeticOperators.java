public class ArithmeticOperators {
    public static void main(String[] args) {
        int num1 = 7;
        // int num2 = 5;

        // int result = num1 + num2;
        // int result = num1 - num2;
        // int result = num1 * num2;
        // int result = num1 / num2;
        // int result = num1 % num2;

        // System.out.println(result);

        num1 = num1 + 2;
        System.out.println(num1);
        num1 += 2;
        System.out.println(num1);
        num1 -= 2;
        System.out.println(num1);
        num1 *= 2;
        System.out.println(num1);
        num1 /= 2;
        System.out.println(num1);

        num1++; // post incement
        System.out.println(num1);
        num1--;
        System.out.println(num1);
        
        ++num1; // pre increment
        System.out.println(num1);

        int result = ++num1; // increment first and then fetch value, assign to result
        System.out.println(num1);
        System.out.println(result);

        result = num1++; // fetch value, assign to result, then increment.
        System.out.println(num1);
        System.out.println(result);
    }
    
}
