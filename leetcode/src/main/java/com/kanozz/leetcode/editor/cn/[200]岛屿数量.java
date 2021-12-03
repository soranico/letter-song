//给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。 
//
// 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。 
//
// 此外，你可以假设该网格的四条边均被水包围。 
//
// 
//
// 示例 1： 
//
// 
//输入：grid = [
//  ["1","1","1","1","0"],
//  ["1","1","0","1","0"],
//  ["1","1","0","0","0"],
//  ["0","0","0","0","0"]
//]
//输出：1
// 
//
// 示例 2： 
//
// 
//输入：grid = [
//  ["1","1","0","0","0"],
//  ["1","1","0","0","0"],
//  ["0","0","1","0","0"],
//  ["0","0","0","1","1"]
//]
//输出：3
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 300 
// grid[i][j] 的值为 '0' 或 '1' 
// 
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 1432 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length ==0 || grid[0].length ==0){
            return 0;
        }
        int isLand = 0,maxRow = grid.length,maxCol = grid[0].length;
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow ; row++) {
                /**
                 * 如果节点是1 那么去找和这个节点相连(上下左右方向)的其他为1节点
                 *
                 * 1 1 0
                 * 1 0 1
                 * 对于左上角这个1 而言 首先找到其下方的1 和右方的 1
                 *
                 * 对于右下角的 1而言 是没有值可以
                 */
                if (grid[row][col] == '1' ){
                    dfsIsland(grid,row,col,maxRow,maxCol);
                    isLand++;
                }
            }
        }

        return isLand;
    }
    private void dfsIsland(char[][] grid,int row ,int col,int maxRow,int maxCol){
        /**
         * 当前为 0 说明这个节点不需要扩展
         *   0 1
         *   1
         * 比如这种情况 对于这个 0 而言 其左右有1 但此时
         * 这个节点是无法和 1 这些节点相连的
         */

        if (row < 0 || col < 0 || row >= maxRow || col >= maxCol || grid[row][col] == '0'){
            return;
        }
        // 当前节点设置为 0
        grid[row][col] = '0';
        /**
         *  查找其上下左右,如果有1的话
         *  说明以当前 节点 至少 其上下左右方向
         *  至少有一个节点可以与其相连
         */
        dfsIsland(grid,row-1,col,maxRow,maxCol);
        dfsIsland(grid,row+1,col,maxRow,maxCol);
        dfsIsland(grid,row,col-1,maxRow,maxCol);
        dfsIsland(grid,row,col+1,maxRow,maxCol);

    }
}
//leetcode submit region end(Prohibit modification and deletion)
