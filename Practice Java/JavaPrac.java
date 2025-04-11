
/*
this is used to code temorary code to test how things work 
 */

import java.util.Arrays;

public class JavaPrac {
    public static void main(String[] args) {
        int[] arr = { 5, 10, 2, 1, 12 };
        insertionSort(arr);
    }

    public static void insertionSort(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int k = i + 1; k < arr.length; k++) {
                if (arr[k] < arr[minIndex]) {
                    minIndex = k;
                    count++;
                }
            }
            if (i != minIndex) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(count);
    }
}
