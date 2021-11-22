//给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。这个二叉树与满二叉树（full binary tree）结构相同，但一些节
//点为空。 
//
// 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。 
//
// 示例 1: 
//
// 
//输入: 
//
//           1
//         /   \
//        3     2
//       / \     \  
//      5   3     9 
//
//输出: 4
//解释: 最大值出现在树的第 3 层，宽度为 4 (5,3,null,9)。
// 
//
// 示例 2: 
//
// 
//输入: 
//
//          1
//         /  
//        3    
//       / \       
//      5   3     
//
//输出: 2
//解释: 最大值出现在树的第 3 层，宽度为 2 (5,3)。
// 
//
// 示例 3: 
//
// 
//输入: 
//
//          1
//         / \
//        3   2 
//       /        
//      5      
//
//输出: 2
//解释: 最大值出现在树的第 2 层，宽度为 2 (3,2)。
// 
//
// 示例 4: 
//
// 
//输入: 
//
//          1
//         / \
//        3   2
//       /     \  
//      5       9 
//     /         \
//    6           7
//输出: 8
//解释: 最大值出现在树的第 4 层，宽度为 8 (6,null,null,null,null,null,null,7)。
// 
//
// 注意: 答案在32位有符号整数的表示范围内。 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 260 👎 0


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
        BiConsumer<TreeNode,Integer> addOrEmpty = (cur, index)->{
            if (cur != null) {
                cur.val = index;
                queue.addLast(cur);
            }
        };
        addOrEmpty.accept(root,0);
        int maxWidth = 1;
        while (!queue.isEmpty()){
            /**
             * 计算第一个和最后一个索引
             * 的距离即当前层的最大宽度
             */
            int firstIndex = queue.getFirst().val , lastIndex = queue.getLast().val,
                    size = queue.size();
            for (int i = 0; i < size ; i++) {
                TreeNode cur = queue.removeFirst();
                addOrEmpty.accept(cur.left,(cur.val<<1) + 1);
                addOrEmpty.accept(cur.right,(cur.val<<1) + 2);
            }

            maxWidth = Math.max(maxWidth,lastIndex - firstIndex + 1);

        }
        return maxWidth;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
