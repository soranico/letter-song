package com.kanozz.stack;

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
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * <p>
     * 整数除法仅保留整数部分。
     * <p>
     * 输入：s = " 3+5 / 2 "
     * 输出：5
     * 0x2B
     * 0x2D
     * 0x2A
     * 0x2F
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        Stack<Character> opStack = new Stack<>();
        Stack<Integer> numStack = new Stack<>();
        char[] calArray = s.toCharArray();
        for (int i = 0; i < calArray.length; i++) {
            char cal = calArray[i];
            if (cal == ' ') {
                continue;
            }
            if (cal >= 0x30 && cal <= 0x39) {
                int[] cals = nextNum(calArray, i);
                numStack.push(cals[1]);
                i = cals[0];
            } else if (cal == 0x2A || cal == 0x2F) {
                int num1 = numStack.pop();
                int[] nextNum = nextNum(calArray, i + 1);
                int num2 = nextNum[1];
                i = nextNum[0];
                numStack.push(cal(num1, num2, cal));
            }
            // 如果栈中没有操作符那么操作符直接入栈,如果占中已经存在操作符,那么弹出两个操作数和操作符计算入栈
            else if (opStack.isEmpty()) {
                opStack.push(cal);
            } else {
                int num2 = numStack.pop();
                int num1 = numStack.pop();
                numStack.push(cal(num1, num2, opStack.pop()));
                opStack.push(cal);
            }
        }
        if (!opStack.isEmpty()) {
            int num2 = numStack.pop();
            int num1 = numStack.pop();
            return cal(num1, num2, opStack.pop());
        }
        return numStack.pop();

    }

    private int[] nextNum(char[] calArray, int start) {
        StringBuilder numBuilder = new StringBuilder();
        while (calArray[start] == ' ') {
            start++;
        }
        while (start < calArray.length
                && calArray[start] >= 0x30 && calArray[start] <= 0x39) {
            numBuilder.append(calArray[start++]);
        }
        return new int[]{start - 1, Integer.parseInt(numBuilder.toString())};
    }

    private int cal(int num1, int num2, char op) {
        int result;
        if (op == '+') {
            result = num1 + num2;
        } else if (op == '-') {
            result = num1 - num2;
        } else if (op == '*') {
            result = num1 * num2;
        } else {
            result = num1 / num2;
        }
        return result;
    }


    /**
     * 请你仅使用两个队列实现一个后入先出（LIFO）的栈，并支持普通队列的全部四种操作（push、top、pop 和 empty）。
     * <p>
     * 实现 MyStack 类：
     * <p>
     * void push(int x) 将元素 x 压入栈顶。
     * int pop() 移除并返回栈顶元素。
     * int top() 返回栈顶元素。
     * boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。
     * <p>
     * 注意：
     * <p>
     * 你只能使用队列的基本操作 ——
     * 也就是push to back、peek/pop from front、size 和is empty这些操作。
     */
    private static class MyStack {
        private ArrayDeque<Integer> first;
        private ArrayDeque<Integer> second;

        /**
         * Initialize your data structure here.
         */
        public MyStack() {
            this.first = new ArrayDeque<>();
            this.second = new ArrayDeque<>();
        }

        /**
         * Push element x onto stack.
         */
        public void push(int x) {
            if (first.isEmpty()) {
                first.push(x);
                return;
            }
            /**
             *
             * 当前有元素 1 则1 出队后 2入队 然后1入队
             * 当前有元素 2 1 则 需要 2 1出队 后 3入队 然后按照 2 1入队
             * 将 2 1看为一个整体的话,即每次将所有元素依次出队，然后新元素
             * 入队，再整体入队
             *
             */
            while (!first.isEmpty()) {
                second.add(first.pop());
            }
            // 新元素入队
            first.push(x);
            while (!second.isEmpty()) {
                first.add(second.pop());
            }

        }

        /**
         * Removes the element on top of the stack and returns that element.
         */
        public int pop() {
            return first.pop();
        }

        /**
         * Get the top element.
         */
        public int top() {
            return first.peek();
        }

        /**
         * Returns whether the stack is empty.
         */
        public boolean empty() {
            return first.isEmpty();
        }
    }

    @Test
    public void testMyStack() {
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);
    }


    /**
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值
     * <p>
     * 1 <= s.length <= 3 * 105
     * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
     * s 表示一个有效的表达式
     * <p>
     * 输入：s = "(1+(4+5+2)-3)+(6+8)"
     * 输出：23
     */
    public int calculateHard(String s) {
        char[] calArray = s.toCharArray();
        ArrayDeque<Integer> calNumStack = new ArrayDeque<>();
        ArrayDeque<Character> calOpStack = new ArrayDeque<>();

        /**
         * 遇到 ( 数字依次入栈 ，操作符依次入入栈
         * 如果操作符为 - 则将操作数转为负数，然后入栈 +
         *
         * 5-3 = -3+5
         *
         * +5 + -5
         *
         */
        boolean transformNum = false;
        // 计算符个数
        int opNum = 0;
        for (int i = 0; i < calArray.length; i++) {
            char cal = calArray[i];
            if (cal == ' ') {
                continue;
            }
            // 数字入栈
            if (cal >= 0x30 && cal <= 0x39) {
                int[] nums = nextNum(calArray, i);
                if (transformNum) {
                    nums[1] = -nums[1];
                    transformNum = false;
                }
                calNumStack.push(nums[1]);
                i = nums[0];
                continue;
            }
            // 下个非空字符
            int start = i;
            while (start+1 < calArray.length && calArray[start+1] == ' '){
                start++;
            }
            if (start+1 < calArray.length){
                char nextOp = calArray[start+1];
                // ++ +- 忽略当前操作符即可 -+ -- 修改下个操作符
                if (cal=='+' && (nextOp=='+'||nextOp=='-')){
                    i = start;
                    continue;
                }
                if (cal == '-' && (nextOp=='-' || nextOp=='+')){
                    calArray[start+1]=nextOp=='+'?'-':'+';
                    continue;
                }
            }
            if (cal == '-') {
                transformNum = true;
                cal = '+';
            }
            if (cal != ')') {
                if (calNumStack.size()!= opNum+1 && cal=='+'){
                    calNumStack.push(0);
                }
                if (cal=='+'){
                    opNum++;
                }
                calOpStack.push(cal);
                continue;
            }
            // 计算 0+3 0+8+3+4+4 n+1
            opNum-=calHard(calOpStack,calNumStack);

        }
        calHard(calOpStack,calNumStack);
        return calNumStack.pop();
    }

    private int calHard(ArrayDeque<Character> calOpStack,ArrayDeque<Integer> calNumStack){
        int loop = 0;
        while (!calOpStack.isEmpty() && calOpStack.peek() != '(') {
            loop++;
            calOpStack.pop();
        }
        if (!calOpStack.isEmpty()){
            calOpStack.pop();
        }
        int opNUm=loop;
        while (loop > 0) {
            int num1 = calNumStack.pop();
            int num2 = calNumStack.pop();
            calNumStack.push(num1 + num2);
            loop--;
        }
        return opNUm;
    }


    /**
     * 402
     * 给定一个以字符串表示的非负整数num，移除这个数中的 k 位数字，使得剩下的数字最小。
     *
     * 注意:
     *
     * num 的长度小于 10002 且≥ k。
     * num 不会包含任何前导零。
     *
     * 输入: num = "10", k = 2
     * 输出: "0"
     * 解释: 从原数字移除所有的数字，剩余为空就是0。
     *
     * 输入: num = "10200", k = 1
     * 输出: "200"
     * 解释: 移掉首位的 1 剩下的数字为 200. 注意输出不能有任何前导零。
     *
     * 输入: num = "1432219", k = 3
     * 输出: "1219"
     * 解释: 移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219。
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {

        return null;
    }

    /**
     * 503
     * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
     *
     * 示例 1:
     *
     * 输入: [1,2,1]
     * 输出: [2,-1,2]
     * 解释: 第一个 1 的下一个更大的数是 2；
     * 数字 2 找不到下一个更大的数；
     * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] nextEle = new int[nums.length];
        int[] sort = nums.clone();
        Arrays.sort(sort);
        for (int i = 0; i < nums.length; i++) {

        }


        return null;
    }

    @Test
    public void testRemoveKdigits(){
        log.info("removeKdigits = {}",removeKdigits("10200",2));
    }

    @Test
    public void testCalculateHard() {
        // "- (3 + (4 + 5))"
        log.info("calculateHard = {}", calculateHard("- (3 + (4 + 5))"));
    }

    @Test
    public void testCalculate() {
        log.info("calculate = {}", calculate(" 3+5 / 2 "));
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


    private static class MinStack {

        private final ArrayDeque<Integer> stackData;
        private  final ArrayDeque<Integer> stackMin;


        /** initialize your data structure here. */
        public MinStack() {
            stackData = new ArrayDeque<>();
            stackMin = new ArrayDeque<>();
        }

        public void push(int val) {

            if (stackMin.isEmpty()){
                stackMin.push(val);
                stackData.push(val);
                return;
            }
            stackData.push(val);
            int curMin = stackMin.peek();
            if (curMin > val){
                stackMin.push(val);
            }
        }

        public void pop() {
            int pop = stackData.pop();
            if (pop == stackMin.poll()){
                stackMin.pop();
            }
        }

        public int top() {
            return stackData.poll();
        }

        public int getMin() {
            return stackMin.poll();
        }
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
