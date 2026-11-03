// Used to throw caught exception to caller method to handle it.
// Checked exception

class MyThrowsClass {
    public void show() throws ClassNotFoundException {
        Class.forName("Calc"); // Demo class is not available so it throws error.
        /*try {
            // Class.forName("ThrowsKeyword");
            Class.forName("Calc"); // Demo class is not available so it throws error.
        } catch (ClassNotFoundException e) {
            System.out.println("Not able to find the class: " + e);
        }*/
    }
}

public class ThrowsKeyword {
    
    static {
        System.out.println("Class Loaded");
    }

    public static void main(String[] args) {
        MyThrowsClass obj = new MyThrowsClass();
        try {
            obj.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}