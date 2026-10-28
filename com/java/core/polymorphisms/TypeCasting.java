package com.java.core.polymorphisms; 

/// TypeCasting
/// UpCasting
/// DownCasting
/// 


class Machine {
    public String name  = "Machine";

    public void machineOn() {
        System.out.println("in Machine.powerOn()");
    }

    public String toString() {
        return "Machine [name=" + name + "]";
    }
    
}

class Computer extends Machine {
    public String brand = "Apple";

    public void computerOn() {
        System.out.println("in Computer.turnOn()");
    }

    public String toString() {
        return "Computer [name=" + name + ", brand=" + brand + "]";
    }
    
}


public class TypeCasting {
    public static void main(String[] args) {
        
        double d = 4.5;
        // int i = d; // error: incompatible types: possible lossy conversion from double to int
        int i = (int) d; //type casting 

        System.out.println("d = " + d + ", i = " + i);

        System.out.println("No Casting");
        Machine m1 = new Machine();
        m1.machineOn();
        m1.toString();


        System.out.println("Down Casting");
        Machine m2 = 
        
        new Computer(); // UpCasting
        m2.machineOn();
        m2.toString();

        Machine m3 = (Machine) new Computer(); // UpCasting
        m3.machineOn();
        m3.toString();

        // m3.turnOn(); //error: cannot find symbol

        Computer com = (Computer) m3; // DownCasting
        com.computerOn();
        com.toString();
    }
    
}
