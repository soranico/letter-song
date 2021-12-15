//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 👍 3973 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public String longestPalindrome(String s) {

        /**
         *
         *   a b c c b d a
         * a 1 0 0
         * b   1 0
         * c     1 1
         * c       1
         * b         1
         * d
         * a
         *
         *
         * 对角线的元素肯定是一个回文子串,因为只有一个字符
         *
         * a bc cb da
         * dp[i,j] 如果此时 arr[i] = arr[j]
         *
         * 如果 j - i <=2 那么必然满足回文 aba这种形式
         *
         * 如果 j -i > 2 那么当 dp[i+1,j-1]是回文的时候
         * 这种情况也是回文 a bc cb a 这种
         * 对于 i,j 而言 此时 i+1,j-1
         * 此时参考的是这个元素左下角的值,也就是这一列的值
         * 需要前一列的值来作为辅助,因此按照列来填充
         *
         *
         */
        char[] arr = s.toCharArray();
        boolean[][] dp = new boolean[arr.length][arr.length];
        int start = 0, end = 1, max = 0;
        for (int col = 0; col < arr.length; col++) {
            for (int row = 0; row < col; row++) {
                if (arr[col] != arr[row]) {
                    continue;
                }
                /**
                 * 此时是三个字符,必然满足
                 */
                if (col - row <= 2) {
                    dp[row][col] = true;
                }
                /**
                 * 前置满足,此时加上首尾也必然满足
                 */
                else if (dp[row + 1][col - 1]) {
                    dp[row][col] = true;
                }
                /**
                 * 满足更新最长
                 */
                if (dp[row][col] && max < col - row+1) {
                    max = col - row + 1;
                    start = row;
                    end = col+1;
                }
            }
        }

        return s.substring(start, end);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
