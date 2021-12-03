//给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。 
//
// 进阶： 
//
// 
// 你可以在 O(n log n) 时间复杂度和常数级空间复杂度下，对链表进行排序吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：head = [4,2,1,3]
//输出：[1,2,3,4]
// 
//
// 示例 2： 
//
// 
//输入：head = [-1,5,3,4,0]
//输出：[-1,0,3,4,5]
// 
//
// 示例 3： 
//
// 
//输入：head = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 链表中节点的数目在范围 [0, 5 * 10⁴] 内 
// -10⁵ <= Node.val <= 10⁵ 
// 
// Related Topics 链表 双指针 分治 排序 归并排序 👍 1380 👎 0


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
    public ListNode sortList(ListNode head) {

        return  mergeSort(head, null);
    }

    private ListNode mergeSort(ListNode head, ListNode tail) {
        /**
         * 只有一个节点那么需要返回进行排序
         */
        if (head == tail || head.next ==null) {
            return head;
        }
        /**
         * 只存在两个节点
         * 那么先对两个节点进行排序
         */
        else if (head.next == tail){
            if (head.val > tail.val){
                tail.next = head;
                head.next = null;
                return tail;
            }
            return head;
        }
        ListNode quick = head, middle = head, next;
        /**
         * 利用快慢指针找到当前 head - tail的中间点
         * 即进行 分治法的 分
         */
        while (quick!=null && quick.next!=null){
            quick = quick.next.next;
            middle = middle.next;
        }
        /**
         * 断开中间节点和其下个节点的指针
         */
        next = middle.next;
        middle.next = null;
        ListNode firstList = mergeSort(head, middle);
        ListNode secondList = mergeSort(next, tail);


        ListNode emptyNode = new ListNode(),emptyHead = emptyNode;

        /**
         * 从 head - middle 和 next - tail
         * 分别排序合并
         * 分支法的治
         * 开始进行链表的重新链接
         */
        while (firstList != null && secondList != null) {
            if (firstList.val > secondList.val) {
                emptyNode.next = secondList;
                next = secondList.next;
                secondList.next = null;
                secondList = next;
            }else {
                emptyNode.next = firstList;
                next = firstList.next;
                firstList.next = null;
                firstList = next;
            }
            emptyNode = emptyNode.next;
        }
        emptyNode.next = firstList == null? secondList:firstList;

        return emptyHead.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
