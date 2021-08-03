//已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,4,4,5,6,7] 在变
//化后可能得到：
// 
// 若旋转 4 次，则可以得到 [4,5,6,7,0,1,4] 
// 若旋转 7 次，则可以得到 [0,1,4,4,5,6,7] 
// 
//
// 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], 
//..., a[n-2]] 。 
//
// 给你一个可能存在 重复 元素值的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,5]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,2,2,0,1]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// n == nums.length 
// 1 <= n <= 5000 
// -5000 <= nums[i] <= 5000 
// nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转 
// 
//
// 
//
// 进阶： 
//
// 
// 这道题是 寻找旋转排序数组中的最小值 的延伸题目。 
// 允许重复会影响算法的时间复杂度吗？会如何影响，为什么？ 
// 
// Related Topics 数组 二分查找 
// 👍 382 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findMin(int[] nums) {
        // 旋转 1 - n次此时存在两个逻辑递增数组 [n-1,n-2,...m] [0,1,2,...,m-1]
        // 最小值在两个逻辑数组的交界处即第二个逻辑数组第一个元素
        int high = nums.length-1,low = 0,middle;
        while (low < high){
            middle = (low+high)>>>1;
            // 此时表明middle在第一个逻辑数组,低指针后移可能移到第二个逻辑数组内
            if (nums[middle] > nums[high]){
                low = middle+1;
            }
            // 此时表明middle在第二个逻辑数组内,此时高指针不能移到middle-1
            // 可能middle就是第二个逻辑数组第一个元素即最小值
            else if (nums[middle] < nums[low]){
                high = middle;
            }
            // middle可能在第一个数组(需要移动low),也可能在第二个数组(需要移动high)
            else if (nums[middle] == nums[high]){
                high = high -1;
            }
            // 此时在第二个数组内,则low指针为最小值
            else if (nums[middle] < nums[high]){
                return nums[low];
            }
        }
        return nums[low];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
