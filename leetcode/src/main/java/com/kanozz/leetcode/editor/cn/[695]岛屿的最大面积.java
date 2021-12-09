//给你一个大小为 m x n 的二进制矩阵 grid 。 
//
// 岛屿 是由一些相邻的 1 (代表土地) 构成的组合，这里的「相邻」要求两个 1 必须在 水平或者竖直的四个方向上 相邻。你可以假设 grid 的四个边缘都
//被 0（代表水）包围着。 
//
// 岛屿的面积是岛上值为 1 的单元格的数目。 
//
// 计算并返回 grid 中最大的岛屿面积。如果没有岛屿，则返回面积为 0 。 
//
// 
//
// 示例 1： 
//
// 
//输入：grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,
//0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,
//0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
//输出：6
//解释：答案不应该是 11 ，因为岛屿只能包含水平或垂直这四个方向上的 1 。
// 
//
// 示例 2： 
//
// 
//输入：grid = [[0,0,0,0,0,0,0,0]]
//输出：0
// 
//
// 
//
// 提示： 
//
// 
// m == grid.length 
// n == grid[i].length 
// 1 <= m, n <= 50 
// grid[i][j] 为 0 或 1 
// 
// Related Topics 深度优先搜索 广度优先搜索 并查集 数组 矩阵 👍 619 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    private int maxArea = 0;
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int maxCol = grid[0].length, maxRow = grid.length;
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                if (grid[row][col] == 1) {
                    int curArea = dfsIslandArea(grid, 0, col, maxCol, row, maxRow);
                    maxArea = Math.max(maxArea, curArea);
                }
            }
        }
        return maxArea;

    }

    private int dfsIslandArea(int[][] grid, int curArea, int col, int maxCol,
                              int row, int maxRow) {
        if (col < 0 || row < 0 || col >= maxCol || row >= maxRow || grid[row][col] == 0) {
            return curArea;
        }
        if (grid[row][col] == 1) {
            curArea += 1;
            grid[row][col] = 0;
        }
        // 左右上下顺序去找
        curArea = dfsIslandArea(grid, curArea, col - 1, maxCol, row , maxRow);
        curArea = dfsIslandArea(grid, curArea, col + 1, maxCol, row, maxRow);
        curArea = dfsIslandArea(grid, curArea, col , maxCol, row -1, maxRow);

        return dfsIslandArea(grid, curArea, col , maxCol, row + 1, maxRow);

    }
}
//leetcode submit region end(Prohibit modification and deletion)
