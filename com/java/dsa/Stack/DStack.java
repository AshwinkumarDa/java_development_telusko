
public class DStack {
    int SIZE = 2;
    int stack[] = new int[SIZE];
    int top = 0;

    public void push(int data) {
        if(size() == SIZE) {
            expand();
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
        shrink();
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

    private void expand() {
        int length = SIZE;
        int newStack[] = new int[SIZE * 2];
        System.arraycopy(stack, 0, newStack, 0, length);
        stack = newStack;
        SIZE *= 2;
    }

    private void shrink() {
        int length = size();
        if(length<=(SIZE/2)/2) {
            SIZE = SIZE/2;
        }

        int newStack[] = new int[SIZE];
        System.arraycopy(stack, 0, newStack, 0, length);
        stack = newStack;
    }

}
