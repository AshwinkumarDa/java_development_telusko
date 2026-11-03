import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class InputValues {
    public static void main(String[] args) {
        System.out.println("Enter a number: ");
        try {
            // 1
            int asciiNumber = System.in.read(); // it gives us ASCII value e.g. 4 -> 52, it reads one byte at a time.
            System.out.println("Entered (ASCII) Number is : " + asciiNumber);
            System.out.println("Actual entered number is : " + (asciiNumber - 48));
            
            // Consume the remaining newline
            System.in.read(); //  it reads one byte at a time. os we need to add it again for \n (Enter/ Return ) key.

            // 2
            System.out.println("Entered a number: ");
            InputStreamReader in = new InputStreamReader(System.in);
            BufferedReader bf = new BufferedReader(in);
            int num = Integer.parseInt(bf.readLine());
            System.out.println("Entered number: " + num);

            
            // 3
            Scanner sc = new Scanner(System.in);
            int num2 = sc.nextInt();
            System.out.println("Num: " + num2);

            bf.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
}
