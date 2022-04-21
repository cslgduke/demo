package com.cslgduke.demo.core.test.mockito.fake;

import com.cslgduke.demo.core.test.mockito.fake.Person;
import lombok.Data;

/**
 * @author i565244
 */

@Data
public class HighSchoolStudent implements Person {
   private String name;
   private int age;
   private int id;
   private int grade;

}
