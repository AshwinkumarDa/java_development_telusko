public class Loops {
    /// loop - while, do while, for
    
    // 100 - condition = for
    // while, do while - run time conditon


    public static void main(String[] args) {
        int i = 1;
        
        while(i<=4) {
            System.out.println("Hi " + i);
            int j = 1;
            
            while (j<=3) { // inner loop 
                System.out.println("Hello " + j);
                j++;
            }
            

            i++;

        }

        // do while loop - it executes the loop at least once even if condition is false.
        i = 5;
        do {
            System.out.println("Hi in do while loop " + i);
            i++;
        } while(i<=4);

        System.out.println("i = " + i);

        while(i<=4) {
            System.out.println("Hi in do while loop " + i); // this never executes because i<=4 condition fails.
            i++;
        } 


        // for loop
        // finite loop - for loop
        // initite loop - while, do while loop

        for(int c=0; c<=3; c++) {
            System.out.println("in for loop, c = "+ c);
        }

        System.err.println();

        for(int c=0; c<4; c++) {
            System.out.println("in for loop 2, c = "+ c); 
        }

         System.err.println();
        
        for(int c=1; c<5; c++) {
            System.out.println("DAY  "+ c);
            for (int j = 1; j<= 9; j++) {
                System.out.println("   " + (j+8) + " - " + (j+9));
            }
            
        }

        System.out.println("skipping increment");
        for(int c=1; c<5;) {
            System.out.println("skipping increment, DAY  "+ c);
            for (int j = 1; j<= 9; j++) {
                System.out.println("   " + (j+8) + " - " + (j+9));
            }
            
            c++;
        }
    }
}
