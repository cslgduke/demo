package com.cslgduke.demo.core.utils;

import cn.hutool.core.util.RandomUtil;

/**
 * @author i565244
 */
public class StringUtils {
    public  static String rdmString(){
        return RandomUtil.randomNumbers(10);
    }
}
