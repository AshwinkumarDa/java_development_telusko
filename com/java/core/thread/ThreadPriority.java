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

public class ThreadPriority {
     public static void main(String[] args) {
        GreetHi greetHi = new GreetHi();
        GreetHello greetHello = new GreetHello();
        

        System.out.println("greetHi.getPriority() = " + greetHi.getPriority());
        System.out.println("greetHello.getPriority() = " + greetHello.getPriority());
        
        greetHello.setPriority(Thread.MAX_PRIORITY);
        System.out.println("greetHi.getPriority() = " + greetHi.getPriority());
        System.out.println("greetHello.getPriority() = " + greetHello.getPriority());
        
        greetHi.start();
        greetHello.start();
    }
    
}
