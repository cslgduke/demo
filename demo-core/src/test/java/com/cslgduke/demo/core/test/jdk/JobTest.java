package com.cslgduke.demo.core.test.jdk;


import lombok.Builder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description :
 * @Author : muming
 * @Date: 2021-07-27 15:35
 */
public class JobTest {

    public static void main(String[] args) {
// [3，45, 9，1，3，4，2，3，7，2]
// 取出这个数组中按照频次排序TOP N元素
        List<Integer> datas = Arrays.asList(3,45,9,1,3,4,2,3,7,2);
        List<Integer> dataTopN = getTopN(datas,2);
        System.out.println(dataTopN);

    }

    public static List<Integer> getTopN(List<Integer> datas,int n){
        HashMap<Integer,Integer> dataMap = new HashMap<>();
        for (int item : datas
        ) {
            Integer count =  dataMap.computeIfAbsent(item,key-> 0);
            dataMap.put(item,count + 1);
        }

        Stream<Map.Entry<Integer, Integer>> sortedData = dataMap.entrySet().stream().sorted((o1,o2) -> {
            //倒叙排列
            return o2.getValue().compareTo(o1.getValue());
        });
        List<Map.Entry<Integer, Integer>> sortedDataFilter = sortedData.limit(n).collect(Collectors.toList());
        List<Integer> rstList = new ArrayList<>(n);
        sortedDataFilter.forEach( dataItem -> rstList.add(dataItem.getKey()));
        return rstList;
    }

}