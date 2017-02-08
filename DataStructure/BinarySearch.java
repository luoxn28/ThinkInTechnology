public class Main {

    public static void main(String[] args) {
        int[] arr = {1, 3, 12, 34, 67, 80, 100};

        int result = binarySearch(arr, 34);
        if (result == -1) {
            System.out.println("not found");
        } else {
            System.out.println("find it, index pos is " + result);
        }
    }

    /**
     * 二分查找
     */
    public static int binarySearch(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (x == arr[mid]) {
                return mid;
            } else if (x < arr[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        // not found
        return -1;
    }
}