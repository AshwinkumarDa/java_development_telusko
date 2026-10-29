/// Below code prints "Bird is Moving" instead of "The Parrot is Moving" ??? Why??? there may be a typo mistake happens. Check spellings of both methods `startMoving()` and `startsMoving()`
/// 1. @Override annotation tells compiler that we are overriding this method, if any typo mistake happens then compiler gives error.
///  In Hibernate, Spring framework, we use most annotations at method level, class level etc.
/// 2. @Depricated - used at class level, 
/// 3. 
/**
 * 
 * Bird
 */


/// Interface Types
/// 1. Normal - interface with 2 or more methods
/// 2. Functional/SAM - Single Abstract Method - It has only one method. 
/// 3.  Marker - no methods, blank interface. to update something to compiler. Serializable. Use @FunctionalInterface prefix before Interface, In Java 8 we can use Lambda Expression with the help of Anonimus Inner Class.


class Bird {
    public void startMoving() {
        System.out.println("The Bird is Moving");
    }

    public void see() {
        System.out.println("Bird is seeing something");
    }
}

class Parrot extends Bird {
    // below is not overridden method. it is a fresh new method.
    public void startsMoving() {
        System.out.println("The Parrot is Moving");
    }

    // @Override annotation tells compiler that we are overriding this method, if any typo mistake happens then compiler gives error.
    @Override 
    public  void see() {
        System.out.println("The Parrot is seeing green chilly");
    }
}

public class AnnotationDemo {
    public static void main(String[] args) {
        Parrot parrot = new Parrot();
        parrot.startMoving(); // it prints Bird is Moving ??? Why??? there may be a typo mistake happens. Check spellings of both methods `startMoving()` and `startsMoving()`

        System.out.println();
        parrot.see();
    }
    
}
