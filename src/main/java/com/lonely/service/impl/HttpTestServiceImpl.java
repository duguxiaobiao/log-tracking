package com.lonely.service.impl;

import com.lonely.http.OkHttpUtil;
import com.lonely.service.HttpTestService;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 11:44
 * @Description
 */
@Service
public class HttpTestServiceImpl implements HttpTestService {

    /**
     * 测试get请求
     *
     * @param url
     * @param headerMap
     * @return
     */
    @Override
    public String testGet(String url, Map<String, String> headerMap) {
        Response data = OkHttpUtil.getInstance().getData(url, headerMap);
        return data.body().toString();
    }

    /**
     * 测试post请求
     *
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    @Override
    public String testPost(String url, Map<String, Object> paramMap, Map<String, String> headerMap) {
        try {
            Response response = OkHttpUtil.getInstance().postData(url, paramMap, headerMap);
            return response.body().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
