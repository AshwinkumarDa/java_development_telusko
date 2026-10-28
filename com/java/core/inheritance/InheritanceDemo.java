package com.java.core.inheritance;
// is , has
// a Laptop is a computer
// a Fortuner is a Car
// we refer an object to its upper model.  
// copy features from upper class.
// Parent = Base = Super class
// Child = Sub = Derived class


public class InheritanceDemo {
    public static void main(String[] args) {
        // Here in we written InheritanceDemo class in a folder named "Inheritance" it has 2 class files one is Calc.java and another is InheritanceDemo.java this file.
        // when we are not using Calc.java file then it won't compile. It can't generate .class files after compile.
        // .class file gets generated when we are using it.
        // delete .class files from this folder, comment all below line and compile and see.
        // for parent/base classes we need only .class files, there is no need to have .java files. try by deleting `Calc.java` file.

        // Use keyword `extends` to inherit. public class AdvCalc extends Calc
        Calc obj = new Calc();
        int r1 = obj.add(4, 5);
        int r2 = obj.sub(7, 3);
        System.out.println(r1 + " : " + r2);

        AdvCalc advCalc = new AdvCalc();
        r1 = advCalc.add(2, 3);
        r2 = advCalc.sub(9, 2);
        int r3 = advCalc.multi(2, 4);
        int r4 = advCalc.div(20, 5);

        System.out.println();
        System.out.println(r1 + " : " + r2 + " : " + r3 + " : " + r4);

        VeryAdvCalc vac = new VeryAdvCalc();
        int twoRaiseTo3 = vac.power(2, 3);
        System.out.println("twoRaiseTo3 = " + twoRaiseTo3);

        r1 = vac.add(1, 2); //accessing and calling method of Calc class
        r2 = vac.multi(3, 4); // accessing and calling method of AdvCalc class
        System.out.println(r1 + " : " + r2 );
    }
}
