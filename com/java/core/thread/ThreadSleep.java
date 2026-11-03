//Thread is a class which implements Runnable Interface (Protocol).
class GreetHi extends Thread {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hi");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}

class GreetHello extends Thread {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hello");
            try { 
                Thread.sleep(10);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}

public class ThreadSleep {
    public static void main(String[] args) {
        GreetHi greetHi = new GreetHi();
        GreetHello greetHello = new GreetHello();
        
        greetHi.start();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) { e.printStackTrace(); }
        greetHello.start();
    }
}
