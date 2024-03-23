package com.example.demo.jpa;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.example.demo.bo.User;
import com.example.demo.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author i565244
 */
@SpringBootTest
@Slf4j
public class JpaTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private  final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");


    @Test
    public void test_dtf_localdate() {
        var str1 = "2022-03-10 00:00:00";
        var str2 = "2022-03-10 00:00:00.000000";
        var ldt1 = LocalDateTime.parse(str1,DF);
        var ldt2 = LocalDateTime.parse(str2,dtf);

        log.info("ldt1:{}, ldt2:{}",ldt1,ldt2);
        var ldt3 = LocalDateTime.parse(str1);
        log.info("parse1:{},parse2:{}",LocalDateTime.parse(str1),LocalDateTime.parse(str2));
    }

    private User mockUser(){
        User user = new User();
        user.setName(RandomUtil.randomString(10));
        user.setAge(RandomUtil.randomInt(20, 50));
        user.setCreateTime(LocalDateTime.now().minusMinutes(RandomUtil.randomInt(10,20)));
        user.setUpdateTime(LocalDateTime.now().minusMinutes(RandomUtil.randomInt(10,20)).toString());
        return user;
    }

    @Test
    public void test_no_transaction() {
        var user1 = mockUser();
        userRepository.save(user1);
        log.info("save user1,id:{}",user1.getId());
        var user2 = mockUser();
        userRepository.save(user2);
        log.info("save user1,id:{}",user2.getId());
    }

    @Test
    @Transactional
    public void test_in_transaction() {
        var user1 = mockUser();
        userRepository.save(user1);
        log.info("save user1,id:{}",user1.getId());
        if(true){
            throw new RuntimeException("transaction test exception");
        }
        var user2 = mockUser();
        userRepository.save(user2);
        log.info("save user1,id:{}",user2.getId());
    }

    @Test
    public void testBatchInsert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(mockUser());
        }
        userRepository.saveAll(users);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void testBatchInsert_nativesql() {
        var batchInsertSql = " insert into tl_user (address, age, create_time, name, update_time) values ('address1', 26, '2022-05-08 13:20:20', 'James', '2022-05-08 13:20:20');\n"
                + "insert into tl_user (address, age, create_time, name, update_time) values ('address2', 26, '2022-05-08 13:20:20', 'James', '2022-05-08 13:20:20');";
        var batchCount = entityManager.createNativeQuery(batchInsertSql).executeUpdate();
        log.info("batch insert rst:{}",batchCount);
    }

    @Test
    public void testDynamicSql() {

        var cnt = entityManager.createQuery("select count(1) from User where age > ?1")
                .setParameter(1, 30)
                .getSingleResult();
        System.out.println("cnt:" + cnt);
    }

    @Test
    public void testDynamicBoSql() {
        var ts = LocalDateTime.now().minusMinutes(15);


        var rst2 =  entityManager.createNativeQuery("select t.* from tl_user t where t.age > ? and t.name = ?")
                .setParameter(1, 20)
                .setParameter(2,"James")
//                .setFirstResult(2)
//                .setMaxResults(5)
                .getResultList();
        log.info("cnt:{}",JSON.toJSONString(rst2));

        entityManager.setProperty("tenant_id",1l);
        var rst10 = entityManager.createQuery("select t from User t where t.age > ? and t.createTime < ? order by t.updateTime desc")
                .setParameter(1, 20)
                .setParameter(2, ts)
                .getResultList();

        var rst10_null = entityManager.createQuery("select t from User t where t.age > ?1 and t.createTime < ?2 order by t.updateTime desc")
                .setParameter(1, 30)
                .setParameter(2,null)
                .getResultList();
        var rst11 = entityManager.createQuery("select t from User t where t.age > 30 and t.createTime < '" + dtf.format(ts) + "'")
//                .setParameter(1, 30)
//                .setParameter(1, ts)
                .getResultList();
        log.info("cnt:{}",JSON.toJSONString(rst11));

    }

    @Test
//    @Transactional
    public void testJPA_cache() {

        var sql = "select t from User t where t.id = 8";
        var query = entityManager.createQuery(sql,User.class);
        var user =  query.getSingleResult();
        log.info("db user info:{}",JSON.toJSONString(user));
        user.setName("Dave100");
        entityManager.persist(user);
        entityManager.flush();
        log.info("user info query by JPA:{}",JSON.toJSONString(entityManager.createQuery(sql,User.class).getResultList()));
    }

    @Test
    public void testPageQuery() {
        Pageable pageable = PageRequest.of(0,10);
        System.out.println("***pageable******：" + JSON.toJSONString(pageable));

        var pageRst = userRepository.findAll(pageable);
        System.out.println("***result******：" + JSON.toJSONString(pageRst));
        while(pageRst.hasNext()){
            pageable = pageRst.nextPageable();
            System.out.println("***pageable******：" + JSON.toJSONString(pageable));
            pageRst = userRepository.findAll(pageable);
            System.out.println("***result******：" + JSON.toJSONString(pageRst));
        }
    }


    @Test
    public void test_localDateTime() {

        var rst =  (Object[])entityManager.createNativeQuery("select min(t.create_Time) as createTime,min(t.update_Time) as updateTime from tl_user t ")
                .getSingleResult();
        log.info("rst:{}",rst);
        var ctTime = (Timestamp)rst[0];

    }

    @Test
    public void test_deleteByAge() {
        var age = 45;
        var cnt = userRepository.deleteByAge(age);
        log.info("delete records which age smaller than {}, records cnt:{}",age,cnt);
    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void test_hana() {
        int sum = 50;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < sum; i++) {
            long start = System.currentTimeMillis();
            var querySql ="SELECT * FROM DISAG_BASE_ITEM_TEST WHERE CUSTOMER_UUID = '82b31e84abb6da21d102eabee3df6b34' LIMIT 10";
            var count = entityManager.createNativeQuery(querySql).getResultList();
            log.info("finish query data from partition ,cost:{}ms",System.currentTimeMillis() - start);
        }
        log.info("finish all queries from partition ,cost:{}ms",System.currentTimeMillis() - begin);


        long begin2 = System.currentTimeMillis();
        for (int i = 0; i < sum; i++) {
            long start = System.currentTimeMillis();
            var querySql ="SELECT * FROM DISAG_BASE_ITEM WHERE CUSTOMER_UUID = '82b31e84abb6da21d102eabee3df6b34' LIMIT 10";
            var count = entityManager.createNativeQuery(querySql).getResultList();
            log.info("finish query data from table ,cost:{}ms",System.currentTimeMillis() - start);
        }
        log.info("finish all queries from table ,cost:{}ms",System.currentTimeMillis() - begin2);

    }

}
