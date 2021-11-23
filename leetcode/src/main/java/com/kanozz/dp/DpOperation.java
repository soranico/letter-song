package com.kanozz.dp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DpOperation {

    public String longestPalindrome(String s) {
        int len = s.length(),begin = 1,maxLen = 0;
        boolean[][] status = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            status[i][i] = true;
        }
        char[] arr = s.toCharArray();
        // i为每一列 status[0][0]没有所以从1开始
        for (int j = 1; j < len; j++) {
            // 每一行数据 因为 i = j 已经填充所以不需要
            for (int i = 0; i < j; ++i) {
                // 回文首尾必等
                if (arr[j]!=arr[i]){
                    status[i][j] = false;
                }else{
                    // 3个字符首尾同必然等
                    if(j-i<3){
                        status[i][j] = true;
                    }else {
                        // 左下方决定
                        status[i][j] = status[i+1][j-1];
                    }
                    if (status[i][j] && j-i +1> maxLen){
                        // 更新最长
                        maxLen = j-i+1;
                        begin = i;
                    }
                }
            }
        }
        return s.substring(begin,begin+maxLen);

    }

    @Test
    public void testLongestPalindrome(){
        log.info("longestPalindrome = {}",longestPalindrome("baabaabad"));
    }
}
