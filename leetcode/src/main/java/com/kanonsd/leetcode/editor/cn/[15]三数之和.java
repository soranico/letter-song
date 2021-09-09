//给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重
//复的三元组。 
//
// 注意：答案中不可以包含重复的三元组。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [-1,0,1,2,-1,-4]
//输出：[[-1,-1,2],[-1,0,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 3000 
// -105 <= nums[i] <= 105 
// 
// Related Topics 数组 双指针 排序 
// 👍 3630 👎 0


import java.util.Arrays;
import java.util.List;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {

        if (nums.length<3){
            return Collections.emptyList();
        }
        Arrays.sort(nums);

        /**
         *
         * -1,0,1,2,-1,-4
         *
         * -4,-1,-1,0,1,2
         * 如果三层循环的话
         * 对于 m
         * 遍历n - len n > m
         * 对于 n
         * 遍历 k - len k > n
         * 如果数组是递增的
         * 那么对于
         * n 和 k  而言
         * m + n + k = 0
         * 则下次必然只有
         * n增加 k减少
         */
        List<List<Integer>> three = new ArrayList<>();

        for (int m = 0; m < nums.length; m++) {
            // 此种情况肯定不存在仍然满足的,直接退出
            if (nums[m] > 0){
                return three;
            }
            /**
             * 保证
             * nums[m] <= nums[n] <= nums[k]
             * m < n < k
             *
             * 如果当前m指向和前一个元素相同,直接过滤这个元素
             * 对于 m 指针而言,如果 m 指针 当前和下一个元素值相同
             * 0,0,0,0,...,0 这种情况下
             * m = 0 找到了 0,0,0 组合
             *
             * 也就是一轮可以找到包含 nums[m]的所有符合组合 A
             *
             * 对于下轮而言，如果元素相同，那么这轮找到的组合 B
             * 有关系 A 包含 B
             * 因为需要去处 相同元素
             */
            if (m != 0 && nums[m] == nums[m-1]){
                continue;
            }
            // 元素不能重复使用,需要从当前数字的下一位开始
            // 让 k指针指向 最后一个元素
            int n = m+1,k = nums.length-1;
            /**
             * 找到 以 nums[m] 开头的组合
             * 这一轮会把包含 nums[m] 所有满足的找出来
             *
             */
            for (; n < k;) {
                int sum = nums[m] + nums[n] + nums[k];
                if (sum == 0){
                    three.add(Arrays.asList(nums[m],nums[n],nums[k]));
                    /**
                     * 移动 n 和 k 找到下一个满足的
                     * 此时 n 和 k 都已经使用了
                     * 对于 n 而言下个数肯定大于等于当前数
                     * 此时 移动 k 找到一个小于nums[k]的数
                     *
                     * 不移动 n 指针 因为此时 k 指针指向的元素
                     * 改变了,如果n 指向的下个元素 和nums[n]
                     * 相同那么是不会
                     *
                     */
                    int num = nums[k];
                    while (n < k && num == nums[k]){
                        k--;
                    }

                }
                /**
                 * 此情况下说明 k 指向的值偏大了
                 * 移动 k 指针
                 */
                else if (sum > 0){
                    k--;
                }
                /**
                 * 此情况说明 对于当前m而言 n偏小了
                 * 移动 n 指针
                 */
                else {
                    n++;
                }

            }
        }

        return three;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
