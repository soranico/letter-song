package com.kanozz.skipwindow;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SlideWindowOperation {
    /**
     *
     * nums[-1] = nums[n] = 无穷小
     *
     */
    public int findPeakElement(int[] nums) {
        if (nums.length ==1){
            return 0;
        }

        int index = 1,first = nums[0],second = nums[1],third,right = 2,max = nums.length;
        while (right < max){
            third = nums[right++];
            if (second>first && second>third){
                return index;
            }
            index++;
            first = second;
            second = third;
        }
        if (nums[0]>nums[1]){
            return 0;
        }
        return  max-1;

    }




    @Test
    public void testFindPeakElement(){
        log.info("findPeakElement = {}",findPeakElement(new int[]{
              1,-1,1
        }));
    }





    public int[] maxSlidingWindow(int[] nums, int k) {
        //
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


    //输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
    @Test
    public void testMaxSlidingWindow(){
        log.info("maxSlidingWindow = {}",maxSlidingWindow(new int[]{
                9,10,9,-7,-4,-8,2,-6
        },5));
    }


    /**
     * 76
     * 最小覆盖子串
     * pass
     * 滑动窗口+hash
     * 缓存字符以及字符出现的次数,两个hash交换每次移动窗口后不满足的情况
     * 1.窗口内字符数 > 最小需要的字符
     * anoak kan
     * 此时窗口内存在两个 a 移除以第一个 a后依然满足 此时需要移除下一个导致不满足条件的字符
     * 也就是 n
     * 2. 每次移除不满足后对比之前满足窗口的大小,这个窗口更小则更新窗口
     */
    public String minWindow(String s, String t) {
        if (s.length() < t.length()){
            return "";
        }
        /**
         * 输入：s = "ADOBECODE BANC", t = "ABC"
         * 输出："BANC"
         *
         * ADOBEC - DOBECODEBA - ODEBANC
         *
         */
        Map<Character,Integer> fromNumMap = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            Integer charNum = fromNumMap.getOrDefault(t.charAt(i), 0);
            fromNumMap.put(t.charAt(i),charNum+1);
        }
        Map<Character,Integer> toNumMap = new HashMap<>(fromNumMap.size());
        Map<Character,Integer> windowNumMap = new HashMap<>(26);
        int len = s.length(),start = 0,end = 0,preMin = Integer.MAX_VALUE;
        StringBuilder minLen = new StringBuilder();
        while (end < len){

            Integer num = windowNumMap.getOrDefault(s.charAt(end),0);
            windowNumMap.put(s.charAt(end),num+1);

            if (fromNumMap.containsKey(s.charAt(end))){
               changeMap(s,end,fromNumMap,toNumMap);
            }

            /**
             * 此时窗口内的数据敲好满足字符全部覆盖
             * 移动窗口直到不满足完全覆盖
             * 只要有一个字符不满足即可完成
             */
            if (fromNumMap.size()==0){
                while (start <= end){
                    /**
                     * 移除一个字符此时不满足
                     * 此时还剩其他满足的字符,只要加上这个移除的字符重新满足
                     */
                    Integer winNum = windowNumMap.get(s.charAt(start));
                    if (toNumMap.containsKey(s.charAt(start))){
                        Integer lessNum = toNumMap.get(s.charAt(start));
                        /**
                         * 窗口内字符多余需要的字符个数,移除这个后窗口
                         * 还是满足覆盖的,直接移除即可
                         */
                        if (winNum > lessNum){
                            windowNumMap.put(s.charAt(start),winNum-1);
                            start++;
                            continue;
                        }
                        changeMap(s,start,toNumMap,fromNumMap);
                        removeChar(windowNumMap,winNum,s.charAt(start));
                        start++;
                        break;
                    }
                    removeChar(windowNumMap,winNum,s.charAt(start));
                    start++;
                }
                if (preMin > end - start + 2){
                    preMin = end - start + 2;
                    minLen.setLength(0);
                    minLen.append(s,start-1,end+1);
                }
            }
            end++;
        }

        return minLen.toString();
    }

    private void changeMap(String s,int end,Map<Character,Integer> fromNumMap,
                           Map<Character,Integer> toNumMap){
        Integer charNum = fromNumMap.get(s.charAt(end));
        Integer toNum = toNumMap.getOrDefault(s.charAt(end),0);
        removeChar(fromNumMap,charNum,s.charAt(end));
        toNumMap.put(s.charAt(end),toNum+1);
    }
    private void removeChar(Map<Character,Integer> fromNumMap,Integer charNum,Character c){
        if (charNum-1==0){
            fromNumMap.remove(c);
        }else {
            fromNumMap.put(c,charNum-1);
        }
    }

    @Test
    public void testMinWindow(){
        // 	测试结果:"aefgc"
        //				期望结果:"cwae"
        log.info("minWindow = {}",minWindow("cabwefgewcwaefgcf","cae"));
//        log.info("minWindow = {}",minWindow("cabwefgewcwaefgcf","aa"));
    }




    /**
     * 395
     * 至少k个重复字符的最长子串
     *
     * 子串必须连续
     */
    public int longestSubstring(String s, int k) {
        int max = 0,len = s.length(),start = 0,end = 0;
        Map<Character,Integer> charNumMap = new HashMap<>(26);
        for (int i = 0; i < len; i++) {


        }

        return max;

    }



}
