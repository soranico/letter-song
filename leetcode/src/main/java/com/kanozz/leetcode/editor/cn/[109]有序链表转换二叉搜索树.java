//给定一个单链表，其中的元素按升序排序，将其转换为高度平衡的二叉搜索树。 
//
// 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。 
//
// 示例: 
//
// 给定的有序链表： [-10, -3, 0, 5, 9],
//
//一个可能的答案是：[0, -3, 9, -10, null, 5], 它可以表示下面这个高度平衡二叉搜索树：
//
//      0
//     / \
//   -3   9
//   /   /
// -10  5
// 
// Related Topics 树 二叉搜索树 链表 分治 二叉树 👍 648 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */


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
    public TreeNode sortedListToBST(ListNode head) {

        if (head == null){
            return null;
        }
        /**
         * 因为要求二叉搜索树平衡
         * 因此对链表进行每次查询
         * 中间节点,每次查询效率过低
         * 如果空间允许的话先转为数组
         */
        List<ListNode> nodeList = new ArrayList<>();
        while (head!=null){
            nodeList.add(head);
            head = head.next;
        }
        ListNode[] nodeArr = nodeList.toArray(new ListNode[0]);
        return sortedListToBST(nodeArr,0,nodeArr.length-1);
    }

    /**
     * 递归构建二叉树
     */
    private TreeNode sortedListToBST(ListNode[] nodeArr, int start, int end){
        /**
         * 没有子节点说明此时直接为 null
         */
        if (start > end){
            return null;
        }
        int middle = start + ((end  - start) >>>1);
        /**
         * 中间节点作为根节点
         * 对于当前根节点而言 分别构建其左右孩子均可
         */
        TreeNode root = new TreeNode(nodeArr[middle].val);
        /**
         * 构建左孩子
         */
        root.left = sortedListToBST(nodeArr,start,middle-1);
        /**
         * 构建右孩子
         */
        root.right = sortedListToBST(nodeArr,middle+1,end);
        return root;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
