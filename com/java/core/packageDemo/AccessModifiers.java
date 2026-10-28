import tools.Car;
import tools.i10;

/// private - can access in the class itself.
/// default - can access in the same package but can't access different package, different package non-subclass.
/// protected - it work in same package but it doesn't work in other package. we can access it in subclass as well.
/// /// public - can access anywere
/// Public: we can't make 2 classes public in one file.
/// Private: make variables private, and make methods public when required to access outside.
/// Protected: access subclass in a package use protected.
/// Default: try to avoid default.



public class AccessModifiers {
    public static void main(String[] args) {
         Car ferrari = new Car();
         ferrari.publicName = "Ferrari";

         // ferrari.protectedSpeed = "100"; // can't access because it is protected in Car which is in different package. we can use only in subclass not in other placess. eg. use in i10 class.
         
         // ferrari.version = "1.0"; we can't acces this. error: version is not public in Car; cannot be accessed from outside package


        // i10 i10Car = new i10();
        //i10Car.setValues();
       // i10Car.show();
    }
}
