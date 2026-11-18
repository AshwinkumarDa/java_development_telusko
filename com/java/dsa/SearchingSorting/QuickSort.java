package SearchingSorting;
public class QuickSort {

    // 1. divide and conquer
    // 2. recursion
    // 3. Pivot
    // 4. Tree

    //1. Find Pivot point to make partition. Pivot point that which is at the right element. randomly pick the pivot value. take last value.

    public static void main(String[] args) {
        int arr[] = { 5,6,2,3,1,8,4};

        System.out.println("Before sorting -");
        for(int n: arr) {
            System.out.print(n + " ");
        }
        System.out.println();
        
        quickSort(arr, 0, arr.length-1);
        
        System.out.println("After sorting -");
        for(int n: arr) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    public static void  quickSort(int[] arr, int low, int high) {
        if(low<high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi-1);
            quickSort(arr, pi+1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for(int j=low; j<high; j++ ) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }



    
}
