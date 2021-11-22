//给定一个二叉树，判断其是否是一个有效的二叉搜索树。 
//
// 假设一个二叉搜索树具有如下特征： 
//
// 
// 节点的左子树只包含小于当前节点的数。 
// 节点的右子树只包含大于当前节点的数。 
// 所有左子树和右子树自身必须也是二叉搜索树。 
// 
//
// 示例 1: 
//
// 输入:
//    2
//   / \
//  1   3
//输出: true
// 
//
// 示例 2: 
//
// 输入:
//    5
//   / \
//  1   4
//     / \
//    3   6
//输出: false
//解释: 输入为: [5,1,4,null,null,3,6]。
//     根节点的值为 5 ，但是其右子节点值为 4 。
// 
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 
// 👍 1134 👎 0


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
            if (cur.val <= pre){
                return false;
            }
            pre = cur.val;
            // 指向最后一个左节点的右子树
            cur = cur.right;
        }
        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
