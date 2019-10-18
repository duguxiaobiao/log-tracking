package com.lonely.service;

import java.util.Map;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 10:59
 * @Description
 */
public interface HttpTestService {

    /**
     * 测试get请求
     * @param url
     * @param headerMap
     * @return
     */
    String testGet(String url, Map<String,String> headerMap);

    /**
     * 测试post请求
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    String testPost(String url,Map<String,Object> paramMap,Map<String,String> headerMap);
}
