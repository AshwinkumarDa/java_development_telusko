class MyCustomException extends Exception {
    public MyCustomException(String string) {
        super(string);
    }
}

class MyRunTimeException extends RuntimeException {
    public MyRunTimeException(String string) {
        super(string);
    }
}

public class ThrowKeyword {
    public static void main(String[] args) {
        int i = 20; // change value to 0 to get exception
        int j = 0;
        try {
            j = 18/i;
            if (j == 0) {
                // throw keyword used to throw exception.
                // throw new ArithmeticException("My customer Arithmetic Exception Message");
               //  throw new MyCustomException("My Customer Exception message");
               throw new MyRunTimeException("my custom Runtime exception");
            }

        } catch(MyRunTimeException mre) {
            System.out.println("My Run Time Exception: " + mre);
        } /*catch(MyCustomException mce) { 
            // if error can't be thrown in try block we can't catch here so commented it here.
            System.out.println("My Customer Exception: " + mce);
        }*/ catch(ArithmeticException ae) {
            j = 18/1;
            System.out.println("Thats the default output exception: " + ae);
        } catch (Exception e) {
            System.out.println("This is unhandled unknown exception: " + e);
            
        }

        System.out.println();
        System.out.println("Bye");
    }
}
