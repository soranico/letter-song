//给你一个整数数组 nums ，数组中共有 n 个整数。132 模式的子序列 由三个整数 nums[i]、nums[j] 和 nums[k] 组成，并同时满足
//：i < j < k 和 nums[i] < nums[k] < nums[j] 。 
//
// 如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3,4]
//输出：false
//解释：序列中不存在 132 模式的子序列。
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,1,4,2]
//输出：true
//解释：序列中有 1 个 132 模式的子序列： [1, 4, 2] 。
// 
//
// 示例 3： 
//
// 
//输入：nums = [-1,3,2,0]
//输出：true
//解释：序列中有 3 个 132 模式的的子序列：[-1, 3, 2]、[-1, 3, 0] 和 [-1, 2, 0] 。
// 
//
// 
//
// 提示： 
//
// 
// n == nums.length 
// 1 <= n <= 2 * 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// 
// Related Topics 栈 数组 二分查找 有序集合 单调栈 👍 609 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public boolean find132pattern(int[] nums) {
        if (nums.length < 3){
            return false;
        }
        /**
         *
         * 对于索引 i j k (i < j < k) 此时需要
         * nums[i] < nums[k] < nums[j]
         * 只需要判断是不是存在这种情况,那么就仅需要
         * (i,k) 之间存在一个  nums[j] > nums[k]
         * 这个 nums[k] 是最大的一个 因为这样可以扩大
         * nums[k] > nums[i] 的范围
         *
         */
        ArrayDeque<Integer> numsKStack = new ArrayDeque<>();
        int maxNumsK = Integer.MIN_VALUE, j;
        numsKStack.push(Integer.MIN_VALUE);
        for (int i = nums.length-1; i >=0 ; i--) {
            /**
             * 当前 nums[i] < nums[k]
             * 此时满足 132
             * 因为 maxNumsK 是从单调栈中来的
             */
            if (nums[i] < maxNumsK){
                return true;
            }
            /**
             * 更新单调栈和当前最大的numsK
             */
            j = i;
            if (numsKStack.size() > 0 && nums[j] > numsKStack.peek()){
                /**
                 * 此时满足 nums[j] > nums[k] && j < k
                 * 同时更新最大的 nums[k] 继续向前找 如果存在
                 * 一个 nums[i] < maxK 那么久满足 132
                 */
                maxNumsK = Math.max(maxNumsK,numsKStack.pop());
                numsKStack.push(nums[i]);
            }

        }
        return false;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
