//一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0～n-1之内。在范围0～n-1内的n个数字中有且只有一个数字不在该数组中，请找出
//这个数字。 
//
// 
//
// 示例 1: 
//
// 输入: [0,1,3]
//输出: 2
// 
//
// 示例 2: 
//
// 输入: [0,1,2,3,4,5,6,7,9]
//输出: 8 
//
// 
//
// 限制： 
//
// 1 <= 数组长度 <= 10000 
// Related Topics 位运算 数组 哈希表 数学 二分查找 
// 👍 154 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int missingNumber(int[] nums) {
        // 对于 0,1,2 .... m-1,m m+1 .... n-1 而言
        // 如果到 m-1都是完整的,那么其下标和数值是相等的
        // 如果 nums[m] > m 则说明缺失数字肯定在 nums[m]之前



        int low = 0,high = nums.length-1,middle;
        while (low <= high){
            if (low == high){
                // 如果高指针一直没有移动,则说明前 0-low 个数是完整的
                if (high==nums.length-1 && nums[high]==high){
                    return nums[high]+1;
                }
                return nums[high]-1;
            }
            middle = (low+high)>>>1;
            if (nums[middle] == middle){
                low= middle+1;
            }
            // 进入这里的话缺失元素一定不是 n
            else if (nums[middle] > middle){
                high = middle;
            }
        }

        return nums[nums.length-1]+1;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
