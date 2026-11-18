public class Queue {
    int queue[] = new int[5];

    int size;
    int front;
    int rear;

    public void enQueue(int data) {
        if(isFull()) {
            System.out.println("Queue is full");
            return;
        }
        //add element at last
        queue[rear] = data;
        rear = (rear+1) % 5;
        size = size + 1;
    }

    public int deQueue() {
        int data = queue[front];

        if(isEmpty()) {
            System.out.println("Queue is empty");
            return data;
        }
        
        front = (front + 1) % 5;
        size = size - 1;
        return data;
    }
    public void show() {
        System.out.print("Queue - ");
        for(int i = 0; i< size; i++) {
            System.out.print(queue[(front+i)%5] + " ");
        }
        System.out.println();

        System.out.println("Printing array elements - ");
        for(int n: queue) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public boolean isFull() {
        return  getSize() == 5;
    }

}