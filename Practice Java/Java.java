/*
this is used to code temorary code to test how things work 
 */

class john {
}

public class Java {
    public static void main(String[] args) {
        int[] arr = { 4, 1, 3, 6, 2, 9 };
        System.out.println(divBySum(arr, 5));
    }

    public static int divBySum(int[] arr, int divisor) {
        int sum = 0;
        for (int value : arr) {
            if (value % divisor == 0) {
                sum += value;
            }
        }
        return sum;
    }
}
