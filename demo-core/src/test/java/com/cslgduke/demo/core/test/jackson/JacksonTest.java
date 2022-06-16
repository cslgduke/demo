package com.cslgduke.demo.core.test.jackson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author i565244
 */
@Slf4j
public class JacksonTest {
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
    public void test_re_serialize_filter_fastjson(){
        var user = User.builder().username("James").password("******").age(18).gender("Meal").blog("James@Weibo").build();
        var str = JSON.toJSONString(user);
        log.info("Fastjson jsonStr:{}", JSON.toJSONString(user,new MyFilter()));
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

}
