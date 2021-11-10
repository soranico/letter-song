//给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。 
//
// 
//
// 示例 1: 
//
// 输入: [2,3,-2,4]
//输出: 6
//解释:子数组 [2,3] 有最大乘积 6。
// 
//
// 示例 2: 
//
// 输入: [-2,0,-1]
//输出: 0
//解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。 
// Related Topics 数组 动态规划 👍 1301 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int maxProduct(int[] nums) {
        /**
         * 包含nums[i]在内的连续子数组最大值
         */
        int[] dpMax = new int[nums.length];
        /**
         * 包含nums[i]在内的连续子数组最小值
         */
        int[] dpMin = new int[nums.length];
        int max = nums[0];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int cur = nums[i];
            /**
             * cur >= 0
             * 那么只需要比较 dpMax[i-1] 的正负
             * 因为 dpMax[i-1] >= dpMin[i-1]
             * 较大值 * 正数 仍是较大值
             */
            if (cur > 0 && dpMax[i - 1] > 0) {
                // 最大值 * 正数 还是最大值
                dpMax[i] = cur * dpMax[i - 1];

                /**
                 * dpMin[i-1] <= 0 * 正数会让数值变小
                 * 否则 cur 最小
                 */
                dpMin[i] = dpMin[i - 1] <= 0 ? cur * dpMin[i - 1] : cur;
            }

            else if (cur >= 0 && dpMax[i - 1] <= 0) {
                // 连续数组最大值就是本身
                dpMax[i] = cur;

                /**
                 * 如果dpMin[i-1] = 0 那么0 最小
                 * < 0 则乘积最小
                 */
                dpMin[i] = Math.min(0, cur * dpMin[i - 1]);
            }
            /**
             * cur < =0
             * 需要比较dpMin[i-1] 的正负
             */
            else if (cur < 0 && dpMin[i - 1] > 0) {
                // dpMax[i-1]>0 正数 * 负数 一定小于当前数,当前数最大
                dpMax[i] = cur;
                // 正数 * 负数 最小值肯定更小
                dpMin[i] = cur * dpMax[i - 1];
            }

            else if (cur <= 0 && dpMin[i - 1] <= 0) {
                // 负数 * 负数 一定变大
                dpMax[i] = cur * dpMin[i - 1];

                // 如果前一个最大值是大于0的那么乘积更小,否则当前数更小
                /**
                 * 如果dpMax[i-1] = 0 那么 cur 和 0 较小值
                 * 如果dpMax[i-1] > 0 乘积最小
                 * 如果dpMax[i-1] < 0 那么cur 最小
                 */
                dpMin[i] = dpMax[i - 1] <= 0 ? cur : cur * dpMax[i - 1];
            }
            max = Math.max(max, dpMax[i]);
        }

        return max;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
