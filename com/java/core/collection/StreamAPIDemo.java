// package com.java.core.collection;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

// Please see ForEachLoopDemo.java file first
// Stream APIs does not effect on array. it does not change its values.
// Stream is a pool, like a filter in a River to clear water and stop all garbage or adulteration.

public class StreamAPIDemo {
    // Stream is Interface.
    // we can use Stream only once. next time it will give exception.

    public static void main(String[] args) {
        List<Integer> intNums = Arrays.asList(4, 5, 7, 3, 2, 6);
        Stream<Integer> sortedValues = intNums.parallelStream() // we can use parellelStream() which runs in multiple
                                                                // threads and return result for filter. For Sorted We need all
                                                                // values so we can't use paralellStream with sort.
                .filter(n -> n % 2 == 0) // it needs object of Predicate, Predicate is a FunctionalInterface having
                                         // test() method.
                .sorted();

        sortedValues.forEach(n -> System.out.println(n));
    }

    public static void main1(String[] args) {
        // see below main2() method for expanded form with FunctionalInterfaces.
        List<Integer> intNums = Arrays.asList(4, 5, 7, 3, 2, 6);
        int result = intNums.stream()
                .filter(n -> n % 2 == 0) // it needs object of Predicate, Predicate is a FunctionalInterface having
                                         // test() method.
                .map(n -> n * 2) // map need object of Function, it is also FunctionalInterface
                .reduce(0, (c, e) -> c + e);// .reduce(intialValue, (carry, newElement))
        System.out.println("Result = " + result);
    }

    public static void main2(String[] args) {
        List<Integer> intNums = Arrays.asList(4, 5, 7, 3, 2, 6);

        // Stream<Integer> s1 = intNums.stream();

        // System.out.println("Traversing arrayList using Stream API and ForEach
        // loop-");

        // // s1.forEach(n -> System.out.println(n));

        // Stream<Integer> s2 = s1.filter(n -> n%2 == 0);

        // Stream<Integer> s3 = s2.map(n -> n*2);

        // int result = s3.reduce(0, (c,e) -> c+e); //makes addition.
        // System.out.println("Result = " + result);

        Predicate<Integer> predicate = new Predicate<Integer>() {
            public boolean test(Integer n) {
                // if(n%2 ==0)
                // return true;
                // return false;
                return n % 2 == 0;
            }
        };

        Function<Integer, Integer> fun = new Function<Integer, Integer>() {
            public Integer apply(Integer n) {
                return n * 2;
            }
        };

        BinaryOperator<Integer> binaryOperator = new BinaryOperator<Integer>() {

            public Integer apply(Integer c, Integer e) {
                // c = carry (running result)
                // e = next element
                return c + e;
            }

        };

        int result = intNums.stream()
                .filter(predicate) // it needs object of Predicate, Predicate is a FunctionalInterface having
                                   // test() method.
                .map(fun) // map need object of Function, it is also FunctionalInterface
                .reduce(0, binaryOperator);// .reduce(intialValue, (carry, newElement)) it uses BinaryOperator
                                           // FunctionalInterface.
        System.out.println("Result = " + result);
    }
}
