//输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，则最小的4个数字是1、2、3、4。 
//
// 
//
// 示例 1： 
//
// 输入：arr = [3,2,1], k = 2
//输出：[1,2] 或者 [2,1]
// 
//
// 示例 2： 
//
// 输入：arr = [0,1,2,1], k = 1
//输出：[0] 
//
// 
//
// 限制： 
//
// 
// 0 <= k <= arr.length <= 10000 
// 0 <= arr[i] <= 10000 
// 
// Related Topics 数组 分治 快速选择 排序 堆（优先队列） 👍 323 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] getLeastNumbers(int[] arr, int k) {
        if (k == 0){
            return new int[0];
        }
        int[] min = new int[k];
        quickMinK(arr.length - 1, 0, arr, k);
        System.arraycopy(arr, 0, min, 0, k);
        return min;
    }


    private void quickMinK(int high, int low, int[] arr, int k) {
        if (low > high) {
            return;
        }
        int left = high, right = low, base = arr[low];
        while (right < left) {
            while (right < left && arr[left] >= base) {
                left--;
            }
            if (right < left) {
                arr[right] = arr[left];
            }
            while (right < left && arr[right] <= base) {
                right++;
            }
            if (right < left) {
                arr[left] = arr[right];
            }
        }
        // low . . right 之间有 right - low +1 个数
        arr[right] = base;
        int width = right - low + 1;
        if (width == k) {
            return;
        } else if (width > k) {
            quickMinK(right, low, arr, k);
        } else {
            // 此时right指向的位置肯定包含在k个数内,右移低位指针
            quickMinK(high, right + 1, arr, k - width);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
