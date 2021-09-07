//已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变
//化后可能得到：
// 
// 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2] 
// 若旋转 7 次，则可以得到 [0,1,2,4,5,6,7] 
// 
//
// 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], 
//..., a[n-2]] 。 
//
// 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [3,4,5,1,2]
//输出：1
//解释：原数组为 [1,2,3,4,5] ，旋转 3 次得到输入数组。
// 
//
// 示例 2： 
//
// 
//输入：nums = [4,5,6,7,0,1,2]
//输出：0
//解释：原数组为 [0,1,2,4,5,6,7] ，旋转 4 次得到输入数组。
// 
//
// 示例 3： 
//
// 
//输入：nums = [11,13,15,17]
//输出：11
//解释：原数组为 [11,13,15,17] ，旋转 4 次得到输入数组。
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
// nums 中的所有整数 互不相同 
// nums 原来是一个升序排序的数组，并进行了 1 至 n 次旋转 
// 
// Related Topics 数组 二分查找 
// 👍 529 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findMin(int[] nums) {
        int low = 0,high = nums.length-1,middle;
        // 逻辑数组左边 L > 逻辑数组右边 R, 每个逻辑数组内部有序
        // 如果没有旋转那么low不会改变一直指向默认值
        // 如果发生旋转low移动到 R的第一个元素事就会满足low middle high一组内有序
        while (low< high){
            middle = (low+high)>>>1;
            // 说明 middle  在 L ,high在 R
            // 这也说明肯定发生了旋转
            if (nums[middle] > nums[high]){
                low = middle+1;
            }
            // 此时 middle 在R low在L 不同数组,表明发生了旋转
            // 但不能middle是不是R的第一个元素,让high指针移动到middle
            else if (nums[middle] < nums[low]){
                high = middle;
            }
            // 此时 low肯定第一次移动到 R
            else if (nums[middle] < nums[high]){
                return nums[low];
            }
        }
        return nums[low];
    }
}
//leetcode submit region end(Prohibit modification and deletion)
