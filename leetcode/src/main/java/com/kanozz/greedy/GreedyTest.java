package com.kanozz.greedy;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class GreedyTest {

    /**
     * 122 买卖股票II
     * 假设第 i 天的股票是 x 元,第 i+1 天的是 y 元
     * pass
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

    @Test
    public void testMaxProfit(){

        log.info("maxProfit = {}",maxProfit(new int[]{
                1,2,3,4,5
        }));
    }
}
