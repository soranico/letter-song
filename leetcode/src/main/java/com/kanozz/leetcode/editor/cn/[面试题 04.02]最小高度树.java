//给定一个有序整数数组，元素各不相同且按升序排列，编写一个算法，创建一棵高度最小的二叉搜索树。示例: 给定有序数组: [-10,-3,0,5,9], 一个可能
//的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：           0          / \        -3 
//  9        /   /      -10  5 Related Topics 树 二叉搜索树 数组 分治 二叉树 👍 106 👎 0


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
    public TreeNode sortedArrayToBST(int[] nums) {

        return buildBST(nums,0,nums.length-1);
    }

    private TreeNode buildBST(int[] nums, int start, int end){
        TreeNode cur = null;
        if (start <= end){
            int middle = (start+end)>>>1;
            cur = new TreeNode(nums[middle]);
            // 构建左节点
            cur.left = buildBST(nums,start,middle-1);
            // 构建右节点
            cur.right = buildBST(nums,middle+1,end);
        }
        return cur;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
