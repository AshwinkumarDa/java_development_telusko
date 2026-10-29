/// interface -> inteface = extends
/// class -> class = extends
/// class -> interface = implements
/// in interface -
/// // all variables are public static final bydefault
/// // all methods are public bydefault
/// Need for Interface - Dependency Injection, Loos coupling


/// Interface Types
/// 1. Normal - interface with 2 or more methods
/// 2. Functional/SAM - Single Abstract Method - It has only one method. 
/// 3.  Marker - no methods, blank interface. to update something to compiler. Serializable


// Interface
interface Device {
    String identifier = "0000000000";

    void turnOn();
    void turnOff();
}

// another interface which extends (inherits) other interface
interface Scannable extends Device {
    
    String name = "Scannable"; // constant: public static final by default, we can access its value using ClassName.PropertyName, we can override it in other implementation class.

    void connect(); // methods: are public bydefault
    void getInfo(); // methods: are public bydefault
    void scan(); // methods: are public bydefault
    void diagnose(); // methods: are public bydefault
}

class ZebraScanner implements Scannable {
    private String identifier;
    private String name;

    public  ZebraScanner(String identifier, String name) {
        this.identifier = identifier; // from Device
        this.name = name; // from Scannable
    }

    public void connect() {
        System.out.println("ZebraScanner.Connect " + name);
    }

    public  void getInfo() {
        System.out.println("ZebraScanner.getInfo " + name);
    }

    public void scan() {
        System.out.println("ZebraScanner.scan " + name);
    }
    
    public  void diagnose() {
        System.out.println("ZebraScanner.diagnose " + name);
    }

    public void turnOn() {
        System.out.println("ZebraScanner.turnOn " + name);
    }

    public void turnOff() {
        System.out.println("ZebraScanner.turnOff " + name);
    }

}

public class InterfaceDemo {
    public static void main(String[] args) {

        System.out.println("Default scannable inteface property `name` value = " + Scannable.name);
        System.out.println();

        // we can call only turnOn() and turnOff() methods and do not call connect(), getInfo(), diagnose(), scan() methods
        //  because they are unknown to Device.
        Device device = new ZebraScanner("0", "ZebraDevice");
        device.turnOn(); 
        device.turnOff();

        System.out.println();

        Scannable scanner = new ZebraScanner("1", "AnyScanner");
        scanner.connect();
        scanner.getInfo();
        scanner.diagnose();
        scanner.scan();


        System.out.println();
        ZebraScanner zebraScanner = new ZebraScanner("2", "ZebraScanner");
        zebraScanner.connect();
        zebraScanner.getInfo();
        zebraScanner.diagnose();
        zebraScanner.scan();
    }
}
