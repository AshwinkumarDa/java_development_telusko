package com.ashwinkumard.maven_demo_app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalcTest {
    @Test 
    public void testCalc() {
        Calc calc = new Calc();

        int actualResult = calc.divide(10, 5);
        int expectedResult = 2;
        assertEquals(expectedResult, actualResult);
    }
}
