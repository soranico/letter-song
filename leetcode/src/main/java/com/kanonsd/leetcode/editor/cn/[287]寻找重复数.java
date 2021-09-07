//给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。 
//
// 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。 
//
// 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,4,2,2]
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：nums = [3,1,3,4,2]
//输出：3
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,1]
//输出：1
// 
//
// 示例 4： 
//
// 
//输入：nums = [1,1,2]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 105 
// nums.length == n + 1 
// 1 <= nums[i] <= n 
// nums 中 只有一个整数 出现 两次或多次 ，其余整数均只出现 一次 
// 
//
// 
//
// 进阶： 
//
// 
// 如何证明 nums 中至少存在一个重复的数字? 
// 你可以设计一个线性级时间复杂度 O(n) 的解决方案吗？ 
// 
// Related Topics 位运算 数组 双指针 二分查找 
// 👍 1330 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findDuplicate(int[] nums) {

        // 1,3,4,2,2  0 - 1 - 3 - 2 - 4 - 2
        // 2,3,1,4,5  0 -2 - 1 - 3 - 4 - 5 - null
        // fn = i -> num[i]映射
        // 如果没有重复元素的话,那么进行映射必然不会出现两个数字映射到同个位置
        // 因为数字在 1 - n 那么对于下标 0<= m <= n-1 而言 其映射到 1 <= nums[m] <= n-1
        // 必然会存在映射到 n-1 超出下标而终止
        // 如果存在重复数字 ,会出现 m映射到 nums[m] 而nums[m] 映射到之前已经被映射的位置(本来不重复应该终止)
        // 从而形成环
        int quick = nums[nums[0]],slow = nums[0],head = 0;
        // quick 每次移动两个 slow移动一个 head 到环 距离 a 当两者相遇时激励环入口为 b ,b到环尾为c
        // 则quick移动了 a + n(b+c)+b  slow移动了 a+(n-1)(b+c)+b quick 是slow的 2倍
        // a + n(b+c) +b = 2a + 2n(b+c) - 2(b+c) + 2b
        // n(b+c) -2(b+c) -b = a
        while (quick != slow){
            quick = nums[nums[quick]];
            slow = nums[slow];
        }
        while (head!=slow){
            slow = nums[slow];
            head = nums[head];
        }


        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
