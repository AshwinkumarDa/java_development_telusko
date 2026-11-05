import java.util.Arrays;
import java.util.List;
// import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Navin", "Lakshmi", "John", "Kishor");

        /// import  java.util.Optional;, 
        // Optional<String> nameHasX = names.stream()
        //                         .filter(n -> n.contains("x"))
        //                         .findFirst();
        //                        // .orElse("Not Found"); // we can write this in below println statememnt as `nameHasX.orElse("Not Found")
        // System.out.println("Name contains X character: " + nameHasX.orElse("Not Found"));


        String nameHasX = names.stream()
                                .filter(n -> n.contains("x"))
                                .findFirst()
                                .orElse("Not Found"); // we can write this in below println statememnt as `nameHasX.orElse("Not Found")



        System.out.println("Name contains X character: " + nameHasX);

    }
}
