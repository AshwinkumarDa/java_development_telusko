// package com.java.core.polymorphisms;

class Laptop {
    String model;
    int price;

    // overloading toString() method of Object class
    // if we comment below method then it prints something like Laptop@15db9742 from Object class instance method.
    // public String toString() {
    //     return "[model: " + model + ", price: " + price +"]";
    // }

    // Using IDE feature to create toString() method
    @Override
    public String toString() {
        return "Laptop [model=" + model + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((model == null) ? 0 : model.hashCode());
        result = prime * result + price;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Laptop other = (Laptop) obj;
        if (model == null) {
            if (other.model != null)
                return false;
        } else if (!model.equals(other.model))
            return false;
        if (price != other.price)
            return false;
        return true;
    }

    // public boolean equals(Laptop that) {
    //     return  this.model.equals(that.model) && this.price == that.price;
    // }

    
}

public class ObjectClassDemo {
    public static void main(String[] args) {
        Laptop lenovo = new Laptop();
        lenovo.model = "Lenovo Yoga";
        lenovo.price = 1000;

        System.out.println(lenovo); // it prints Laptop@15db9742
        System.out.println(lenovo.toString());


         Laptop lenovo2 = new Laptop();
        lenovo2.model = "Lenovo Yoga";
        lenovo2.price = 1000;

        boolean result = lenovo == lenovo2;
        System.out.println("result = " + result); // false

        result = lenovo.equals(lenovo2); // it is coming from Object class if we don't implement
        System.out.println("result (equals) = " + result); // false // true if we implemented (overridded)

        lenovo2.model = "Lenovo Yoga2";
        result = lenovo.equals(lenovo2); 
        System.out.println("result (equals) yoga2 = " + result); // false
        
    }
}
