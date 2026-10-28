class A {
    public void show() {
        System.out.println("in A show()");
    }
}

public class NamingConvention {
    public static void main(String[] args) {

        // CamelCasing
        
        // Classes and Interface - Starts with Capital e.g. Human, String, Array

        // Variables and Methods - starts with small letter, e.g. marks, firstName, lastName, age.

        // Constants = all capital, e.g. PI, BRAND, ACCESS_KEY.

        // showmymarks() - it is difficult to read so suse camel case
        // showMyMarks()

        //snake_casing - java will not give any error for camel casing. but we follow camelCasing not snake_case.

        //age, DATA, Human() 

        A a = new A();
        a.show();

        A newA; //Reference creation - declaring variable
        newA = new A(); // Object creation - assigining value
        newA.show();

        new A(); // anonimus object, we can't reuse it. we can use it only once.
        new A().show();
        new A().show();
    }


}
