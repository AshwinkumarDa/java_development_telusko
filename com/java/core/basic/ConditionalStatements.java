public class ConditionalStatements {
    public static void main(String[] args) {
        int x = 8;


        // below line does not prints "Hello"
        if (false) {
            System.out.println("Hello 1"); // doesn't print
        }


        if (x > 10) {
            System.out.println("Hello 2"); // doesn't print
        }

        x = 18;
        if (x > 10) {
            System.out.println("Hello 3");
        }
        
        x = 8;
        if (x > 10 && x <= 20) { // 11 - 20
            System.out.println("Hello 4");   // doesn't print
        }

        x = 18;
        if (x > 10 && x <= 20) { // 11 - 20
            System.out.println("Hello 5");  
        }

        x = 28;
        if (x > 10 && x <= 20) { // 11 - 20
            System.out.println("Hello 6");   // doesn't print
        }

        x = 18;
        if (x > 10 && x <= 20) 
            System.out.println("Hello 7"); 
        else 
            System.out.println("Bye"); // doesn't print

        x = 28;
        if (x > 10 && x <= 20) 
            System.out.println("Hello 8"); // doesn't print
        else 
            System.out.println("Bye 1");

        x = 8;
        int y = 7;

        if (x>y)
            System.out.println("x = " + x);
        else 
            System.out.println("y = " + y);


        if (x>y) { // Use curely braces when multiple stements need to execute in condition. for one statement we can skip curely braces.
            System.out.println("with thank you, x = " + x);
            System.out.println("Thank you!");
        }
        else 
            System.out.println("y = " + y);

        //int z = 6;
        // int z = 9;

        x = 8;
        y = 7;
        int z = 9;

        // greatest among three values x, y, z
        if (x > y && x > z) { // false
            System.out.println("x is greatest");
        }

                // greatest among three values x, y, z
        if (x > y && x > z)  // false
            System.out.println("x is greatest");
         else if (y > x && y > z) 
             System.out.println("y is greatest");
        else 
             System.out.println("z is greatest");
            

        // Ternary Operator
        int n = 4;
        int result = 0;
        if (n%2==0)
            result = 10;
        else
            result = 20;

        System.out.println(result);

        result = n%2==0 ? 30 : 40; // Ternary Operator
         System.out.println(result);


         // Switch statement
         n = 1;
         
         if (n==1)
            System.out.println("Monday");
        else if (n==2)
            System.out.println("Tuesday");
        else if (n==3)
            System.out.println("Wednesday");
        else if (n==4)
            System.out.println("Thursday");
        else if (n==5)
             System.out.println("Friday");
        else if (n==6)
            System.out.println("Saturday");
        else 
            System.out.println("Sunday");

        // Switch statement - based on value it execute particular case.
        System.out.println("\nUsing switch case");
        switch (n) {
            case 1: System.out.println("Monday"); 
            case 2: System.out.println("Tuesday"); 
            case 3: System.out.println("Wednesday"); 
            case 4: System.out.println("Thursday"); 
            case 5: System.out.println("Friday"); 
            case 6: System.out.println("Saturday"); 
            case 7: System.out.println("Sunday"); 
        }

        System.out.println("\nUsing switch case break");
        switch (n) {
            case 1: System.out.println("Monday"); break;
            case 2: System.out.println("Tuesday"); break;
            case 3: System.out.println("Wednesday"); break;
            case 4: System.out.println("Thursday"); break;
            case 5: System.out.println("Friday"); break;
            case 6: System.out.println("Saturday"); break;
            default: System.out.println("Sunday"); break;
        }

    }
}
