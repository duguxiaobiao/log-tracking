package com.lonely.log.aspect.bean;

import lombok.Data;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 14:10
 * @Description 链路跟踪实体, 用于将基础信息传递下去，根据requestid构建全链路跟踪查询
 */
@Data
public class LinkTrackingBean {

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 客户端ip
     */
    private String clientIp;

}
