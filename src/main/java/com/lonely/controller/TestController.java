package com.lonely.controller;

import com.alibaba.fastjson.JSON;
import com.lonely.datasources.DbChangeUtil;
import com.lonely.log.aspect.annotations.ServiceLog;
import com.lonely.entity.User;
import com.lonely.service.TestService;
import com.lonely.utils.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ztkj-hzb
 * @Date 2019/10/14 14:24
 * @Description
 */
@RestController
@RequestMapping
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/sayHello")
    public String sayHello(String name){
        System.out.println(Thread.currentThread().getName());
        return this.testService.sayHello(name);
    }

    @RequestMapping("/insertUser")
    @ServiceLog
    public String insertUser(@RequestBody User user){
        System.out.println(user);
        return JSON.toJSONString(user);
    }


    @RequestMapping("/test")
    public void test(){
        DbChangeUtil bean = SpringContextHolder.getBean(DbChangeUtil.class);
        DbChangeUtil bean2 = SpringContextHolder.getBean(DbChangeUtil.class);
        System.out.println(bean == bean2);
    }

}
