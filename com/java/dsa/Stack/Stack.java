
public class Stack {
    final int SIZE = 5;
    int stack[] = new int[SIZE];
    int top = 0;

    public void push(int data) {
        if(top == SIZE) {
            System.out.println("Stack is full.");
            return;
        }

        stack[top] = data;
        top++;
    }

    public int pop() { 
        int data = 0;
        if(isEmpty()) {
            System.out.println("Stack is empty.");
            return data;
        }

        top--;
        data = stack[top];
        stack[top] = 0;
        return data;
    }


    public int peek() {
        return stack[top-1];
    }

    public int size() {
        return top;
    }

    public boolean isEmpty() {
        return top <= 0;
    }

    public void show() {
        for(int n: stack) {
            System.out.print(n + " ");
        }
        System.out.println();
    }


}
