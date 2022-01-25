//给定两个用链表表示的整数，每个节点包含一个数位。 
//
// 这些数位是反向存放的，也就是个位排在链表首部。 
//
// 编写函数对这两个整数求和，并用链表形式返回结果。 
//
// 
//
// 示例： 
//
// 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
//输出：2 -> 1 -> 9，即912
// 
//
// 进阶：思考一下，假设这些数位是正向存放的，又该如何解决呢? 
//
// 示例： 
//
// 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
//输出：9 -> 1 -> 2，即912
// 
// Related Topics 递归 链表 数学 👍 107 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(),cur = head;
        int carry = 0;
        /**
         * 两个链表相加,直接使用已经存在链表节点即可
         * 没有必要重新创建新的节点来赋值,减少内存占用
         */
        while (l1!=null && l2!=null){
            if (l1.val + l2.val + carry >= 10){
                l1.val = l1.val + l2.val + carry -10;
                carry = 1;
            }else {
                l1.val = l1.val + l2.val + carry;
                carry = 0;
            }
            cur.next = l1;
            l1 = l1.next;
            l2 = l2.next;
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
        return head.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
