package com.cslgduke.demo.core.test.jdk;

import com.alibaba.fastjson.JSONObject;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@Slf4j
public class CommonTest {

    @Test
    public void test_localDateTime() {
        var str = "2022-05-03T20:00:00.000000";

        var ldt = LocalDateTime.parse((String) str);
    }

    @Test
    public void test_strFormat() {
        var str =
                String.format("%s %s %s", "Fist", null, null);
        log.info("str format result:{}", str);
    }


    @Test
    public void test_stream() {
        var emailsList = Arrays.asList("test1", "test2");
        var emails = emailsList.stream()
                .filter(t -> t != null);

       /* var defaultEmails = emails.filter(t -> t.length() > 2);

        if(defaultEmails.findFirst().isPresent()){
            log.info("find default eamil:{}",defaultEmails.findFirst().get());
        }//stream has already been operated upon or closed

        */
        var defaultEmail = emails.filter(t -> t.length() > 2).findFirst();
        if (defaultEmail.isPresent()) {
            log.info("find default eamil:{}", defaultEmail.get());
        }
    }

    @Test
    public void test_Boolean() {
        var boMessage = BoMessage.builder().build();
        //NPE
        if (!boMessage.getNeedInitial()) {
            log.info("don't need to initial");
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoMessage {
        private Boolean needInitial;
    }


    @Test
    public void test_Timestamp() {
        var tstamp = Timestamp.valueOf(LocalDateTime.now());
        log.info("timestamp for now is :{}", tstamp);
    }


    @Test
    public void test_Arrays() {
        var strs = new String[]{"111"};

        var parentStrs = new String[]{"222"};
        var list = Arrays.stream(parentStrs).map(str -> str).collect(Collectors.toList());
//        list.addAll(List.of(strs));
    }

    public enum TeamMemberFunctionEnum {
        KA("1", "keyAccountManager"),
        SP("2", "salesRepresentative");

        private String code;
        private String name;

        private TeamMemberFunctionEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static String getName(String code) {
            String name = "";
            for (TeamMemberFunctionEnum s : TeamMemberFunctionEnum.values()) {
                if (s.getCode().equals(code)) {
                    name = s.getName();
                    break;
                }
            }
            return name;
        }

        public static String getCode(String name) {
            String code = "";
            for (TeamMemberFunctionEnum s : TeamMemberFunctionEnum.values()) {
                if (s.getName().equals(name)) {
                    code = s.getCode();
                    break;
                }
            }
            return code;
        }

        @Override
        public String toString() {
            return String.valueOf(this.getCode());
        }

    }


    private static final String registerServiceBody = "{\n" +
            "    \"cluster\":\"crp-dev\",\n" +
            "    \"namespace\":\"crp-sit\",\n" +
            "    \"mmpCluster\":\"crp-dev\",\n" +
            "    \"mmpNamespace\":\"crp-sit\",\n" +
            "    \"services\":[{\"serviceName\":\"%s\",\"endpoint\":\"https://api.crp-sit.crp-dev.eurekacloud.io/integration-config/system/v1/tenant-lcm-plus\",\"order\":50,\"group\":\"defaultGroup\"}]\n" +
            "}";

    public static void main(String[] args) {
        System.out.println(registerServiceBody);
    }


    @Test
    public void test_urlEncode() {
        var rawStr = "hello world";
        var encodeStr = URLEncoder.encode(rawStr, Charset.forName("utf-8"));
        var decodeStr = URLDecoder.decode(encodeStr);
        log.info("encodeStr:{}", encodeStr);
        log.info("decodeStr:{}", decodeStr);

    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    class Student {
        int age;
    }

    @Test
    public void test_sort() {
        List<Student> stus = List.of(new Student(20),new Student(10) , new Student(30));
        var stusSorted = stus.stream().sorted(Comparator.comparing(Student::getAge).reversed()).collect(Collectors.toList());
        log.info("origin list:{}", stus);

        log.info("sorted list:{}", stusSorted);
        var tmp = stus.stream().sorted((o1,o2) ->{
            return o2.getAge() - o1.getAge();
        }).collect(Collectors.toList());
        log.info("reverse sorted list:{}", stusSorted);

//        Collections.sort(stus,Comparator.comparing(Student::getAge));
        List intList = Arrays.asList(2, 3, 1);
        Collections.sort(intList);
        log.info("sorted intList:{}", intList);

        List<Student> stus2=  new ArrayList<>();
        stus2.add(new Student(20));
        stus2.add(new Student(30));
        stus2.add(new Student(10));
        Collections.sort(stus2,Comparator.comparing(Student::getAge));
        log.info("sorted stus2:{}", stus2);

    }

    @Test
    public void test_reflection_field() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        var modifierField = this.getModifierField();
        System.out.println(modifierField);
    }

    private static Field getModifierField() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getDeclaredFields0 = Class.class.getDeclaredMethod("getDeclaredFields0", boolean.class);
        getDeclaredFields0.setAccessible(true);
        Field[] fields = (Field[]) getDeclaredFields0.invoke(Field.class, false);
        Field modifiers = null;
        for (Field each : fields) {
            if ("modifiers".equals(each.getName())) {
                modifiers = each;
            }
        }
        return modifiers;
    }


    @Test
    public void test_matcher(){
        var input = "james";
        var output = Pattern.compile("^.").matcher(input).replaceFirst(s -> s.group().toUpperCase());
        log.info("input:{},output:{}",input,output);

    }

}
