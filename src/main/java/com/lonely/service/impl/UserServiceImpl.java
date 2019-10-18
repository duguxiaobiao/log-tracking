package com.lonely.service.impl;

import com.lonely.dao.UserDao;
import com.lonely.log.aspect.annotations.ServiceLog;
import com.lonely.service.UserService;
import com.lonely.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:29
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    @ServiceLog
    public int insertUser(User user) {
        return this.userDao.insertUser(user);
    }

    @Override
    public User findAllUser() {
        return null;
    }
}
