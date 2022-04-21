package com.example.demo.aop;

import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author i565244
 */
public class AopTest {

    @Test
    public void test_methodInterceptor(){
        UserDaoImpl userDaoImpl = new UserDaoImpl();

        ProxyFactory proxyFactory = new ProxyFactory(); // 初始化一个代理工厂
        proxyFactory.setTarget(userDaoImpl);
        proxyFactory.addAdvice(new UserMethodInterceptor());

        UserDaoImpl proxyUserDaoImpl = (UserDaoImpl) proxyFactory.getProxy();

        proxyUserDaoImpl.deleteUser(2);

        proxyUserDaoImpl.addUser("LiHua");  //params enhance

        proxyUserDaoImpl.deleteUser(-1);  //exception handle
    }
}
