//统计一个数字在排序数组中出现的次数。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2 
//
// 示例 2: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 105 
// -109 <= nums[i] <= 109 
// nums 是一个非递减数组 
// -109 <= target <= 109 
// 
//
// 
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/ 
// Related Topics 数组 二分查找 
// 👍 192 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int search(int[] nums, int target) {
        int frequency = 0,low = 0,high = nums.length-1,middle;
        while (low<=high){
            middle = (low + high)>>>1;
            if (nums[middle]>target){
                high = middle - 1;
            }else if (nums[middle] < target){
                low = middle + 1;
            }else {
                frequency += binaryScope(nums,low,middle-1,target,true)+
                        binaryScope(nums,middle+1,high,target,false);
                frequency++;
                break;
            }
        }
        return frequency;
    }

    private int binaryScope(int[] nums,int low,int high,int target,boolean pre){
        while (low<=high){
            int middle = (high+low)>>>1;
            if (nums[middle]==target ){
                if (pre){
                    return high-middle+1 + binaryScope(nums,low,middle-1,target,true);
                }
                return middle - low +1 + binaryScope(nums,middle+1,high,target,false);
            }
            // 只可能比target小
            else if (pre){
                low = middle+1;
            }
            // 只可能比target大
            else {
                high = middle-1;
            }
        }
        return 0;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
