package com.kanozz.dp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DpOperation {
    /**
     * 5 最长回文子串
     */
    public String longestPalindrome(String s) {

        /**
         *
         *   k a n o o n a k
         * k 1 0 0
         * a   1 0
         * n     1 0
         * o       1
         * o         1
         * n           1
         * a             1
         * k                1
         * 对角线的元素肯定是一个回文子串,因为只有一个字符
         *
         */
        char[] arr = s.toCharArray();
        boolean[][] dp = new boolean[arr.length][arr.length];
        return "";
    }

    @Test
    public void testLongestPalindrome(){
        log.info("longestPalindrome = {}",longestPalindrome("baabaabad"));
    }
}
