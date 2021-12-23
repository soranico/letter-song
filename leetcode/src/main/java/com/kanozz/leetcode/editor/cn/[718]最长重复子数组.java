//给两个整数数组 A 和 B ，返回两个数组中公共的、长度最长的子数组的长度。 
//
// 
//
// 示例： 
//
// 输入：
//A: [1,2,3,2,1]
//B: [3,2,1,4,7]
//输出：3
//解释：
//长度最长的公共子数组是 [3, 2, 1] 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= len(A), len(B) <= 1000 
// 0 <= A[i], B[i] < 100 
// 
// Related Topics 数组 二分查找 动态规划 滑动窗口 哈希函数 滚动哈希 👍 581 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        int len = 0;
        /**
         * 如果 x1,x2,x3...xn 和 y1,y2,y3...ym
         * 相同的话 那么从 x2 - xn 之间的 任意一个肯定
         * 小于这个序列的长度
         * 对于外层循环可以直接从 n+1的位置开始
         *
         */
        for (int i = 0; i < nums1.length; i++) {
            int temp = i, curLen = 0;
            for (int j = 0; j < nums2.length; j++) {
                if (temp >= nums1.length) {
                    break;
                }
                if (nums1[temp] != nums2[j]) {
                    continue;
                }
                curLen++;
                temp++;
            }
            len = Math.max(len, curLen);
        }
        return len;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
