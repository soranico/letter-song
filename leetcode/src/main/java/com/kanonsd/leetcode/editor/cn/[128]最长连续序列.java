//给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。 
//
// 请你设计并实现时间复杂度为 O(n) 的算法解决此问题。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [100,4,200,1,3,2]
//输出：4
//解释：最长数字连续序列是 [1, 2, 3, 4]。它的长度为 4。 
//
// 示例 2： 
//
// 
//输入：nums = [0,3,7,2,5,8,4,6,0,1]
//输出：9
// 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// 
// Related Topics 并查集 数组 哈希表 👍 918 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int longestConsecutive(int[] nums) {
        /**
         *
         * 暴力解法
         * 对于每个数都查询是否有后继
         *
         *
         */
//        for (int i = 0; i < nums.length; i++) {
//            int cur = nums[i],next = cur+1;
//
//            while (true){
//                int start = next;
//                // 找下一个数是否存在数组中
//                for (int j = 0 ;j<nums.length;j++){
//                    // 找到了更新下个数
//                    if (next == nums[j]){
//                        next++;
//                        break;
//                    }
//                }
//                if (start==next){
//                    break;
//                }
//            }
//        }

        if (nums.length == 0){
            return 0;
        }
        // 缓存所有数据
        Set<Integer> existNumSet = new HashSet<>(nums.length);
        for (int num : nums) {
            existNumSet.add(num);
        }
        int maxLen = 0;
        for (int curIndex = 0; curIndex < nums.length; curIndex++) {
            int curNum = nums[curIndex],curLen = 1;
            /**
             * 如果前一个不存在
             * 说明这是一个新开始的连续
             * 需要计算从 curNum 开始的连续序列的长度
             *
             * 如果前面一个数存在，那么这个没有必要重新计算
             * 因为对于 .... n-1 ,n n+1 而言
             * n-1 n组成的长度比 n大
             */
            if (!existNumSet.contains(curNum - 1)){
                while (existNumSet.contains(curNum+1)){
                    curLen++;
                    curNum++;
                }
            }
            // 更新最大长度
            maxLen = Math.max(maxLen,curLen);
        }

        return maxLen;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
