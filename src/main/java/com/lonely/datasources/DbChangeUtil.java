package com.lonely.datasources;

import com.lonely.log.aspect.annotations.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.UUID;

/**
 * @author ztkj-hzb
 * @Date 2019/5/17 17:28
 * @Description 数据源切换工具类
 */
@Slf4j
@Component
public class DbChangeUtil {

   /* private DbChangeUtil() {
    }


    private static DbChangeUtil dbChangeUtil = new DbChangeUtil();

    public static DbChangeUtil getInstance(){
        return dbChangeUtil;
    }*/


    /**
     * 根据数据源id 切换数据源
     *
     * @param dataSourceId
     */
    public String changeDataSourceById(String dataSourceId) {
        log.info("DbChangeUtil.changeDataSourceById({})", dataSourceId);

        /*StackTraceElement[] stackTrace = new Exception().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            String classname = stackTrace[i].getClassName();
            String methodname = stackTrace[i].getMethodName() ;
            log.info(MessageFormat.format("{0}.{1}",classname,methodname));
        }*/

        return UUID.randomUUID().toString();
    }


}
