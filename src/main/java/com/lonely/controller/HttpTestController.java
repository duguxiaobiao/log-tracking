package com.lonely.controller;

import com.lonely.service.HttpTestService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 10:58
 * @Description 测试发送http请求的控制器
 */
@RestController
@RequestMapping("/http")
public class HttpTestController {

    @Autowired
    private HttpTestService httpTestService;


    @RequestMapping("/get")
    public String testGet(@RequestBody HttpParamBean httpParamBean) {
        String result = this.httpTestService.testGet(httpParamBean.getUrl(), httpParamBean.getHeaderMap());
        return result;
    }


    @Data
    public static class HttpParamBean {
        private String url;
        private Map<String, String> headerMap;
    }
}
