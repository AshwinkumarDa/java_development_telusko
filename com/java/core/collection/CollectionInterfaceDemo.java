import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/// Collection -
/// 1. List - ArrayList, LinkedList
/// 2. Queue - Dequeue
/// 3. Set - HashSet, LinkedHashSet
/// 
/// ArrayList
/// Collection works with Objects not primitive types.

public class CollectionInterfaceDemo {
    public static void main(String[] args) {
        // below code will give warning because we haven't metioned which type of object is stored in intNums.
        // Collection intNums = new ArrayList();
        // collection does not support index values.

        Collection<Integer> intCollection = new ArrayList<Integer>(); // warning will go away, using Generics.
        intCollection.add(6);
        intCollection.add(5);
        intCollection.add(8);
        intCollection.add(2);
        // intNums.add("8"); // we will get compile time error when we use Collection<Integer> in above line 17.
        
        for(Object n: intCollection) {
            int num = (Integer)n;
            System.out.println(num * 2);
        }
        System.out.println();

        // 2. List supports index values
        List<Integer> intList = new ArrayList<Integer>();
        intList.add(6);
        intList.add(5);
        intList.add(8);
        intList.add(2);
        intList.add(6);
        intList.add(5);
        System.out.println("intList object at index 2 is: " + intList.get(2)); // get value at given index
        // find first index having value 5
        System.out.println("Index which has value 5 is at: " + intList.indexOf(5));

        for(Integer i: intList) {
            System.out.println(i);
        }

        // 3. List can contain duplicte items. We can use Set for unique values.
        /// Set does not have repeated values, 
        // It is not in sorted order
        // It does not have index value. we can't use get() API
        Set<Integer> intSet = new HashSet<Integer>();
        
        System.out.println("Set does not have repeated values, \nIt is not in sorted order\nIt does not have index value. we can't use get() API");
        System.out.println("\nAll elements in set: ");
        intSet.add(6);
        intSet.add(5);
        intSet.add(8);
        intSet.add(2);
        intSet.add(6);
        intSet.add(5);
        
        for (int n: intSet) {
            System.out.println(n);
        }

        // TreeeSet - it is a set but it is sorted set.
        Collection<Integer> intTreeSet = new TreeSet<Integer>();
        intTreeSet.add(62);
        intTreeSet.add(54);
        intTreeSet.add(82);
        intTreeSet.add(21);

        Iterator<Integer> values = intTreeSet.iterator();
        System.out.println("\nPrinting values in TreeSet - ");
        while (values.hasNext()) {
            System.out.println(values.next());
        }

        // Map - it is part of Collection API.
        /// if we have list of values like 56, 23, 67, 92. if we work with array list or list we need to work with indexes.
        /// sometimes it is better to have names. we can have key and value.
        /// 
        Map<String, Integer> studentsMap = new HashMap<String, Integer>();
        studentsMap.put("Navin", 56);
        studentsMap.put("Harsh", 23);
        studentsMap.put("Sushil", 67);
        studentsMap.put("Kiran", 92);
        studentsMap.put("Ashwin", 92);

        System.out.println("Updating value of Harsh to 45 from - "+ studentsMap.put("Harsh", 45));

        System.out.println("\nstudentsMap: "+ studentsMap);

        System.out.println("Get Marks of Harsh - " + studentsMap.get("Harsh"));

        System.out.println("Removing Ashwin: " + studentsMap.remove("Ashwin"));
        System.out.println("studentsMap = " + studentsMap);
        
        System.out.println("keyset = " + studentsMap.keySet());

        studentsMap.replace("Kiran", 80);
        
        System.out.println("studentsMap = " + studentsMap);
        
        System.out.println("all values: " + studentsMap.values());
    
        for(String key: studentsMap.keySet()) {
            System.out.println("Name: "+ key + ", Marks: " + studentsMap.get(key));
        }

        studentsMap.clear();
        System.out.println("studentsMap = " + studentsMap);

        /// HashTable both wroks same like HashMap.
        /// HashTable is synchronized. Thread safe.
        /// HashMap is not synchronized. Not thread safe.

    }
}
