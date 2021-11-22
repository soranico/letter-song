//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回滑动窗口中的最大值。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1,-1], k = 1
//输出：[1,-1]
// 
//
// 示例 4： 
//
// 
//输入：nums = [9,11], k = 2
//输出：[11]
// 
//
// 示例 5： 
//
// 
//输入：nums = [4,-2], k = 2
//输出：[4] 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// 1 <= k <= nums.length 
// 
// Related Topics 队列 数组 滑动窗口 单调队列 堆（优先队列） 👍 1143 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {

        int right = 1,numsLen = nums.length,max = nums[0],index =0
                ,maxIndex = 0;
        int[] windowMax = new int[numsLen - k + 1];
        while (right <= numsLen){
            // 窗口达到 k  计算
            if (right - maxIndex == k && maxIndex +1 < numsLen){
                windowMax[index++] = max;
                // 如果最大值此时在移除位置 4 1 -1  5 需要更新最大值
                max = nums[++maxIndex];
                int left = maxIndex;
                // 需要重新计算当前窗口的最大值
                while (++left < right){
                    if (max < nums[left]){
                        max = nums[left];
                        maxIndex = left;
                    }
                }

            }else if (right >= k){
                windowMax[index++] = max;
            }
            if (right == numsLen){
                break;
            }
            // 计算最大值
            if (max < nums[right]){
                max = nums[right];
                maxIndex = right;
            }
            // 窗口扩大
            right++;

        }
        return windowMax;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
