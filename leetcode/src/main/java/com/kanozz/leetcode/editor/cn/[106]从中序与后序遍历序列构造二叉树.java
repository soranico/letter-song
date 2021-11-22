//根据一棵树的中序遍历与后序遍历构造二叉树。 
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
// Related Topics 树 数组 哈希表 分治 二叉树 👍 554 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


import java.util.Map;

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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer,Integer> nodeIndexMap = new HashMap<>(inorder.length);
        // 缓存节点和对应的位置
        for (int i = 0; i < inorder.length; i++) {
            nodeIndexMap.put(inorder[i], i);
        }
        return buildTreeWithIP(inorder,0,postorder,0,postorder.length-1,nodeIndexMap);
    }

    /**
     *      1
     *   2     3
     * 4  5  6   7
     *
     * 4 2 5 1 6 3 7
     * 4 5 2 6 7 3 1
     *  1的左边是 左子树
     *  1 的右边是右子树
     *  此时 1的索引为 3
     *  左子树的根节点为 2
     *  右子树的根节点为 3
     *
     *
     */

    public TreeNode buildTreeWithIP(int[] inorder, int inStart,
                                                        int[] postorder, int postStart, int postEnd, Map<Integer,Integer> nodeIndexMap){
        if (postStart > postEnd){
            return null;
        }
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
        root.left = buildTreeWithIP(inorder,inStart,postorder,postStart,postStart+leftTreeNodeNum-1,nodeIndexMap);
        // 右子树而言 后序[start+节点个数,end-1]  个节点构成 右子树 end已经使用所以取出
        root.right = buildTreeWithIP(inorder,rootIndex+1,postorder,postStart+leftTreeNodeNum,postEnd-1,nodeIndexMap);
        return root;

    }

}
//leetcode submit region end(Prohibit modification and deletion)
