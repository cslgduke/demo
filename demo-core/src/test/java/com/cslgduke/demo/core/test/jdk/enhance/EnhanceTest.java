package com.cslgduke.demo.core.test.jdk.enhance;

import com.alibaba.fastjson.JSON;
import javassist.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author i565244
 */
@Slf4j
public class EnhanceTest {

    @Test
    public void test_javassist_modifymethod() throws Exception {
        ClassPool classPool = ClassPool.getDefault();

        var ctClass = classPool.getCtClass(OriginBo.class.getName());

        CtMethod getNameMethod = ctClass.getDeclaredMethod("getName");
        getNameMethod.setBody("return \"dynamic modified your code ,old result: \" + name;");
        ctClass.setName(OriginBo.class.getCanonicalName()+"Enhance");
//        if(ctClass.isFrozen()){
//            ctClass.defrost();
//        }

        //测试使用被修改后的类
        Class enhanceClass = ctClass.toClass();
        var enhanceBo =  enhanceClass.newInstance();
        enhanceClass.getMethod("setName",String.class).invoke(enhanceBo,"Lebron James");
        var name =  enhanceClass.getMethod("getName").invoke(enhanceBo);
        log.info("Enhance getName:{}",name);

        var originB = new OriginBo();
        originB.setName("Lebron James");
        log.info("Origin getName:{}",originB.getName());
    }

    @Test
    public void test_bytecode_enhance() throws Exception {
        var originClass = OriginBo.class;
        var bcEnhancer = new ByteCodeEnhancer();

        var enhanceClass = bcEnhancer.initClass(originClass,originClass.getName() + "Enhancer")
                .deFroze()
                .addField("age",Integer.class.getName())
                .addGetterSetter("age","setAge","getAge").build();

        var modelMapper = new ModelMapper();
        var enhanceInstance = modelMapper.map(OriginBo.builder().name("ZhangSan").build(),enhanceClass);
//        enhanceClass.getMethod("setAge",List.class).invoke(enhanceInstance, Arrays.asList(18));
//        var age = enhanceClass.getMethod("getAge").invoke(enhanceInstance);
//        log.info("Dynamic field value age:{},enhance info:{}",age,JSON.toJSONString(enhanceInstance));

        ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(enhanceInstance.getClass(),"setName",String.class)
                ,enhanceInstance
                ,"LiSi");

        ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(enhanceInstance.getClass(),"setAge",Integer.class)
                ,enhanceInstance
                ,18);

        log.info("enhance className:{},detail info:{}",enhanceClass.getCanonicalName(), JSON.toJSONString(enhanceInstance));
    }

    @Test
    public void test_javassist_addMethod() throws Exception {
        Class proxyClass = addMethodToClass(OriginBo.class, "public String sayHello(int age) {\n" +
                "        System.out.println(\"Hello, I'm \" + name + \", age \" + age);\n" +
                "        return \"Hello\";\n" +
                "    }");

        Object personProxy = proxyClass.newInstance();
        OriginBo originBo = new OriginBo();
        originBo.setName("Tony");

        BeanUtils.copyProperties(originBo, personProxy);
        Object result = proxyClass.getMethod("sayHello", int.class).invoke(personProxy, 18);
        log.info("invoke dynamic method result:{}",result);


    }

    public  Class<?> addMethodToClass(Class clazz, String methodBody) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(clazz.getName());
        CtMethod ctMethod = CtNewMethod.make(methodBody, ctClass);
        ctClass.addMethod(ctMethod);
        ctClass.setName(ctClass.getName() + "Proxy");
        return ctClass.toClass();
    }


    @Test
    public void test_javassist_addField() throws Exception {
        Class clazz = OriginBo.class;
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(clazz.getName());

        CtClass fieldClazz = pool.get(Integer.class.getName());
        CtField ctField = new CtField(fieldClazz, "age", ctClass);

        ctClass.addField(ctField);
        ctClass.addMethod(CtNewMethod.setter("setAge", ctField));
        ctClass.addMethod(CtNewMethod.getter("getAge", ctField));

        ctClass.setName(ctClass.getName() + "Enhance");
        var enhanceClass = ctClass.toClass();
        var enhanceInstance = enhanceClass.newInstance();

        enhanceClass.getMethod("setAge",Integer.class).invoke(enhanceInstance,18);
        log.info("enhance className:{},detail info:{}",enhanceClass.getCanonicalName(), JSON.toJSONString(enhanceInstance));

    }



}
