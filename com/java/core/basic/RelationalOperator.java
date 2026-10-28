public class RelationalOperator {
    public static void main(String[] args) {
        int five = 5;
        int six = 6;
        int another_six = 6;

        boolean result = six < five;
        System.out.println("Result (six < five) = " + result);

        result = six > another_six;
        System.out.println("Result (six > another_six) = " + result);
         

        result = six >= another_six;
        System.out.println("Result (six >= another_six) = " + result);

        result = six <= another_six;
        System.out.println("Result (six <= another_six) = " + result);

        result = five != six;
        System.out.println("Result (five != six) = " + result);


        result = five == six;
        System.out.println("Result (five == six) = " + result);

        result = six == another_six;
        System.out.println("Result (six == another_six) = " + result);
    }
}
