// Bydefault preference is given to local variable.
// `this` is a keywrod which represent current object.

class Human {
    private int age;
    private String name;

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

public class Encapsulation {
    public static void main(String args[]) {
        Human human = new Human();
        System.out.println(human.getName() + " " + human.getAge());
        human.setName("Navin");
        human.setAge(34);
    
        System.out.println(human.getName() + " " + human.getAge());

        
    }
}
