package algorithm;

import org.junit.Test;

public class Algorithm {

    /**
     * 二分查找
     * 时间复杂度 O(log n)
     */
    public int binarySearch(int[] nums, int target) {
        // 形成闭区间[0,max-1],以免遗漏0或者max-1的元素
        int low = 0, high = nums.length-1, middle;
        while (low <= high) {
            middle = (high + low) >>> 1;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else if (nums[middle] == target){
                return middle;
            }
        }
        return -1;
    }


    @Test
    public void testBinarySearch() {

    }


}
