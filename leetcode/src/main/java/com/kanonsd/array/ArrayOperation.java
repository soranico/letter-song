package com.kanonsd.array;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

@Slf4j
public class ArrayOperation {


    ////////////////  二分 ////////////////////////////////

    // 每行中的整数从左到右按升序排列。
    // 每行的第一个整数大于前一行的最后一个整数。
    public boolean searchMatrix(int[][] matrix, int target) {
        int YLow = 0, YHigh = matrix.length - 1, x = matrix[0].length - 1;
        while (YLow <= YHigh) {
            int YMiddle = (YHigh + YLow) >>> 1;
            // 在某行范围内
            if (matrix[YMiddle][0] <= target && matrix[YMiddle][x] >= target) {
                return binarySearch(matrix[YMiddle], target) != -1;
            }
            // 说明比这这个小
            else if (matrix[YMiddle][0] > target) {
                YHigh = YMiddle - 1;
            } else if (matrix[YMiddle][x] < target) {
                YLow = YMiddle + 1;
            }
        }
        return false;

    }

    private int binarySearch(int[] nums, int target) {
        int low = 0, high = nums.length - 1, middle;
        while (low <= high) {
            middle = (high + low) >>> 1;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else if (nums[middle] == target) {
                return middle;
            }
        }
        return -1;
    }

    @Test
    public void testSearchMatrix() {
        log.info("searchMatrix = {}", searchMatrix(new int[][]{
//                {1, 3, 5, 7},
//                {10, 11, 16, 20},
//                {23, 30, 34, 60}
                {1, 3}

        }, 3));
    }


