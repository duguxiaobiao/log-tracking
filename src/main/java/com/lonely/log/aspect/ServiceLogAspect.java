package com.lonely.log.aspect;

import com.alibaba.fastjson.JSON;
import com.lonely.log.aspect.annotations.ServiceLog;
import com.lonely.log.aspect.bean.LinkTrackingBean;
import com.lonely.log.aspect.bean.LogBean;
import com.lonely.log.aspect.threadlocals.LinkTrackingThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * 服务日志拦截器,一般用于后台service服务阶段
 */
@Aspect
@Component
@Slf4j
public class ServiceLogAspect {

    /**
     * 拦截指定注解
     */
    @Pointcut("@within(com.lonely.log.aspect.annotations.ServiceLog) || @annotation(com.lonely.log.aspect.annotations.ServiceLog)")
    public void pointcut() {
    }


    /**
     * 开始时处理
     *
     * @param joinPoint
     * @return
     */
    //@Before("pointcut()")
    public LogBean before(JoinPoint joinPoint) {

        log.info(Thread.currentThread().getName());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        //获取待调用的方法
        Method method = signature.getMethod();

        //组装logbean
        LogBean logBean = new LogBean();

        //设置服务调用时间
        logBean.setStartTimeMillis(System.currentTimeMillis());

        ServiceLog logAnnotation = method.getAnnotation(ServiceLog.class);
        if (logAnnotation != null) {
            // 注解上的描述
            logBean.setOperation(logAnnotation.value());
        }

        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();

        //设置触发类名和方法名
        logBean.setClasz(className);
        logBean.setMethod(methodName);

        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                params.append(MessageFormat.format(" {0}: {1}", paramNames[i], JSON.toJSONString(args[i])));
            }
            logBean.setParamStr(params.toString());
        }

        //设置基础信息
        LinkTrackingBean linkTrackingBean = LinkTrackingThreadLocal.get();
        if (linkTrackingBean != null) {
            logBean.setRequestId(linkTrackingBean.getRequestId());
            logBean.setUserName(linkTrackingBean.getUserId());
            logBean.setProductId(linkTrackingBean.getProductId());
            logBean.setClientIp(linkTrackingBean.getClientIp());
        }

        return logBean;
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
        LogBean logBean = before(point);

        //判断当前线程中是否存在链路跟踪对象,true:标识该拦截之前经过了controller层，这种情况，不需要执行完该方法清空ThreadLocal的值，反之，需要清除
        boolean isHttpAfter = LinkTrackingThreadLocal.get() != null;
        if (!isHttpAfter) {
            //非http连接接入的,
            logBean.setRequestId(UUID.randomUUID().toString());
        }

        //打印开始日志
        log.info(logBean.pringStartLog());

        try {
            // 执行方法
            result = point.proceed();
        } catch (Throwable e) {
            logBean.setErrorMsg(ExceptionUtils.getStackTrace(e));
        }

        logBean.setEndTimeMillis(System.currentTimeMillis());
        logBean.setReturned(result);

        //打印结束日志
        log.info(logBean.printEndLog());

        if (!isHttpAfter) {
            LinkTrackingThreadLocal.clear();
        }

        return result;
    }


}
