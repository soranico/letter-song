package com.kanonsd.stack;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class SortOperation {

    public int[] sortArray(int[] nums) {
        heapSort(nums);
        return nums;
    }


    public ListNode sortList(ListNode head) {


        return null;
    }

    private void fork(ListNode head, ListNode tail, ListNode middle) {
        if (head == tail) {
            return;
        }


    }

    /**
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        int[] min = nums1.length > nums2.length ? nums2 : nums1;
        for (int i : min) {
            set.add(i);
        }
        Set<Integer> data = new HashSet<>();
        int[] max = nums1.length > nums2.length ? nums1 : nums2;
        for (int i : max) {
            if (set.contains(i)) {
                data.add(i);
            }
        }

        int[] result = new int[data.size()];
        int index = 0;
        for (Integer datum : data) {
            result[index++] = datum;
        }
        return result;
    }


    /**
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * <p>
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
     * <p>
     * 输入：nums = [30,30,34,5,9]
     * 输出："9534330"
     *
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        /**
         * 最高位大的大
         * 最高位相同的 次位大于最高位的大 次位小于等于最高位的小
         */
        String[] tree = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            tree[i] = String.valueOf(nums[i]);
        }
        // 构建最大堆
        heapSort(tree, (o1, o2) -> {
            // 对比每个字符
            int minLcm = minLcm(o1.length(), o2.length());
            int loop = minLcm / o1.length();
            StringBuilder first = new StringBuilder(minLcm);
            StringBuilder second = new StringBuilder(minLcm);
            for (int i = 0; i < loop; i++) {
                first.append(o1);
            }
            loop = minLcm / o2.length();
            for (int i = 0; i < loop; i++) {
                second.append(o2);
            }
            return first.toString().compareTo(second.toString());
        });

        StringBuilder num = new StringBuilder();
        for (int i = tree.length - 1; i >= 0; i--) {
            num.append(tree[i]);
        }

        return num.toString();
    }


    public String minNumber(int[] nums) {
        /**
         * 最高位大的大
         * 最高位相同的 次位大于最高位的大 次位小于等于最高位的小
         */
        String[] tree = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            tree[i] = String.valueOf(nums[i]);
        }
        // 构建最大堆
        heapSort(tree, (o1, o2) -> {
            // 对比每个字符
            int minLcm = minLcm(o1.length(), o2.length());
            int loop = minLcm / o1.length();
            StringBuilder first = new StringBuilder(minLcm);
            StringBuilder second = new StringBuilder(minLcm);
            for (int i = 0; i < loop; i++) {
                first.append(o1);
            }
            loop = minLcm / o2.length();
            for (int i = 0; i < loop; i++) {
                second.append(o2);
            }
            return -first.toString().compareTo(second.toString());
        });

        StringBuilder num = new StringBuilder();
        for (int i = tree.length - 1; i >= 0; i--) {
            num.append(tree[i]);
        }
        int index;
        char[] filter = num.toString().toCharArray();
        for (index = 0; index < filter.length - 1; index++) {
            if (filter[index] == '0') {
                continue;
            }
            break;
        }
        return new String(filter, index, filter.length - index);
    }


    public void sortColors(int[] nums) {
        heapSort(nums);
    }


    /**
     * 给你两个有序整数数组
     * nums1 和 nums2，请你将 nums2 合并到nums1中，使 nums1 成为一个有序数组。
     * <p>
     * 初始化nums1 和 nums2 的元素数量分别为m 和 n 。
     * 你可以假设nums1 的空间大小等于m + n，这样它就有足够的空间保存来自 nums2 的元素。
     * <p>
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {


        int nonUse = m + n - 1;
        while (m >= 1 && n >= 1) {
            if (nums1[m - 1] > nums2[n - 1]) {
                nums1[nonUse--] = nums1[--m];
                continue;
            }
            nums1[nonUse--] = nums2[--n];
        }
        // 如果是nums1剩余,则表明nums2都已经处理过了,本身有序无需处理
        // 如果nums2有剩余,则说明nums1已经处理完成,将nums2依次放入即可
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                nums1[i] = nums2[i];
            }
        }


    }


    @Test
    public void testMerge() {
        merge(new int[]{
                1, 7, 7, 0, 0, 0
        }, 3, new int[]{
                4
        }, 1);
    }


    @Test
    public void test() {
//        "43243432"
        log.info("largestNumber = {}",
                minNumber(new int[]{
                        43243, 432
                }));
    }

    private int minLcm(int x, int y) {
        int z = Math.min(x, y);
        while (true) {
            if (z % x == 0 && z % y == 0) {
                break;
            }
            z++;
        }
        return z;
    }

    private <T> void heapSort(T[] tree, Comparator<T> comparator) {
        int max = tree.length;
        for (int i = (max >> 1) - 1; i >= 0; i--) {
            buildHeap(tree, i, max, comparator);
        }
        for (int i = max - 1; i >= 0; i--) {
            T maxValue = tree[0];
            tree[0] = tree[i];
            tree[i] = maxValue;
            buildHeap(tree, 0, i, comparator);
        }

    }

    public <T> void buildHeap(T[] tree, int lastNonLeaf, int max, Comparator<T> comparator) {
        T lastNonLeafValue = tree[lastNonLeaf];
        for (int leaf = (lastNonLeaf << 1) + 1; leaf < max; leaf = (leaf << 1) + 1) {
            if (leaf + 1 < max && comparator.compare(tree[leaf + 1], tree[leaf]) > 0) {
                leaf++;
            }
            if (comparator.compare(tree[leaf], lastNonLeafValue) > 0) {
                tree[lastNonLeaf] = tree[leaf];
                lastNonLeaf = leaf;
                continue;
            }
            break;
        }
        tree[lastNonLeaf] = lastNonLeafValue;
    }




    public int search(int[] nums, int target) {
        int middle , low =0,high = nums.length-1;
        while (low<=high){
            middle = (low+high)>>>1;
            // 之前位置不满足必须移动指针,否则会因为middle指针一直没有变化死循环
            if (nums[middle]>target){
                high = middle-1;
            }else if (nums[middle]<target){
                low = middle + 1;
            }else {
                return middle;
            }
        }
        return -1;
    }


    /**给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。

     岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。

     此外，你可以假设该网格的四条边均被水包围。
     输入：grid = [
     ["1","1","0","0","0"],
     ["1","1","0","0","0"],
     ["0","0","1","0","0"],
     ["0","0","0","1","1"]
     ]
     输出：3
     *
     * 输入：grid = [
     *   ["1","1","1","1","0"],
     *   ["1","1","0","1","0"],
     *   ["1","1","0","0","0"],
     *   ["0","0","0","0","0"]
     * ]
     * 输出：1
     *
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {



        return -1;
    }


    /**
     * 整数数组 nums 按升序排列，数组中的值 互不相同 。
     *
     * 在传递给函数之前，nums 在预先未知的某个下标 k
     * （0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     *
     * 给你 旋转后 的数组 nums 和一个整数 target ，
     * 如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回-1。
     *
     * 输入：nums = [4,5,6,7,0,1,2], target = 0
     * 输出：4
     */
    public int searchK(int[] nums, int target) {
        int k =0,max = nums.length;
        boolean change = true;
        for (int i = 0; i < max-1; i++) {
            if (nums[i] == target){
                return i;
            }
            if (nums[i] > nums[i+1]){
                k = i;
                change = false;
                break;
            }
        }
        if (k == 0 && change){
            return searchScope(nums,target,0,max-1);
        }
        // 搜索后半段
        if (target < nums[0]){
            return searchScope(nums,target,k+1,max-1);
        }
        // 搜索前半段
        return searchScope(nums,target,0,k);

    }

    private int searchScope(int[] nums, int target ,int start ,int end){
        int middle , low =start,high = end;
        while (low<=high){
            middle = (low+high)>>>1;
            // 之前位置不满足必须移动指针,否则会因为middle指针一直没有变化死循环
            if (nums[middle]>target){
                high = middle-1;
            }else if (nums[middle]<target){
                low = middle + 1;
            }else {
                return middle;
            }
        }
        return -1;
    }


    @Test
    public void testSearchK(){
        System.err.println(searchK(new int[]{
               1,2,3,4
        }, 1));
    }

    @Test
    public void  testSearch(){
        search(new int[]{
                -1,0,3,5,9,12

        },13);
    }




















    @Test
    public void testIntersection() {
        log.info("intersection = {}", intersection(
                new int[]{
                        1, 2, 2, 1
                },
                new int[]{
                        2, 2
                }
        ));
    }


    private void quickSort(int left, int right, int[] nums) {
        if (left > right) {
            return;
        }
        int sap = nums[left], leftPointer = left, rightPointer = right;
        while (leftPointer < rightPointer) {
            while (leftPointer < rightPointer && nums[rightPointer] > sap) {
                rightPointer--;
            }
            if (leftPointer < rightPointer) {
                nums[leftPointer] = nums[rightPointer];
            }
            while (leftPointer < rightPointer && nums[leftPointer] < sap) {
                leftPointer++;
            }
            if (leftPointer < rightPointer) {
                nums[rightPointer] = nums[leftPointer];
            }
        }
        nums[leftPointer] = sap;

        quickSort(left, leftPointer - 1, nums);
        quickSort(rightPointer + 1, right, nums);


    }


    /**
     * 将元素构成一个大顶堆,此时堆顶元素即最大值
     * 将堆顶元素和堆尾元素交换,然后将剩余n-1个元素重新构成
     * 大顶堆,直到没有剩余元素
     * <p>
     * 大顶堆：arr[i] >= arr[2i+1] && arr[i] >= arr[2i+2]
     * <p>
     * 小顶堆：arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
     */
    private void heapSort(int[] nums) {
        int max = nums.length;
        // 构建大顶堆
        for (int i = (max >>> 1) - 1; i >= 0; i--) {
            buildHeap(nums, i, max);
        }
        // 将每次调整的大顶堆的根节点移除并再次调整
        for (int i = max - 1; i >= 0; i--) {
            int temp = nums[i];
            nums[i] = nums[0];
            nums[0] = temp;
            buildHeap(nums, 0, i);
        }


    }

    private void buildHeap(int[] nums, int nonLeaf, int max) {
        // 当前的非叶子节点
        int nonLeafVal = nums[nonLeaf];
        for (int k = (nonLeaf << 1) + 1; k < max; k = (k << 1) + 1) {
            // 存在右孩子并且右孩子比左孩子大,则和父节点比较的为较大值
            if (k + 1 < max && nums[k] < nums[k + 1]) {
                k++;
            }
            // 孩子节点大于父节点
            if (nums[k] > nonLeafVal) {
                // 父节点值改为孩子节点
                nums[nonLeaf] = nums[k];
                // 将非叶子节点指向孩子节点,因为交换后此时孩子节点的值会变小
                // 需要调整孩子节点的子树节点
                // e.g  2  - 5 - 3 4  5 - 2 - 3 4 此时需要调整 2 - 3 4
                nonLeaf = k;
                continue;
            }
            break;
        }
        // 修改最后一个非叶子节点值为调整前的叶子节点值
        nums[nonLeaf] = nonLeafVal;
    }


    @Test
    public void testSortArray() {
        heapSort(new int[]{
                3, 2, 1, 5, 8, 4, 9, 0, 11, 6
        });
        log.info("sortArray = {}", sortArray(new int[]{
                3, 2, 1, 5, 8, 4, 9, 0, 11, 6
        }));
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
