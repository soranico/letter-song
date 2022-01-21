//路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不
//一定经过根节点。 
//
// 路径和 是路径中各节点值的总和。 
//
// 给你一个二叉树的根节点 root ，返回其 最大路径和 。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,3]
//输出：6
//解释：最优路径是 2 -> 1 -> 3 ，路径和为 2 + 1 + 3 = 6 
//
// 示例 2： 
//
// 
//输入：root = [-10,9,20,null,null,15,7]
//输出：42
//解释：最优路径是 15 -> 20 -> 7 ，路径和为 15 + 20 + 7 = 42
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目范围是 [1, 3 * 10⁴] 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 深度优先搜索 动态规划 二叉树 👍 1361 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    /**
     *
     *  124 二叉树最大路径和
     *
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
}
//leetcode submit region end(Prohibit modification and deletion)
