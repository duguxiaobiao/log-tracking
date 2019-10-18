package com.lonely.entity;

import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 11:08
 * @Description
 */
@Data
public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 年龄
     */
    private String age;
}
