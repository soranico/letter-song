package com.kanonsd.link;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class LinkedOperation {


    /**
     * 给你单链表的头指针 head 和两个整数left 和 right ，其中
     * left <= right 。请你反转从位置 left 到位置 right 的链表节点，
     * 返回 反转后的链表 。
     * <p>
     * 输入：head = [1,2,3,4,5], left = 2, right = 4
     * 输出：[1,4,3,2,5]
     *
     * @param head
     * @param left
     * @param right
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == right) {
            return head;
        }

        ListNode start = head, startPre = head;
        int move = 1;
        while (move < left) {
            startPre = start;
            start = start.next;
            move++;
        }
        ListNode end = start;
        while (move < right) {
            end = end.next;
            move++;
        }

        ListNode pre = start, cur = start.next, next, endNext = end.next;
        while (cur != endNext) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = endNext;
        if (start == head) {
            return pre;
        }
        startPre.next = pre;
        return head;
    }

    // L0→Ln→L1→Ln-1→L2→Ln-2→
    public void reorderList(ListNode head) {
        ListNode cur = head, pre;
        LinkNode preNode = new LinkNode(null, null), curNode = null, headNode = preNode;
        while (Objects.nonNull(cur)) {
            curNode = new LinkNode(preNode, cur);
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
                headNode != curNode && headNode.next != curNode) {
            headNode = headNode.next;
            pre.next = headNode.cur;
            headNode.cur.next = curNode.cur;
            pre = curNode.cur;
            curNode = curNode.pre;
        }
        if (headNode == curNode) {
            return;
        }
        pre.next = headNode.next.cur;

    }

    private class LinkNode {
        private LinkNode pre, next;
        private ListNode cur;

        LinkNode(LinkNode pre, ListNode cur) {
            this.pre = pre;
            this.cur = cur;
        }

        public void setNext(LinkNode next) {
            this.next = next;
        }
    }


    public ListNode deleteDuplicates(ListNode head) {
        if (Objects.isNull(head)) {
            return null;
        }
        ListNode pre = head, cur = head.next;
        while (Objects.nonNull(cur)) {
            int val = pre.val;
            if (val != cur.val) {
                pre = pre.next;
                cur = cur.next;
                continue;
            }
            while (Objects.nonNull(cur = cur.next) && cur.val == val) ;
            pre.next = cur;
            pre = pre.next;

        }
        return head;
    }

    @Test
    public void testDeleteDuplicates() {
        log.info("deleteDuplicates = {}", listPrint(deleteDuplicates(getHead(new int[]{
                1, 1, 2, 3, 4, 5, 6, 7, 7, 7, 8, 8, 8, 8, 9, 10, 11, 12, 14, 16, 16, 16, 17, 17, 20
        }))));
    }


    public ListNode sortList(ListNode head) {

        mergeSort(head, null);


        return head;
    }

    private void mergeSort(ListNode head, ListNode tail) {
        if (head == tail || head.next == tail)
            return;
        ListNode quick = head, slow = head, next;
        while (Objects.nonNull(quick)) {
            quick = quick.next;
            if (Objects.isNull(quick))
                break;
            quick = quick.next;
            slow = slow.next;
        }
        next = slow.next;
        slow.next = null;
        mergeSort(head, slow);
        mergeSort(next, tail);
        ListNode empty = new ListNode();
        while (head != slow && next != tail) {
            if (head.val > next.val) {
                empty.next = next;
                empty = empty.next;
                next = next.next;
                continue;
            }
            empty.next = head;
            empty = empty.next;
            head = head.next;
        }


    }


    @Test
    public void testSortList() {
        log.info("sortList = {}", listPrint(sortList(getHead(new int[]{
                -1, 5, 3, 4, 0
        }))));
    }


    @Test
    public void testReorderList() {
        ListNode head = getHead(new int[]{

        });
        head = null;
        reorderList(head);
        log.info("{}", listPrint(head));
        reorderList(null);
    }

    @Test
    public void test() {
        ListNode head = getHead(new int[]{1, 2});
        int bitCount = 1;
        for (ListNode cur = head; ; ++bitCount) {
            ListNode pre = cur;
            if (cur.next == null) {
                cur.next = new ListNode();
                break;
            }
            cur = cur.next;
        }
        log.info("bit = {}", bitCount);
    }

    @Test
    public void testReverseBetween() {
        log.info("ReverseBetween = {}", reverseBetween(getHead(new int[]{
                1, 2, 3, 4, 5
        }), 3, 4));
    }


    public ListNode deleteDuplicatesII(ListNode head) {
        // 空直接返回
        if (Objects.isNull(head)) {
            return null;
        }

        ListNode pre = head, cur = head.next, preDiff = new ListNode();
        // 保证preDiff和pre值永远不同
        preDiff.next = head;
        // 当前节点存在
        while (Objects.nonNull(cur)) {
            // 前一个节点值
            int val = pre.val;
            // 值不同更新指针,指针依次后移即可
            if (val != cur.val) {
                preDiff = pre;
                pre = pre.next;
                cur = cur.next;
                continue;
            }
            // 找到下一个不同节点,如果相同此时 [pre,cur)之间的节点需要去处
            while (Objects.nonNull(cur = cur.next) && cur.val == val) ;
            // 说明从head开始是重复的
            if (preDiff.next == head) {
                head = cur;
            }
            // 指向pre的前一个不同节点preDiff 指向 cur
            preDiff.next = cur;
            pre = cur;
            if (Objects.nonNull(cur)) {
                cur = cur.next;
            }

        }
        return head;
    }

    @Test
    public void testDeleteDuplicatesII() {
        log.info("deleteDuplicatesII = {}", listPrint(
                deleteDuplicatesII(
                        getHead(new int[]{
                                1, 1, 2, 2, 3, 3, 4, 4, 5, 5
                        })
                )
        ));
    }


    /**
     * //输入：head = [1,2,3,4,5], k = 1
     * //输出：[1,2,3,4,5]
     * <p>
     * //输入：head = [1,2,3,4,5], k = 2
     * //输出：[2,1,4,3,5]
     * <p>
     * //输入：head = [1,2,3,4,5], k = 3
     * //输出：[3,2,1,4,5]
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        /**
         * 从节点cur - 节点 nextK 之间的 k个节点
         * 是一条单独的链表翻转
         * 翻转过后 cur 指向 nextK
         *
         * cur（包含） - nextK (包含) 满足 k个节点
         *
         * next 指向 翻转前的 nextK的下一个节点
         *
         *
         *
         */
        if (k==1){
            return head;
        }
        int stamp = 1;
        ListNode cur = head, nextK = head, preStart = head, nextStart = null, start = head, curNext, pre;
        while (nextK != null) {
            nextK = nextK.next;
            stamp++;
            // 继续移动指针
            if (nextK == null) {
                start.next = nextStart;
                break;
            }
            if (stamp != k) {
                continue;
            }
            preStart = start;
            // 存在k个节点进行翻转操作
            // 前一组翻转和此组翻转
            start = cur;
            pre = null;
            while (cur != nextK) {
                // 当前节点的下个节点
                curNext = cur.next;
                // 当前节点指向前一个
                cur.next = pre;
                // 更新前一个节点
                pre = cur;
                // 更新当前节点
                cur = curNext;

            }

            // 指向下个节点
            stamp = 1;
            nextK = cur.next;
            nextStart = nextK;
            cur.next = pre;
            // 更新前一个节点
            pre = cur;
            cur = nextK;
            if (preStart == head) {
                head = pre;
            }else {
                preStart.next = pre;
            }

        }

        return head;
    }

    @Test
    public void testReverseKGroup() {
        log.info("reverseKGroup = {}", listPrint(
                reverseKGroup(
                        getHead(new int[]{
                                1, 2, 3, 4, 5,6,7
                        }), 7
                )
        ));
    }


    private List<Integer> listPrint(ListNode head) {
        ListNode pre = head;
        List<Integer> list = new ArrayList<>();
        while (Objects.nonNull(pre)) {
            list.add(pre.val);
            pre = pre.next;
        }
        return list;
    }

    private static ListNode getHead(int[] node) {
        ListNode head = new ListNode(node[0]), pre = head;
        for (int i = 1; i < node.length; i++) {
            pre.next = new ListNode(node[i]);
            pre = pre.next;
        }
        return head;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
