package com.cslgduke.demo.core.test.jdk.function;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author i565244
 */
@Slf4j
public class FunctionTest {

    @Test
    public void testFunction(){
       Function<Integer,String> fa = t -> {
           return RandomUtil.randomString(5)+ "-" + t;
       };
       System.out.println(fa.apply(8));

    }

    @Test
    public void testConsmuer(){
        var strList = Arrays.asList("a","b","c");
        strList.forEach(t ->{
            System.out.println("hello-" + t);
        });

        Consumer<String> consumerA = t ->{
            t = t + "_consumer";
            System.out.println(t);
        };
        strList.forEach(consumerA);
    }

    @Test
    public void test_funcion_refer(){
        List<Integer> list = Arrays.asList(1000,2000,3000);
        //static function refer
        List<String> rstList = CollectionUtil.convert(list,Integer::toHexString);
        log.info("convert to hexString:{}", JSON.toJSONString(rstList));

        //non-static function refer
        var hexList = CollectionUtil.convert(rstList,String::toUpperCase);
        log.info("convert to upperString:{}", JSON.toJSONString(hexList));


        FunctionRefer functionRefer = new FunctionRefer();
        List<Integer> incrementList = CollectionUtil.convert(list,functionRefer::increment);
        log.info("increment list :{}", JSON.toJSONString(incrementList));


    }

    @Test
    public void test_predict(){
        Predicate<FieldInfoDTO> func = FieldInfoDTO::getLongIdColumn;

        var fieldInfo =  FieldInfoDTO.builder().longIdColumn(true).build();
        log.info("predict result:{}",func.test(fieldInfo));

        var a = (String) null;
        System.out.println(a);
    }

    @Test
    public void test_function(){
        var parallelTask = new ParallelTaskBooster();
        var strList = Arrays.asList("a","b","c");
        strList.forEach(t -> {
            var r = parallelTask.convert(t,k -> k + RandomUtil.randomString(10));
            log.info("new str value:{}",r);
        });

        var executor = Executors.newFixedThreadPool(100);
        var originList = Arrays.asList("one","two","three");
        var rstList = parallelTask.genericParallel(originList,r -> {
            var rstStr = r+RandomUtil.randomString(10);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("origin str:{},rstStr:{}",r,rstStr);
            return rstStr;
        },executor);
        log.info("result list:{}",rstList);
    }

}
