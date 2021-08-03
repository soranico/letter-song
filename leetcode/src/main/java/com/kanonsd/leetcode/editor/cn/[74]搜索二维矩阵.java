//编写一个高效的算法来判断 m x n 矩阵中，是否存在一个目标值。该矩阵具有如下特性： 
//
// 
// 每行中的整数从左到右按升序排列。 
// 每行的第一个整数大于前一行的最后一个整数。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
//输出：false
// 
//
// 
//
// 提示： 
//
// 
// m == matrix.length 
// n == matrix[i].length 
// 1 <= m, n <= 100 
// -104 <= matrix[i][j], target <= 104 
// 
// Related Topics 数组 二分查找 矩阵 
// 👍 468 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
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

}
//leetcode submit region end(Prohibit modification and deletion)
