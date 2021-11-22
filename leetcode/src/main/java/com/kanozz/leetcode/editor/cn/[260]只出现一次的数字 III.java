//给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。 
//
// 
//
// 进阶：你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？ 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,1,3,2,5]
//输出：[3,5]
//解释：[5, 3] 也是有效的答案。
// 
//
// 示例 2： 
//
// 
//输入：nums = [-1,0]
//输出：[-1,0]
// 
//
// 示例 3： 
//
// 
//输入：nums = [0,1]
//输出：[1,0]
// 
//
// 提示： 
//
// 
// 2 <= nums.length <= 3 * 10⁴ 
// -2³¹ <= nums[i] <= 2³¹ - 1 
// 除两个只出现一次的整数外，nums 中的其他数字都出现两次 
// 
// Related Topics 位运算 数组 👍 452 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] singleNumber(int[] nums) {

        int first = 0, second = 0;
        int xor = 0;
        /**
         * x ^ x = 0
         * 此时只可能剩下两个独数的 ^ 值
         * 对于两个不同数字,位数不可能完全相同
         * 1001
         * 0011
         * ^ 得到
         * 1110
         */
        for (int num : nums) {
            xor = xor ^ num;
        }
        // 0110 0011 0001
        int rightFirstBit = 0;
        /**
         * 异或结果从低位到高位第一个1
         * 也就是两个不同数第一个不同的位
         */
        while ((xor & 1) == 0){
            xor = xor>>>1;
            rightFirstBit++;
        }
        for (int num : nums) {
            /**
             * 如果两个数相同,在指定位肯定相同
             * 此时相同的数肯定在一个组里面
             */
            if ((num >>> rightFirstBit & 1) == 0){
                first = first ^ num;
            }else {
                second = second ^ num;
            }
        }
        return new int[]{first,second};
    }
}
//leetcode submit region end(Prohibit modification and deletion)
