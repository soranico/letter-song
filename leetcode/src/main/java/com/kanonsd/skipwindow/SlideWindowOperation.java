package com.kanonsd.skipwindow;

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




}
