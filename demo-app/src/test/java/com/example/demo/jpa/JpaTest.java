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

import javax.persistence.EntityManager;
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


    private  final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    @Test
    public void testBatchInsert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setName(RandomUtil.randomString(10));
            user.setAge(RandomUtil.randomInt(20, 50));
            user.setCreateTime(LocalDateTime.now().minusMinutes(RandomUtil.randomInt(10,20)));
            users.add(user);
        }
        userRepository.saveAll(users);
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
        var rst10 = entityManager.createQuery("select t from User t where t.age > ?1 and t.createTime < ?2")
                .setParameter(1, 30)
                .setParameter(2, ts)
                .getResultList();
        var rst11 = entityManager.createQuery("select t from User t where t.age > 30 and t.createTime < '" + dtf.format(ts) + "'")
//                .setParameter(1, 30)
//                .setParameter(1, ts)
                .getResultList();
        log.info("cnt:{}",JSON.toJSONString(rst11));

        var rst2 =  entityManager.createNativeQuery("select t.* from tl_user t where t.age > ?1")
                .setParameter(1, 30)
                .getResultList();
        log.info("cnt:{}",JSON.toJSONString(rst2));
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

}
