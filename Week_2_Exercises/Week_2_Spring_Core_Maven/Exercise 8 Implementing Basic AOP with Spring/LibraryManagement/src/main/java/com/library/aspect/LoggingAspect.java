
package com.library.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LoggingAspect {

    // Pointcut for all methods in the service package
    @Pointcut("execution(* com.library.management.service.*.*(..))")
    public void allServiceMethods() {}

    // Before advice
    @Before("allServiceMethods()")
    public void logBefore() {
        System.out.println("LoggingAspect: Before method execution");
    }

    // After advice
    @After("allServiceMethods()")
    public void logAfter() {
        System.out.println("LoggingAspect: After method execution");
    }
}
