class Computer {
    public void playMusic() { // returning nothing so use void.
        System.out.println("Playigg music...");
    }

    public String getMeAPen(int cost) { // returning String
        if (cost >= 10)
            return "Pen";
        else
            return "Nothing";
    }
}

public class Class2 {
    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.playMusic();

        String str = computer.getMeAPen(4);
        System.out.println(str);   


        str = computer.getMeAPen(10);
        System.out.println(str); 
    }
    
}
