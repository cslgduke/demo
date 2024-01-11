package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.bo.User;
import com.example.demo.cache.CacheService;
import com.example.demo.msg.KafkaProducer;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vo.Response;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@RestController
@Slf4j
public class CommonController {

    @Resource
    private UidGenerator uidGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Userservice userservice;

    @Autowired
    private KafkaProducer kafkaProducer;


    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    private CacheService cacheService;

    @PostMapping("/uuid")
    public String generateUUid() {
        var uuid = uidGenerator.getUID();
        return String.valueOf(uuid);
    }

    @PostMapping("/userAdd")
    public String userAdd() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName(RandomUtil.randomString(10));
            user.setAge(RandomUtil.randomInt(20, 50));
            user.setCreateTime(LocalDateTime.now().minusMinutes(RandomUtil.randomInt(10,20)));
            user.setUpdateTime(LocalDateTime.now().minusMinutes(RandomUtil.randomInt(10,20)).toString());
            users.add(user);
        }
        userRepository.saveAll(users);
        return "success";
    }

    @PostMapping("/userTest")
    @Transactional
    public String userTest() {
        var sql = "select t from User t where t.id = 8";
        var query = entityManager.createQuery(sql,User.class);
        var user = query.getSingleResult();
        user.setName("Dave" + RandomUtil.randomInt());
//        user.setId(null);
        var query2  = entityManager.createQuery(sql,User.class);
        var user2 = query2.getSingleResult();
        user2.setName("Dave" + RandomUtil.randomInt());
//        entityManager.refresh(user2);

        entityManager.persist(user2);
        entityManager.flush();
        return "success";
    }


    @PostMapping("/userSave")
    public String userSave() {
        User user = new User();
        user.setName(RandomUtil.randomString(10));
        userservice.save(user);
        return "success";
    }

    @PostMapping("/userDel/{id}")
    public String userDel(@PathVariable Long id) {
        userRepository.deleteById(id);
//        cacheService.batchDeleteCache("userCache",Arrays.asList(String.valueOf(id)));
        return "success";
    }

    @PostMapping("/userUpdate/{id}")
    public String userUpdate(@PathVariable Long id) {
        var user = userRepository.findById(id).get();
        user.setName(RandomUtil.randomString(10));
        userservice.update(user);
        return "success";
    }

    @PostMapping("/userDetail/{id}")
    public User userDetail(@PathVariable Long id) {
        return userservice.findById(id);
    }

    @PostMapping("/userList")
    public Object userList() {
        return userRepository.findAll();
    }


    @PostMapping("/userRefine")
    public Object update() {
        var toList = userRepository.findAll().stream().filter(t -> t.getNo() == null).collect(Collectors.toList());
        log.info("no is null entities:{}", toList);
        userRepository.saveAll(toList);
        return "success";
    }

    @PostMapping("/userRefresh")
    public Object userRefresh() {
        userRepository.refreshUserNo();
        return "success";
    }

    @PostMapping("/delAccount/{tenantId}")
    public Object delAccount(@PathVariable String tenantId) {
        var topic = "CommonSearchDataDeletion";
//        var tenantId = "433746360799232"; // 1384207532298240
        var accountIds = Arrays.asList("ACT0101","ACT0102","ACT0103","ACT0104","ACT0105","ACT01","ACT0201","ACT0202","ACT0203","ACT0204","ACT0205","ACT02");
        accountIds.forEach(actId -> {
            var value = "{\"type\": \"account\",\"isDelete\":true,\"source\": {\"accountId\":" +
                    "\"" +
                    actId +
                    "\"},\"tenantId\":\"" +
                    tenantId +
                    "\",\"fieldInfoList\": [{\"columnName\": \"accountId\",\"fieldType\": \"VARCHAR\",\"fieldName\": \"accountId\",\"nullable\": false,\"primaryKey\": true,\"childColumn\": true,\"parentColumn\": false,\"longIdColumn\": true,\"buildPathColumn\": false,\"searchable\": true,\"length\": 32,\"unique\": false}]}";

            var key = generateUUID();
            var headers = new HashMap<String,String>();
            headers.put("X-Tenant-ID",tenantId);
            headers.put("X-Message-ID",key);
            kafkaProducer.sendMsg(topic,key,value,headers);
        });
        return "success";
    }

    @PostMapping("/delST/{tenantId}")
    public Object delST(@PathVariable String tenantId) {
        var topic = "CommonSearchDataDeletion";
//        var tenantId = "433746360799232"; //1384207532298240
        var stIds = Arrays.asList("ST01","ST0101","ST0102","ST0103","ST0104","ST0105","ST02","ST0201","ST0202","S01010","S01011","S01021","S01020","S01030","S01031","S01032","S01033");
        stIds.forEach(stId -> {
            var value = "{\"type\": \"salesTerritory\",\"isDelete\":true,\"source\": {\"territoryId\":" +
                    "\"" +
                    stId +
                    "\"},\"tenantId\":\"" +
                    tenantId +
                    "\",\"fieldInfoList\": [{\"columnName\": \"territoryId\",\"fieldType\": \"VARCHAR\",\"fieldName\": \"territoryId\",\"nullable\": false,\"primaryKey\": true,\"childColumn\": true,\"parentColumn\": false,\"longIdColumn\": true,\"buildPathColumn\": false,\"searchable\": true,\"length\": 32,\"unique\": false}]}";

            var key = generateUUID();
            var headers = new HashMap<String,String>();
            headers.put("X-Tenant-ID",tenantId);
            headers.put("X-Message-ID",key);
            kafkaProducer.sendMsg(topic,key,value,headers);
        });
        return "success";
    }
    public String generateUUID() {
        return StringUtils.upperCase(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
    }


    @GetMapping("/visit/{apId}")
    public Response<Boolean> visit(@PathVariable String apId) {
//        log.info("receive param:{}", JSON.toJSONString(request));
        log.info("receive param:{}", apId);
        return  Response.<Boolean>builder().code(apId).data(true).build();
    }


    @PostMapping("/redisGet/{key}")
    public Object redisGet(@PathVariable String key) {
        var value = "";
        if(redisTemplate.hasKey(key)){
           value  = (String) redisTemplate.opsForValue().get(key);
        }
        return value;
    }

    @PostMapping("/redisSet/{key}")
    public Object redisSet(@PathVariable String key) {
        redisTemplate.opsForValue().set(key,RandomUtil.randomString(10));
        return "success";
    }
}
