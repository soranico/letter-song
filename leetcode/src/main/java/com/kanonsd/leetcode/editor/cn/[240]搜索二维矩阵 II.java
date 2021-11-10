//编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性： 
//
// 
// 每行的元素从左到右升序排列。 
// 每列的元素从上到下升序排列。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21
//,23,26,30]], target = 5
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21
//,23,26,30]], target = 20
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
// 1 <= n, m <= 300 
// -109 <= matix[i][j] <= 109 
// 每行的所有元素从左到右升序排列 
// 每列的所有元素从上到下升序排列 
// -109 <= target <= 109 
// 
// Related Topics 数组 二分查找 分治 矩阵 
// 👍 674 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int row = 0, col = matrix[0].length-1,rowMax = matrix.length;
        /**
         * 从右上角[0,colMax-1] 开始
         * 1.如果[row,col] > target 则左移 col  因为从上到下递增 下移row 会导致值变大
         * 2.如果[row,col] < target 则下移 row
         * 此时不移动 col 是因为 如果col = colMax 是这个一维数组最大值 此时需要找比它大的
         * 如果 col < colMax 说明[row,colMax] > target 是从 colMax - col移动过来的
         * 3.
         */
        while (row < rowMax){
            while (col >=0 && matrix[row][col] > target){
                col--;
            }
            if (col < 0){
                return false;
            }
            if (matrix[row][col] == target){
                return true;
            }

            if (matrix[row][col] < target){
                row++;
            }
        }


        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
