//给定一个单链表 L：L0→L1→…→Ln-1→Ln ， 
//将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→… 
//
// 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。 
//
// 示例 1: 
//
// 给定链表 1->2->3->4, 重新排列为 1->4->2->3. 
//
// 示例 2: 
//
// 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3. 
// Related Topics 栈 递归 链表 双指针 
// 👍 616 👎 0


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
    public void reorderList(ListNode head) {
        ListNode cur = head,pre;
        LinkNode preNode = new LinkNode(null,null) ,curNode=null,headNode = preNode;
        while (Objects.nonNull(cur)){
            curNode = new LinkNode(preNode,cur);
            preNode.setNext(curNode);
            preNode = curNode;
            pre = cur;
            cur = cur.next;
            pre.next = null;
        }
        // 空节点不需要每次对前一个节点判断非null
        pre = new ListNode();
        // 偶数节点headNode == curNode
        while (Objects.nonNull(curNode) &&
                headNode!= curNode && headNode.next != curNode){
            headNode = headNode.next;
            pre.next =  headNode.cur;
            headNode.cur.next = curNode.cur;
            pre = curNode.cur;
            curNode = curNode.pre;
        }
        if (headNode ==curNode){
            return;
        }
        pre.next = headNode.next.cur;

    }

    private class LinkNode{
        private LinkNode pre,next;
        private ListNode cur;
        LinkNode(LinkNode pre, ListNode cur){
            this.pre = pre;
            this.cur = cur;
        }

        public void setNext(LinkNode next) {
            this.next = next;
        }
    }
}
//leetcode submit region end(Prohibit modification and deletion)
