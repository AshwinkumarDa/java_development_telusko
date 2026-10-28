
// Bydefault preference is given to local variable.
// `this` is a keywrod which represent current object.
// Constructor is a special method, 
// it has same name as a class name, 
// it never returns anything. 
// specify access as public. 
// It's name should be same as class name. 
// Everytime we create object it call constructor.

public class Constructor {
        public static void main(String args[]) {
        Human human = new Human();
        System.out.println(human.getName() + " " + human.getAge());
        human.setName("Navin");
        human.setAge(34);
        System.out.println(human.getName() + " " + human.getAge());

        Human anotherHuman = new Human(30, "Aswinkumar");
        System.out.println(anotherHuman.getName() + " " + anotherHuman.getAge());
        anotherHuman.setName("Navin");
        anotherHuman.setAge(34);
        System.out.println(anotherHuman.getName() + " " + anotherHuman.getAge());

        
    }
}

class Human {
    private int age;
    private String name;

    // Default constructor
    public Human() {
        System.out.println("In constructor");
        age = 12;
        name = "John";
    }

     // Paramterised constructor
    public Human(int age) {
        this.age = age;
    }

    // Paramterised constructor
    public Human(String name) {
        this.name = name;
    }

    // Paramterised constructor
    public Human(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }   
}
