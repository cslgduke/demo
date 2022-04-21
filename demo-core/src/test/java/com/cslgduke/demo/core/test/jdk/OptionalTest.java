package com.cslgduke.demo.core.test.jdk;

import com.cslgduke.demo.core.common.BaseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * @author i565244
 */

@ExtendWith(MockitoExtension.class)
public class OptionalTest {
    @Test
    public void testApi(){
        Optional<Integer> optional1 = Optional.of(1);
        Optional<Integer> optional2 = Optional.ofNullable(null);
        Optional<Integer> optional3 = Optional.ofNullable(null);
        Optional<Integer> optional4 = Optional.empty();
        System.out.println("Optional.empty() isEmpty:" + optional4.isEmpty());

        System.out.println("Optional.empty() get" + optional4.get());


        System.out.println(optional1 == optional2);
        System.out.println(optional2 == optional3);// true
        System.out.println(optional3 == optional4);// true

        System.out.println(optional1.isPresent());// true

        optional1.ifPresent( t -> {
            System.out.println("present value:" + t);
        });

        optional2.ifPresent( t -> {
            System.out.println("present value:" + t);
        });


        String strVal = optional1.map( t -> "value:" + t).orElse("Null");
        System.out.println(strVal);

        String strVa2 = optional2.map( t -> "value:" + t).orElse("Null");
        System.out.println(strVa2);
        strVa2 = optional2.map( t -> "value:" + t).orElseThrow(() -> new BaseException("NullValue"));

    }
}
