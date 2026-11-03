//Thread is a class which implements Runnable Interface (Protocol).

public class RunnableLambdaDemo {
    public static void main(String[] args) {
        Runnable greetHi = () -> {
            for (int i = 0; i< 100; i++) {
                System.out.println("Hi");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        };

        Runnable greetHello = () -> {
            for (int i = 0; i< 100; i++) {
                System.out.println("Hello");
                try { 
                    Thread.sleep(10);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        };
        
        Thread t1 = new Thread(greetHi);
        Thread t2 = new Thread(greetHello);

        t1.start();
        t2.start();

    }
}