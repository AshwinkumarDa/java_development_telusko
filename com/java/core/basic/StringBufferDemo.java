public class StringBufferDemo {
    public static void main(String a[]) {
        // StringBuffer is mutable string instance type.
        // it allocates more space, if we update to more than allocated space then it will reallocate to new memory location.
        // StringBuffer is thread safe but StringBuilder is not thread safe.
        
        StringBuffer sb = new StringBuffer("Navin");
        System.out.println(sb.capacity());
        System.out.println(sb.length());

        sb.append(" Reddy"); 
        System.out.println(sb);

        String str = sb.toString();
        System.out.println(str);

        sb.deleteCharAt(2);
        System.out.println(sb);

        sb.insert(6, "Java");
        System.out.println(sb);

        System.out.println(sb.capacity());
        sb.setLength(30);
        System.out.println(sb.capacity());
        sb.ensureCapacity(100);
        System.out.println(sb.capacity());



    }
}
