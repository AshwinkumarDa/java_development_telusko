//Thread is a class which implements Runnable Interface (Protocol).
class GreetHi implements Runnable {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hi");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}

class GreetHello implements Runnable {
    public void run() {
        for (int i = 0; i< 100; i++) {
            System.out.println("Hello");
            try { 
                Thread.sleep(10);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}

public class RunnableDemo {
     public static void main(String[] args) {
        GreetHi greetHi = new GreetHi();
        GreetHello greetHello = new GreetHello();
        
        Thread t1 = new Thread(greetHi);
        Thread t2 = new Thread(greetHello);

        t1.start();
        t2.start();

    }
}
