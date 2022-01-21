//给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。 
//
// 你可以假设除了数字 0 之外，这两个数字都不会以零开头。 
//
// 
//
// 示例1： 
//
// 
//
// 
//输入：l1 = [7,2,4,3], l2 = [5,6,4]
//输出：[7,8,0,7]
// 
//
// 示例2： 
//
// 
//输入：l1 = [2,4,3], l2 = [5,6,4]
//输出：[8,0,7]
// 
//
// 示例3： 
//
// 
//输入：l1 = [0], l2 = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 链表的长度范围为 [1, 100] 
// 0 <= node.val <= 9 
// 输入数据保证链表代表的数字无前导 0 
// 
//
// 
//
// 进阶：如果输入链表不能翻转该如何解决？ 
// Related Topics 栈 链表 数学 👍 468 👎 0


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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(),cur = head;
        int carry = 0;
        // 逆序链表
        l1 = reverseListNode(l1);
        l2 = reverseListNode(l2);
        /**
         * 两个链表相加
         */
        while (l1!=null && l2!=null){
            ListNode node = new ListNode();
            if (l1.val + l2.val + carry >=10){
                node.val = l1.val + l2.val + carry -10;
                carry = 1;
            }else {
                node.val = l1.val + l2.val + carry;
                carry = 0;
            }
            l1 = l1.next;
            l2 = l2.next;
            cur.next = node;
            cur = cur.next;
        }
        l1 = l1==null?l2:l1;

        while (l1!=null){
            if (l1.val + carry >=10){
                l1.val = l1.val + carry -10;
                carry = 1;
            }else {
                l1 .val = l1.val + carry;
                carry = 0;
            }
            cur.next = l1;
            l1 = l1.next;
            cur = cur.next;
        }
        /**
         * 此时产生了一个进位所以在首位加一位
         */
        if (carry!=0){
            cur.next = new ListNode(1);
        }
        return reverseListNode(head.next);
    }

    private ListNode reverseListNode(ListNode head){
        ListNode pre = null,cur = head;
        while (cur!=null){
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
