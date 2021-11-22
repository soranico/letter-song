package com.kanozz.skipwindow;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
@Slf4j
public class SlideWindowOperation {
    /**
     *
     * nums[-1] = nums[n] = 无穷小
     *
     */
    public int findPeakElement(int[] nums) {
        if (nums.length ==1){
            return 0;
        }

        int index = 1,first = nums[0],second = nums[1],third,right = 2,max = nums.length;
        while (right < max){
            third = nums[right++];
            if (second>first && second>third){
                return index;
            }
            index++;
            first = second;
            second = third;
        }
        if (nums[0]>nums[1]){
            return 0;
        }
        return  max-1;

    }




    @Test
    public void testFindPeakElement(){
        log.info("findPeakElement = {}",findPeakElement(new int[]{
              1,-1,1
        }));
    }





    public int[] maxSlidingWindow(int[] nums, int k) {
        //
        int right = 1,numsLen = nums.length,max = nums[0],index =0
                ,maxIndex = 0;
        int[] windowMax = new int[numsLen - k + 1];
        while (right <= numsLen){
            // 窗口达到 k  计算
            if (right - maxIndex == k && maxIndex +1 < numsLen){
                windowMax[index++] = max;
                // 如果最大值此时在移除位置 4 1 -1  5 需要更新最大值
                max = nums[++maxIndex];
                int left = maxIndex;
                // 需要重新计算当前窗口的最大值
                while (++left < right){
                    if (max < nums[left]){
                        max = nums[left];
                        maxIndex = left;
                    }
                }

            }else if (right >= k){
                windowMax[index++] = max;
            }
            if (right == numsLen){
                break;
            }
            // 计算最大值
            if (max < nums[right]){
                max = nums[right];
                maxIndex = right;
            }
            // 窗口扩大
            right++;

        }
        return windowMax;
    }


    //输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
    @Test
    public void testMaxSlidingWindow(){
        log.info("maxSlidingWindow = {}",maxSlidingWindow(new int[]{
                9,10,9,-7,-4,-8,2,-6
        },5));
    }




}
