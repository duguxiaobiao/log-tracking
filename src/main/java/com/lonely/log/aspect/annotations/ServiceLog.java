package com.lonely.log.aspect.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ztkj-hzb
 * @Date 2019/10/14 13:35
 * @Description 开始日志注解,该注解只记录调用时的基础信息(包括入参)，不记录整个服务的消耗时间
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceLog {

    String value() default "";

}
