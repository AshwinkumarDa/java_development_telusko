/// An abstract class is a partially complete blueprint. It defines what subclasses must do, and can also share common code.
/// Abstract method only belongs to Abstract class we need to prefix `abstract` keyword before `class ClassName`
/// The other classes who extends this Abstract class, must implement all abstract methods.
/// We cannot create object / instanciate of Abstract class instead we extend it and create a NewClass, implement all abstract methods and take isntance of NewClass.
/// It is not mandatory to have abstract methods in abstract class. normal methods can also works.

abstract class BaseCar {
    // mo methods and no properties are implementeed, it is optional to have abstract methods in abstract class.
}

abstract class Car {
    
    // public void changeGear(); //  error: missing method body, or declare abstract if Car is not abstract class, `class Car`. works when we prefix abstract e.g. `abstract class Car`
    public abstract void turn(); //abstract method. need to prefix with `abstract` keyword otherwise error: missing method body, or declare abstract

    public void drive() { } //empty implmementation, not abstract method.


    public void playMusic() {
        System.out.println("Playing music");
    }
}

//AbstractClass
// this is new abstract class extends another abstract class, it can not implement all abstract methods.
// to skip some abstract methods to implement we need to must have create a new Abstract class.
abstract class Nexon extends Car {
    public abstract void centralLockOnOff();

    public void turnOnOffPowerWindows() {
        System.out.println("power window on off...");
    }
}

// Concrete class
class NexonSmart extends Car { 
    // when extending Abstract class (Car) need to implement all abstract method in sublcass.
    public void turn() {
        System.out.println("Take Turn");
    }
}

// This is Concrete class as well.
class NexonFearlessPlus extends Nexon {
    
    public void togglePanaromicSunroof() {
        // this is new method of it's own class.
        System.out.println("toggle Panaromic Sunroof");
    }

    public void turn() {
        System.out.println("Turn On Car");
    }

    public void centralLockOnOff() {
        System.out.println("Central Lock On Off");
    }
}

public class AbstractClassDemo {
    public static void main(String[] args) {
        // Car car = new Car(); // Car is abstract; cannot be instantiated
        // car.drive(); //here we haven't implemented drive() method, it's just empty implementation.
        // car.playMusic();

        NexonSmart nexonSmart = new NexonSmart();
        nexonSmart.drive();
        nexonSmart.turn();
        nexonSmart.playMusic();

        // 
        NexonFearlessPlus nexonFearlessPlus = new NexonFearlessPlus();
        nexonFearlessPlus.togglePanaromicSunroof();
        nexonFearlessPlus.centralLockOnOff();
        nexonFearlessPlus.drive();
        nexonFearlessPlus.turn();
        nexonFearlessPlus.playMusic();
        
        

    }
}
