package com.example.demomultithreads;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: ForkJoinTest
 * @Description: TODO
 * @Author: zzl
 * @Date: 2024/7/10 10:45
 * @Version: 1.0
 */
public class ForkJoinTest {

    public static void main(String[] args) {

        int sum = 0;
        for (int i = 0; i < 100000; i++) {

            for (int i1 = 0; i1 < 10; i1++) {
                sum = sum + i1;
            }

        }
        System.out.println("输出求和为:" + sum);
    }

    @Test
    void main1() {
        class Fibonacci extends RecursiveTask<Integer> {
            final int n;

            Fibonacci(int n) {
                this.n = n;
            }

            @Override
            protected Integer compute() {
                if (n <= 1)
                    return n;
                Fibonacci f1 = new Fibonacci(n - 1);
                f1.fork();
                Fibonacci f2 = new Fibonacci(n - 2);
                return f2.compute() + f1.join();

            }
        }

        Fibonacci fibonacci = new Fibonacci(30);
        Integer compute = fibonacci.compute();
        System.out.println("fibonacii Num is ：" + compute);
    }

    class ForkJoinDemo extends RecursiveTask<Long> {

        private Long start;
        private Long end;
        //临界值
        private Long temp;

        public ForkJoinDemo(Long start, Long end, Long temp) {
            this.start = start;
            this.end = end;
            this.temp = temp;
        }

        @Override
        protected Long compute() {
            if ((end - start) < temp) {
                Long sum = 0l;
                Long a;
                for (a = start; a <= end; a++){
                    sum = a + sum;
                }
                return sum;
            } else {
                Long mid  =  (start + end)/2;
                ForkJoinDemo forkJoin1 = new ForkJoinDemo(start, mid, 10000L);
                forkJoin1.fork();
                ForkJoinDemo forkJoin2 = new ForkJoinDemo(mid+1, end, 10000L);
                forkJoin2.fork();
                Long join = forkJoin1.join();
                Long join1 = forkJoin2.join();
                return join+join1;
            }
        }
    }



    @Test
    void simpleAddTest() {

        Long sum = 0l;
        long l = System.currentTimeMillis();
        for (Long i = 0l; i <= 100_000_000; i++) {
            sum += i;
        }
        long l2 = System.currentTimeMillis();
        long l1 = l2 - l;
        System.out.println("简单计算最终结果为: "+sum+"时间是！"+l1);
    }


    @Test
    void ForkJoinTestSubmit() throws ExecutionException, InterruptedException {

        long l = System.currentTimeMillis();
        ForkJoinDemo forkJoinDemo = new ForkJoinDemo(0l, 100_000_000l, 1000l);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> submit = forkJoinPool.submit(forkJoinDemo);
        Long compute = submit.get();
        long l2 = System.currentTimeMillis();
        long l1 = l2 - l;
        System.out.println("最终结果为: "+compute+"时间是！"+l1);

        List<String> collect = Stream.of("one", "two", "three", "four", "five")
                .filter(e -> e.length() > 3)
                .peek(e -> System.out.println("Filtered value: " + e))
                .map(String::toUpperCase)
                .peek(e -> System.out.println("Mapped value: " + e))
                .peek(e -> System.out.println("hello" + e))
                .collect(Collectors.toList());

        System.out.println("collect"+collect);

    }
}
