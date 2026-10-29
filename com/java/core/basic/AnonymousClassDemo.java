// Using Anonymous Class we can change the implementation of existing methods of class without creating a new class, we can create it from it's instance.
// Provide new implementation of existing another class methods.
// We can use it in Interface, Lambda Expression, Abstract Class, etc.

abstract class LivingThing {
    public abstract void breathe();
}

class Animal {
    public String name;

    public Animal(String name) {
        this.name = name;
    }
    public void sound() {
        System.out.println("animal sound");
    }
}

// after compile it creates `AnonymousClassDemo$1.class` file.
public class AnonymousClassDemo {
    public static void main(String[] args) {
        Animal dog = new Animal("Puppy") {
            public void sound() {
                System.out.println(this.name + "says Woof Woof...");
            }
        };

        dog.sound();

        // compiler creates `AnonymousClassDemo$2.class` file.
        Animal ashwin = new Animal("Ashwinkumar") {
            public void sound() {
                System.out.println("Hello there! says " + this.name);
            }
        };
        ashwin.sound();
    }
}
