public class Main {

    public static void main(String[] args) {
        int[] arr = {2, 1, -4, 4, 2, 1, -1};

        System.out.println(maxSubSum1(arr));
        System.out.println(maxSubSum2(arr));
        System.out.println(maxSubSum3(arr));
        System.out.println(maxSubSum4(arr));
    }

    /**
     * 单层for循环，依次遍历求出最大子序列和，使用临时变量thisSum，
     * 如果thisSum小于0时，赋值为0再重新参与计算
     */
    public static int maxSubSum4(int[] arr) {
        int maxSum = 0;
        int thisSum = 0;

        for (int i = 0; i < arr.length; i++) {
            thisSum += arr[i];
            if (thisSum > maxSum) {
                maxSum = thisSum;
            } else if (thisSum < 0) {
                thisSum = 0;
            }
        }

        return maxSum;
    }

    /**
     * 分治法求最大子序列和，getSubSum3Internal方法中，left须小于等于right。
     * 分治法是将数组分为左右2部分，先求左边部分的最大子序列和，再求右边的最大子序列和，最后求横跨左右两边的最大子序列和。
     * 其中最大的子序列和为整个数组的最大子序列和。
     */
    public static int maxSubSum3(int[] arr) {
        return getSubSum3Internal(arr, 0, arr.length - 1);
    }
    public static int getSubSum3Internal(int[] arr, int left, int right) {
        if (left == right) {
            return (arr[left] < 0 ? 0 : arr[left]);
        }

        int mid = (left + right) / 2;
        int sumLeft = getSubSum3Internal(arr, left, mid);
        int sumRight = getSubSum3Internal(arr, mid + 1, right);
        int maxSum = sumLeft > sumRight ? sumLeft : sumRight;

        // 从arr[mid]开始，包括arr[mid]，往左边计算最大子序列和
        int sumMidLeft = 0;
        int tmpSum = 0;
        for (int i = mid; i >= left; i--) {
            tmpSum += arr[i];
            if (tmpSum > sumMidLeft) {
                sumMidLeft = tmpSum;
            }
        }

        // 从arr[mid+1]开始，包括arr[mid+1]，往右边计算最大子序列和
        int sumMidRight = 0;
        tmpSum = 0;
        for (int i = mid + 1; i <= right; i++) {
            tmpSum += arr[i];
            if (tmpSum > sumMidRight) {
                sumMidRight = tmpSum;
            }
        }

        int sumMid = sumMidLeft + sumMidRight;
        return maxSum > sumMid ? maxSum : sumMid;
    }

    /**
     * 2层for循环，每次从i开始计算子序列
     */
    public static int maxSubSum2(int[] arr) {
        int maxSum = 0;

        for (int i = 0; i < arr.length; i++) {
            int thisSum = 0;

            for (int j = i; j < arr.length; j++) {
                thisSum += arr[j];

                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }

        return maxSum;
    }

    /**
     * 最简单的方式，3层for循环，每次计算一个子序列
     */
    public static int maxSubSum1(int[] arr) {
        int maxSum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                int thisSum = 0;

                for (int k = i; k <= j; k++) {
                    thisSum += arr[k];
                }
                if (thisSum > maxSum) {
                    maxSum = thisSum;
                }
            }
        }

        return maxSum;
    }

}
