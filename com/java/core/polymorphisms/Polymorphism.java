// Polymorphism

// Compile time - overloading
// run time - overriding - in inheritance, 

// dynamic method dispatch

class Computer {
    public void show() {
        System.out.println("in Computer.show()");
    }
}

class Laptop extends Computer {
    public void show() {
        System.out.println("in Laptop.show()");
    }
}

class SmartPhone extends Computer {
    public void show() {
         System.out.println("in SmartPhone.show()");
    }
}


public class Polymorphism {
    public static void main(String[] args) {
        Computer acer = new Computer();
        acer.show();

        acer = new Laptop();
        acer.show();

        acer = new SmartPhone();
        acer.show();
        
        Computer comp = new Laptop();
        comp.show();
    }
}
