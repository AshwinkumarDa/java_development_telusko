import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student {
    int age;
    String name;

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String toString() {
        return "Student [age = " + age + ", name = " + name + "]";
    }
}


class ComparableStudent implements Comparable<ComparableStudent> {
    int age;
    String name;

    public ComparableStudent(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String toString() {
        return "ComparableStudent [age = " + age + ", name = " + name + "]";
    }

    public int compareTo(ComparableStudent that) {
        if(this.age > that.age)
            return 1;
        return -1;
    }
    
}

public class SortingDemo {
    public static void main(String[] args) {
        // from Java version 1.7 right hand side type mention is optional.
        List<Integer> nums = new ArrayList<>();

        nums.add(4);
        nums.add(3);
        nums.add(7);
        nums.add(9);

        // collection class have methods like sort.
        System.out.println("nums are " + nums);
        Collections.sort(nums);
        System.out.println("after sorting nums are " + nums);
        System.out.println();


        // sort using Comparator - Custom sort
        // compare 2 consecative values in array and sort
        List<Integer> anotherNums = new ArrayList<>();
        
        anotherNums.add(43);
        anotherNums.add(31);
        anotherNums.add(72);
        anotherNums.add(29);

        // Sort Unit place digits.
        Comparator<Integer> comparator = new Comparator<Integer>() {
            public int compare(Integer i, Integer j) {
                if (i%10 > j%10)
                    return 1;
                 return  -1;
            }
        };

        System.out.println("Original anotherArray elements - " + anotherNums);
        Collections.sort(anotherNums, comparator);
        System.out.println("Customer Sorting Unit Place Digits in Ascending order using Comparator: "+ anotherNums);

        // sort array of string based on its length.
        List<String> names = new ArrayList<>();
        names.add("Alexander");
        names.add("John");
        names.add("Sophia");
        names.add("Mia");
        names.add("Al");
        names.add("Christopher");
        names.add("Emma");
        names.add("Michael");
        names.add("Isabella");
        names.add("David");

        Comparator<String> namesComparator = new Comparator<String>() {
            public int compare(String first, String second) {
                if (first.length() > second.length()) 
                    return 1;
                return -1;
            }
        };

        System.out.println("Names Before Sorting: " + names);
        Collections.sort(names, namesComparator);
        System.out.println("Names After Sorting: " + names);
    
        List<Student> students = new ArrayList<>();
        students.add(new Student(21, "Navin"));
        students.add(new Student(12, "John"));
        students.add(new Student(18, "Parul"));
        students.add(new Student(20, "Kiran"));

        System.out.println("\nStudents before sorting - ");
        for(Student s: students) {
            System.out.println(s);
        }

        // Comparator<Student> studentComparator = new Comparator<Student>() {
        //     public int compare(Student i, Student j) {
        //         if(i.age > j.age)
        //             return 1;
        //         return -1;
        //     }
        // };

        // Shorthand lambda expression of above commented code.
        Comparator<Student> studentComparator = (i, j) -> i.age > j.age ? 1 : -1;

        Collections.sort(students, studentComparator);
        System.out.println("\nStudents after sorting - ");
        for(Student s: students) {
            System.out.println(s);
        }

        List<ComparableStudent> comparableStudents = new ArrayList<>();
        comparableStudents.add(new ComparableStudent(30, "Mohan"));
        comparableStudents.add(new ComparableStudent(20, "Sagar"));
        comparableStudents.add(new ComparableStudent(12, "Madan"));
        comparableStudents.add(new ComparableStudent(28, "Ashwin"));

        System.out.println("ComparableStudnent before sorting - ");
        for(ComparableStudent s: comparableStudents) {
            System.out.println(s);
        }


        Collections.sort(comparableStudents);

        // No need to use Comparator here because ComparableStudent implements Comparable.
        System.out.println("ComparableStudnent after sorting - ");
        for(ComparableStudent s: comparableStudents) {
            System.out.println(s);
        }

    }


}
