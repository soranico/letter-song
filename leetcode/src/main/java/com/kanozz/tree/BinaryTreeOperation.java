package com.kanozz.tree;

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
     * 199
     * 二叉树的右视图
     * pass
     * <p>
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
            /**
             * 同一层只能看到第一个节点的值
             */
            seeValList.add(depList.getLast().val);
            for (int i = depList.size() - 1; i >= 0; i--) {
                TreeNode cur = depList.removeLast();
                consumer.accept(cur.right, depList);
                consumer.accept(cur.left, depList);
            }
        }


        return seeValList;
    }

    /**
     * 110
     * 平衡二叉树
     * pass
     */
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
                /**
                 * 左右子树高度大于1
                 */
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
        /**
         * 这个节点不为 null 那么高度至少为1
         * 本身就是一个
         * 递归求出左右子树的较高值
         */
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

    /**
     * 98
     * 验证二叉搜索树
     * 中序遍历
     */
    public boolean isValidBST(TreeNode root) {
        LinkedList<TreeNode> ldr = new LinkedList<>();

        //       F-D-H-G-I-B-E-A-C
        long pre = Long.MIN_VALUE;
        TreeNode cur = root;
        while (Objects.nonNull(cur) || !ldr.isEmpty()) {
            // 当前节点存在左节点需要判断左子树是否存在,直到找到不存在左节点
            while (Objects.nonNull(cur)) {
                ldr.add(cur);
                cur = cur.left;
            }
            // 没有左节点,移除当前节点并与前一个节点值对比
            cur = ldr.removeLast();
            if (cur.val <= pre) {
                return false;
            }
            pre = cur.val;
            // 指向最后一个左节点的右子树
            cur = cur.right;
        }
        return true;
    }


    /**
     * 剑36
     * 二叉搜索树与双向链表
     * <p>
     * 中序遍历构建
     */
    public Node treeToDoublyList(Node root) {
        LinkedList<Node> ldr = new LinkedList<>();
        Node cur = root, pre = new Node(), head = pre;
        while (Objects.nonNull(cur) || !ldr.isEmpty()) {
            // 节点入队
            while (Objects.nonNull(cur)) {
                ldr.add(cur);
                cur = cur.left;
            }
            cur = ldr.removeLast();
            cur.left = pre;
            pre.right = cur;
            pre = cur;
            cur = cur.right;
        }
        if (pre != head) {
            head.right.left = pre;
            pre.right = head.right;
        }

        return head.right;
    }

    @Test
    public void testTreeToDoublyList() {
        treeToDoublyList(createNode(new Integer[]{
                4, 2, 5, 1, 3
        }));
    }


    /**
     * 二叉搜索树第k小
     */
    public int kthSmallest(TreeNode root, int k) {
        List<TreeNode> ldrList = new ArrayList<>(k);
        recursionLDR(root, k, ldrList);

        return ldrList.remove(k - 1).val;
    }

    private void recursionLDR(TreeNode root, int k, List<TreeNode> ldrList) {
        if (ldrList.size() >= k) {
            return;
        }
        if (root == null) {
            return;
        }
        recursionLDR(root.left, k, ldrList);
        ldrList.add(root);
        recursionLDR(root.right, k, ldrList);
    }


    @Test
    public void testKthSmallest() {
        log.info("kthSmallest = {}", kthSmallest(
                createTree(new Integer[]{5, 3, 6, 2, 4, null, null, 1}), 4));
    }


    /**
     * 236
     * 二叉树最近公共祖先
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 已经搜索到叶子节点
        if (Objects.isNull(root)) {
            return null;
        }
        /**
         *
         * 找到了p或q,此时这个节点是候选节点
         *
         * 说明此时这颗子树上面包含 需要查找的节点
         * 但是不知道这颗子树是不是最小的那颗
         *
         * 查找此时节点的 左子树 右子树
         *
         *
         */
        if (root == q || root == p) {
            return root;
        }
        /**
         * 此节点不是p或q,需要判断p,q是否分别在左右子树上
         */
        TreeNode leftTree = lowestCommonAncestor(root.left, p, q);
        TreeNode rightTree = lowestCommonAncestor(root.right, p, q);
        /**
         * p,q不在左子树上,那么需要去查找当前节点的右子树
         * 也就是剪去除去 右子树的所有分叉
         * 将右子树当做一颗新的树去找
         *
         */
        if (Objects.isNull(leftTree)) {
            return rightTree;
        }
        /**
         * p,q不在右子树上,查找左子树
         * 也就是剪去除去 左子树的所有分叉
         * 将左子树当做一颗新的树去找
         */
        else if (Objects.isNull(rightTree)) {
            return leftTree;
        }
        /**
         * 这种情况说明p,q在左右子树上
         * 并且一定分别在左右子树上
         * 此时的节点就是 最近的
         */
        return root;
    }


    @Test
    public void testLowestParent() {
        TreeNode root = createTree(new Integer[]{
                3, 5, 1, 6, 2, 0, 8, null, null, 7, 4
        });
        TreeNode p = root.left;
        TreeNode q = root.right;
        log.info("low = {}", lowestCommonAncestor(root, p, q));
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

    /**
     * 105
     * 前序和中序构建树
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {

        /**
         * 缓存每个节点对应的索引
         * 对于中序节点而言 left root child 也就是 左边部分是做子树 右边部分是右子树
         */
        for (int i = 0; i < inorder.length; i++) {
            treeNodeIndex.put(inorder[i], i);
        }
        return buildTree(preorder, 0, preorder.length, inorder, 0);

    }

    private static Map<Integer, Integer> treeNodeIndex = new HashMap<>();

    private static TreeNode buildTree(int[] preorder, int preStart, int preEnd,
                                      int[] inorder, int inStart) {
        // 前序起始大于终止也就是没有子节点了直接返回null
        if (preStart >= preEnd) {
            return null;
        }
        // 取出子树的根节点索引
        Integer rootIndex = treeNodeIndex.get(preorder[preStart]);
        // 子树的根节点
        TreeNode root = new TreeNode(inorder[rootIndex]);
        // 子树的左子树节点个数 1 2 3 4 5 6
        int leftChildNum = rootIndex - inStart;
        root.left = buildTree(preorder, preStart + 1, preStart + leftChildNum + 1, inorder, inStart);

        root.right = buildTree(preorder, preStart + leftChildNum + 1, preEnd, inorder, rootIndex + 1);
        return root;

    }


    /**
     * 106
     * 中序和后序构建二叉树
     */
    public TreeNode buildTreeWithIP(int[] inorder, int[] postorder) {
        Map<Integer, Integer> nodeIndexMap = new HashMap<>(inorder.length);
        // 缓存节点和对应的位置
        for (int i = 0; i < inorder.length; i++) {
            nodeIndexMap.put(inorder[i], i);
        }
        /**
         * 初始的时候后序数组最后一个节点为 root
         */
        return buildTreeWithIP(inorder, 0, postorder, 0, postorder.length - 1, nodeIndexMap);
    }

    /**
     * 1
     * 2     3
     * 4  5  6   7
     * <p>
     * 4 2 5 1 6 3 7
     * 4 5 2 6 7 3 1
     * 1的左边是 左子树
     * 1 的右边是右子树
     * 此时 1的索引为 3
     * 左子树的根节点为 2
     * 右子树的根节点为 3
     */

    public TreeNode buildTreeWithIP(int[] inorder, int inStart,
                                    int[] postorder, int postStart, int postEnd, Map<Integer, Integer> nodeIndexMap) {
        /**
         * 后序使用完
         */
        if (postStart > postEnd) {
            return null;
        }
        /**
         * 当前根节点在中序的位置
         * 前部分是左子树
         * 后部分是右子树
         */
        Integer rootIndex = nodeIndexMap.get(postorder[postEnd]);
        // 最后一个数据为根节点
        TreeNode root = new TreeNode(postorder[postEnd]);
        /**
         *
         * 此时将左子树当做一个新的树
         * 左子树在后序数组中的最后一个节点
         * 为新树的根节点
         *
         */
        // 中序中 构成左子树的节点个数 即后续数组中从postStart开始的 leftTreeNodeNum个元素
        int leftTreeNodeNum = rootIndex - inStart;
        // 左子树而言 后序[start , start+节点个数-1] 个节点为左子树 -1是因为数组索引
        root.left = buildTreeWithIP(inorder, inStart, postorder, postStart, postStart + leftTreeNodeNum - 1, nodeIndexMap);
        // 右子树而言 后序[start+节点个数,end-1]  个节点构成 右子树 end已经使用所以取出
        root.right = buildTreeWithIP(inorder, rootIndex + 1, postorder, postStart + leftTreeNodeNum, postEnd - 1, nodeIndexMap);
        return root;

    }


    //     3
