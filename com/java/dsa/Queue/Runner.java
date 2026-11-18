public class Runner {
    public static void main(String[] args) {
        Queue queue = new Queue();
        queue.enQueue(5);
        queue.enQueue(2);
        queue.enQueue(7);
        queue.enQueue(3);
        System.out.println("Is Full: " + queue.isFull());
        queue.enQueue(9);
        System.out.println("Is Full: " + queue.isFull());
        

        queue.show();

    queue.deQueue();
    queue.deQueue();
        queue.show();

        queue.enQueue(19);
        queue.enQueue(15);

        System.out.println("Is Empty: " + queue.isEmpty());
        System.out.println("Is Full: " + queue.isFull());

        System.out.println("Size: "+ queue.getSize());
        queue.show();
    }
}
