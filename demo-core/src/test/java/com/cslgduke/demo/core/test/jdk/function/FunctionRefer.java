package com.cslgduke.demo.core.test.jdk.function;

import cn.hutool.core.util.RandomUtil;

/**
 * @author i565244
 */
public  class FunctionRefer {

    public Integer increment(Integer initial){
        return initial + RandomUtil.randomInt();
    }
}
