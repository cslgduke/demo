package com.cslgduke.demo.core.test.jdk.function;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author i565244
 */
public class CollectionUtil {

    public static <T,R> List<R> convert(List<T> list, Function<T,R> function){
        List<R> result = new ArrayList<>();
        list.forEach(t -> result.add(function.apply(t)));
        return result;
    }
}
