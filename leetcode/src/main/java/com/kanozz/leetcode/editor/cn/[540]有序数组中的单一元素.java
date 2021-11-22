//给定一个只包含整数的有序数组，每个元素都会出现两次，唯有一个数只会出现一次，找出这个数。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [1,1,2,3,3,4,4,8,8]
//输出: 2
// 
//
// 示例 2: 
//
// 
//输入: nums =  [3,3,7,7,10,11,11]
//输出: 10
// 
//
// 
//
// 
//
// 提示: 
//
// 
// 1 <= nums.length <= 105 
// 0 <= nums[i] <= 105 
// 
//
// 
//
// 进阶: 采用的方案可以在 O(log n) 时间复杂度和 O(1) 空间复杂度中运行吗？ 
// Related Topics 数组 二分查找 
// 👍 252 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int singleNonDuplicate(int[] nums) {
        // 位运算 一次遍历可以得到
        // 这个数组的长度一定是 2n+1 此时middle为 n
        // 1 1 2 此时middle 是1
        int low = 0, high = nums.length - 1, middle;
        while (low <= high) {
            middle = (low + high) >>> 1;
            // 只有3个数情况,直接运算返回
            if (high - low == 2 || high == low) {
                return nums[low] ^ nums[middle] ^ nums[high];
            }
            // 如果唯一数在前面,则加上这个数  low - middle  之间数肯定是不是2的倍数
            else if (nums[middle] == nums[middle - 1] && ((middle - low + 1) & 1) != 0) {
                high = middle - 2;
            }
            // 说明前半部分全是一对,移除到low - middle为止成对元素
            else if (nums[middle] == nums[middle - 1]) {
                low = middle + 1;
            }
            // 唯一数在后半部分， middle - high 之间数肯定是2的倍数
            else if (nums[middle] == nums[middle + 1] && ((high - middle + 1) & 1) != 0) {
                low = middle + 2;
            }
            // 说明后半部分全是一对,移除middle -high为止的成对元素
            else if (nums[middle] == nums[middle + 1]) {
                high = middle - 1;
            } else {
                return nums[middle];
            }
        }
        return -1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