//   / \
//  9  20
//    /  \
//   15   7
    @Test
    public void testBuildTreeWithIP() {
        buildTreeWithIP(new int[]{
                9, 3, 15, 20, 7
        }, new int[]{
                9, 15, 7, 20, 3
        });
    }


    public boolean isCompleteTree(TreeNode root) {
        LinkedList<TreeNode> levelList = new LinkedList<>();
        levelList.add(root);
        // 如果下层有数据，那么上层必须是满的
        int level = 0;
        while (!levelList.isEmpty()) {

            boolean first = false, notFull = levelList.size() != 1 << (level++);
            for (int i = levelList.size() - 1; i >= 0; i--) {
                TreeNode cur = levelList.removeFirst();
                if (notFull && (cur.right != null || cur.left != null)) {
                    return false;
                }
                // 第一个存在孩子后，前面必须存在孩子
                if (cur.left == null && cur.right != null) {
                    return false;
                }
                if (first && (cur.left == null || cur.right == null)) {
                    return false;
                }
                if (cur.right != null) {
                    first = true;
                    levelList.addLast(cur.right);
                }
                if (cur.left != null) {
                    first = true;
                    levelList.addLast(cur.left);
                }
            }
        }

        return true;
    }


    @Test
    public void testIsCompleteTree() {
        log.info("isCompleteTree = {}", isCompleteTree(
                createTree(
                        new Integer[]{
                                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, null, null, 15
                        }
                )
        ));
    }


    /**
     * [3,1,4,null,2], k = 1
     * 4
     * <p>
     * <p>
     * [5,3,6,2,4,null,null,1], k = 3
     * 4
     * <p>
     * 二叉搜索树第k大
     */
    public int kthLargest(TreeNode root, int k) {
        // 中序遍历是个有序数组
        List<Integer> nums = new ArrayList<>();

        LinkedList<TreeNode> ldrList = new LinkedList<>();
        TreeNode cur = root;
        while (!ldrList.isEmpty() || cur != null) {
            while (cur != null) {
                ldrList.push(cur);
                cur = cur.left;
            }
            TreeNode curRoot = ldrList.pop();
            nums.add(curRoot.val);
            if (curRoot.right != null) {
                cur = curRoot.right;
            }
        }

        return nums.get(nums.size() - k);
    }

    @Test
    public void testKthLargest() {
        log.info("kthLargest = {}", kthLargest(createTree(
                new Integer[]{
                        3, 1, 4, null, 2
                }
        ), 4));
    }


    /**
     * 662
     */
    public int widthOfBinaryTree(TreeNode root) {
        /**
         *
         * 假设是一颗完全二叉树
         * 那么存在 对于节点 i
         * 其左孩子索引为 2*i+1
         * 右孩子索引为 2*i+2
         * 对于同层的宽度而言
         * 最左节点所在位置假设为 L
         * 最右节点所在位置假设为 R
         * 则宽度 = L - R + 1
         *
         * 将每一层看成一个数组
         * 数组中的每个节点的索引由其父节点索引决定
         * 初始 root  = 0
         * 则 左孩子索引 1 右孩子 索引 2
         */
        LinkedList<TreeNode> queue = new LinkedList<>();
        BiConsumer<TreeNode, Integer> addOrEmpty = (cur, index) -> {
            if (cur != null) {
                cur.val = index;
                queue.addLast(cur);
            }
        };
        addOrEmpty.accept(root, 0);
        int maxWidth = 1;
        while (!queue.isEmpty()) {
            /**
             * 计算第一个和最后一个索引
             * 的距离即当前层的最大宽度
             */
            int firstIndex = queue.getFirst().val, lastIndex = queue.getLast().val,
                    size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.removeFirst();
                addOrEmpty.accept(cur.left, (cur.val << 1) + 1);
                addOrEmpty.accept(cur.right, (cur.val << 1) + 2);
            }

            maxWidth = Math.max(maxWidth, lastIndex - firstIndex + 1);

        }
        return maxWidth;
    }

    @Test
    public void testWidthOfBinaryTree() {
        log.info("widthOfBinaryTree = {}", widthOfBinaryTree(
                createTree(
                        new Integer[]{
                                1, 3, 2, 5, null, null, 9, 6, null, null, 7
                        }
                )
        ));
    }


    /**
     * pass
     * 面 04.02
     * 最小高度树
     * 有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树
     * [-10,-3,0,5,9,10]
     */
    public TreeNode sortedArrayToBST(int[] nums) {


        return buildBST(nums, 0, nums.length - 1);
    }

    private TreeNode buildBST(int[] nums, int start, int end) {
        TreeNode cur = null;
        if (start <= end) {
            int middle = (start + end) >>> 1;
            cur = new TreeNode(nums[middle]);
            // 构建左节点
            cur.left = buildBST(nums, start, middle - 1);
            // 构建右节点
            cur.right = buildBST(nums, middle + 1, end);
        }
        return cur;
    }

    @Test
    public void testSortedArrayToBST() {
        log.info("sortedArrayToBST = {}", sortedArrayToBST(new int[]{
                -10, -3
        }));
    }



    /**
     *
     * 124 二叉树最大路径和
     * pass
     *
     * 对于一个节点 root
     * 过这个节点的最大路径为
     * max(0,L) + root + max(0,R)
     * <p>
     * 对于子树的路径求解可以递归
     * 当子树没有子节点的时候,最大路径就是其本身
     *    -10
     *  9     20
     *      15   7
     *
     */
    private int maxPath = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxPathSumRecursion(root);
        return maxPath;
    }


    private int maxPathSumRecursion(TreeNode root) {
        /**
         * 没有子节点,节点值为0
         */
        if (root == null) {
            return 0;
        }
        /**
         * 右孩子的最大路径
         */
        int maxRight = maxPathSumRecursion(root.right);
        /**
         * 左孩子的最大路径
         */
        int maxLeft = maxPathSumRecursion(root.left);

        /**
         * 过这个节点的最大路径
         * 跨越左右节点
         */
        maxPath = Math.max(maxPath,Math.max(0,maxLeft)+Math.max(0,maxRight)+root.val);

        /**
         * 不能跨越这个节点
         * 因为跨越这个节点已经终止
         * 所以最大的路径是包含这个节点的 左右子树的较大值
         */
        return root.val+ Math.max(Math.max(maxLeft,maxRight),0);


    }

    @Test
    public void testMaxPathSum() {
        log.info("maxPathSum = {}", maxPathSum(createTree(
                new Integer[]{
                        10,9,20,null,null,15,7
//                        1, 2, 3
                }
        )));
    }


}
