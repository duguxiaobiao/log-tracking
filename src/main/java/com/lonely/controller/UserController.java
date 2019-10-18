package com.lonely.controller;

import com.lonely.entity.User;
import com.lonely.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:42
 * @Description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/insert")
    public String insertUser(@RequestBody User user) {
        int result = this.userService.insertUser(user);
        return result > 0 ? "保存成功" : "保存失败";
    }



}
