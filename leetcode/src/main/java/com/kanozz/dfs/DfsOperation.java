package com.kanozz.dfs;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DfsOperation {

    /**
     * 200
     * 岛屿数量
     * pass
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int isLand = 0, maxRow = grid.length, maxCol = grid[0].length;
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                /**
                 * 如果节点是1 那么去找和这个节点相连(上下左右方向)的其他为1节点
                 *
                 * 1 1 0
                 * 1 0 1
                 * 对于左上角这个1 而言 首先找到其下方的1 和右方的 1
                 *
                 * 对于右下角的 1而言 是没有值可以
                 */
                if (grid[row][col] == '1') {
                    dfsIsland(grid, row, col, maxRow, maxCol);
                    isLand++;
                }
            }
        }

        return isLand;
    }

    private void dfsIsland(char[][] grid, int row, int col, int maxRow, int maxCol) {
        /**
         * 当前为 0 说明这个节点不需要扩展
         *   0 1
         *   1
         * 比如这种情况 对于这个 0 而言 其左右有1 但此时
         * 这个节点是无法和 1 这些节点相连的
         */

        if (row < 0 || col < 0 || row >= maxRow || col >= maxCol || grid[row][col] == '0') {
            return;
        }
        // 当前节点设置为 0
        grid[row][col] = '0';
        /**
         *  查找其上下左右,如果有1的话
         *  说明以当前 节点 至少 其上下左右方向
         *  至少有一个节点可以与其相连
         */
        dfsIsland(grid, row - 1, col, maxRow, maxCol);
        dfsIsland(grid, row + 1, col, maxRow, maxCol);
        dfsIsland(grid, row, col - 1, maxRow, maxCol);
        dfsIsland(grid, row, col + 1, maxRow, maxCol);

    }

    @Test
    public void testNumIslands() {
        log.info("numIslands  = {}", numIslands(new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        }));
    }

    /**
     * 695
     * 岛屿最大面积
     * pass
     * @param grid
     * @return
     */
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


    @Test
    public void testMaxAreaOfIsland() {
        log.info("maxAreaOfIsland = {}", maxAreaOfIsland(
                new int[][]{
//                        {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                        {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
//                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
//                        {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
//                        {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
                        {0,0,0,0,0,0,0,0}
                }
        ));
    }

}