    public int search(int[] nums, int target) {
        int frequency = 0, low = 0, high = nums.length - 1, middle;
        while (low <= high) {
            middle = (low + high) >>> 1;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                frequency += binaryScope(nums, low, middle - 1, target, true) +
                        binaryScope(nums, middle + 1, high, target, false);
                frequency++;
                break;
            }
        }
        return frequency;
    }

    private int binaryScope(int[] nums, int low, int high, int target, boolean pre) {
        while (low <= high) {
            int middle = (high + low) >>> 1;
            if (nums[middle] == target) {
                if (pre) {
                    return high - middle + 1 + binaryScope(nums, low, middle - 1, target, true);
                }
                return middle - low + 1 + binaryScope(nums, middle + 1, high, target, false);
            }
            // 只可能比target小
            else if (pre) {
                low = middle + 1;
            }
            // 只可能比target大
            else {
                high = middle - 1;
            }
        }
        return 0;
    }

    @Test
    public void testSearch() {
        log.info("search = {}", search(new int[]{
//                1,2,3,4,5,5,5,5,5,6,7,8,9
                5, 7, 7, 8, 8, 10
        }, 10));
    }


    public int findMin(int[] nums) {
        // 旋转 1 - n次此时存在两个逻辑递增数组 [n-1,n-2,...m] [0,1,2,...,m-1]
        // 最小值在两个逻辑数组的交界处即第二个逻辑数组第一个元素
        int high = nums.length - 1, low = 0, middle;
        while (low < high) {
            middle = (low + high) >>> 1;
            // 此时表明middle在第一个逻辑数组,低指针后移可能移到第二个逻辑数组内
            if (nums[middle] > nums[high]) {
                low = middle + 1;
            }
            // 此时表明middle在第二个逻辑数组内,此时高指针不能移到middle-1
            // 可能middle就是第二个逻辑数组第一个元素即最小值
            else if (nums[middle] < nums[low]) {
                high = middle;
            }
            // middle可能在第一个数组(需要移动low),也可能在第二个数组(需要移动high)
            else if (nums[middle] == nums[high]) {
                high = high - 1;
            }
            // 此时在第二个数组内,则low指针为最小值
            // 对于low指针移动而言是middle +1 且只有middle在第一个数组内才会变动
            // 因此low只可能从第一个数组移到第二个数组的第一个元素
            else if (nums[middle] < nums[high]) {
                return nums[low];
            }
        }
        return nums[low];
    }

    @Test
    public void testFindMin() {
        log.info("findMin = {}", findMin(new int[]{
//                4,5,6,7,0,1,4
//                3,3,3,0,1
//                1,3,5
                6, 5, 5
//                3,3,1,3,3
        }));

    }

    /**
     * //给你一个 n x n 矩阵 matrix ，其中每行和每列元素均按升序排序，找到矩阵中第 k 小的元素。
     * //请注意，它是 排序后 的第 k 小元素，而不是第 k 个 不同 的元素。
     * //输入：matrix = [ [1,5,9],
     * [10,11,13],
     * [12,13,15]], k = 8
     * //输出：13
     * //解释：矩阵中的元素为 [1,5,9,10,11,12,13,13,15]，第 8 小元素是 13
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {


        int max = matrix.length;
        int[] nums = new int[max * max];
        for (int i = 0; i < max; i++) {
            System.arraycopy(matrix[i], 0, nums, max * i, max);
        }

        // 快速排序  1...k-1 k ....

        return quickSortK(nums, 0, nums.length - 1, k);
    }

    private int quickSortK(int[] nums, int start, int end, int k) {
        if (start > end) {
            return -1;
        }
        int low = start, high = end, base = nums[low];
        while (low < high) {
            while (low < high && nums[high] >= base) {
                high--;
            }
            if (low < high) {
                nums[low] = nums[high];
            }
            while (low < high && nums[low] <= base) {
                low++;
            }
            if (low < high) {
                nums[high] = nums[low];
            }
        }
        nums[low] = base;
        // 此时low 为基准,从start ..... low 之间有 low - start +1 个数
        if (low - start + 1 == k) {
            return base;
        } else if (low - start + 1 > k) {

            return quickSortK(nums, start, low - 1, k);
        }
        // 不足k个数,去除已经较小的的 low -start+1个
        else {
            return quickSortK(nums, low + 1, end, k - low + start - 1);
        }
    }

    @Test
    public void testKthSmallest() {
        log.info("kthSmallest = {}", kthSmallest(new int[][]{
//                {1,5,9},
//                {10,11,13},
//                {12,13,15}
                {1, 2},
                {1, 3}
        }, 2));
    }


    /**
     * [1,1,2,3,3,4,4,8,8]
     */
    public int singleNonDuplicate(int[] nums) {
        // 位运算 一次遍历可以得到
        // 这个数组的长度一定是 2n+1 此时middle为 n
        // 1 1 2 此时middle 是1
        int low = 0, high = nums.length - 1, middle;
        while (low <= high) {
            middle = (low + high) >>> 1;
            // 只有3个数情况,直接运算返回
            if (high - low == 2 || high == low) {
                return nums[low] ^ nums[middle] ^ nums[high];
            }
            // 如果唯一数在前面,则加上这个数  low - middle  之间数肯定是不是2的倍数
            else if (nums[middle] == nums[middle - 1] && ((middle - low + 1) & 1) != 0) {
                high = middle - 2;
            }
            // 说明前半部分全是一对,移除到low - middle为止成对元素
            else if (nums[middle] == nums[middle - 1]) {
                low = middle + 1;
            }
            // 唯一数在后半部分， middle - high 之间数肯定是2的倍数
            else if (nums[middle] == nums[middle + 1] && ((high - middle + 1) & 1) != 0) {
                low = middle + 2;
            }
            // 说明后半部分全是一对,移除middle -high为止的成对元素
            else if (nums[middle] == nums[middle + 1]) {
                high = middle - 1;
            } else {
                return nums[middle];
            }
        }
        return -1;
    }

    @Test
    public void testSingleNonDuplicate() {
        log.info("singleNonDuplicate = {}", singleNonDuplicate(new int[]{
                3,3,7,7,10,11,11
        }));
    }


    /**
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {

        // 对于 0,1,2 .... m-1,m m+1 .... n-1 而言
        // 如果到 m-1都是完整的,那么其下标和数值是相等的
        // 如果 nums[m] > m 则说明缺失数字肯定在 nums[m]之前



        int low = 0,high = nums.length-1,middle;
        while (low <= high){
            if (low == high){
                // 如果高指针一直没有移动,则说明前 0-low 个数是完整的
                if (high==nums.length-1 && nums[high]==high){
                    return nums[high]+1;
                }
                return nums[high]-1;
            }
            middle = (low+high)>>>1;
            if (nums[middle] == middle){
                low= middle+1;
            }
            // 进入这里的话缺失元素一定不是 n
            else if (nums[middle] > middle){
                high = middle;
            }
        }

        return nums[nums.length-1]+1;
    }

    @Test
    public void testMissingNumber(){
        log.info("missingNumber = {}",missingNumber(new int[]{
              1
        }));
    }



    public int findMinMiddle(int[] nums) {
        int low = 0,high = nums.length-1,middle;
        // 逻辑数组左边 L > 逻辑数组右边 R, 每个逻辑数组内部有序
        // 如果没有旋转那么low不会改变一直指向默认值
        // 如果发生旋转low移动到 R的第一个元素事就会满足low middle high一组内有序
        while (low< high){
            middle = (low+high)>>>1;
            // 说明 middle  在 L ,high在 R
            // 这也说明肯定发生了旋转
            if (nums[middle] > nums[high]){
                low = middle+1;
            }
            // 此时 middle 在R low在L 不同数组,表明发生了旋转
            // 但不能middle是不是R的第一个元素,让high指针移动到middle
            else if (nums[middle] < nums[low]){
                high = middle;
            }
            // 此时 low肯定第一次移动到 R
            else if (nums[middle] < nums[high]){
                return nums[low];
            }
        }
        return nums[low];
    }


    @Test
    public void testFindMinMiddle(){
        log.info("findMin = {}",findMinMiddle(new int[]{
                3,1,2
        }));
    }

    /**
     * 空间 o(1)
     *
     * 1 - n-1
     */
    public int findDuplicate(int[] nums) {

        // 1,3,4,2,2  0 - 1 - 3 - 2 - 4 - 2
        // 2,3,1,4,5  0 -2 - 1 - 3 - 4 - 5 - null
        // fn = i -> num[i]映射
        // 如果没有重复元素的话,那么进行映射必然不会出现两个数字映射到同个位置
        // 因为数字在 1 - n 那么对于下标 0<= m <= n-1 而言 其映射到 1 <= nums[m] <= n-1
        // 必然会存在映射到 n-1 超出下标而终止
        // 如果存在重复数字 ,会出现 m映射到 nums[m] 而nums[m] 映射到之前已经被映射的位置(本来不重复应该终止)
        // 从而形成环
        int quick = nums[nums[0]],slow = nums[0],head = 0;
        // quick 每次移动两个 slow移动一个 head 到环 距离 a 当两者相遇时激励环入口为 b ,b到环尾为c
        // 则quick移动了 a + n(b+c)+b  slow移动了 a+(n-1)(b+c)+b quick 是slow的 2倍
        // a + n(b+c) +b = 2a + 2n(b+c) - 2(b+c) + 2b
        // n(b+c) -2(b+c) -b = a
        while (quick != slow){
            quick = nums[nums[quick]];
            slow = nums[slow];
        }
        while (head!=slow){
            slow = nums[slow];
            head = nums[head];
        }


        return head;
    }

    @Test
    public void testFindDuplicate(){
        log.info("findDuplicate = {}",findDuplicate(new int[]{
                1,5,4,3,2
        }));
    }


    /**
     *
     * 不可以包含重复的三元组
     *
     * 	测试用例:[-1,0,1,2,-1,-4]
     * 	期望结果:[[-1,-1,2],[-1,0,1]]
     *
     */
    public List<List<Integer>> threeSum(int[] nums) {

        if (nums.length<3){
            return Collections.emptyList();
        }
        Arrays.sort(nums);

        /**
         *
         * -1,0,1,2,-1,-4
         *
         * -4,-1,-1,0,1,2
         * 如果三层循环的话
         * 对于 m
         * 遍历n - len n > m
         * 对于 n
         * 遍历 k - len k > n
         * 如果数组是递增的
         * 那么对于
         * n 和 k  而言
         * m + n + k = 0
         * 则下次必然只有
         * n增加 k减少
         */
        List<List<Integer>> three = new ArrayList<>();

        for (int m = 0; m < nums.length; m++) {
            // 此种情况肯定不存在仍然满足的,直接退出
            if (nums[m] > 0){
                return three;
            }
            /**
             * 保证
             * nums[m] <= nums[n] <= nums[k]
             * m < n < k
             *
             * 如果当前m指向和前一个元素相同,直接过滤这个元素
             * 对于 m 指针而言,如果 m 指针 当前和下一个元素值相同
             * 0,0,0,0,...,0 这种情况下
             * m = 0 找到了 0,0,0 组合
             *
             * 也就是一轮可以找到包含 nums[m]的所有符合组合 A
             *
             * 对于下轮而言，如果元素相同，那么这轮找到的组合 B
             * 有关系 A 包含 B
             * 因为需要去处 相同元素
             */
            if (m != 0 && nums[m] == nums[m-1]){
                continue;
            }
            // 元素不能重复使用,需要从当前数字的下一位开始
            // 让 k指针指向 最后一个元素
            int n = m+1,k = nums.length-1;
            /**
             * 找到 以 nums[m] 开头的组合
             * 这一轮会把包含 nums[m] 所有满足的找出来
             *
             */
            for (; n < k;) {
                int sum = nums[m] + nums[n] + nums[k];
                if (sum == 0){
                    three.add(Arrays.asList(nums[m],nums[n],nums[k]));
                    /**
                     * 移动 n 和 k 找到下一个满足的
                     * 此时 n 和 k 都已经使用了
                     * 对于 n 而言下个数肯定大于等于当前数
                     * 此时 移动 k 找到一个小于nums[k]的数
                     *
                     * 不移动 n 指针 因为此时 k 指针指向的元素
                     * 改变了,如果n 指向的下个元素 和nums[n]
                     * 相同那么是不会
                     *
                     */
                    int num = nums[k];
                    while (n < k && num == nums[k]){
                        k--;
                    }

                }
                /**
                 * 此情况下说明 k 指向的值偏大了
                 * 移动 k 指针
                 */
                else if (sum > 0){
                    k--;
                }
                /**
                 * 此情况说明 对于当前m而言 n偏小了
                 * 移动 n 指针
                 */
                else {
                    n++;
                }
                
            }
        }

        return three;
    }

    @Test
    public void testThreeSum(){
        log.info("threeSum = {}",threeSum(new int[]{
                -1,0,1,2,-1,-4
        }));
    }



    public int maxSubArray(int[] nums) {
        int[] dp = nums;
        int max = dp[0];
        /**
         * dp[i] 表示到 i(包括)为止的最优解
         * 如果nums[i] < 0
         * 则对于 dp[i]而言
         * 如果dp[i-1] < 0 则 dp[i] = nums[i] 不能连续
         *
         * 如果dp[i-1] >0  则 dp[i] = nums[i]+dp[i-1] 不能连续
         * 此时对于 dp[i]而言是最优解，因为保证了 dp[i]最大
         *
         * 如果num[i] > 0 则对于 dp[i] 而言
         * 如果dp[i-1] < 0
         * 则dp[i]最大为 nums[i]  不能连续
         *
         * 如果 dp[i-1] > 0
         * 则 dp[i] = dp[i-1]+nums[i] 可以连续
         *
         *
         */

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(0,dp[i-1])+nums[i];
            max = Math.max(max,dp[i]);
        }
        return max;
    }


    @Test
    public void testMaxSubArray(){
        log.info("maxSubArray = {}",maxSubArray(new int[]{
                -2,1,-3,4,-1,2,1,-5,4
        }));
    }


    /**
     * 给定一个整数数组和一个整数 k，
     * 你需要找到该数组中和为 k 的连续的子数组的个数。
     * 输入:nums = [1,1,1], k = 2
     * 输出: 2 , [1,1] 与 [1,1] 为两种不同的情况。
     */
    public int subarraySum(int[] nums, int k) {

        /**
         *
         * 前缀和
         * sum[i] = sum[i-1] + nums[i]
         * 即sum[i] 表示从 0 - i 的和
         *
         * 假设[j,i]的和为 k
         * k = sum[i] - sum[j-1]
         *
         *
         *
         * 如果count[i] 为 nums[0] - nums[i] 之和
         * 子数组[j,i]满足和为k
         * 则 nums[j,i] = count[i] - count[j-1]
         * count[i] - count[j-1] = k
         *
         * count[i] = k + count[j-1]
         *
         * 也就是 count[i] 和 count[j-1] 构成差值为 k
         *
         *
         * 如果之前 count[j-1] 有两个序列
         * 那么这两个序列均可以和[i,j] 组合成
         *
         *
         *
         * 此时nums[j,i]满足 为k
         * 1,2   2,-4,2,3   6,2,-4,2,3,-9,3
         * [1,2,5,6,2,-4,2,3,-9,3] k = 3
         * 0 1
         * 1 1
         * 3 1
         * 8 1
         * 14 1
         * 16 1
         * 12 1
         * 14 2
         * 17 1
         * 12 2
         * 15 1
         */

        Map<Integer,Integer> sumMap = new HashMap<>();
        int sum = 0,count = 0;

        sumMap.put(0,1);

        // 计算区间[j,i]的和
        // 如果和为k 则满足
        // 计算[1,2] 时 可以通过 [0,1]的和跟 nums[2] 推出
        /**
         * 如果[i,j]的和满足情况
         *
         */
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            /**
             * 如果sum[i] - k 存在
             * 说明之前存在sum[j] + k = sum[i]  i > j
             * 此时[j-1,i-1]数的和为 k
             * 如果此时有多个数组为k存在，那么说明有多个数组满足
             * 这种情况需要累加
             */
            if (sumMap.containsKey(sum - k)){
                count += sumMap.get(sum-k);
            }
            sumMap.put(sum,sumMap.getOrDefault(sum,0)+1);
        }

        return count;
    }


    @Test
    public void testSubarraySum(){
        log.info("subarraySum = {}",subarraySum(
                new int[]{
                        3,4,7,2,-3,1,4,2
                },7
        ));
    }



//    public List<List<Integer>> threeSum(int[] nums) {
//        List<List<Integer>> treeList = new ArrayList<>();
//        return treeList;
//    }




}
