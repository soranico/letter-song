package com.kanozz.link;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

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

    /**
     * 143
     * @param head
     */
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
        if (k == 1) {
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
            } else {
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
                                1, 2, 3, 4, 5, 6, 7
                        }), 7
                )
        ));
    }

    /**
     * //输入：head = [1,2,3,4]
     * //输出：[2,1,4,3]
     */
    public ListNode swapPairs(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = new ListNode(), curPre = head, cur = head.next, next;
        boolean first = true;
        while (cur != null) {
            next = cur.next;
            curPre.next = null;
            cur.next = curPre;
            pre.next = cur;
            if (pre != head)
                pre = curPre;
            if (first) {
                first = false;
                head = cur;
            }
            curPre = next;
            if (curPre == null) {
                break;
            }
            cur = curPre.next;
        }

        if (curPre != null) {
            pre.next = curPre;
        }

        return head;
    }

    @Test
    public void testSwapPairs() {
        log.info("swapPairs = {}", listPrint(swapPairs(getHead(new int[]{
                1, 2, 3, 4, 5, 6, 7, 8, 9
        }))));
    }

    @SuppressWarnings("all")
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        Node copyHead = new Node(head.val), copyPre = copyHead, copyCur;
        Node cur = head.next;
        Map<Node, Node> cacheMap = new HashMap<>(16);
        cacheMap.put(head, copyHead);
        copyHead.random = cacheMap.computeIfAbsent(head.random, key->key == null ? null : new Node(key.val));
        while (cur != null) {
            // 已经构建过复用
            copyCur = cacheMap.getOrDefault(cur, new Node(cur.val));
            cacheMap.put(cur, copyCur);
            copyCur.random = cacheMap.computeIfAbsent(cur.random, key->key == null ? null : new Node(key.val));
            copyPre.next = copyCur;
            copyPre = copyCur;
            cur = cur.next;
        }
        return copyHead;
    }


    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    @Test
    public void testCopyRandomList() {
//        [7,null],[13,0],[11,4],[10,2],[1,0]
        Node head = new Node(7);
        Node next = new Node(13);

        next.random = head;
        head.next = next;

        Node nextNext = new Node(11);
        next.next = nextNext;

        Node nextNextNext = new Node(10);
        Node nextNextNextNext = new Node(1);

        nextNext.next = nextNextNext;
        nextNext.random = nextNextNextNext;

        nextNextNext.next = nextNextNextNext;
        nextNextNext.random = nextNext;

        nextNextNextNext.random = head;

        log.info("copyRandomList = {}", copyRandomList(head));
    }


    /**
     * // 输入: 1->2->3->4->5->NULL
     * //输出: 1->3->5->2->4->NULL
     *
     * // 输入: 2->1->3->5->6->4->7->NULL
     * //输出: 2->3->6->7->1->5->4->NULL
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null){
            return null;
        }
        ListNode odd = head ,even = head.next,
                cur = head.next,oddCur = odd,evenCur = even;
        int curIndex = 1;
        while (cur != null){
            cur = cur.next;
            /**
             * 偶数节点
             */
            if (curIndex++ %2 == 0){
                evenCur.next = cur;
                // 最后一个节点为偶节点 不做任何操作
                evenCur = cur;

            }else {
                // 最后一个节点是奇节点
                if (cur == null){
                    evenCur.next = null;
                }else {
                    oddCur.next = cur;
                    oddCur = cur;
                }
            }
        }

        oddCur.next = even;
        // 将奇偶链接
        return odd;
    }

    @Test
    public void testOddEvenList(){
        log.info("oddEvenList = {}",listPrint(
                oddEvenList(
                getHead(new int[]{
                  1,2,3,4,5
                })
        ))
        );
    }


    /**
     * 86 , 面试题 02.04
     //输入：head = [1,4,3,2,5,2], x = 3
     //输出：[1,2,2,4,3,5]

     //输入：head = [2,1], x = 2
     //输出：[1,2]
     */
    public ListNode partition(ListNode head, int x) {
        if (head == null){
            return null;
        }
        ListNode ltNode = new ListNode(),gtNode = new ListNode(),
                cur = head, ltCur = ltNode, gtCur = gtNode;
        while (cur != null){
            if (cur.val >= x){
                gtCur.next = cur;
                gtCur = cur;
            }else {
                /**
                 * 最后一个节点是小于指定数的节点
                 * 说明上一个大于指定数的节点存在
                 * 下个节点避免死环,这个时候需要断开连接
                 */
                if (cur.next == null){
                    gtCur.next = null;
                }
                ltCur.next = cur;
                ltCur = cur;
            }
            cur = cur.next;
        }
        ltCur.next = gtNode.next;
        return ltNode.next;

    }

    @Test
    public void testPartition(){
        log.info("partition = {}",listPrint(partition(getHead(new int[]{
                2,1
        }),2
        )));
    }


    /**
     * 876
     * 如果有两个中间结点，则返回第二个中间结点。
     *
     * //输入：[1,2,3,4,5]
     * //输出：此列表中的结点 3 (序列化形式：[3,4,5])
     *
     * //输入：[1,2,3,4,5,6]
     * //输出：此列表中的结点 4 (序列化形式：[4,5,6])
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        ListNode quick = head,slow = head;
        /**
         * 快指针每次走两步
         * 慢指针每次走一步
         * 快指针走到尾时慢指针指向中间节点
         */
        while (slow != null && slow.next !=null){
            quick = quick.next;
            slow = slow.next.next;
        }

        return quick;

    }

    @Test
    public void testMiddleNode(){
        log.info("middleNode = {}",middleNode(
                getHead(new int[]{
                        1,2,3,4
                })
        ));
    }


    /**
     * 21
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1==null){
            return l2;
        }else if (l2==null){
            return l1;
        }
        ListNode head = l1.val >= l2.val ? l2 : l1;
        ListNode pre = head;
        ListNode current ;
        if (head == l1) {
            l1 = l1.next;
        } else {
            l2 = l2.next;
        }
        while (l1 != null && l2 != null) {
            if (l1.val >= l2.val) {
                current = l2;
                l2 = l2.next;
            } else {
                current = l1;
                l1 = l1.next;
            }
            pre.next = current;
            pre = pre.next;

        }
        pre.next = l1 == null ? l2 : l1;

        return head;
    }

    /**
     * 23
     * 合并k个升序链表
     * @param lists
     * @return
     */
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


    private ListNode merge(ListNode first ,ListNode second){
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

    @Test
    public void testMergeKLists(){
        // [2,6]
        log.info("mergeKLists = {}",listPrint(mergeKLists(new ListNode[]{
                getHead(new int[]{1,4,5}),getHead(new int[]{1,4,5}),
                getHead(new int[]{4,5}),getHead(new int[]{5}),getHead(new int[]{1})
        })));
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
        if (node.length == 0) {
            return null;
        }
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


    /**
     * 148
     * pass
     * 排序链表
     */
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




    @Test
    public void testSortList() {
        log.info("sortList = {}", listPrint(sortList(getHead(new int[]{
               9,0,7,10,-1,-20,80,67
        }))));
    }

    /**
     * 445 两数相加||
     * pass
     * 翻转链表,每位上进行简单的数字相加,注意进位即可
     */
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
            if (l1.val + l2.val + carry >= 10){
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

    @Test
    public void testAddTwoNumbers(){
        log.info("addTwoNumbers = {}",listPrint(addTwoNumbers(getHead(
                new int[]{
                       0
                })
        ,getHead(
                new int[]{
                       5,6,4
                }
                ))));
    }


    /**
     * 109
     * 有序链表转为二叉搜索树
     *
     * pass
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null){
            return null;
        }
        /**
         * 因为要求二叉搜索树平衡
         * 因此对链表进行每次查询
         * 中间节点,每次查询效率过低
         * 如果空间允许的话先转为数组
         */
        List<ListNode> nodeList = new ArrayList<>();
        while (head!=null){
            nodeList.add(head);
            head = head.next;
        }
        ListNode[] nodeArr = nodeList.toArray(new ListNode[0]);
        return sortedListToBST(nodeArr,0,nodeArr.length-1);
    }

    /**
     * 递归构建二叉树
     */
    private TreeNode sortedListToBST(ListNode[] nodeArr,int start,int end){
        /**
         * 没有子节点说明此时直接为 null
         */
        if (start > end){
            return null;
        }
        int middle = start + ((end  - start) >>>1);
        /**
         * 中间节点作为根节点
         * 对于当前根节点而言 分别构建其左右孩子均可
         */
        TreeNode root = new TreeNode(nodeArr[middle].val);
        /**
         * 构建左孩子
         */
        root.left = sortedListToBST(nodeArr,start,middle-1);
        /**
         * 构建右孩子
         */
        root.right = sortedListToBST(nodeArr,middle+1,end);
        return root;
    }


    @Data
    @Accessors(chain = true)
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left,TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }

    @Test
    public void testSortedListToBST(){
        log.info("sortedListToBST = {}",sortedListToBST(
                getHead(new int[]{-10, -3, 0, 5, 9,10})
        ));
    }


    /**
     * 面试题 02.05
     * 链表求和
     *
     * 链表初始是逆序的,结果也是逆序的
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbersAudition(ListNode l1, ListNode l2) {
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

    @Test
    public void testAddTwoNumbersAudition(){
        log.info("addTwoNumbersAudition = {}",listPrint(
                addTwoNumbersAudition(
                        getHead(new int[]{
                                6,1,7
                        }),
                        getHead(new int[]{
                                2,9,5
                        })
                )
        ));
    }


}
