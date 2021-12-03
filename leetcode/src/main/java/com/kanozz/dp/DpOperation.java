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
         * k
         * a
         * n
         * o
         * o
         * n
         * a
         * k
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
