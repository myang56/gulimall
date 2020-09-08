package com.ym.gulimall.search.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        System.out.println("main... start...");
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程 " + Thread.currentThread().getId());
            int i = 10 / 2;
            System.out.println("运行结果 " + i);
        }, executor);
        System.out.println("main... end...");
    }
}
