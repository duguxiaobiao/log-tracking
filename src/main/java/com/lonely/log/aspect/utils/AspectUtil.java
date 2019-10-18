package com.lonely.log.aspect.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 17:10
 * @Description
 */
public class AspectUtil {


    public static String getParams(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

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
            return params.toString();
        }

        return StringUtils.EMPTY;
    }
}
