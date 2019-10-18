package com.lonely.service;

import com.lonely.entity.User;
import com.lonely.log.aspect.annotations.ServiceLog;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:27
 * @Description
 */
public interface UserService {

    int insertUser(User user);

    User findAllUser();

}
