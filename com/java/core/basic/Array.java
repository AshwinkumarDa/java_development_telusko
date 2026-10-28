// int i = 5;
// int j = 6;
// int k = 7;

// // save above all values in one variable.
// int nums[] = {5, 6, 7};

// int num1[] = new int[4]; // array of size 4 elements.

/// array has length property in java
///  
/// 
class Student {
    int rollno;
    String name;
    int marks;
}

public class Array {
    public static void main(String[] args) {
        int nums[] = { 3, 7, 2,4};
        
        System.out.println(nums[1]);
        System.out.println(nums[3]);

        int ints[] = new int[4];
        System.out.println(ints[1]); /// array of int , all elements intitialized default to 0.
        ints[0] = 4;
        ints[1] = 8;
        ints[2] = 3;
        ints[3] = 9;
    
        System.out.println(ints[1]); 

         System.out.println(); 
        for (int i = 0; i<4; i++) {
             System.out.println(ints[i]); 
        }

        // Multidimensional array
        int multi_nums[][] = new int[3][4];
        
        System.out.println();
        for(int i = 0; i< 3; i++) {
            for (int j = 0; j< 4; j++) {
                System.out.print(multi_nums[i][j]);
            }
            System.out.println();
        }


         System.out.println();
        for(int i = 0; i< 3; i++) {
            for (int j = 0; j< 4; j++) {
                multi_nums[i][j] = (int)(Math.random() * 100);
                System.out.print(multi_nums[i][j] + " ");
            }

            System.out.println();
        }

        System.out.println();

        // Enhanced for loop
        for (int n[]: multi_nums) {
            for (int m: n) {
                System.out.print(m + " ");
            }
            System.out.println();
        }


        // jagged array - columns value is different in multidimension array
        int jagged[][] = new int[3][];

        jagged[0] = new int[3];
        jagged[1] = new int[4];
        jagged[2] = new int[2];
        
        // set values using traditional for loop
        for(int r = 0; r< jagged.length; r++) {
            for(int c=0; c<jagged[r].length; c++) {
                jagged[r][c] = (int)(Math.random() * 100);
            }
        }

        System.out.println();
        // Print using Enhanced For Loop
        for(int row[]: jagged) {
            for(int column: row) {
                System.out.print(column + " ");
            }
            System.out.println();
        }

        int threeDimensionArray[][][] = new int[3][4][5]; // three dimensional array
        int firstElement = threeDimensionArray[0][0][1];
        System.out.println(firstElement);

        System.out.println("Students array ---");

        Student s1 = new Student();
        s1.rollno = 1;
        s1.name = "Navin";
        s1.marks = 88;
    
        Student s2 = new Student();
        s2.rollno = 2;
        s2.name = "Harsh";
        s2.marks = 67;

        Student s3 = new Student();
        s3.rollno = 3;
        s3.name = "Kiran";
        s3.marks = 97;
        
        Student students[] = new Student[3];
        students[0] = s1;
        students[1] = s2;
        students[2] = s3;

        for(int i=0; i< students.length; i++) {
            // System.out.println(students[i]); // it prints memory address of students instance elements.
            System.out.println(students[i].name + " : " + students[i].rollno + " : " + students[i].marks);
        }

        System.out.println("Using Enhanced ForLoop");
        for(Student stud: students) {
            System.out.println(stud.name + " : " + stud.rollno + " : " + stud.marks);
        }

        // Enhanced for loop
        for(int n: nums) {
            System.err.println(n);
        }

    }
}