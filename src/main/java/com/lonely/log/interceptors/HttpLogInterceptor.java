package com.lonely.log.interceptors;

import com.alibaba.fastjson.JSON;
import com.lonely.log.aspect.bean.HttpHandlerBean;
import com.lonely.log.aspect.bean.LinkTrackingBean;
import com.lonely.log.aspect.threadlocals.LinkTrackingThreadLocal;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okio.Buffer;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * okhttp 拦截器定义: 统计请求信息以及耗时
 *
 * @author hzb-ztkj
 */
@Slf4j
public class HttpLogInterceptor implements Interceptor {

    /**
     * 拦截过程中 收集 http请求输入、输出等信息，日志输出打印
     *
     * @param chain
     * @return
     * @throws IOException
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取当前请求
        Request request = chain.request();

        //根据请求类型分析
        HttpHandlerBean httpHandlerBean = new HttpHandlerBean();

        //从http request中获取请求信息
        httpHandlerBean.setUrl(request.url().toString());

        //设置请求头信息
        Headers headers = request.headers();
        Map<String, List<String>> stringListMap = headers.toMultimap();
        httpHandlerBean.setParamHeaderStr(JSON.toJSONString(stringListMap));

        //设置请求body信息
        RequestBody requestBody = request.body();
        if (requestBody != null) {
            httpHandlerBean.setParamStr(getParam(requestBody));
        }

        /*httpHandlerBean.setCode(request.);
        httpHandlerBean.setUrl();
        httpHandlerBean.setParamStr();
        httpHandlerBean.setParamHeaderStr();
        httpHandlerBean.setResultStr();
        httpHandlerBean.setResultHeaderStr();
        httpHandlerBean.setConsumingTime();
        httpHandlerBean.setRequestId();
        httpHandlerBean.setUserId();
        httpHandlerBean.setProductId();*/

        //设置请求方式
        httpHandlerBean.setRequestMode(request.method());

        /*if (methodName.equalsIgnoreCase("GET")) {
            log.info(TAG, "-url--" + methodName + "--" + request.url());
        } else if (methodName.equalsIgnoreCase("POST")) {
            RequestBody mRequestBody = request.body();
            if (mRequestBody != null) {
                String msg = "-url--" + methodName + "--" + request.url();
                String content;
                if (msg.contains("uploadFile")) {
                    content = "--上传文件内容--";
                } else {
                    content = getParam(mRequestBody);
                }
                log.info(TAG, msg + content);
            }
        }*/


        Response response = null;
        try {
            response = chain.proceed(request);

            //设置状态码
            httpHandlerBean.setCode(response.code() + "");

            //设置响应头信息
            Map<String, List<String>> responseHeaderMap = response.headers().toMultimap();
            httpHandlerBean.setResultHeaderStr(JSON.toJSONString(responseHeaderMap));

            //设置响应信息
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                httpHandlerBean.setResultStr(JSON.toJSONString(responseBody.string()));
            }

            //设置用时信息
            httpHandlerBean.setSendTime(response.sentRequestAtMillis());
            httpHandlerBean.setReceiveTime(response.receivedResponseAtMillis());

            //设置唯一性id
            LinkTrackingBean linkTrackingBean = LinkTrackingThreadLocal.get();
            if (linkTrackingBean == null) {
                httpHandlerBean.setRequestId(UUID.randomUUID().toString());
            } else {
                httpHandlerBean.setRequestId(linkTrackingBean.getRequestId());
                httpHandlerBean.setUserId(linkTrackingBean.getUserId());
                httpHandlerBean.setProductId(linkTrackingBean.getProductId());
            }

            //响应消息
            httpHandlerBean.setMessage(response.message());


        } catch (IOException e) {
            log.error("处理请求异常，异常原因:{}", ExceptionUtils.getStackTrace(e));
            httpHandlerBean.setMessage(ExceptionUtils.getStackTrace(e));
        }


        //输出，打印日志
        log.info(httpHandlerBean.httpLogPrint());

        return response;
    }

    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private String getParam(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return logparm;
    }
}