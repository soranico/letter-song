package com.kanozz.str;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class StrOperation {

    /**
     * 8 字符串转整数
     * pass
     */
    public int myAtoi(String s) {
        StringBuilder atoi = new StringBuilder(32);
        int len = s.length();
        boolean positive = true;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            // 已经存在 + - 数字,后面不是 数字直接退出
            if (atoi.length() > 0 && (c<0x30 || c > 0x39)){
                break;
            }else if (atoi.length()==0){
                if (c == ' ') {
                }
                // 符号
                else if (c == '-'){
                    positive = false;
                    atoi.append('-');
                }else if (c == '+')
                    atoi.append('+');
                // 非符号和空格退出
                else if (c<0x30 || c > 0x39)
                    break;
                // 数字添加
                else
                    atoi.append(c);
            }else {
                atoi.append(c);
            }
        }
        if (atoi.length()==0 ||
                (atoi.length()==1 &&
                        (atoi.charAt(0)=='-' || atoi.charAt(0)=='+'))){
            return 0;
        }
        try {
            return Integer.parseInt(atoi.toString());
        }catch (NumberFormatException ignore){
            return positive?Integer.MAX_VALUE:Integer.MIN_VALUE;
        }
    }


    @Test
    public void testMyAtoi() {
        log.info("atoi = {}", myAtoi("++1"));
        log.info("atoi = {}", myAtoi("--1"));
        log.info("atoi = {}", myAtoi("-+1"));
        log.info("atoi = {}", myAtoi("+-1"));
        log.info("atoi = {}", myAtoi("  00000000000-12345678"));
        log.info("atoi = {}", myAtoi("   -42"));
        log.info("atoi = {}", myAtoi("words and 987"));
        log.info("atoi = {}", myAtoi("-91283472332"));
        log.info("atoi = {}", myAtoi("+123-"));
        log.info("atoi = {}", myAtoi("00000-42a1234"));
        log.info("atoi = {}", myAtoi("00000000012345678"));
        log.info("atoi = {}", myAtoi("+-12"));
    }


    /**
     * // IPv4 地址由十进制数和点来表示，每个地址包含 4 个十进制数，
     * 其范围为 0 - 255， 用(".")分割。比如，172.16.254.1；
     * <p>
     * // IPv6 地址由 8 组 16 进制的数字来表示，每组表示 16 比特。
     * 这些组数字通过 (":")分割。比如,
     * 2001:0db8:85a3:0000:0000:8a2e:0370:7334 是一个有效的地址。而且，
     * 我们可以加入一些以 0 开头的数字，字母可以使用大写，也可以是小写。所以，
     * <p>
     * 2001:db8:85a3:0:0:8A2E:0370:7334 也是一个有效的 IPv6 address地址
     * (即，忽略 0 开头，忽略大小写)。
     * <p>
     * 然而，我们不能因为某个组的值为 0，而使用一个空的组，
     * 以至于出现 (::) 的情况。 比如，
     * 2001:0db8:85a3::8A2E:0370:7334是无效的 IPv6 地址。
     * <p>
     * 同时，在 IPv6 地址中，多余的 0 也是不被允许的。比如，
     * 02001:0db8:85a3:0000:0000:8a2e:0370:7334 是无效的
     * <p>
     * // 输入：IP = "172.16.254.1"
     * //输出："IPv4"
     * <p>
     * // 输入：IP = "2001:0db8:85a3:0:0:8A2E:0370:7334"
     * //输出："IPv6"
     * <p>
     * // 输入：IP = "2001:0db8:85a3:0:0:8A2E:0370:7334:"
     * //输出："Neither"
     */
    public String validIPAddress(String IP) {
        String v4 = "IPv4", v6 = "IPv6", notIp = "Neither";
        /**
         * . 0x2E
         * : 0x3A
         * A 0x41
         * a 0x61
         * 0 0x30
         */
        char[] ip = IP.toCharArray();
        byte group = 0;
        boolean v4Flag = false;
        StringBuilder num = new StringBuilder(4);
        for (int i = 0; i < ip.length; i++) {

            /**
             *
             * 1. 0x30 - 0x39 + 0x2E 组合 v4
             * 2.
             *
             */
            char cur = ip[i];
            if (v4Flag) {
                /**
                 * . 处理
                 * 下个字符0开头 只能为0
                 * 否则 最大 255
                 */
                if (cur == 0x2E) {
                    if (group++ > 3) {
                        return notIp;
                    } else if (ip[i + 1] == 0x30 && i + 2 < ip.length && ip[i + 2] != 0x2E) {
                        return notIp;
                    } else {
                        while (ip[i + 1] != 0x2E) {
                            num.append(ip[i + 1]);
                            i++;
                        }
                        if (num.length() == 3 && "255".compareTo(num.toString()) < 0) {
                            return notIp;
                        }
                    }
                }
            }

            if (cur == 0x2E) {
                v4Flag = true;
            }
        }
        return notIp;
    }



    /**
     * 165
     * // 如果 version1 > version2 返回 1，
     * // 如果 version1 < version2 返回 -1，
     * <p>
     * //输入：version1 = "1.01", version2 = "1.001"
     * //输出：0
     * //解释：忽略前导零，"01" 和 "001" 都表示相同的整数 "1"
     * <p>
     * <p>
     * //输入：version1 = "1.0", version2 = "1.0.0"
     * //输出：0
     * //解释：version1 没有指定下标为 2 的修订号，即视为 "0"
     * <p>
     * //输入：version1 = "1.0.1", version2 = "1"
     * //输出：1
     * <p>
     * //输入：version1 = "7.5.2.4", version2 = "7.5.3"
     * //输出：-1
     */
    public int compareVersion(String version1, String version2) {

        return -1;
    }



}
