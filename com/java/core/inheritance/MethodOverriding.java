package com.java.core.inheritance;
class A {
    public void show() {
        System.out.println("in A:show()");
    }
}

class B extends A {
    public void show() {
        System.out.println("in B:show()");
    }
}

public class MethodOverriding {
    public static void main(String[] args) {
        B b = new B();
        b.show(); // we have implemented `show()` method in class B hence it calls show() method of class B instead of class A show method. 
    }
}
