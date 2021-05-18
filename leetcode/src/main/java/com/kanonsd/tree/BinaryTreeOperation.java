package com.kanonsd.tree;

import com.kanonsd.stack.StackOperation;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class BinaryTreeOperation {


    /**
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。
     * （即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
     * 例如：
     * 给定二叉树[3,9,20,null,null,15,7],
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回锯齿形层序遍历如下：
     *
     * [
     *   [3],
     *   [20,9],
     *   [15,7]
     * ]
     *
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (Objects.isNull(root)){
            return Collections.emptyList();
        }
        List<List<Integer>> valList =new ArrayList<>();
        LinkedList<TreeNode> nodeList = new LinkedList<>();
        nodeList.add(root);
        boolean left2Right = true;
        /**
         * 1. 当前层从左往右,子节点左右顺序入栈,则出栈顺序为右左,满足
         * 2. 当前层右左,下层左右,子节点右左入栈,则出栈顺序为左右
         */
        while (!nodeList.isEmpty()){
            List<Integer> curValList = new ArrayList<>();
            // 入栈的索引,去除当前遍历层的头插法
            int pushIndex = nodeList.size()-1;
            for (int i = nodeList.size() - 1; i >= 0; i--) {
                TreeNode curNode = nodeList.remove();
                curValList.add(curNode.val);
                // 当前层从左往右,则孩子入栈为左右,出栈为右左
                TreeNode first = left2Right?curNode.left:curNode.right;
                if (Objects.nonNull(first)){
                    nodeList.add(pushIndex,first);
                }
                TreeNode second = left2Right?curNode.right:curNode.left;
                if (Objects.nonNull(second)){
                    nodeList.add(pushIndex,second);
                }
                pushIndex--;
            }
            valList.add(curValList);
            left2Right=!left2Right;
        }
        return valList;
    }


    @Test
    public void testZigzagLevelOrder(){
        // [[3],[20,9],[3,15,7]]
        log.info("zigzagLevelOrder = {}",zigzagLevelOrder(createTree(new Integer[]{
                3,9,20,3,null,15,7
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
