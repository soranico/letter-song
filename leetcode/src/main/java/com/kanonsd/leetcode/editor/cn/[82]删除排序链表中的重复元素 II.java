//存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除链表中所有存在数字重复情况的节点，只保留原始链表中 没有重复出现 的数字。 
//
// 返回同样按升序排列的结果链表。 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [1,2,3,3,4,4,5]
//输出：[1,2,5]
// 
//
// 示例 2： 
//
// 
//输入：head = [1,1,1,2,3]
//输出：[2,3]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点数目在范围 [0, 300] 内 
// -100 <= Node.val <= 100 
// 题目数据保证链表已经按升序排列 
// 
// Related Topics 链表 双指针 👍 691 👎 0


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
    public ListNode deleteDuplicates(ListNode head) {

        // 空直接返回
        if (Objects.isNull(head)){
            return null;
        }

        ListNode pre = head,cur = head.next,preDiff = new ListNode();
        preDiff.next = head;
        // 当前节点存在
        while (Objects.nonNull(cur)){
            // 前一个节点值
            int val = pre.val;
            // 值不同更新指针,指针依次后移即可
            if (val != cur.val){
                preDiff = pre;
                pre = pre.next;
                cur = cur.next;
                continue;
            }
            // 找到下一个不同节点,如果相同此时 [pre,cur)之间的节点需要去处
            while (Objects.nonNull(cur = cur.next) && cur.val == val);
            // 说明从head开始是重复的
            if (preDiff.next == head){
                head = cur;
            }
            // 指向pre的前一个不同节点preDiff 指向 cur
            preDiff.next = cur;
            pre = cur;
            if (Objects.nonNull(cur)){
                cur = cur.next;
            }

        }
        return head;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
