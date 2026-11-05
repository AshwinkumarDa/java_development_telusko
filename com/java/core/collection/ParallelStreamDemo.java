// package com.java.core.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParallelStreamDemo {

    public static void main(String[] args) {
        final int SIZE = 10_000;

        List<Integer> nums = new ArrayList<>(SIZE);
        Random random = new Random();

        for(int i=1; i<= SIZE; i++){
            nums.add(random.nextInt(100));
        }

        long startSeq = System.currentTimeMillis();
        int sum1 = nums.stream()
                        .map(i -> {
                            try { Thread.sleep(1); } catch (Exception e) { e.printStackTrace(); }

                            return  i*2;
                        })
                        .mapToInt(i -> i)
                        .sum();
        long endSeq = System.currentTimeMillis();

        // we can use `parallelStream()` only when values are independet to process. 
        // for sorting we cant use `paralellStream()`.
        long startPara = System.currentTimeMillis();
        int sum2 = nums.parallelStream()
                        .map(i -> {
                            try { Thread.sleep(1); } catch (Exception e) { e.printStackTrace(); }
                            
                            return  i*2;
                        })
                        .mapToInt(i -> i)
                        .sum();
        long endPara = System.currentTimeMillis();
        

        System.out.println("seq sum1 = " + sum1 + " took " + (endSeq-startSeq));
        System.out.println("para sum2 = " + sum2 + " took " + (endPara-startPara));

    }

}
