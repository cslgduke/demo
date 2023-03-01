package com.cslgduke.demo.core.test.jackson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author i565244
 */
@Slf4j
public class JacksonTest {

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test_serialize() throws JsonProcessingException {
        var user = User.builder().username("James").password("******").age(18).gender("Meal").blog("James@Weibo").build();
        ObjectMapper mapper = new ObjectMapper();
        var jsonStr = mapper.writeValueAsString(user);
        log.info("json string:{}",jsonStr);
    }

    @Test
    public void test_re_serialize_filter() throws JsonProcessingException {
        var user = User.builder().username("James").password("******").age(18).gender("Meal").blog("James@Weibo").build();
        ObjectMapper mapper = new ObjectMapper();

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("non-password", SimpleBeanPropertyFilter.serializeAllExcept("password"));
        mapper.setFilterProvider(filterProvider);

        var jsonStr = mapper.writeValueAsString(user);
        log.info("json string:{}",jsonStr);

        var newUser = mapper.readValue(jsonStr, User.class);
        log.info("****{}",newUser);
    }

    @Test
    public void test_re_serialize_jackson() {
        ObjectMapper om = new ObjectMapper();
        om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    //
        om.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);

    }

    @Test
    public void test_re_serialize_filter_fastjson() throws JsonProcessingException {
        var user1 = User.builder().username("James").password("******").age(18).gender("Meal").blog("James@Weibo").build();
        var user2 = User.builder().username("James").password("******").age(18).gender("Meal").blog("James@Weibo").build();

        log.info("Fastjson jsonStr:{}", JSON.toJSONString(user1,new MyFilter()));

        var jsonStr = JSON.toJSONString(Arrays.asList(user1,user2));
        log.info("list user:{}",jsonStr);
        ObjectMapper mapper = new ObjectMapper();
        var userList = mapper.readValue(jsonStr, new TypeReference<List<User>>() {});
        log.info("new user list:{}",JSON.toJSONString(userList));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonFilter("non-password")
    public static class User{
        String username;
//        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Jackson
//        @JSONField(serialize = false)//fastjson
        String password;
        Integer age;
        String gender;
        String blog;
    }

    public class MyFilter implements   PropertyFilter{
        @Override
        public boolean apply(Object object, String name, Object value) {
            if (name.equals("password")) {
                return false;
            }
            return true;
        }
    }


    @Test
    public void test_nested_obj_jackson() throws JsonProcessingException {
        OutterDto outter = OutterDto.builder().name("JacksonTest").build();
        outter.setInner(InnerDto.builder().index(0).desc("Summary").build());

        outter.setInners(Arrays.asList(
                InnerDto.builder().index(1).desc("inner-1").build(),
                InnerDto.builder().index(2).desc("inner-2").build()));

        var jsonStr = mapper.writeValueAsString(outter);
        log.info("outter info:{}",jsonStr);



        var deSerialObj = mapper.readValue(jsonStr,OutterDto.class);
        log.info("deserial outter obj:{}",jsonStr);
    }


}
