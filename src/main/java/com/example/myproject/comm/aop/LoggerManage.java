package com.example.myproject.comm.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: 日志注解
 * @author Xiaoshi Wan
 * @date 2019年6月18日   
 * @version 1.0
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {
     public String description();
}
