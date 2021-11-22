//给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,4]
//输出：[2,1,4,3]
// 
//
// 示例 2： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：head = [1]
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 100] 内 
// 0 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你能在不修改链表节点值的情况下解决这个问题吗?（也就是说，仅修改节点本身。） 
// Related Topics 递归 链表 👍 1038 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode pre = new ListNode(),curPre = head ,cur = head.next,next;
        boolean first = true;
        while (cur != null){
            next = cur.next;
            curPre.next = null;
            cur.next = curPre;
            pre.next = cur;
            if (pre!=head)
                pre = curPre;
            if (first){
                first= false;
                head = cur;
            }
            curPre = next;
            if (curPre == null){
                break;
            }
            cur = curPre.next;
        }
        if (curPre!=null){
            pre.next = curPre;
        }

        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
