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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 15:25
 * @Description
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * controller所有请求都拦截
     */
    @Pointcut("execution(public * com.lonely.controller..*.*(..))")
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

        //针对http请求的请求参数
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        logBean.setRequestPath(request.getRequestURI());
        logBean.setRequestId(request.getHeader("requestId"));
        logBean.setUserName(request.getHeader("userId"));
        logBean.setProductId(request.getHeader("productId"));
        logBean.setClientIp(request.getRemoteHost());

        if (null != logBean.getClientIp() && "127.0.0.1".equals(logBean.getClientIp())) {
            logBean.setClientIp(request.getHeader("X-Real-IP"));
        }

        //设置requestid
        logBean.setRequestId(UUID.randomUUID().toString());

        return logBean;
    }


    /**
     * 环绕切面
     *
     * @param point
     * @return
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) {
        Object result = null;

        //start级别
        LogBean logBean = before(point);

        //打印开始日志
        log.info(logBean.pringStartLog());

        //构建跟踪链路对象实体，添加到当前线程中保存
        this.saveToCurrentThread(logBean);

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

        //清除当前线程的链路信息
        LinkTrackingThreadLocal.clear();

        return result;
    }


    /**
     * 将日志信息保存到当前线程中
     *
     * @param logBean
     */
    private void saveToCurrentThread(LogBean logBean) {
        LinkTrackingBean linkTrackingBean = new LinkTrackingBean();
        linkTrackingBean.setRequestId(logBean.getRequestId());
        linkTrackingBean.setUserId(logBean.getUserName());
        linkTrackingBean.setProductId(logBean.getProductId());
        linkTrackingBean.setClientIp(logBean.getClientIp());
        LinkTrackingThreadLocal.set(linkTrackingBean);
    }



    /*private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            String params = "";
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i];
            }
            sysLog.setParams(params);
        }
        // 获取request
//		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
//		sysLog.setIp(IPUtils.getIpAddr(request));
        sysLog.setIp("127.0.0.1");
        // 模拟一个用户名
        sysLog.setUserName("mrbird");
        sysLog.setTime((int) time);
        sysLog.setCreateTime(new Date());
        // 保存系统日志
        iSysLogService.save(sysLog);
    }*/

}
