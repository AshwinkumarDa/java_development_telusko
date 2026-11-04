// package com.java.core.newFeatures;

class Alien {
    private final int id;
    private final String name;

    public Alien(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Alien other = (Alien) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alien {id=" + id + ", name=" + name + "}";
    }
}


// Data Carrier class 
record AlienRecord (int id, String name) { } // we get implicit constructor AlientRecord(int id, String name)
// we can create one and call other constructor in it. (canonical constructor 
// these are immutable, we can't change its value.
// this class is created to only carry data.
// all variables are private and final (imutable data)
// these class cannot extend any other class
// record class can extend record class
// we can implement Interfaces as many as we want.
// we can define multiple methods if we want. 
// we can't create instance variable.

record PersonRecord (int id, String name) {
    public PersonRecord(int id, String name) {
        if(id == 0) 
            throw new IllegalArgumentException("id cannot be zero"); // add custom check 

        this.id = id;
        this.name = name;
    }
 }

public class RecordClassesDemo {
    // Data Carrier Classes
    public static void main(String[] args) {
        Alien a1 = new Alien(1 , "Navin");
        System.out.println(a1);

        Alien a2 = new Alien(1, "Navin");
        System.out.println(a2);

        System.out.println(a1.equals(a2));


        System.out.println();
        AlienRecord alienRecord1 = new AlienRecord(1 , "Navin");
        System.out.println(a1);

        AlienRecord alienRecord2 = new AlienRecord(1, "Navin");
        System.out.println(alienRecord2);

        AlienRecord alienRecord3 = new AlienRecord(0, null);

        System.out.println(alienRecord1.equals(alienRecord2));
        
        System.out.println("alienRecord1 name = " + alienRecord1.name()); // accessing `record` isntance variables.


        // PersonRecord person1 = new PersonRecord(0, "Ashwin"); // we get runtime error - java.lang.IllegalArgumentException: id cannot be zero
    }
}
