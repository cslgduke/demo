package com.cslgduke.demo.core.test.jdk.jdk17;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

/**
 * @author i565244
 */
@Slf4j
public class Jdk17Test {

    public static void main(String[] args) {
        int a = (int)(Math.random() *20 + 20);
        System.out.println(a);


        Integer b = 1;
        System.out.println(b == 1);
    }

    @Test
    public void test_textBlock() {
        String poem = """
                Twinkle, Twinkle, Little Star

                Twinkle, twinkle, little star,
                How I wonder what you are!
                Up above the world so high,
                Like a diamond in the sky.

                Twinkle, twinkle, little star,
                How I wonder what you are!
            """;
        log.info("text block poem \n:{}",poem);
    }

    @Test
    public void test_map() {
        List<Map<String,Object>> ms = List.of(Map.of("k1","k1","k2","k2"),Map.of("k1","k1k1","k2","k2k2"));
        var cnt = ms.stream().filter(t -> t.get("k1").equals("k1")).count();
        log.info("count is :{}",cnt);
    }


    @Test
    public void test_UUIDHash() {
        var maps = new HashMap<Integer,Integer>();
        var total = 1000000;
        var sets = new HashSet<Integer>();
        for (int i = 0; i < total; i++) {
            var uuid = UUID.randomUUID().toString().replace("-", "");
//            var hashInt = HashUtil.bkdrHash(uuid);
            var hashInt = HashUtil.murmur32(uuid.getBytes());
            sets.add(hashInt);
//            log.info("uuid:{} ,hashInt:{}",uuid,hashInt);
            var index = hashInt % 100;
            Integer count =  maps.computeIfAbsent(index,key-> 0);
            maps.put(index,count + 1);
        }
        log.info("algorithm:{},total:{},hashCount:{},collision-rate:{}","murmur32",total,sets.size(),(total - sets.size()) / total);
        log.info("uuid distribution {}",maps);

        var maps2 = new HashMap<Integer,Integer>();
        var sets2 = new HashSet<Integer>();
        for (int i = 0; i < total; i++) {
            var uuid = UUID.randomUUID().toString().replace("-", "");
            var hashInt = HashUtil.bkdrHash(uuid);
            sets2.add(hashInt);
//            log.info("uuid:{} ,hashInt:{}",uuid,hashInt);
            var index = hashInt % 100;
            Integer count =  maps2.computeIfAbsent(index,key-> 0);
            maps2.put(index,count + 1);
        }
        log.info("algorithm:{},total:{},hashCount:{},collision-rate:{}","bkdrHash",total,sets.size(),(total - sets.size()) / total);
        log.info("uuid distribution {}",maps2);
    }

    @Test
    public void test_ListAdd() {
        List<String> listA = null;
        List<String> listB = List.of();
        List<String> listC = List.of("c-001","c-002");
        List<String> listD = List.of("d-001","d-002");

        var finalList = new ArrayList<String>();
        if(CollectionUtil.isNotEmpty(listA)){
            finalList.addAll(listA);
        }
        if(CollectionUtil.isNotEmpty(listB)){
            finalList.addAll(listB);
        }
        if(CollectionUtil.isNotEmpty(listC)){
            finalList.addAll(listC);
        }
        if(CollectionUtil.isNotEmpty(listD)){
            finalList.addAll(listD);
        }
        log.info("the final list is:{}",finalList);
    }

    @Test
    public void test_file() {
        File defaultEnvFile = new File("/Users/i565244/serviceBindings");
        log.info("is Directory:{}" + defaultEnvFile.isDirectory());
        log.info("is exist:{}" + defaultEnvFile.exists());
    }

}
