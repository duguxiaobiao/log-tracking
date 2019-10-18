package com.lonely.log.configs;

import com.lonely.log.interceptors.FinalSqlGetInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 16:49
 * @Description
 */
@Configuration
@MapperScan("com.lonely.dao")
public class MybatisPluginsConfig {

    /**
     * 拦截最终的sql，打印日志拦截器
     *
     * @return
     */
    @Bean
    public FinalSqlGetInterceptor finalSqlGetInterceptor() {
        return new FinalSqlGetInterceptor();
    }
}
