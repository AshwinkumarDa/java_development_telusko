package com.java.core.polymorphisms;

// 1. Helper class (No public keyword)
class Machine {
    public String name = "Machine";

    public void machineOn() {
        System.out.println("in Machine.powerOn()");
    }

    @Override
    public String toString() {
        return "Machine [name=" + name + "]";
    }
}

// 2. Subclass inheriting from Machine (No public keyword)
class Computer extends Machine {
    public String brand = "Apple";

    public void computerOn() {
        System.out.println("in Computer.turnOn()");
    }

    @Override
    public String toString() {
        return "Computer [name=" + name + ", brand=" + brand + "]";
    }
}

// 3. Main execution class (Must be PUBLIC and match the filename)
public class TypeCasting {
    public static void main(String[] args) {
        
        double d = 4.5;
        int i = (int) d; // type casting 

        System.out.println("d = " + d + ", i = " + i);

        System.out.println("No Casting");
        Machine m1 = new Machine();
        m1.machineOn();
        System.out.println(m1.toString()); // Added print statement to actually see output

        System.out.println("Down Casting");
        
        Machine m2 = new Computer(); // UpCasting
        m2.machineOn();
        System.out.println(m2.toString());

        Machine m3 = (Machine) new Computer(); // UpCasting
        m3.machineOn();
        System.out.println(m3.toString());

        // DownCasting to access subclass-specific methods
        Computer com = (Computer) m3; 
        com.computerOn();
        System.out.println(com.toString());
    }
}




// package com.java.core.inheritance;
//  class A {
//     public A() {
//         // default it calls super();
//         // `super()` means call the constructor of super class.
//         System.out.println("in A()");
//     }

//     public A(int n) {
//         // super();
//         System.out.println("in A(int)");
//     }

//     public A(String name) {
//         System.out.println("in A(String)");
//     }
//  }

//  class B extends A {
//     public B() {
//         // super();
//         System.out.println("in B()");
//     }


//     public B(int n) {
//         //super();
//         super(n);
//         System.out.println("in B(int)");
//     }

//     public B(String name) {
//         System.out.println("in B(String)");
//     }
//  }

//  class C extends  B {
//     public C() {
//         // super();
//         System.out.println("in C()");
//     }

//     public C(int n) {
//         // super();
//         super(n);
//         System.out.println("in C(int)");
//     }

//     public C(String name) {
//         this();
//         System.out.println("in C(String)");
//     }
//  }


// public class SuperDemo {
//     // Every constructor in java has a method called super(). even if me don't mention, it is there.
//     // super() will call default constructor of base classes.
//     // Every class in java extend `Object` class even if we don't mention it. so we are calling contructor of Object type.
//     // `this()` will execute the constructor of it's same class.

//     public static void main(String[] args) {
//         /// prints below
//         /// in A()
//         /// in B()
//         /// in C()
//         C c = new C();

//         System.out.println();

//         /// below line prints 
//         /// in A()
//         // in B()
//         // in C(int)
//         C c2 = new C(2);

//         // after calling `super(n) in parent classes above line will print as below - 
//         // in A(int)
//         // in B(int)
//         // in C(int)

//         System.out.println();
//         C c3 = new C("ashwin");
//     }

// }
 