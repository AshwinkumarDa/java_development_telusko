import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Read notes: ./java_method_references.md
 * 
 * MethodRefDemo
 */

/// 1. Method Reference
//  2. Constructor Reference

class Student {
    private String name;
    private int age;
    
    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}
public class MethodRefDemo {
    
    public static void main(String[] args) {
        // Using Lambda Expression
        // List<String> names = Arrays.asList("Navin", "Harsh", "John");

        // List<String> upperCasedNames = names.stream()
        //                                     .map(n -> n.toUpperCase())
        //                                     .toList();
        // System.out.println("Names - " + names);
        // System.out.println("Upper cased names - " + upperCasedNames);


        // 2. Using Method Reference -
        List<String> names = Arrays.asList("Navin", "Harsh", "John");

        List<String> upperCasedNames = names.stream()
                                            .map(String:: toUpperCase)
                                            .toList();

        System.out.println("Names - " + names);
        System.out.println("Upper cased names - " + upperCasedNames);
        System.out.println("\nPrinting each line using Method Reference - ");
        upperCasedNames.forEach(System.out:: println);


        /// Constructor Reference.
        List<Student> students = new ArrayList<>();
        
        // for(String name: names) {
        //     students.add(new Student(name));
        // }
        // System.out.println(names);

        // Using Stream
        students = names.stream()
                        .map(name -> new Student(name))
                        .toList();
        System.out.println(students);

        // Constructor method - 
        students = names.stream()
                        .map(Student:: new)
                        .toList();
        System.out.println(students);
    }
}
