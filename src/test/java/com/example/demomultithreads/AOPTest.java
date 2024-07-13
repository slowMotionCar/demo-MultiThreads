package com.example.demomultithreads;

import com.example.demomultithreads.demos.web.AOPService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @ClassName: AOPTest
 * @Description: TODO
 * @Author: zzl
 * @Date: 2024/7/12 14:27
 * @Version: 1.0
 */

@SpringBootTest
public class AOPTest {

    @Resource
    AOPService aopService;


    @Test
    public void test() throws InterruptedException {
        String s = aopService.AOPHellotest();
        System.out.println(s);
    }
}
