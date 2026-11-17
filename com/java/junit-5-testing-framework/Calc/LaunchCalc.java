public class LaunchCalc {
    public static void main(String[] args) {
        Calc c = new Calc();

        int result = c.divide(10, 5);

        // verify actual result with expected result
        if(result == 2) {
            System.out.println("test case is passed...");
        } else {
            System.out.println("Test case is failed.");
        }


        result = c.divide(10, 2);

        // verify actual result with expected result
        if(result == 2) {
            System.out.println("test case is passed...");
        } else {
            System.out.println("test case is failed.");
        }
 
    }
}
