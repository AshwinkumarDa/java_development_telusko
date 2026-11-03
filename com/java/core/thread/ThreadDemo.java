class GreetHi extends Thread {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hi");
        }
    }
}

class GreetHello extends Thread {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hello");
        }
    }
}

public class ThreadDemo {
    // Scheduler decides which thread need to execute when.
    // every shceuler has different priority.
    public static void main(String[] args) {
        GreetHi greetHi = new GreetHi();
        greetHi.start();

        GreetHello greetHello = new GreetHello();
        greetHello.start();
    }
}
