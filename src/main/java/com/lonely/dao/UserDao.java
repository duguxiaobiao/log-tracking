package com.lonely.dao;

import com.lonely.entity.User;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:29
 * @Description
 */
public interface UserDao {


    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    int insertUser(User user);

}
