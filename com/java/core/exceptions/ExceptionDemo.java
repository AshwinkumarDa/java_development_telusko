/// Error types - 
/// 1. Compile Time - e.g. wrong syntax, typo error in syntax, error: cannot find symbol
/// 2. Run Time - run time error we call Exceptions, we need to handle it.
//      Everyting is working, suddenly because of some reason the code stops working, execution stops. eg. File I/O file missing, divide by zero.
            // java.lang.ArithmeticException: / by zero
/// 3. Logical - everything is working, output is different not as expected. 
//      this type of error called logical error. 
//      difficult to solve, need lot of debugging.
// Object
public class ExceptionDemo {
   public static void main(String[] args) {
     //2 types of statements
    
    int zero = 0; // 1. Normal statement

    int two = 2; // 1. Normal statement
    int j = 18/two; // 2. Critical statement
    System.out.println("j = " + j); // if j = 0 then  we get exception - java.lang.ArithmeticException: / by zero

    String nullName = null;
    
    // try to handle exeption
    try {
        // j = 18/zero;
        j = 18/two;

        // array index out of bounds
        int numArray[] = new int[5];
        System.out.println("second element = " + numArray[1]);
        
        //Uncomment below to get exception - hjava.lang.ArrayIndexOutOfBoundsException: Index 5 out of bounds for length 5
        // System.out.println("sixth element = " + numArray[5]); 

        // error: java.lang.NullPointerException: Cannot invoke "String.length()" because "<local4>" is nullBye
        System.out.println(nullName.length()); 

    }  catch (ArithmeticException ae) {
        System.out.print("in ArithmeticException: "+ ae);
    } catch (ArrayIndexOutOfBoundsException aioobe) {
        System.out.print("in ArrayIndexOutOfBoundsException: "+ aioobe);
    }
    catch (Exception e) {
        System.out.print("Something went wrong error: "+ e);
    }

    


    // if we get exception and not handled then below line does not gets executed after line where exception occurred.
    System.out.println("Bye");
   }
    
}
