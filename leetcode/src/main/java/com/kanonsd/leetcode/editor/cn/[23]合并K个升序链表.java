//给你一个链表数组，每个链表都已经按升序排列。 
//
// 请你将所有链表合并到一个升序链表中，返回合并后的链表。 
//
// 
//
// 示例 1： 
//
// 输入：lists = [[1,4,5],[1,3,4],[2,6]]
//输出：[1,1,2,3,4,4,5,6]
//解释：链表数组如下：
//[
//  1->4->5,
//  1->3->4,
//  2->6
//]
//将它们合并到一个有序链表中得到。
//1->1->2->3->4->4->5->6
// 
//
// 示例 2： 
//
// 输入：lists = []
//输出：[]
// 
//
// 示例 3： 
//
// 输入：lists = [[]]
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// k == lists.length 
// 0 <= k <= 10^4 
// 0 <= lists[i].length <= 500 
// -10^4 <= lists[i][j] <= 10^4 
// lists[i] 按 升序 排列 
// lists[i].length 的总和不超过 10^4 
// 
// Related Topics 链表 分治 堆（优先队列） 归并排序 👍 1592 👎 0


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
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length==0){
            return null;
        }else if (lists.length ==1){
            return lists[0];
        }

        /**
         *
         * 0 1 , 2 3 , 4 5
         * 0 ,2 ,4
         * 0,4
         * 0
         */
        int next = 0,max = lists.length;
        while (true){
            for (int i = 0; i < max -1; i+=2) {
                lists[next++] = merge(lists[i],lists[i+1]);
            }
            if(next == 1 && (max&1) ==0){
                break;
            }else if ((max&1) != 0){
                lists[next++] = lists[max-1];
            }
            max = next;
            next = 0;
        }
        return lists[0];
    }


    private ListNode merge(ListNode first , ListNode second){
        ListNode firstCur = first,secondCur = second,
                firstNext,secondNext,head = new ListNode(),cur = head;
        while (firstCur !=null && secondCur!=null){
            firstNext = firstCur.next;
            secondNext = secondCur.next;
            if (firstCur.val > secondCur.val){
                cur.next = secondCur;
                secondCur = secondNext;
            }else if (secondCur.val > firstCur.val){
                cur.next = firstCur;
                firstCur = firstNext;
            }else {
                cur.next = firstCur;
                cur = cur.next;
                cur.next = secondCur;
                firstCur = firstNext;
                secondCur = secondNext;
            }
            cur = cur.next;
        }
        cur.next = firstCur ==null?secondCur:firstCur;
        return head.next;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
