package com.java.core.inheritance;
// below class is derived from AdvCalc, ultimately it has Calc feature as well.
// This is multilevel inheritance.
// Single level inheritance = Calc -> AdvCalc
// Multi level inheritance = Calc -> AdvCalc -> VeryAdvCalc

/// Multiple inheritance will not work in java. (A, B) -> C - it is not possible in java.
/// class A { func show() }
/// class B { func show() }
/// class C extends A,B { } // -> this will not work.
/// c.show() -> it is confusing to which `show()` method to call from A or B. It's ambiguity.
/// 

public class VeryAdvCalc extends AdvCalc {
    public int power(int n1, int n2) {
        return (int)Math.pow(n1, n2);
    }
}
