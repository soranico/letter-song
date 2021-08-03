//给定一个二叉树，判断它是否是高度平衡的二叉树。 
//
// 本题中，一棵高度平衡二叉树定义为： 
//
// 
// 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：true
// 
//
// 示例 2： 
//
// 
//输入：root = [1,2,2,3,3,null,null,4,4]
//输出：false
// 
//
// 示例 3： 
//
// 
//输入：root = []
//输出：true
// 
//
// 
//
// 提示： 
//
// 
// 树中的节点数在范围 [0, 5000] 内 
// -104 <= Node.val <= 104 
// 
// Related Topics 树 深度优先搜索 二叉树 
// 👍 722 👎 0


//leetcode submit region begin(Prohibit modification and deletion)



import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

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
    public boolean isBalanced(TreeNode root) {
        List<TreeNode> levelList = new ArrayList<>();

        Consumer<TreeNode> consumer = cur->{
            if (Objects.nonNull(cur)){
                levelList.add(cur);
            }
        };
        consumer.accept(root);
        while (!levelList.isEmpty()){
            for (int i = levelList.size() - 1; i >= 0; i--) {
                TreeNode cur = levelList.remove(i);
                int leftDep = maxDep(cur.left);
                int rightDep = maxDep(cur.right);
                if (Math.abs(leftDep-rightDep) > 1){
                    return false;
                }
                consumer.accept(cur.right);
                consumer.accept(cur.left);
            }
        }
        return true;
    }
    private Map<TreeNode,Integer> depMap = new HashMap<>();
    private int maxDep(TreeNode root){

        if (Objects.isNull(root)){
            return 0;
        }
        if (depMap.containsKey(root)){
            return depMap.get(root);
        }
        int maxDep =  Math.max(maxDep(root.right),maxDep(root.left))+1;
        depMap.put(root,maxDep);
        return maxDep;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
