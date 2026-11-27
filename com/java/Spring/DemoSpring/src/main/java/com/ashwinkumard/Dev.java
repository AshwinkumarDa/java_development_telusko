package com.ashwinkumard;

public class Dev {
    private IComputer IComputer;

    public IComputer getMyComputer() {
        return IComputer;
    }

    public void setMyComputer(IComputer IComputer) {
        this.IComputer = IComputer;
    }

    public Dev() {
        System.out.println("Dev constructor");
    }
    // private Laptop laptop;

//    public Laptop getLaptop() {
//        return laptop;
//    }
//
//    public void setLaptop(Laptop laptop) {
//        this.laptop = laptop;
//    }

//    private int salary;

//
//    // Setter Injection
//    public int getAge() {
//        return age;
//    }

//        private int age = 8;
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public int getSalary() {
//        return salary;
//    }
//
//    public void setSalary(int salary) {
//        this.salary = salary;
//    }



    // Constructor Injection

//
//    public Dev(int age) {
//        this.age = age;
//    }
//
//    public Dev(int age, int salary) {
//        this.age = age;
//        this.salary = salary;
//    }

    public void build() {
        System.out.println("Working on Awesome Project");
        // laptop.compile();
     }

//    public String toString() {
//        return "Dev{" +
//                "laptop=" + laptop +
//                '}';
//    }


    public String toString() {
        return "Dev{" +
                "computer=" + IComputer +
                '}';
    }
}
