package com.kanozz.algorithm;

import org.junit.Test;

public class Algorithm {

    /**
     * 二分查找
     * 时间复杂度 O(log n)
     */
    public int binarySearch(int[] nums, int target) {
        // 形成闭区间[0,max-1],以免遗漏0或者max-1的元素
        int low = 0, high = nums.length - 1, middle;
        while (low <= high) {
            middle = (high + low) >>> 1;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else if (nums[middle] == target) {
                return middle;
            }
        }
        return -1;
    }


    @Test
    public void testBinarySearch() {

    }


    public void quickSort(int[] nums, int start, int end) {
        if (start > end) {
            return;
        }
        int low = start, high = end, base = nums[low];
        while (low < high) {
            // 从右找一个比基准小的
            while (low < high && nums[high] >= base) {
                high--;
            }
            if (low < high) {
                nums[low] = nums[high];
            }
            while (low < high && nums[low] <= base) {
                low++;
            }
            if (low < high) {
                nums[high] = nums[low];
            }
        }
        nums[low] = base;
        quickSort(nums, start, low - 1);
        quickSort(nums, low + 1, end);

    }

    @Test
    public void testQuickSort() {
        int[] nums = new int[]{
                5, 6, 1, 8, 9, 0
        };
        quickSort(nums, 0, nums.length - 1);
    }


}
