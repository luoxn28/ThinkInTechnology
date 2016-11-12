
import java.util.Arrays;
import java.util.Random;

/**
 * Sort class
 */
public class Sort {

    static int[] getArray(int length) {
        int[] array = new int[length];
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100);
        }

        return array;
    }

    public static void main(String[] args) {
        int[] array = getArray(10);
        int[] sorts = Arrays.copyOf(array, array.length);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();

        bubbleSort(sorts);
        System.out.print("bubble sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();

        sorts = Arrays.copyOf(array, array.length);
        selectSort(sorts);
        System.out.print("select sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();

        sorts = Arrays.copyOf(array, array.length);
        insertSort(sorts);
        System.out.print("insert sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();

        sorts = Arrays.copyOf(array, array.length);
        quickSort(sorts);
        System.out.print("quick  sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();

        sorts = Arrays.copyOf(array, array.length);
        mergeSort(sorts);
        System.out.print("merge  sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();

        sorts = Arrays.copyOf(array, array.length);
        heapSort(sorts);
        System.out.print("heap   sort: ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(sorts[i] + " ");
        }
        System.out.println();
    }

    /**
     * heap sort.
     */
    static void sink(int[] array, int maxIndex, int k) {
        while (2 * k <= maxIndex) {
            int child = 2 * k;
            if ((2 * k < maxIndex) && (array[child] < array[child + 1])) {
                child++;
            }

            if (array[k] >= array[child]) {
                break;
            }

            swap(array, k, child);
            k = child;
        }
    }
    static void heapSort(int[] array) {
        for (int i = array.length / 2; i >= 0; i--) {
            sink(array, array.length - 1, i);
        }

        int maxIndex = array.length - 1;
        while (maxIndex > 0) {
            swap(array, 0, maxIndex--);
            sink(array, maxIndex, 0);
        }
    }

    /**
     * merge sort.
     */
    static void merge(int[] array, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int k = left;
        int[] tmpArray = new int[array.length];

        while ((i <= mid) && (j <= right)) {
            if (array[j] < array[i]) {
                tmpArray[k++] = array[j++];
            }
            else {
                tmpArray[k++] = array[i++];
            }
        }
        while (i <= mid) {
            tmpArray[k++] = array[i++];
        }
        while (j <= right) {
            tmpArray[k++] = array[j++];
        }

        /* back fill the array data */
        for (int t = left; t <= right; t++) {
            array[t] = tmpArray[t];
        }
    }
    static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }
    static void mergeSort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    /**
     * quit sort.
     */
    static int partion(int[] array, int left, int right) {
        int i, j;
        int tmp = array[right];

        for (i = j = left; i < right; i++) {
            if (array[i] <= tmp) {
                swap(array, i, j++);
            }
        }
        swap(array, j, right);

        return j;
    }
    static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = partion(array, left, right);
            quickSort(array, left, mid - 1);
            quickSort(array, mid + 1, right);
        }
    }
    static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * insert sort.
     */
    static void insertSort(int[] array) {
        int length = array.length;

        for (int i = 1, j; i < length; i++) {
            for (j = i; (j > 0) && (array[j] < array[j-1]); j--) {
                swap(array, j, j - 1);
            }
        }
    }

    /**
     * bubble sort.
     * A simple sort function.
     */
    static void bubbleSort(int[] array) {
        int length = array.length;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (array[j] > array[j+1]) {
                    swap(array, j, j+1);
                }
            }
        }
    }

    /**
     * select sort.
     */
    static void selectSort(int[] array) {
        int length = array.length;

        for (int i = 0; i < length; i++) {
            int min = i;
            for (int j = i + 1; j < length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(array, i, min);
            }
        }
    }

    /**
     * swap two element in array.
     */
    static void swap(int[] array, int i, int j) {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

}
