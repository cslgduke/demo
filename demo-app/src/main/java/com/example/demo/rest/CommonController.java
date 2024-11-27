package com.example.demo.rest;

import cn.amorou.uid.UidGenerator;
import cn.hutool.core.thread.ThreadFactoryBuilder;
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
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.vo.Response;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
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
    private EntityManager entityManager;


    @PostMapping("/uuid")
    public String generateUUid() {
        var uuid = uidGenerator.getUID();
        return String.valueOf(uuid);
    }

    int concurrency = 1;

    @PostMapping("/userAdd")
    public String userAdd() {
//        var executor = new ThreadPoolExecutor(concurrency, concurrency, 10, TimeUnit.MICROSECONDS,
//                new ArrayBlockingQueue<>(1000),
//                new ThreadFactoryBuilder().setNamePrefix("hana-query-test").build(),
//                new ThreadPoolExecutor.AbortPolicy());
//        int batchCount = 1000;
//        for (int i = 0; i < 10000; i++) {
//            executor.execute(()->{
//                var start = System.currentTimeMillis();
//                List<User> users = new ArrayList<>();
//                for (int j = 0; j < batchCount; j++) {
//                    User user = new User();
//                    user.setName(RandomUtil.randomString(10));
//                    user.setAge(RandomUtil.randomInt(20, 50));
//                    user.setCreateTime(LocalDateTime.now().minusDays(RandomUtil.randomInt(365)));
//                    user.setUpdateTime(user.getCreateTime());
//                    users.add(user);
//                }
//                userRepository.saveAll(users);
//                log.info("insert {} records,cost:{}ms",batchCount,System.currentTimeMillis() - start);
//            });
//        }
        int batchCount = 100;
        List<User> users = new ArrayList<>();
        for (int j = 0; j < batchCount; j++) {
            User user = new User();
            user.setName(RandomUtil.randomString(10));
//            user.setAge(RandomUtil.randomInt(20, 50));
            user.setAge(60);
            user.setCreateTime(LocalDateTime.now().minusDays(RandomUtil.randomInt(365)));
            user.setUpdateTime(user.getCreateTime());
            users.add(user);
        }
        long start = System.currentTimeMillis();
        userRepository.saveAll(users);
        log.info("insert {} records,cost:{}ms",batchCount,System.currentTimeMillis() - start);

        return "success";
    }

    @PostMapping("/userTest")
    @Transactional
    public String userTest() {
        var sql = "select t from User t where t.id = 8";
        var query = entityManager.createQuery(sql, User.class);
        var user = query.getSingleResult();
        user.setName("Dave - 1");
//        user.setId(null);
        var query2 = entityManager.createQuery(sql, User.class);
        var user2 = query2.getSingleResult();
        user2.setName("Dave -2 ");
//        entityManager.refresh(user2);//No EntityManager with actual transaction available for current thread - cannot reliably process 'refresh' call


        //if want to read the db data must open a new seesion
        var newEm = this.entityManager.getEntityManagerFactory().createEntityManager();
        var newQuery = newEm.createQuery(sql, User.class);
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

    @GetMapping("/visit/{apId}")
    public Response<Boolean> visit(@PathVariable String apId) {
//        log.info("receive param:{}", JSON.toJSONString(request));
        log.info("receive param:{}", apId);
        return Response.<Boolean>builder().code(apId).data(true).build();
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

    private void testFgc() {
        for (int i = 0; i < corePoolSize; i++) {

            executor.execute(() -> {
                while (true){
                var persons = new ArrayList<User>();
                persons.add(getInstance());
                log.info("The size of persons:{}", persons.size());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            });
        }
    }

    public User getInstance() {
        return User.builder()
                .id(Long.parseLong(RandomUtil.randomNumbers(18)))
                .name(RandomUtil.randomString(10000))
                .age(RandomUtil.randomInt(0, 100))
                .address(RandomUtil.randomString("ShangHai ", 10000)).build();
    }

    @GetMapping("/dpp/Users/{uuid}")
    public Result getUserByUuid(@PathVariable String uuid) {

        return Result.Ok(HttpStatus.OK.value(), "success", null);
    }
}
