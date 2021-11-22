//输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。 
//
// 要求时间复杂度为O(n)。 
//
// 
//
// 示例1: 
//
// 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。 
//
// 
//
// 提示： 
//
// 
// 1 <= arr.length <= 10^5 
// -100 <= arr[i] <= 100 
// 
//
// 注意：本题与主站 53 题相同：https://leetcode-cn.com/problems/maximum-subarray/ 
//
// 
// Related Topics 数组 分治 动态规划 👍 365 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = nums;
        int max = dp[0];
        /**
         * dp[i] 表示到 i(包括)为止的最优解
         * 如果nums[i] < 0
         * 则对于 dp[i]而言
         * 如果dp[i-1] < 0 则 dp[i] = nums[i] 不能连续
         *
         * 如果dp[i-1] >0  则 dp[i] = nums[i]+dp[i-1] 不能连续
         * 此时对于 dp[i]而言是最优解，因为保证了 dp[i]最大
         *
         * 如果num[i] > 0 则对于 dp[i] 而言
         * 如果dp[i-1] < 0
         * 则dp[i]最大为 nums[i]  不能连续
         *
         * 如果 dp[i-1] > 0
         * 则 dp[i] = dp[i-1]+nums[i] 可以连续
         *
         *
         */

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(0,dp[i-1])+nums[i];
            max = Math.max(max,dp[i]);
        }
        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
