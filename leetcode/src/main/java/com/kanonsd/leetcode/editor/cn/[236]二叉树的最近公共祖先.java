//给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。 
//
// 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（
//一个节点也可以是它自己的祖先）。” 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
//输出：3
//解释：节点 5 和节点 1 的最近公共祖先是节点 3 。
// 
//
// 示例 2： 
//
// 
//输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
//输出：5
//解释：节点 5 和节点 4 的最近公共祖先是节点 5 。因为根据定义最近公共祖先节点可以为节点本身。
// 
//
// 示例 3： 
//
// 
//输入：root = [1,2], p = 1, q = 2
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [2, 10⁵] 内。 
// -10⁹ <= Node.val <= 10⁹ 
// 所有 Node.val 互不相同 。 
// p != q 
// p 和 q 均存在于给定的二叉树中。 
// 
// Related Topics 树 深度优先搜索 二叉树 👍 1256 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 已经搜索到叶子节点
        if (Objects.isNull(root)){
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
        if (root == q || root == p){
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
        if (Objects.isNull(leftTree)){
            return rightTree;
        }
        /**
         * p,q不在右子树上,查找左子树
         * 也就是剪去除去 左子树的所有分叉
         * 将左子树当做一颗新的树去找
         */
        else if (Objects.isNull(rightTree)){
            return leftTree;
        }
        /**
         * 这种情况说明p,q在左右子树上
         * 并且一定分别在左右子树上
         * 此时的节点就是 最近的
         */
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
