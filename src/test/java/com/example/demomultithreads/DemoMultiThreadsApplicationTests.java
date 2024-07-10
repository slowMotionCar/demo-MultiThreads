package com.example.demomultithreads;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.stream.Stream;

@SpringBootTest
class DemoMultiThreadsApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
        //https://juejin.cn/post/6881847706663747597
    void testMultiThreadFutureTaskStatus() throws InterruptedException {

        //线程安全的返回参数
        AtomicReference<FutureTask<Integer>> a = new AtomicReference<>();
        Runnable task = () -> {

            //循环添加
            while (true) {
                FutureTask<Integer> f = new FutureTask<>(() -> 1);

                //在这个返回参时添加这个线程的futureTask
                a.set(f);
                //运行futureTask
                f.run();
            }
        };

        //观察者
        Supplier<Runnable> observe = () -> () -> {

            //获取到外部的返回参
            //没有诊断
            while (a.get() == null) ;

            //计数器
            int c = 0;
            int ic = 0;

            //循环等待
            while (true) {
                c++;
                FutureTask<Integer> f = a.get();

                //如果没有返回处理完毕则无限等待
                while (!f.isDone()) {
                }
                try {
                    /*
                    Set the interrupt flag of this thread.
                    The future reports it is done but in some cases a call to
                    "get" will result in an underlying call to "awaitDone" if
                    the state is observed to be completing.
                    "awaitDone" checks if the thread is interrupted and if so
                    throws an InterruptedException.
                     */
                    Thread.currentThread().interrupt();
                    f.get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    ic++;
                    System.out.println("InterruptedException observed when isDone() == true " + c + " " + ic + " " + Thread.currentThread());
                }
            }
        };

        CompletableFuture.runAsync(task);
        //根据 观察者 生成流
        Stream.generate(observe)
                .limit(Runtime.getRuntime().availableProcessors() - 1)
                //遍历所有的运行线程
                .forEach(CompletableFuture::runAsync);

        Thread.sleep(1000);
        System.exit(0);

    }


}
