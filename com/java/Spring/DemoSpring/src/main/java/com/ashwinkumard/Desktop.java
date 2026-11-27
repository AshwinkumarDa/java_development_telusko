package com.ashwinkumard;

public class Desktop implements IComputer {
    private String name = "Desktop-iMac";

    public Desktop() {
        System.out.println("Desktop constructor");
    }

    public void compile() {
        System.out.println("Desktop compiled");
    }

    public String toString() {
        return "Desktop{" +
                "name='" + name + '\'' +
                '}';
    }
}
