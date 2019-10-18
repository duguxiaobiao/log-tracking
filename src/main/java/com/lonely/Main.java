package com.lonely;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ztkj-hzb
 * @Date 2019/10/14 11:31
 * @Description
 */
@SpringBootApplication
@MapperScan("com.lonely.dao")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
