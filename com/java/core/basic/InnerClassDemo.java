
// Error when we make CarOuter static as `static class CarOuter`, `error: modifier static not allowed here`, we can make innter class static.

class CarOuter { 
    private String model;
    private int fuel;

    public CarOuter(String model, int fuel) {
        this.model = model;
        this.fuel = fuel;
    }
    
    // after compile, it creates CarOuter$EngineInner.class file
     class EngineInner {
        void start() {
            System.out.println("Start engine");
        }

    }

    // after compile compiler will create CarOuter$Manufacturer.class file
    // Static nested class: independent of any Car object
    static class Manufacturer {
        private String name;
        private String country;

        Manufacturer(String name, String country) {
            this.name = name;
            this.country = country;
        }

        void showInfo() {
            System.out.println("Manufacturer: " + name + " (" + country + ")");
        }
    }
    
}

public class InnerClassDemo {
    public static void main(String[] args) {
        /// Here we can't create instance of EngineInner directly as EngineInner engine = new EngineInner()
        // EngineInner engine = EngineInner(); //error: cannot find symbol
        CarOuter car = new CarOuter("Creta", 100);
        CarOuter.EngineInner engine = car.new EngineInner();
        engine.start();

        // Static Inner class
        CarOuter.Manufacturer manufacturer = new CarOuter.Manufacturer("Hyundai","India");
        manufacturer.showInfo();

    }
}
