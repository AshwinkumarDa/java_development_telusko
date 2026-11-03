import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ForEachLoopDemo {

    public static void main(String[] args) {
        List<Integer> intNums = Arrays.asList(4,5,7,3,2,6);

        // int sum = 0;

        // for (int n: intNums) {
        //     if (n%2 == 0) {
        //         n = n*2;
        //         sum = sum + n;
        //     }
        // }
        // System.out.println(sum);

        /// 3 ways to iterate array elements -
        /// 1 using for loop
        System.out.println("1. Printing array elements using For Loop-");
        for(int i = 0; i< intNums.size(); i++) {
            System.out.println(intNums.get(i));
        }

        // 2 Using Enhanced For Loop
        System.out.println("\n2. Printing array elements using Enhanced For Loop");
        for(int n: intNums) {
            System.out.println(n);
        }

        System.out.println("\n3. Printing array elements using ForEach loop with Consumer FunctionalInterface `accept()` method");
        //`ForEach` conforms (Implements) to Iterable.
        //`ForEach` has a parameter of type `Consumer`, it is FunctionalInterface (functional interface an interface having only one method and we can use Lambda expressions with Anonymuous class.)
        // Consumer interface has `accept()` method.

        // 3.1 Using ForEach Loop
        Consumer<Integer> consumer = new Consumer<Integer>() {
            public void accept(Integer n) {
                System.out.println("n = " + n);
            }
            
        };

        intNums.forEach(consumer);


        // 3.2 Using For Each Loop
        System.out.println("\nIn ShortHand Consumer Accept method-");
        Consumer<Integer> shortHandConsumer = (Integer n) -> System.out.println("shortHandConsumer n = " + n);
        intNums.forEach(shortHandConsumer);

        //3.3 Using optimised For Each Loop
        System.out.println("\nOptimised ShortHandConsumer");
        Consumer<Integer> optimisedShortHandConsumer = n -> System.out.println("optimised n = " + n);
        intNums.forEach(optimisedShortHandConsumer);

        // 3.4 For Each Loop final use
        System.out.println("\n replaced only lambda part.");
        intNums.forEach(n -> System.out.println(n));
        
    }
}
