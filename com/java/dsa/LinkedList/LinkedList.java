package LinkedList;

class Node {
    int data;
    Node next;
}

class List {
    Node head;

    public void insert(int data) {
        Node node = new Node();
        node.data = data;
        node.next = null;

        if(head == null) { 
            head = node;
        } else {
            Node n = head;
            while(n.next != null) {
                n = n.next;
            }
            n.next = node;
        }

    }

    public void insertAtStart(int data) {
        Node node = new Node();
        node.data = data;
        
        node.next = head;
        head = node;
    }

    public void insertAt(int index, int data) {

        if(index == 0) {
            insertAtStart(data);
            return; 
        }

        Node newNode = new Node();
        newNode.data = data;
        newNode.next = null;

        Node n = head;
        for(int i=0; i < index-1; i++) {
            n = n.next;
        }

        newNode.next = n.next; 
        n.next = newNode;
    }

    public int deleteAt(int index) {
        if(index == 0) {
            int data = head.data;
            head = head.next;
            return data;
        } else {
            Node n = head;
            Node temp = null;

            for(int i = 0; i<index-1; i++) {
                n = n.next;
            }
            temp = n.next;
            n.next = temp.next;
            int data = temp.data;
            temp = null;
            return data;
        }
    }

    public void show() {
        Node node = head;
        while(node.next != null) {
            System.out.print(node.data + " ");
            node = node.next;
        }
        // print last element value.
        System.out.print(node.data + " ");
        System.out.println();
    }
}
public class LinkedList {
    public static void main(String[] args) {
        List list = new List();
        list.insert(18);
        list.insert(45);
        list.insert(12);
        list.show();
        list.insertAtStart(25);
        list.show();

        list.insertAt(2, 55);
        list.show();

        list.insertAt(0, 56);
        list.show();

        int deletedValueAtIndex2 = list.deleteAt(2);
        System.out.println("deletedValueAtIndex2 = " + deletedValueAtIndex2);
        list.show();

        int deleteFirstElement = list.deleteAt(0);
        System.out.println("deleteFirstElement = " + deleteFirstElement);
        list.show();
    }
}
