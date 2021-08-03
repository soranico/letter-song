package com.kanonsd.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ArrayOperation {


    public void rotate(int[][] matrix) {

        int val, x = 0, y = 0, n = matrix.length - 1;
        // first
//        val = matrix[0][0];
//        matrix[0][0] = matrix[n][0];
//        matrix[n][0] = matrix[n][n];
//        matrix[n][n] = matrix[0][n];
//        matrix[0][n] = val;
//        // second
//        val = matrix[0][1];
//        matrix[0][1] = matrix[n-1][0];
//        matrix[n-1][0] = matrix[n][n-1];
//        matrix[n][n-1] = matrix[n-1][n];
//        matrix[0][n-1] = val;
//
//        // x,y
//        val = matrix[x][y] ;
//        matrix[x][y] = matrix[n-y][x];
//        matrix[n-y][x] = matrix[n-x][n-y];
//        matrix[n-x][n-y] = matrix[n-y][n-x];
//        matrix[n-y][n-x] = val;
        // [1,2,3],[4,5,6],[7,8,9]
        // [7,4,1],[8,5,2],[9,6,3]

        for (x = 0; x <= n; x++) {
            for (y = 0; y <= n - x; y++) {
                val = matrix[x][y];
                matrix[x][y] = matrix[n - x][y];
                matrix[n - x][y] = matrix[n - y][n - y];
                matrix[n - y][n - y] = matrix[x][n - y];
                matrix[x][n - y] = val;
            }

        }


    }


    // 每行中的整数从左到右按升序排列。
    // 每行的第一个整数大于前一行的最后一个整数。
    public boolean searchMatrix(int[][] matrix, int target) {
        int  YLow = 0, YHigh = matrix.length-1, x = matrix[0].length-1 ;
        while (YLow <= YHigh) {
            int YMiddle = (YHigh + YLow) >>> 1;
            // 在某行范围内
            if (matrix[YMiddle][0] <= target && matrix[YMiddle][x] >= target) {
                return binarySearch(matrix[YMiddle], target) != -1;
            }
            // 说明比这这个小
            else if (matrix[YMiddle][0] > target) {
                YHigh = YMiddle - 1;
            } else if (matrix[YMiddle][x] < target) {
                YLow = YMiddle + 1;
            }
        }
        return false;

    }

    private int binarySearch(int[] nums, int target) {
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
    public void testSearchMatrix() {
        log.info("searchMatrix = {}", searchMatrix(new int[][]{
//                {1, 3, 5, 7},
//                {10, 11, 16, 20},
//                {23, 30, 34, 60}
                {1,3}

        }, 3));
    }




    @Test
    public void testMatrix() {
        rotate(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        });
    }


    public int search(int[] nums, int target) {
        int frequency = 0,low = 0,high = nums.length-1,middle;
        while (low<=high){
            middle = (low + high)>>>1;
            if (nums[middle]>target){
                high = middle - 1;
            }else if (nums[middle] < target){
                low = middle + 1;
            }else {
                frequency += binaryScope(nums,low,middle-1,target,true)+
                        binaryScope(nums,middle+1,high,target,false);
                frequency++;
                break;
            }
        }
        return frequency;
    }

    private int binaryScope(int[] nums,int low,int high,int target,boolean pre){
        while (low<=high){
            int middle = (high+low)>>>1;
            if (nums[middle]==target ){
                if (pre){
                   return high-middle+1 + binaryScope(nums,low,middle-1,target,true);
                }
                return middle - low +1 + binaryScope(nums,middle+1,high,target,false);
            }
            // 只可能比target小
            else if (pre){
                low = middle+1;
            }
            // 只可能比target大
            else {
                high = middle-1;
            }
        }
        return 0;
    }

    @Test
    public void testSearch(){
        log.info("search = {}",search(new int[]{
//                1,2,3,4,5,5,5,5,5,6,7,8,9
                5,7,7,8,8,10
        },10));
    }





    public int findMin(int[] nums) {
        // 旋转 1 - n次此时存在两个逻辑递增数组 [n-1,n-2,...m] [0,1,2,...,m-1]
        // 最小值在两个逻辑数组的交界处即第二个逻辑数组第一个元素
        int high = nums.length-1,low = 0,middle;
        while (low < high){
            middle = (low+high)>>>1;
            // 此时表明middle在第一个逻辑数组,低指针后移可能移到第二个逻辑数组内
            if (nums[middle] > nums[high]){
                low = middle+1;
            }
            // 此时表明middle在第二个逻辑数组内,此时高指针不能移到middle-1
            // 可能middle就是第二个逻辑数组第一个元素即最小值
            else if (nums[middle] < nums[low]){
                high = middle;
            }
            // middle可能在第一个数组(需要移动low),也可能在第二个数组(需要移动high)
            else if (nums[middle] == nums[high]){
                high = high -1;
            }
            // 此时在第二个数组内,则low指针为最小值
            // 对于low指针移动而言是middle +1 且只有middle在第一个数组内才会变动
            // 因此low只可能从第一个数组移到第二个数组的第一个元素
            else if (nums[middle] < nums[high]){
                return nums[low];
            }
        }
        return nums[low];
    }

    @Test
    public void testFindMin(){
        log.info("findMin = {}",findMin(new int[]{
//                4,5,6,7,0,1,4
//                3,3,3,0,1
//                1,3,5
                6,5,5
//                3,3,1,3,3
        }));

    }

    /**
     * //给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * //请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     * //输入：matrix = [ [1,5,9],
     *                  [10,11,13],
     *                  [12,13,15]], k = 8
     * //输出：13
     * //解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {








        return -1;
    }


}
