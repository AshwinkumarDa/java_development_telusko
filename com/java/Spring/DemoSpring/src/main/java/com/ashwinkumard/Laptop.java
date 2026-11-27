package com.ashwinkumard;

public class Laptop implements IComputer {
    private String name = "Laptop-Apple MacBook Pro M1";

    public Laptop() {
        System.out.println("Laptop constructor");
    }

    public void compile() {
        System.out.println("Laptop compiled");
    }

    @Override
    public String toString() {
        return "Laptop{" +
                "name='" + name + '\'' +
                '}';
    }
}
