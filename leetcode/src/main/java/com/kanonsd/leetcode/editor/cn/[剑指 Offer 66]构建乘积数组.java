//给定一个数组 A[0,1,…,n-1]，请构建一个数组 B[0,1,…,n-1]，其中 B[i] 的值是数组 A 中除了下标 i 以外的元素的积, 即 B[
//i]=A[0]×A[1]×…×A[i-1]×A[i+1]×…×A[n-1]。不能使用除法。 
//
// 
//
// 示例: 
//
// 
//输入: [1,2,3,4,5]
//输出: [120,60,40,30,24] 
//
// 
//
// 提示： 
//
// 
// 所有元素乘积之和不会溢出 32 位整数 
// a.length <= 100000 
// 
// Related Topics 数组 前缀和 👍 162 👎 0


//leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int[] constructArr(int[] a) {

        int len = a.length;
        if (len == 0){
            return new int[0];
        }
        int [] b = new int[len];
        /**
         *
         * b[0] = 1
         * b[1] = a[0]
         * b[2] = b[1] * a[1] = a[0] * a[1]
         * b[3] = b[2] * a[2] = a[0] * a[1] * a[2]
         * 此时 b[i] = a[0] * ... * a[i-1]
         *
         * 也就是不包括 a[i] 缺少 a[i+1] * ... * a[len-1]
         *
         */
        b[0] = 1;
        for (int i = 1; i < len; i++) {
            b[i] = b[i-1] * a[i-1];
        }
        /**
         * b[len-1] = a[0] * ... * a[len-2]
         *
         * b[len-2] = a[0] * ... * a[len-3]
         * 缺少a[len-1]
         *
         * b[len-3] = a[0] * ... * a[len-4]
         * 缺少a[len-2] a[len-1]
         *
         * 也就是对于从后向前遍历的话
         * data 在 len -2 保存 data = a[len -1]
         * len - 3 data = data * a[len-2] = a[len -1] * a[len -2]
         */
        int data = 1;
        for (int i = len-2; i >=0 ; i--) {
            data *= a[i+1];
            b[i] = b[i] * data;
        }

        return b;
    }
}
//leetcode submit region end(Prohibit modification and deletion)
