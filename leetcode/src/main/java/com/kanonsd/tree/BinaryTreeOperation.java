package com.kanonsd.tree;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class BinaryTreeOperation {


    /**
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。
     * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * 例如：
     * 给定二叉树[3,9,20,null,null,15,7],
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * 返回锯齿形层序遍历如下：
     * <p>
     * [
     * [3],
     * [20,9],
     * [15,7]
     * ]
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        List<List<Integer>> valList = new ArrayList<>();
        LinkedList<TreeNode> nodeList = new LinkedList<>();
        nodeList.add(root);
        boolean left2Right = true;
        /**
         * 1. 当前层从左往右,子节点左右顺序入栈,则出栈顺序为右左,满足
         * 2. 当前层右左,下层左右,子节点右左入栈,则出栈顺序为左右
         */
        while (!nodeList.isEmpty()) {
            List<Integer> curValList = new ArrayList<>();
            // 入栈的索引,去除当前遍历层的头插法
            int pushIndex = nodeList.size() - 1;
            for (int i = nodeList.size() - 1; i >= 0; i--) {
                TreeNode curNode = nodeList.remove();
                curValList.add(curNode.val);
                // 当前层从左往右,则孩子入栈为左右,出栈为右左
                TreeNode first = left2Right ? curNode.left : curNode.right;
                if (Objects.nonNull(first)) {
                    nodeList.add(pushIndex, first);
                }
                TreeNode second = left2Right ? curNode.right : curNode.left;
                if (Objects.nonNull(second)) {
                    nodeList.add(pushIndex, second);
                }
                pushIndex--;
            }
            valList.add(curValList);
            left2Right = !left2Right;
        }
        return valList;
    }


    /**
     * 给定一棵二叉树，想象自己站在它的右侧，
     * 按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * 输入:[1,2,3,null,5,null,4]
     * 输出:[1, 3, 4]
     * 解释:
     * <p>
     * 1            <---
     * /   \
     * 2     3         <---
     * \     \
     * 5     4       <---
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        BiConsumer<TreeNode, LinkedList<TreeNode>> consumer = (cur, list) -> {
            if (Objects.nonNull(cur)) {
                list.addFirst(cur);
            }
        };
        LinkedList<TreeNode> depList = new LinkedList<>();
        depList.add(root);
        List<Integer> seeValList = new ArrayList<>();
        while (!depList.isEmpty()) {
            seeValList.add(depList.getLast().val);
            for (int i = depList.size() - 1; i >= 0; i--) {
                TreeNode cur = depList.removeLast();
                consumer.accept(cur.right, depList);
                consumer.accept(cur.left, depList);
            }
        }


        return seeValList;
    }


    public boolean isBalanced(TreeNode root) {
        List<TreeNode> levelList = new ArrayList<>();

        Consumer<TreeNode> consumer = cur -> {
            if (Objects.nonNull(cur)) {
                levelList.add(cur);
            }
        };
        consumer.accept(root);
        while (!levelList.isEmpty()) {
            for (int i = levelList.size() - 1; i >= 0; i--) {
                TreeNode cur = levelList.remove(i);
                int leftDep = maxDep(cur.left);
                int rightDep = maxDep(cur.right);
                if (Math.abs(leftDep - rightDep) > 1) {
                    return false;
                }
                consumer.accept(cur.right);
                consumer.accept(cur.left);
            }
        }
        return true;
    }

    private Map<TreeNode, Integer> depMap = new HashMap<>();

    private int maxDep(TreeNode root) {

        if (Objects.isNull(root)) {
            return 0;
        }
        if (depMap.containsKey(root)) {
            return depMap.get(root);
        }
        int maxDep = Math.max(maxDep(root.right), maxDep(root.left)) + 1;
        depMap.put(root, maxDep);
        return maxDep;
    }

    @Test
    public void testIsBalanced() {
        log.info("balance = {}", isBalanced(
                createTree(new Integer[]{
                        1, 2, 2, 3, 3, null, null, 4, 4
                })
        ));
    }

    @Test
    public void testRightSideView() {
        rightSideView(createTree(new Integer[]{
                1, 2, 3, null, 5, null, 4, 6, null, 7
        }));
    }


    @Test
    public void testZigzagLevelOrder() {
        // [[3],[20,9],[3,15,7]]
        log.info("zigzagLevelOrder = {}", zigzagLevelOrder(createTree(new Integer[]{
                3, 9, 20, 3, null, 15, 7
        })));
    }


    @Test
    public void testIsValidBST() {
        log.info("isValidBST = {}", isValidBST(createTree(new Integer[]{
                Integer.MIN_VALUE
//                2,1,3,null,null,2
//                1,1
        })));
    }

    public boolean isValidBST(TreeNode root) {
        LinkedList<TreeNode> ldr = new LinkedList<>();

        //       F-D-H-G-I-B-E-A-C
        long pre = Long.MIN_VALUE;
        TreeNode cur = root;
        while ( Objects.nonNull(cur) || !ldr.isEmpty() ){
            // 当前节点存在左节点需要判断左子树是否存在,直到找到不存在左节点
            while (Objects.nonNull(cur)){
                ldr.add(cur);
                cur = cur.left;
            }
            // 没有左节点,移除当前节点并与前一个节点值对比
            cur = ldr.removeLast();
            if(cur.val <= pre){
                return false;
            }
            pre = cur.val;
            // 指向最后一个左节点的右子树
            cur = cur.right;
        }
        return true;
    }


    public Node treeToDoublyList(Node root) {
        LinkedList<Node> ldr = new LinkedList<>();
        Node cur = root,pre = new Node(),head = pre;
        while (Objects.nonNull(cur) || !ldr.isEmpty()){
            // 节点入队
            while (Objects.nonNull(cur)){
                ldr.add(cur);
                cur = cur.left;
            }
            cur = ldr.removeLast();
            cur.left = pre;
            pre.right = cur;
            pre = cur;
            cur = cur.right;
        }
        if (pre != head){
            head.right.left = pre;
            pre.right  = head.right;
        }

        return head.right;
    }

    @Test
    public void testTreeToDoublyList(){
        treeToDoublyList(createNode(new Integer[]{
                4,2,5,1,3
        }));
    }


    @Data
    @Accessors(chain = true)
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }


    @Data
    @Accessors(chain = true)
    private static class Node {
        int val;
        Node left;
        Node right;

        Node() {
        }

        Node(int val) {
            this.val = val;
        }

        Node(int val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }

    }


    public static TreeNode createTree(Integer[] arr) {
        // 使用队列来存储每一层的非空节点，下一层的数目要比上一层高
        ArrayDeque<TreeNode> pre = new ArrayDeque<>();
        TreeNode root = new TreeNode(arr[0]);
        pre.addLast(root);
        // 表示要遍历的下一个节点
        int index = 0;
        while (!pre.isEmpty()) {

            ArrayDeque<TreeNode> cur = new ArrayDeque<>();
            while (!pre.isEmpty()) {
                TreeNode node = pre.removeFirst();
                TreeNode left = null;
                TreeNode right = null;
                // 如果对应索引上的数组不为空的话就创建一个节点,进行判断的时候，
                // 要先索引看是否已经超过数组的长度，如果索引已经超过了数组的长度，那么剩下节点的左右子节点就都是空了
                // 这里index每次都会增加，实际上是不必要的，但是这样写比较简单
                if (++index < arr.length && arr[index] != null) {
                    left = new TreeNode(arr[index]);
                    cur.addLast(left);
                }
                if (++index < arr.length && arr[index] != null) {
                    right = new TreeNode(arr[index]);
                    cur.addLast(right);
                }
                node.left = left;
                node.right = right;
            }
            pre = cur;
        }


        return root;
    }


    public static Node createNode(Integer[] arr) {
        // 使用队列来存储每一层的非空节点，下一层的数目要比上一层高
        ArrayDeque<Node> pre = new ArrayDeque<>();
        Node root = new Node(arr[0]);
        pre.addLast(root);
        // 表示要遍历的下一个节点
        int index = 0;
        while (!pre.isEmpty()) {

            ArrayDeque<Node> cur = new ArrayDeque<>();
            while (!pre.isEmpty()) {
                Node node = pre.removeFirst();
                Node left = null;
                Node right = null;
                // 如果对应索引上的数组不为空的话就创建一个节点,进行判断的时候，
                // 要先索引看是否已经超过数组的长度，如果索引已经超过了数组的长度，那么剩下节点的左右子节点就都是空了
                // 这里index每次都会增加，实际上是不必要的，但是这样写比较简单
                if (++index < arr.length && arr[index] != null) {
                    left = new Node(arr[index]);
                    cur.addLast(left);
                }
                if (++index < arr.length && arr[index] != null) {
                    right = new Node(arr[index]);
                    cur.addLast(right);
                }
                node.left = left;
                node.right = right;
            }
            pre = cur;
        }


        return root;
    }

}
