package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.util.RandomUtil;
import com.example.demo.bo.Result;
import com.example.demo.bo.User;
import com.example.demo.core.CustomThreadFactory;
import com.example.demo.msg.KafkaProducer;
import com.example.demo.repo.UserRepository;
import com.example.demo.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vo.Response;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author i565244
 */
@RequestMapping(value = {"/common"})
@Validated
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
        for (int i = 0; i < 100; i++) {
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
        user.setName("Dave - 1");
//        user.setId(null);
        var query2  = entityManager.createQuery(sql,User.class);
        var user2 = query2.getSingleResult();
        user2.setName("Dave -2 ");
//        entityManager.refresh(user2);//No EntityManager with actual transaction available for current thread - cannot reliably process 'refresh' call


        //if want to read the db data must open a new seesion
        var newEm = this.entityManager.getEntityManagerFactory().createEntityManager();
        var newQuery = newEm.createQuery(sql,User.class);
        var userFromDb = newQuery.getSingleResult();

        //entityManager.persist(user2);
        // entityManager.flush();
        /**
         * if remove @Transactional when invoke persist or flush will get follow exception
            No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
            No EntityManager with actual transaction available for current thread - cannot reliably process 'flush' call
         */
        userRepository.save(user2);
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


    @PostMapping("/fullGc")
    public Object fullGc() {
        testFgc();
        return "success";
    }


    int corePoolSize = 100;
    ThreadPoolExecutor executor = new ThreadPoolExecutor(10,
            corePoolSize,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new CustomThreadFactory(),
            new ThreadPoolExecutor.AbortPolicy());

    private void  testFgc(){
        for (int i = 0; i < corePoolSize; i++) {
            var persons = new ArrayList<User>();
            executor.execute(() ->{
//                while (true){
                    persons.add(getInstance());
                    log.info("The size of persons:{}",persons.size());
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                }
            });
        }
    }

    public User getInstance(){
        return User.builder()
                .id(Long.parseLong(RandomUtil.randomNumbers(18)))
                .name(RandomUtil.randomString(100))
                .age(RandomUtil.randomInt(0,100))
                .address(RandomUtil.randomString("ShangHai ",100)).build();
    }

    @GetMapping("/dpp/Users/{uuid}")
    public Result getUserByUuid(@PathVariable String uuid) {

        return Result.Ok(HttpStatus.OK.value(), "success", null);
    }
}
