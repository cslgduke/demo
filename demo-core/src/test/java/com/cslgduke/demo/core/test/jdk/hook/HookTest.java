package com.cslgduke.demo.core.test.jdk.hook;

/**
 * @author i565244
 */
public class HookTest {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            //只要不是机器断电，强制kill -9 强制杀进程，都会触发
            System.out.println("Hook funciton execute");
        }));
        while (true);
    }
}
