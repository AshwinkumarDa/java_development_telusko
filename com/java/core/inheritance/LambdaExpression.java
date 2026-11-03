@FunctionalInterface 
/**
 * Printable
 */
interface Runnable {
    //void run();
    void run(int speed);
}

// class Car implements Runnable {
//     public void run() {
//         System.out.println("Car is running.");
//     }
// }

public class LambdaExpression {
    public static void main(String[] args) {
       // Car car = new Car();
       // car.run();

       // Using anonimous inner class
       Runnable runnable = new Runnable(){
            public void run(int speed) {
                System.out.println("In Anonymous class a Runnable thing is running at speed: " + speed);
            }
       };

       runnable.run(1);

    // In java 8 - Java is no more verbose
    // we can - shorten the code with Lambda Expression in Functional Interface

    // Lambda Expression - 
    Runnable runnable2 = (int speed) -> {
        System.out.println("Another anonymous class with Lambda expression is running at speed: " + speed);
    };
    runnable2.run(2);

    // Lambda Expression - shorten version 1 - if it contains only one statement then we can skip curely brackets.
    Runnable runnable3 = (int speed) -> System.out.println("Running in Lambda Expression shorter version 1 without curely braces due to only one statement in this block running at speed: " + speed);
    runnable3.run(3);

    //We can ignore variable type, if we have one variable we don't need round brackets.
    Runnable runnable4 = speed -> System.out.println("running in lambda expression shorten version2 ignoring variable type and removed round brackets running at speed " + speed);
    runnable4.run(4);
    }    
}
