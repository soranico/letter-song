//给定一棵二叉搜索树，请找出其中第k大的节点。 
//
// 
//
// 示例 1: 
//
// 输入: root = [3,1,4,null,2], k = 1
//   3
//  / \
// 1   4
//  \
//   2
//输出: 4 
//
// 示例 2: 
//
// 输入: root = [5,3,6,2,4,null,null,1], k = 3
//       5
//      / \
//     3   6
//    / \
//   2   4
//  /
// 1
//输出: 4 
//
// 
//
// 限制： 
//
// 1 ≤ k ≤ 二叉搜索树元素个数 
// Related Topics 树 深度优先搜索 二叉搜索树 二叉树 👍 200 👎 0


//leetcode submit region begin(Prohibit modification and deletion)


import java.util.List;

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
    public int kthLargest(TreeNode root, int k) {
        // 中序遍历是个有序数组
        List<Integer> nums = new ArrayList<>();

        LinkedList<TreeNode> ldrList = new LinkedList<>();
        TreeNode cur = root;
        while (!ldrList.isEmpty() || cur!=null){
            while (cur!=null){
                ldrList.push(cur);
                cur = cur.left;
            }
            TreeNode curRoot = ldrList.pop();
            nums.add(curRoot.val);
            if (curRoot.right!=null){
                cur = curRoot.right;
            }
        }

        return nums.get(nums.size()-k);
    }
}
//leetcode submit region end(Prohibit modification and deletion)
