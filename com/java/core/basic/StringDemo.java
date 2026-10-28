public class StringDemo {
    // String stored in Heap in String Constant Pool
    // Mutable String -> StringBuffer, StringBuilder
    // Immutable String  -> String

    // Rule of thumb: use equals() for comparing strings, and treat == on strings as a reference check only.

    public static void main(String[] args) {
        // String is a Class type
        // String name = "navin"; //This will work but this is not correct way.

        StringAddressDemo addressDemo = new StringAddressDemo();

        String name = new String("Navin");
        System.out.println("Hello " + name);

        System.out.println(name.concat(" Reddy"));

        System.out.println(name.charAt(1));

        name = name + " Reddy";
        System.out.println("Hello again, " + name);

        String s1 = "Navin";
        String s2 = "Navin";
        String s3 = "navin";
        String s4 = "Navin Reddy";

        System.out.println();
        System.out.println("Name = " + name);
        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);
        System.out.println("s3 = " + s3);
        System.out.println("s4 = " + s4);
         
        System.out.println("name == s1 - " + (name == s1));
        System.out.println("name == s2 - " + (name == s2));
        System.out.println("name == s3 - " + (name == s3));
        System.out.println("name == s3 - " + (name == s3));
        System.out.println("name == s4 - " + (name == s4));

        System.out.println("name = " + name +" is at " + addressDemo.getMemoryAddress(name));
        System.out.println("s1 = " + s1 + " is at " + addressDemo.getMemoryAddress(s1));
        System.out.println("s2 = " + s2 +" is at " + addressDemo.getMemoryAddress(s2));
        System.out.println("s3 = " + s3 +" is at " + addressDemo.getMemoryAddress(s3));
        System.out.println("s4 = " + s4 +" is at " + addressDemo.getMemoryAddress(s4));
        
        // Here name is not initialized on String Literal pool so name and s4 are different.
    }


  
}

class StringAddressDemo {
      public String getMemoryAddress(String str) {
        return Integer.toHexString(System.identityHashCode(str));
      }
}
