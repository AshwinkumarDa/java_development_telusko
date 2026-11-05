import java.util.ArrayList;

// Introduced in Java 10

public class TypeInference {

    // var num = 10; // it will give us error, we can't use var on class istance variables.
    public static void main(String[] args) {

        int a = 9; // Java is statically typed programming language
        var b = 8; // Local Variable Type Inference. Use only in methods not as class variables. Type is assigned at compile time.

        int c; // it will not give compile time error but it will crash at run time.
        
        //var d; // this will give compile time error. we need to assign a value 
        var d = 10;

        // var we can use as a variable and as a keyword. we can't use var as a class name e.g. class var { ... } we cant't use.
        String var = "Navin"; // works
        int nums[] = new int[10];

        var nums2 = new int[10];
        // var nums3[10] = new int[10]; // this is not allowed we can't mention square bracked.



       var arrayList = new ArrayList<>(); 

       System.out.println(a);
       System.out.println(b);
       // System.out.println(c);
       System.out.println(arrayList);
    }
}
