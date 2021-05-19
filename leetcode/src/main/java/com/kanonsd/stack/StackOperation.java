package com.kanonsd.stack;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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


    /**
     * 71
     * 给你一个字符串 path ，表示指向某一文件或目录的Unix 风格 绝对路径 （以 '/' 开头），请你将其转化为更加简洁的规范路径。
     * <p>
     * 在 Unix 风格的文件系统中，一个点（.）表示当前目录本身；此外，两个点 （..）
     * 表示将目录切换到上一级（指向父目录）；两者都可以是复杂相对路径的组成部分。
     * 任意多个连续的斜杠（即，'//'）都被视为单个斜杠 '/' 。 对于此问题，任何其他格式的点（例如，'...'）均被视为文件/目录名称。
     * <p>
     * 请注意，返回的 规范路径 必须遵循下述格式：
     * <p>
     * 始终以斜杠 '/' 开头。
     * 两个目录名之间必须只有一个斜杠 '/' 。
     * 最后一个目录名（如果存在）不能 以 '/' 结尾。
     * 此外，路径仅包含从根目录到目标文件或目录的路径上的目录（即，不含 '.' 或 '..'）。
     * 返回简化后得到的 规范路径 。
     * <p>
     * 输入：path = "/home/"
     * 输出："/home"
     * 解释：注意，最后一个目录名后面没有斜杠。
     * <p>
     * 输入：path = "/a/./b/../../c/"
     * 输出："/c"
     *
     * @param path
     * @return
     */
    public static String simplifyPath(String path) {

        Stack<Character> pathStack = new Stack<>();
        char[] pathArray = path.toCharArray();
        int max = pathArray.length;
        pathStack.push('/');
        for (int i = 1; i < pathArray.length; i++) {
            if (pathArray[i] == '.' && i + 1 < max) {
                if (pathArray[i + 1] == '/') {
                    i += 1;
                    continue;
                } else if ((pathArray[i + 1] == '.' && i + 2 < max && pathArray[i + 2] == '/')
                        || (pathArray[i + 1] == '.' && i + 2 >= max)) {
                    if (pathStack.size() == 1) {
                        continue;
                    }
                    pathStack.pop();
                    while (pathStack.size() > 1 && pathStack.peek() != '/') {
                        pathStack.pop();
                    }
                    i += 2;
                    continue;
                }
            } else if (pathArray[i] == '.' && i + 1 >= max) {
                continue;
            }
            while (i < max && pathArray[i] != '/') {
                pathStack.push(pathArray[i]);
                i++;
            }
            if (i >= max) {
                break;
            }
            if (pathStack.size() > 0 && pathStack.peek() == '/') {
                continue;
            }
            pathStack.push(pathArray[i]);
        }
        if (pathStack.isEmpty()) {
            return null;
        } else if (pathStack.peek() == '/' && pathStack.size() > 1) {
            pathStack.pop();
        }
        StringBuilder sb = new StringBuilder(pathStack.size());
        while (!pathStack.isEmpty()) {
            sb.append(pathStack.pop());
        }

        return sb.reverse().toString();
    }

    /**
     * 150
     * 根据 逆波兰表示法，求表达式的值。
     * <p>
     * 有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     * <p>
     * 说明：
     * <p>
     * 整数除法只保留整数部分。
     * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     * <p>
     * 输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
     * 输出：22
     * 解释：
     * 该算式转化为常见的中缀算术表达式为：
     * ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
     * = ((10 * (6 / (12 * -11))) + 17) + 5
     * = ((10 * (6 / -132)) + 17) + 5
     * = ((10 * 0) + 17) + 5
     * = (0 + 17) + 5
     * = 17 + 5
     * = 22
     *
     * @param tokens
     * @return
     */
    public static int evalRPN(String[] tokens) {
        Stack<String> evalStack = new Stack<>();
        String add = "+", sub = "-", multi = "*", div = "/";
        for (int i = 0; i < tokens.length; i++) {
            String eval = tokens[i];
            if (add.equals(eval)) {
                Integer num1 = Integer.parseInt(evalStack.pop());
                Integer num2 = Integer.parseInt(evalStack.pop());
                evalStack.push(String.valueOf(num1 + num2));
            } else if (sub.equals(eval)) {
                Integer num1 = Integer.parseInt(evalStack.pop());
                Integer num2 = Integer.parseInt(evalStack.pop());
                evalStack.push(String.valueOf(num2 - num1));
            } else if (multi.equals(eval)) {
                Integer num1 = Integer.parseInt(evalStack.pop());
                Integer num2 = Integer.parseInt(evalStack.pop());
                evalStack.push(String.valueOf(num1 * num2));
            } else if (div.equals(eval)) {
                Integer num1 = Integer.parseInt(evalStack.pop());
                Integer num2 = Integer.parseInt(evalStack.pop());
                evalStack.push(String.valueOf(num2 / num1));
            } else {
                evalStack.push(eval);
            }
        }
        return Integer.parseInt(evalStack.pop());
    }


    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，
     * 计算按此排列的柱子，下雨之后能接多少雨水。
     */
    public int trap(int[] height) {
        int w = 1;
        /**
         * 1.找到第一个不为0的数
         * 2.
         */

        return -1;
    }






    @Test
    public void testEvalRPN() {
        log.info("eval = {}", evalRPN(new String[]{
                "4", "13", "5", "/", "+"
        }));

    }

    @Test
    public void testSimplifyPath() {
        log.info("path = {}", simplifyPath("/a/.../b../."));
    }

    @Test
    public void testPostorderTraversal() {
        log.info("post = {} ", postorderTraversal(createTree(new Integer[]{1, 2, 3, 4, 55, 6, null, 0})));
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
