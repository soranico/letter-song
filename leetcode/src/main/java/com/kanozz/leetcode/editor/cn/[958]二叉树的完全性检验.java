//给定一个二叉树，确定它是否是一个完全二叉树。 
//
// 百度百科中对完全二叉树的定义如下： 
//
// 若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。（注：
//第 h 层可能包含 1~ 2ʰ 个节点。） 
//
// 
//
// 示例 1： 
//
// 
//
// 输入：[1,2,3,4,5,6]
//输出：true
//解释：最后一层前的每一层都是满的（即，结点值为 {1} 和 {2,3} 的两层），且最后一层中的所有结点（{4,5,6}）都尽可能地向左。
// 
//
// 示例 2： 
//
// 
//
// 输入：[1,2,3,4,5,null,7]
//输出：false
//解释：值为 7 的结点没有尽可能靠向左侧。
// 
//
// 
//
// 提示： 
//
// 
// 树中将会有 1 到 100 个结点。 
// 
// Related Topics 树 广度优先搜索 二叉树 👍 147 👎 0


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
    public boolean isCompleteTree(TreeNode root) {
        LinkedList<TreeNode> levelList = new LinkedList<>();
        levelList.add(root);
        // 如果下层有数据，那么上层必须是满的
        int level = 0;
        while (!levelList.isEmpty()){

            boolean first = false,notFull = levelList.size() != 1<<(level++);
            for (int i = levelList.size()-1; i >=0 ; i--) {
                TreeNode cur = levelList.removeFirst();
                if (notFull && (cur.right!=null || cur.left!=null)){
                    return false;
                }
                // 第一个存在孩子后，前面必须存在孩子
                if (cur.left==null && cur.right!=null){
                    return false;
                }
                if (first && (cur.left==null || cur.right==null)){
                    return false;
                }
                if (cur.right!=null){
                    first = true;
                    levelList.addLast(cur.right);
                }
                if (cur.left!=null){
                    first = true;
                    levelList.addLast(cur.left);
                }
            }
        }

        return true;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
