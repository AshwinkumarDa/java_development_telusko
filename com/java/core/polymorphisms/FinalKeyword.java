/*final*/ class Calc {
    final public void show() {
        System.out.println("in Calc show");
    }

    public void add(int a, int b) {
        System.out.println("Calc.add = " + (a+b));
    }
}

class AdvCalc extends Calc {
    // error: show() in AdvCalc cannot override show() in Calc
    /*public void show() {
        System.out.println("in AdvCalc.show()");
    }*/
}

// error: cannot inherit from final Calc
// add final keyword before class Calc above.
// class AdvCalc extends Calc { }

public class FinalKeyword {
    public static void main(String[] args) {
        // final -variable, method, class
        // fianl - variable - we override, change its value. Making constants.
        // final - method - we can't override it.
        // final - class - we can't extend it. it stops inheritance.

        final int num = 8;
        System.out.println(num);
        // num = 9; // error: cannot assign a value to final variable num
        // System.out.println(num);
        
        Calc obj = new Calc();
        obj.show();

        obj.add(4, 5);

    }
}
