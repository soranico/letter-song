package com.soranico.arithmetic.simple;

/**
 * <pre>
 * @title com.soranico.arithmetic.simple.BitOperation
 * @description
 *        <pre>
 *          位运算
 *        </pre>
 *
 * @author soranico
 * @version 1.0
 * @date 2020/7/4
 *
 * </pre>
 */
public class BitOperation {


    /**
     * 1486
     * 给你两个整数，n 和 start 。
     * 数组 nums 定义为：nums[i] = start + 2*i（下标从 0 开始）且 n == nums.length 。
     * 请返回 nums 中所有元素按位异或（XOR）后得到的结果。
     * <p>
     * 输入：n = 5, start = 0
     * 输出：8
     * 解释：数组 nums 为 [0, 2, 4, 6, 8]，其中 (0 ^ 2 ^ 4 ^ 6 ^ 8) = 8 。
     * "^" 为按位异或 XOR 运算符。
     *
     * @param n
     * @param start
     * @return
     */
    public static int xorOperation(int n, int start) {
        int result = 0;
        for (int i = 0; i < n; i++) {
            result ^= (start + (i << 1));
        }
        return result;

    }


    /**
     * 136
     * 给定一个非空整数数组，除了某个元素只出现一次以外，
     * 其余每个元素均出现两次。找出那个只出现了一次的元素。
     * @param nums
     * @return
     */
    public static int singleNumber(int[] nums) {
        int result=0;
        for (int num : nums) {
            /**
             * a^(b^c)=(a^b)^c 所以位置不影响结果
             * a^a=0 将所有数全部异或一遍，其余成对的均为0
             * 0^a=a 唯一的那个数还是那个数
             */
            result^=num;
        }
        return result;
    }







    public static void main(String[] args) {

    }


}
