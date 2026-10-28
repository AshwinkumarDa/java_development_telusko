class Mobile {
    String brand;
    int price;
    static String name; // this name variable is common for all object. Static variable should get called with class name.

    

    public Mobile() {
        System.out.println("in default constructor");
        brand = "Apple";
        price = 1000;
        // Here `name` is static so it will get updated everytime when this constructor gets called. No need to set `name` here in this constructor. 
        // There is separate static block to initialize static veriable.
        name = "iPadAir"; 
    }

    static {
        System.out.println("In the static block");
        name = "MyStaticPhone";
    }

    public void show() {
        System.out.println(brand + " : " + price + " : " + name);
    }

    public static void showAgain(Mobile mobile) {
        System.out.println("In static method.");
        System.out.println(mobile.brand + " : " + mobile.price + " : " +  name); // we can't use non static variables and methods inside static method. It (Compiler) doesn't know which object to refer.
    }
}


public class StaticKeyword {
    // Each program should contain one public static void main(String[] args) method. 
    // Here we need to use static because compiler / jvm will call main method without creating object of StaticKeyword class. It can directly access main(String[]) method and starts the program.
    // main(String[]) method is starting point of each java program.
    // class loads only once for all instances/objects, and then objects initialized.

     public static void main(String[] args) throws ClassNotFoundException {

        // When we don't use or don't initialize a class instances then it's static block will get never executed.
        // To execute it's static block we can call below method. Class.forName() with desired class name. it will execute static block.
        // comment all lines except below one line.
        Class.forName("Mobile");
        
        // comment all below line to test static block is gets executed.

        // System.out.println("In main method");
        // Mobile apple = new Mobile();
        // apple.brand = "Apple"; 
        // apple.price = 1500;
        // apple.name = "SmartPhone";
        // apple.show();

        
        // Mobile samsung = new Mobile();
        // samsung.brand = "Samsung";
        // samsung.price = 1700;
        // samsung.name = "SmartPhone";
        // samsung.show();

        // System.out.println();

        // //apple.name = "Phone"; // call using Class name e.g. Mobile.name, it works but not good way.
        // Mobile.name = "Phone";
        
        // apple.show(); // name = "Phone"
        // samsung.show(); // name = "Phone" here as well as name is static.

        // System.out.println();
        // Mobile.showAgain(apple);
        // Mobile.showAgain(samsung);
    }
} 
