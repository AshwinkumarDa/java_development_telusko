package PackageDemo;
import java.util.ArrayList; // there is a `java` folder - inside `java`  folder there is a `util` folder.

//eg. import other.* // .* means all files contained in `other` folder but not folders. so we need to use package structure.
// the package which we share to entire world it should be unique on the internet so reverse our domain. e.g. com.google.calculator.


// bydefault `java.lang.*` is imported

import tools.AdvCalc;
import tools.Calc;

// Here we need to add import statement as above because AdvCalc and Calc are in tools folder. 
// and AdvCalc, and Calc adds a line in its files `package tools`
public class Demo {
    public static void main(String[] args) {
        Calc c = new Calc();
        c.add(2, 3);

        AdvCalc ac = new AdvCalc();
        ac.multi(2, 3);

        // to run below line we need to add `import java.util.ArrayList;` at the begning of file. otherwise we get error - error: cannot find symbol.
        ArrayList list = new ArrayList<>(); 
    }
}
