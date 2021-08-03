package com.kanonsd.str;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StrOperation {


    public int myAtoi(String s) {
        char space = ' ',sign = ' ',add = '+',sub = '-';
        char[] chars = s.toCharArray();
        boolean hasNum = false;
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char cur = chars[i];
            // 空格 或 前导0忽略
            if (cur == space){
            }else if (cur ==0x30 && num.length() ==0){
                hasNum = true;
            }
            // 存在多个正负符退出
            else if ((sign != space || hasNum) && (cur==add || cur==sub)){
                break;
            }
            // 符号位
            else if (sign == space && (cur ==add || cur ==sub) && num.length()==0){
                sign = cur;
            }
            // 非数字直接退出
            else if (cur<0x30 || cur>0x39){
                break;
            } else {
                num.append(cur);
            }
        }
        if (num.length()==0) return 0;
        String max = "+"+Integer.MAX_VALUE,min = String.valueOf(Integer.MIN_VALUE);
        num.insert(0,sign==space?add:sign);

        if (num.length() < 11){
            return sign==space || sign ==add ?Integer.parseInt(num.toString()):
                    Integer.parseInt(num.toString());
        }else if (num.length()>11){
            return sign ==sub?Integer.MIN_VALUE:Integer.MAX_VALUE;
        }
        if (sign == sub){
            return min.compareTo(num.toString())>0?Integer.parseInt(num.toString())
                    :Integer.MIN_VALUE;
        }

        return max.compareTo(num.toString())>0?Integer.parseInt(num.toString()):Integer.MAX_VALUE;
    }










    @Test
    public void testMyAtoi(){
        log.info("atoi = {}",myAtoi("21474836460"));
        log.info("atoi = {}",myAtoi("  00000000000-12345678"));
        log.info("atoi = {}",myAtoi("   -42"));
        log.info("atoi = {}",myAtoi("words and 987"));
        log.info("atoi = {}",myAtoi("-91283472332"));
        log.info("atoi = {}",myAtoi("+123-"));
        log.info("atoi = {}",myAtoi("00000-42a1234"));
        log.info("atoi = {}",myAtoi("00000000012345678"));
        log.info("atoi = {}",myAtoi("+-12"));
    }
}
