//给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。 
//
// 
//
// 注意： 
//
// 
// 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。 
// 如果 s 中存在这样的子串，我们保证它是唯一的答案。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "ADOBECODEBANC", t = "ABC"
//输出："BANC"
// 
//
// 示例 2： 
//
// 
//输入：s = "a", t = "a"
//输出："a"
// 
//
// 示例 3: 
//
// 
//输入: s = "a", t = "aa"
//输出: ""
//解释: t 中两个字符 'a' 均应包含在 s 的子串中，
//因此没有符合条件的子字符串，返回空字符串。 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length, t.length <= 10⁵ 
// s 和 t 由英文字母组成 
// 
//
// 
//进阶：你能设计一个在 o(n) 时间内解决此问题的算法吗？ Related Topics 哈希表 字符串 滑动窗口 👍 1498 👎 0


import java.util.Map;

//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
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
                if (preMin > end-start+2){
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
}
//leetcode submit region end(Prohibit modification and deletion)
