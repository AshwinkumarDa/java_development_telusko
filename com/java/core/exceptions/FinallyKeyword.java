import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class FinallyKeyword {
    public static void main(String[] args) throws NumberFormatException, IOException {
        // AutoClosable - closed in try itself
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int num = Integer.parseInt(br.readLine());
            System.out.println("Entered Number is: " + num);
        }
    }

    public static void main3(String[] args) {
        int num = 0;

        BufferedReader br = null;

        try {
            System.out.print("Enter a number: ");
            br = new BufferedReader(new InputStreamReader(System.in));
            num = Integer.parseInt(br.readLine());
            System.out.println("Entered Number is: " + num);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e);
        }
        finally {
            try {
                br.close();
            } catch (Exception e) {
                System.out.println("Exception during closing BufferedReader: " + e);
            }
        }
    }

    public static void main2(String[] args) {
        int numerator = 18;
        //int denominator = 0;
        int denominator = 2;
        int division = 0;
        try {
            division = numerator / denominator;
            System.out.println("The division is :" + division);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            System.out.println("Bye in Catch");
        } finally {
            // Finally block always gets exectued once even if it caught exception or not.
            System.out.println("Bye in Finally");
        }
    }
    
}
