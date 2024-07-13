package com.example.demomultithreads.demos.web;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * @ClassName: AOPController
 * @Description: TODO
 * @Author: zzl
 * @Date: 2024/7/12 14:30
 * @Version: 1.0
 */
@Service
public class AOPService {

    public String AOPHellotest() throws InterruptedException {
        System.out.println("hello!");
//        int i = 100/0;
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Mammba Out!");
        return "西红柿";
    }


}
