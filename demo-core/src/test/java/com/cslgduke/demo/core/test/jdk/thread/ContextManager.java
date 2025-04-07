package com.cslgduke.demo.core.test.jdk.thread;

/**
 * @author i565244
 */
public class ContextManager {

    private static final ThreadLocal<String> tenantIdContextHolder = new InheritableThreadLocal<>();

    private static final ThreadLocal<String> userIdContextHolder = new ThreadLocal<>();


    public static String getUserId(){
        return userIdContextHolder.get();
    }

    public static void setUserId(String userId){
        userIdContextHolder.set(userId);
    }

    public static String getTenantId(){
        return tenantIdContextHolder.get();
    }

    public static void setTenantId(String tenantId){
        tenantIdContextHolder.set(tenantId);
    }

    public static void reset(){
        tenantIdContextHolder.remove();
        userIdContextHolder.remove();
    }
}
