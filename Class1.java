/// Method overloading
/// 
/// 
class Calculator {

    int num = 5; // num will store in heap
    
    public int add(int n1, int n2) { // n1, n2 will store in stack
        return n1 + n2;
    }

    // public int add2(int n1, int n2, int n3) {
    //     return n1 + n2 + n3;
    // }

    public int add(int n1, int n2, int n3) { // n1, n2, n3 will store in stack
        return n1 + n2 + n3;
    }

    public double add(double n1, double n2, double n3) { // n1, n2 will store in stack
        return n1 + n2 + n3;
    }

    public void printNum() {
        System.out.println("num = " + num);
    }
}

public class Class1 {
    public static void main(String[] args) {
        int num1 =4;
        int num2 = 5; 
       
        Calculator calc = new Calculator();
        int result = calc.add(num1, num2);

        System.out.println(result);

        Calculator calc2 = new Calculator();
         
        int r1 = calc.add(3, 2);
        System.out.println(r1);
        
        calc.num = 8;

        System.out.println(calc.num);
        System.out.println(calc2.num);

    }
}



