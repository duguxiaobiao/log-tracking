package com.lonely.log.aspect;

import com.lonely.log.aspect.bean.DbDataSourceChangeLogBean;
import com.lonely.log.aspect.bean.LinkTrackingBean;
import com.lonely.log.aspect.threadlocals.LinkTrackingThreadLocal;
import com.lonely.log.aspect.utils.AspectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 数据库连接切换日志拦截器
 */
@Aspect
@Component
@Slf4j
public class DbChangeLogAspect {

    /**
     * 拦截数据源操作类
     */
    //@Pointcut("execution(public * com.lonely.datasources.DbChangeUtil.changeDataSourceById(..))")
    @Pointcut("execution(public * com.lonely.datasources..*.*(..))")
    public void pointcut() {
    }


    /**
     * 开始时处理
     *
     * @param joinPoint
     * @return
     */
    //@Before("pointcut()")
    public DbDataSourceChangeLogBean before(JoinPoint joinPoint) {

        log.info(Thread.currentThread().getName());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取待调用的方法
        Method method = signature.getMethod();

        //组装
        DbDataSourceChangeLogBean dbDataSourceChangeLogBean = new DbDataSourceChangeLogBean();


        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        //设置触发类名和方法名
        dbDataSourceChangeLogBean.setClassName(className);
        dbDataSourceChangeLogBean.setMethodName(methodName);

        //设置入参
        dbDataSourceChangeLogBean.setDbId(AspectUtil.getParams(joinPoint));


        //设置基础信息
        LinkTrackingBean linkTrackingBean = LinkTrackingThreadLocal.get();
        if (linkTrackingBean != null) {
            dbDataSourceChangeLogBean.setRequestId(linkTrackingBean.getRequestId());
            dbDataSourceChangeLogBean.setUserId(linkTrackingBean.getUserId());
            dbDataSourceChangeLogBean.setProductId(linkTrackingBean.getProductId());
        }

        return dbDataSourceChangeLogBean;
    }


    /**
     * 环绕切面
     *
     * @param point
     * @return
     */
    @Around("pointcut() ")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;

        //start级别
        DbDataSourceChangeLogBean dbDataSourceChangeLogBean = before(point);

        //判断当前线程中是否存在链路跟踪对象,true:标识该拦截之前经过了controller层，这种情况，不需要执行完该方法清空ThreadLocal的值，反之，需要清除
        boolean isHttpAfter = LinkTrackingThreadLocal.get() != null;
        if (!isHttpAfter) {
            //非http连接接入的,
            dbDataSourceChangeLogBean.setRequestId(UUID.randomUUID().toString());
        }

        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            dbDataSourceChangeLogBean.setErrorMsg(ExceptionUtils.getStackTrace(e));
        }

        //设置最终返回的数据源的key
        dbDataSourceChangeLogBean.setChangeKey((String) result);

        //打印结束日志
        log.info(dbDataSourceChangeLogBean.dbChangeLogPrint());

        if (!isHttpAfter) {
            LinkTrackingThreadLocal.clear();
        }

        return result;
    }


}
