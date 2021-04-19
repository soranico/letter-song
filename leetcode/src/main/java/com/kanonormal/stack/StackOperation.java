package com.kanonormal.stack;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class StackOperation {


    /**
     * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
     * 144
     * 输入：root = [1,null,2,3]
     * 输出：[1,2,3]
     *
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        // 空树直接返回
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        Stack<TreeNode> treeStack = new Stack<>();
        List<Integer> nodesVal = new ArrayList<>();
        treeStack.push(root);
        while (!treeStack.isEmpty()) {
            // 弹出栈顶节点
            TreeNode curNode = treeStack.pop();
            nodesVal.add(curNode.val);
            // 右孩子进栈
            if (Objects.nonNull(curNode.right)) {
                treeStack.push(curNode.right);
            }
            // 左孩子进栈
            if (Objects.nonNull(curNode.left)) {
                treeStack.push(curNode.left);
            }
        }

        return nodesVal;
    }


    /**
     * [1,4,2,3,5,6,8,10]
     * <p>
     * [10,3,5,4,6,8,2,1]
     * <p>
     * 栈后序遍历
     *
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        if (Objects.isNull(root)) {
            return Collections.emptyList();
        }
        List<Integer> nodesVal = new ArrayList<>();
        // 左右根
        Stack<TreeNode> treeStack = new Stack<>();
        TreeNode curRoot = root;
        // 根节点入栈
        treeStack.push(root);
        while (Objects.nonNull(curRoot) || !treeStack.isEmpty()) {
            // 左孩子入栈
            while (Objects.nonNull(curRoot)) {
                if (Objects.nonNull(curRoot.left)) {
                    treeStack.push(curRoot.left);
                }
                curRoot = curRoot.left;
            }
            // 取出当前最后一个左孩子
            TreeNode curNode = treeStack.peek();
            // 当前节点没有右孩子
            if (Objects.isNull(curNode.right)) {
                nodesVal.add(treeStack.pop().val);
                continue;
            }
            // 存在右孩子,右孩子入栈,同时断开父子关系,因为当前节点还没有出栈
            curRoot = curNode.right;
            treeStack.push(curNode.right);
            curNode.right = null;
        }
        return nodesVal;
    }

    public static void main(String[] args) throws Exception {

        log.info("{}", postorderTraversal(createTree(new Integer[]{
                1, 4, 2, 3, 5, 6, 8, 10
        })));


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


}
