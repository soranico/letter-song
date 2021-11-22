//给定一个整数数组和一个整数 k，你需要找到该数组中和为 k 的连续的子数组的个数。 
//
// 示例 1 : 
//
// 
//输入:nums = [1,1,1], k = 2
//输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
// 
//
// 说明 : 
//
// 
// 数组的长度为 [1, 20,000]。 
// 数组中元素的范围是 [-1000, 1000] ，且整数 k 的范围是 [-1e7, 1e7]。 
// 
// Related Topics 数组 哈希表 前缀和 👍 1076 👎 0


import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int subarraySum(int[] nums, int k) {
   /**
     *
     * 前缀和
     * sum[i] = sum[i-1] + nums[i]
     * 即sum[i] 表示从 0 - i 的和
     *
     * 假设[j,i]的和为 k
     * k = sum[i] - sum[j-1]
     *
     *
     *
     * 如果count[i] 为 nums[0] - nums[i] 之和
     * 子数组[j,i]满足和为k
     * 则 nums[j,i] = count[i] - count[j-1]
     * count[i] - count[j-1] = k
     *
     * count[i] = k + count[j-1]
     *
     * 也就是 count[i] 和 count[j-1] 构成差值为 k
     *
     *
     * 如果之前 count[j-1] 有两个序列
     * 那么这两个序列均可以和[i,j] 组合成
     *
     *
     *
     * 此时nums[j,i]满足 为k
     * 1,2   2,-4,2,3   6,2,-4,2,3,-9,3
     * [1,2,5,6,2,-4,2,3,-9,3] k = 3
     * 0 1
     * 1 1
     * 3 1
     * 8 1
     * 14 1
     * 16 1
     * 12 1
     * 14 2
     * 17 1
     * 12 2
     * 15 1
     */

        Map<Integer,Integer> sumMap = new HashMap<>();
        int sum = 0,count = 0;

        sumMap.put(0,1);

        // 计算区间[j,i]的和
        // 如果和为k 则满足
        // 计算[1,2] 时 可以通过 [0,1]的和跟 nums[2] 推出
        /**
         * 如果[i,j]的和满足情况
         *
         */
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            /**
             * 如果sum[i] - k 存在
             * 说明之前存在sum[j] + k = sum[i]  i > j
             * 此时[j-1,i-1]数的和为 k
             * 如果此时有多个数组为k存在，那么说明有多个数组满足
             * 这种情况需要累加
             */
            if (sumMap.containsKey(sum - k)){
                count += sumMap.get(sum-k);
            }
            sumMap.put(sum,sumMap.getOrDefault(sum,0)+1);
        }

        return count;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
