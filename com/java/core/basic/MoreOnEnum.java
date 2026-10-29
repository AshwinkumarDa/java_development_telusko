enum Laptop {
    // Here the constructors are private because we are creating the objects in this same class itself.
    MacBook(2000), XPS(2200), Surface, ThinkPad(1800);

    private int price;

    // private constructor
    private Laptop() { //Surface enum case need to implement this default constructor to set value.
        price = 500;
        System.out.println("Initializing: "+ this.name() + "with default price: 500");
    }

    // private constructor
    private Laptop(int price) {
        this.price = price;
        System.out.println("Initializing: "+ this.name() + "with price: " + price);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

public class MoreOnEnum {
    public static void main(String[] args) {
        Laptop macbook = Laptop.MacBook;
        System.out.println(macbook);

        System.out.println("Price of macbook is " + macbook.getPrice());

        System.out.println("Updating price of macbook to 5000");
        macbook.setPrice(5000);

        System.out.println();
        System.out.println("Get all laptops");
        
        for(Laptop laptop: Laptop.values()) {
            System.out.println(laptop + ": "+ laptop.getPrice());
        }
    }
}
