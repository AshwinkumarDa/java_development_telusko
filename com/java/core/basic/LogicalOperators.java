public class LogicalOperators {
    public static void main(String[] args) {
        // && AND 
        // || OR 
        // ! NOT

        // Short Circut
        // in case of && -> if first condition false then it does not check second condition, 
        // in case of || -> if first condition true then it does not check second condition. it is called short circuit.

        int x = 7;
        int y = 5;
        int a = 5;
        int b = 9;

        boolean result = x > y && a > b; // first condition true, second one is false, result is false.
        System.out.println(result); // false

        result = x > y || a > b;
        System.out.println(result); // true

        result = x < y || a > b;
        System.out.println(result); // false

        result = x < y || a < b;
        System.out.println(result); // true

        result = x < y || a < b || a > 1;
        System.out.println(result); // true


        result = a > b;
        System.out.println(result); // false

        result = a > b;
        System.out.println(!result); // NOT operator.
    }
}
