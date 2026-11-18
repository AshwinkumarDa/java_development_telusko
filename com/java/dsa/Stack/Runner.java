public class Runner {
	public static void main(String args[]) {
		//Stack nums = new Stack();
		DStack nums = new DStack();

		System.out.println("isEmpty: "+ nums.isEmpty());
		nums.push(15);
		nums.show();
		
		
		nums.push(8);
		System.out.println("peek: "+ nums.peek());
		nums.show();
		nums.push(10);
		nums.show();
		nums.push(7);
		nums.show();
		nums.push(17);
		nums.show();
		nums.push(77); // this will not get added, it gives error "Stack is full."

		nums.show();
		System.out.println("size: "+ nums.size());

		System.out.println("pop - " + nums.pop());
		nums.show();

		System.out.println("isEmpty: "+ nums.isEmpty());

		System.out.println("Popping items - ");
		nums.pop();
		nums.show();
		nums.pop();
		nums.show();
		nums.pop();
		nums.show();
		nums.pop();
		nums.show();
		nums.pop();
		nums.show();
	}
    
}
