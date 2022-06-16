package com.cslgduke.demo.core.test.jdk.enhance;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *  Base class for dynamically create run-time class objects.
 *  @author Leo Shan(i540800)
 */
@Slf4j
public class ByteCodeEnhancer {
    protected ClassPool pool;

    protected ConstPool constpool;

    protected Class<?> parentClass;

    protected CtClass cc;

    protected Class<?> generatedClass;

    protected ClassFile classFile;

    protected Map<String, CtField> ctFieldMap = new HashMap<>();

    public ByteCodeEnhancer(){
        this.pool = ClassPool.getDefault();
    }

    @SneakyThrows
    public ByteCodeEnhancer initClass(String newClassName){
        this.initClass(null,newClassName);
        return this;
    }

    @SneakyThrows
    public ByteCodeEnhancer initClass(Class<?> parentClass, String newClassName){
        this.parentClass = parentClass;
        this.cc = Objects.isNull(parentClass)?pool.makeClass(newClassName):pool.makeClass(newClassName, pool.get(parentClass.getCanonicalName()));

        classFile = cc.getClassFile();
        constpool = classFile.getConstPool();

        return this;
    }

    public ByteCodeEnhancer deFroze(){
        if (cc.isFrozen()) {
            cc.defrost();
        }
        return this;
    }

    @SneakyThrows
    public ByteCodeEnhancer addField(String fieldName, String fieldType){
        CtClass fieldClazz = pool.get(fieldType);
        CtField ctField = new CtField(fieldClazz, fieldName, cc);
        cc.addField(ctField);//add
        ctFieldMap.put(fieldName, ctField);

        return this;
    }

    @SneakyThrows
    public ByteCodeEnhancer addFieldAnnotation(String fieldName,String annotation){
        AnnotationsAttribute fieldAnnotationsAttribute = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
        Annotation fieldAnnotation = new Annotation(annotation, constpool);
        fieldAnnotationsAttribute.setAnnotation(fieldAnnotation);
        CtField ctField = ctFieldMap.get(fieldName);
        ctField.getFieldInfo().addAttribute(fieldAnnotationsAttribute);
        cc.addField(ctField);
        return this;
    }

    @SneakyThrows
    public ByteCodeEnhancer addSetter(String fieldName, String setterMethod){
        CtField ctField = ctFieldMap.get(fieldName);
        cc.addMethod(CtNewMethod.setter(setterMethod, ctField));
        return this;
    }

    @SneakyThrows
    public ByteCodeEnhancer addGetter(String fieldName, String getterMethod){
        CtField ctField = ctFieldMap.get(fieldName);
        cc.addMethod(CtNewMethod.getter(getterMethod, ctField));
        return this;
    }

    public ByteCodeEnhancer addGetterSetter(String fieldName,String setterMethod,String getterMethod){
        this.addSetter(fieldName,setterMethod);
        this.addGetter(fieldName,getterMethod);
        return this;
    }

    public ByteCodeEnhancer addClassAnnotation(Object annotation,Object... params){
        throw new RuntimeException("addClassAnnotation must be implemented by children class!");
    }

    /**
     * Finally, generate the new class.
     * @return
     */
    @SneakyThrows
    public Class<?> build(){
        this.generatedClass = cc.toClass();
        log.info("Success generate new class {}",generatedClass.getName());

        return generatedClass;
    }
}
