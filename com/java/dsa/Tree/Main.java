public class Main {
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        tree.insert(8);
        tree.insert(7);
        tree.insert(12);
        tree.insert(15);
        tree.insert(2);
        tree.insert(5);

        System.out.println("Printing Binary tree in order");
        tree.inorder();

        System.out.println("Printin Binary tree: pre order -");
        tree.preOrder();

        System.out.println("Printing Binary tree: post order -");
        tree.postOrder();
    }
}
