package com.kanozz.dp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DpOperation {
    /**
     * 5 最长回文子串
     * pass
     */
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

    @Test
    public void testLongestPalindrome() {
        log.info("longestPalindrome = {}", longestPalindrome("ac"));
    }
}
