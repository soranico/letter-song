//给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。 
//
// 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。 
//
// 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。 
//
// 
//
// 示例 1: 
//
// 
//输入: prices = [7,1,5,3,6,4]
//输出: 7
//解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
// 
//
// 示例 2: 
//
// 
//输入: prices = [1,2,3,4,5]
//输出: 4
//解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
//     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
// 
//
// 示例 3: 
//
// 
//输入: prices = [7,6,4,3,1]
//输出: 0
//解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。 
//
// 
//
// 提示： 
//
// 
// 1 <= prices.length <= 3 * 10⁴ 
// 0 <= prices[i] <= 10⁴ 
// 
// Related Topics 贪心 数组 动态规划 👍 1481 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    /**
     * 122 买卖股票II
     * 假设第 i 天的股票是 x 元,第 i+1 天的是 y 元
     *
     * 情况一
     *
     * 如果 x > y 那么我们肯定不需要在 i 天买这个股票
     * 假设后面 i+n(n > 1) 天 的股票价格为 z > x
     *
     * 因为在 i 天 买 获利 z - x
     * 在 i+1 天买的 z -y
     * 而  y > x 此时 在 i+1 天买入获利更大
     *
     * 情况二
     *
     * 如果 x < y 那么可以买入
     * 即使后面 i+n(n > 1) 天 的股票价格 z > x
     *
     * 1. i+1 天卖出
     * y - x(卖) + z - y 此时的获利 为 z - x
     * 2. i+1 天不卖出, i+n 天卖出
     * 获利 z -x
     * 此时 两种方式获利相同
     *
     * 综上
     * 在 i 天价格小于 i+1天加个可以买入,无论后面是否会有更高价格最终获利都一样
     * 在 i天价格大于 i+1 天加个 不需要买入,因为此时买入获利不如i+1天获利
     *
     *
     *
     */
    public int maxProfit(int[] prices) {
        int maxProfit = 0, len = prices.length -1;
        for (int i = 0; i < len ; i++) {
            /**
             * i 天买入 i+1 天卖出
             * 相同价格减少交易次数,不进行交易
             */
            if (prices[i+1] > prices[i]){
                maxProfit = maxProfit + prices[i+1] - prices[i];
            }
        }
        return maxProfit;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
