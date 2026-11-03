/// Threads
/// Mutation - we can change something. change the value of variable. more than one thread accessing same variable to read / write.
/// - work with immutable data
/// - thread safe for mutable data only one thread can access the data/variable.

/// Thread States
/// 1. New - t1 = new Thread() it will be in this state
/// 2. Runnable - when we call start() it will be in Runnable state.
/// 3. Running - running with the `run()` method.
/// 4. Waiting - kept on hold with `sleep()`, `wait()` method. or any other. this state notify() and move the state to Runnable state.
/// 5. Blocked - stop() state (deprecated)
/// 6. Dead - stop
/// 
class Counter {
    int count;
    
    //test by removing keyword `synchronized`. we get different value of count at the end.
    // when we add `synchronized` we get same value of count evertyime and that is 20000.
    public synchronized void increment() { 
        //System.out.println("count++");
        count++;
    }
}

public class RaceCondition {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        
        Runnable obj1 = () -> {
            System.out.println("obj1");
            for(int i =1; i<=10000; i++) {
                counter.increment();
            }
        };

        Runnable obj2 = () -> {
            System.out.println("obj2");
            for(int i = 1; i<=10000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(obj1);
        Thread t2 = new Thread(obj2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("counter = " + counter.count);
    }    
}
