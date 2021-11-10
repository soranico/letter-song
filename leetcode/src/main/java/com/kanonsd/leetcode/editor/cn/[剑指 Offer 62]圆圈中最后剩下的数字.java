//0,1,···,n-1这n个数字排成一个圆圈，从数字0开始，每次从这个圆圈里删除第m个数字（删除后从下一个数字开始计数）。求出这个圆圈里剩下的最后一个数字。
// 
//
// 例如，0、1、2、3、4这5个数字组成一个圆圈，从数字0开始每次删除第3个数字，则删除的前4个数字依次是2、0、4、1，因此最后剩下的数字是3。 
//
// 
//
// 示例 1： 
//
// 
//输入: n = 5, m = 3
//输出: 3
// 
//
// 示例 2： 
//
// 
//输入: n = 10, m = 17
//输出: 2
// 
//
// 
//
// 限制： 
//
// 
// 1 <= n <= 10^5 
// 1 <= m <= 10^6 
// 
// Related Topics 递归 数学 👍 460 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int lastRemaining(int n, int m) {
        Set<Integer> remove = new HashSet<>(n);
        int loop = m,cur = 0;
        // 一共 n+1 个数 移除了 n 个数
        while (remove.size() < n-1){
            cur = 0;
            // 从 0 - n
            while (cur <= n-1){
                // 没有被移除并且是第m个，则加入移除链表 从下个位置开始新一轮
                if (!remove.contains(cur) && --loop == 0){
                    remove.add(cur);
                    if (remove.size() == n-1){
                        break;
                    }
                    loop = m;
                }

                cur++;
            }
        }
        for (int i = 0; i < n; i++) {
            if (!remove.contains(i)){
                return i;
            }
        }
        return cur;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
