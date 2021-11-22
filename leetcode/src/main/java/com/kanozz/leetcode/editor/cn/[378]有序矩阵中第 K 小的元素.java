//给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。 
//请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。 
//
// 
//
// 示例 1： 
//
// 
//输入：matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
//输出：13
//解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
// 
//
// 示例 2： 
//
// 
//输入：matrix = [[-5]], k = 1
//输出：-5
// 
//
// 
//
// 提示： 
//
// 
// n == matrix.length 
// n == matrix[i].length 
// 1 <= n <= 300 
// -109 <= matrix[i][j] <= 109 
// 题目数据 保证 matrix 中的所有行和列都按 非递减顺序 排列 
// 1 <= k <= n2 
// 
// Related Topics 数组 二分查找 矩阵 排序 堆（优先队列） 
// 👍 633 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int kthSmallest(int[][] matrix, int k) {
        int max = matrix.length;
        int[] nums = new int[max*max];
        for (int i = 0; i < max; i++) {
            System.arraycopy(matrix[i],0,nums,max*i,max);
        }

        // 快速排序  1...k-1 k ....

        return quickSortK(nums,0,nums.length-1,k);
    }

    private int quickSortK(int[] nums,int start,int end,int k){
        if (start > end){
            return -1;
        }
        int low = start,high = end,base = nums[low];
        while (low < high){
            while (low < high && nums[high] >= base){
                high--;
            }
            if (low < high){
                nums[low] = nums[high];
            }
            while (low < high && nums[low] <= base){
                low++;
            }
            if (low <high){
                nums[high] = nums[low];
            }
        }
        nums[low] = base;
        // 此时low 为基准,从start ..... low 之间有 low - start +1 个数
        if(low - start + 1 == k){
            return base;
        }
        else if(low - start +1 > k){

            return quickSortK(nums,start,low-1,k);
        }
        // 不足k个数,去除已经较小的的 low -start+1个
        else {
            return quickSortK(nums,low+1,end,k-low+start-1);
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
