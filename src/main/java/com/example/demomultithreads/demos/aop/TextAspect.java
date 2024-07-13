package com.example.demomultithreads.demos.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TimeAspect
 * @Description: TODO
 * @Author: zzl
 * @Date: 2024/7/12 14:14
 * @Version: 1.0
 */

@Slf4j
@Component
@Aspect
public class TextAspect {

    @Pointcut("execution(* com.example.demomultithreads.demos.web.AOPService.*(..))")
    public void pointcut(){}


    //插入到切点之前 （通知⽅法会在目标⽅法调⽤之前执⾏。）
    @Before("pointcut()")
    public void before(){
      log.info("Bro, Here we go again!");
    }

    //插入到切点之后 （通知⽅法会在⽬标⽅法返回或者抛出异常后调⽤）
    @After("pointcut()")
    public void after(){
        log.error("Bro, Now it is over!");
    }

    //通知⽅法会在⽬标⽅法返回后调⽤。
    @AfterReturning("pointcut()")
    public void afterReturning(){
        log.info("now you are returning");
    }

    //通知⽅法会在⽬标⽅法抛出异常后调⽤。
    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        log.info("OOPS!!!!!!!!!!!");
    }

    //环绕，计算时间
    @Around("pointcut()")
    public String around(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("Before Around");
        long l1 = System.currentTimeMillis();
        String proceed = (String)joinPoint.proceed();
        long l2 = System.currentTimeMillis();
        log.info("After Around");
        log.info("耗时为："+(l2-l1)+"s");
        return proceed;
    }


}
