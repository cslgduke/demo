package com.cslgduke.demo.core.test.jdk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

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
    public void test_Map() {

    }

    public enum TeamMemberFunctionEnum {
        KA("1","keyAccountManager"),
        SP("2","salesRepresentative");

        private String code;
        private String name;

        private TeamMemberFunctionEnum(String code, String name){
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

        public static String getName(String code){
            String name = "";
            for(TeamMemberFunctionEnum s : TeamMemberFunctionEnum.values()){
                if(s.getCode().equals(code)){
                    name = s.getName();
                    break;
                }
            }
            return name;
        }

        public static String getCode(String name){
            String code = "";
            for(TeamMemberFunctionEnum s : TeamMemberFunctionEnum.values()){
                if(s.getName().equals(name)){
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

}
