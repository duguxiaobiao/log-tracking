/*
package com.lonely.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
	//@Autowired
	//ISysLogService iSysLogService;

	*/
/**
	 * 拦截指定注解
	 *//*

	@Pointcut("@annotation(com.lonely.log.aspect.annotations.Log)")
	public void pointcut() { }



	*/
/**
	 * 环绕切面
	 * @param point
	 * @return
	 *//*

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint point) {
		Object result = null;
		long beginTime = System.currentTimeMillis();
		try {
			// 执行方法
			result = point.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveLog(point, time);
		return result;
	}








	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
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
	}
}
*/
