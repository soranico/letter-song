//
// 示例: 
//
// 输入: [1,2,3,null,5,null,4]
//输出: [1, 3, 4]
//解释:
//
//   1            <---
// /   \
//2     3         <---
// \     \
//  5     4       <---
// 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 
// 👍 487 👎 0


//leetcode submit region begin(Prohibit modification and deletion)

import java.util.*;
import java.util.function.BiConsumer;
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
    public List<Integer> rightSideView(TreeNode root) {
        if (Objects.isNull(root)){
            return Collections.emptyList();
        }
        BiConsumer<TreeNode,LinkedList<TreeNode>> consumer = (cur,list)->{
            if (Objects.nonNull(cur)){
                list.addFirst(cur);
            }
        };
        LinkedList<TreeNode> depList = new LinkedList<>();
        depList.add(root);
        List<Integer> seeValList = new ArrayList<>();
        while (!depList.isEmpty()){
            seeValList.add(depList.getLast().val);
            for (int i = depList.size() - 1; i >= 0; i--) {
                TreeNode cur = depList.removeLast();
                consumer.accept(cur.right,depList);
                consumer.accept(cur.left,depList);
            }
        }


        return seeValList;
    }
}

