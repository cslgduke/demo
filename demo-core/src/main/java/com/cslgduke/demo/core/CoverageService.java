package com.cslgduke.demo.core;

import cn.hutool.Hutool;
import cn.hutool.core.util.RandomUtil;

/**
 * @author i565244
 */
public class CoverageService {

    private String name;

    private int age;

    //instruction 1
    public void method(){
    }

    //instruction 2
    public String mRetrun_2(){
        return null;
    }

    //4
    public String mRetrun_4(){
        return RandomUtil.randomString(20) + "a";
    }

    public String mRetrun_6(){
        return (RandomUtil.randomString(20) + "a").substring(2);
    }

//    public String calculateName(){
////        return name + RandomUtil.randomString(20);//3
////        return name;//3
//        var a = RandomUtil.randomInt();
//        return name + "-" + a;
//    }
//
////    public int calculAge(){
//////        return name + RandomUtil.randomString(20);//3
////        return age;
////    }


}
