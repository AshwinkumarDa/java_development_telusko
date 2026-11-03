/**
 * 
 *  Addable
 * Lambda Expression only works with Functional Interface.
 */
@FunctionalInterface 
interface Addable {
    int add(int i, int j);
}


public class LambdaExpression2 {
    public static void main(String[] args) {
        Addable addObj = new Addable() {
            public int add(int i, int j) {
                return i + j;
            }
        };

        int result = addObj.add(5, 4);
        System.out.println("result = " + result); // prints `result = 9`

        // Using Lambda expression
        Addable addObjLambda = (int i, int j) -> {
            return i + j; 
        };

        result = addObjLambda.add(6, 5);
        System.out.println("result using Lambda expression = " + result);

        Addable shortenLambda = (i, j) -> i+j;
        result = shortenLambda.add(7, 8);
        System.out.println("result using Shorten Lambda Expression = " + result);
    }
}
