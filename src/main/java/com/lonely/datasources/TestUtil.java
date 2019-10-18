package com.lonely.datasources;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author ztkj-hzb
 * @Date 2019/10/17 11:18
 * @Description
 */
@Component
@Slf4j
public class TestUtil {


    public String changeDataSourceById(String dataSourceId) {
        log.info("TestUtil.changeDataSourceById({})", dataSourceId);

        /*StackTraceElement[] stackTrace = new Exception().getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            String classname = stackTrace[i].getClassName();
            String methodname = stackTrace[i].getMethodName() ;
            log.info(MessageFormat.format("{0}.{1}",classname,methodname));
        }*/

        return UUID.randomUUID().toString();
    }

}
