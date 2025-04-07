package com.cslgduke.demo.core.test.jdk;

import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * @author i565244
 */
public class DurationTest {

    @Test
    public void testApi() {
        var start = "2023-11-14";
        var end = "2023-11-15";
        var startDate = LocalDate.parse(start);
        var endDate = LocalDate.parse(end);
        var currDate = LocalDate.now();
        var curr = String.valueOf(currDate);
        System.out.println((currDate.isAfter(startDate) && currDate.isBefore(endDate)) || currDate.isEqual(startDate) || currDate.isEqual(endDate)) ;

        var start2 = LocalDateTime.of(2021, 1, 1, 0, 0);
        var duration = Duration.between(start2, LocalDateTime.now());

        System.out.println(LocalDate.now().plusDays(2));
//        System.out.println(duration.get(ChronoUnit.DAYS));
//        System.out.println("day of year :" + LocalDate.now().getDayOfYear());
//
//        LocalDate startDate = LocalDate.of(2021, 1, 1);
//        Period period = Period.between(startDate, LocalDate.now());
//
//
//        var seconds = ChronoUnit.SECONDS.between(start, LocalDateTime.now());
//        System.out.println("duration seconds:" + seconds);
//
//        var minutes = ChronoUnit.MINUTES.between(start, LocalDateTime.now());
//        System.out.println("duration minutes:" + minutes);
//
//        var hours = ChronoUnit.HOURS.between(start, LocalDateTime.now());
//        System.out.println("duration hours:" + hours);
//
//        var days = ChronoUnit.DAYS.between(startDate, LocalDate.now());
//        System.out.println("duration days:" + days);
//
//        var weeks = ChronoUnit.WEEKS.between(startDate, LocalDate.now());
//        System.out.println("duration weeks:" + weeks);

        var sets = new HashSet<String>();
        var dupCount = 0;
        for (int i = 0; i < 1000; i++) {
            var rn = RandomUtil.randomNumbers(5);
            if(!sets.contains(rn)){
                sets.add(rn);
//                System.out.println("random numbers :"+ rn);
            }else{
                dupCount++;
                System.out.println("duplicate random numbers :"+ rn);
            }
        }
        System.out.println("dupCount is :" + dupCount);

//        var setInts = new HashSet<Long>();
//        for (int i = 0; i < 100; i++) {
//            var rn = RandomUtil.randomLong(999L);
//            if(!setInts.contains(rn)){
//                setInts.add(rn);
//                System.out.println("random numbers :"+ rn);
//            }else{
//                System.out.println("duplicate random numbers :"+ rn);
//            }
//        }
    }
}
