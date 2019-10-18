package com.lonely.log.aspect.bean;

import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 14:24
 * @Description http请求处理实体
 */
@Data
public class HttpHandlerBean implements Serializable {

    /**
     * 请求方式
     */
    private String requestMode;

    /**
     * http状态码
     */
    private String code;

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求入参
     */
    private String paramStr;

    /**
     * 请求头字符串
     */
    private String paramHeaderStr;

    /**
     * 响应结果
     */
    private String resultStr;

    /**
     * 响应头字符串
     */
    private String resultHeaderStr;

    /**
     * 发送请求时间（毫秒）
     */
    private long sendTime;

    /**
     * 处理完请求，即返回结果的时间
     */
    private long receiveTime;

    /**
     * 响应的消息
     */
    private String message;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 发起请求的用户
     */
    private String userId;

    /**
     * 当前产品id
     */
    private String productId;


    /**
     * 打印http日志
     *
     * @return
     */
    public String httpLogPrint() {

        long ms = receiveTime - sendTime;

        String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sendTime);


        StringBuilder stb = new StringBuilder("\n");
        stb.append("===================Http请求日志===========================");
        stb.append("\r\n");

        stb.append(MessageFormat.format("请求ID：{0}", requestId)).append("\r\n");
        stb.append(MessageFormat.format("请求人：{0}", userId)).append("\r\n");
        stb.append(MessageFormat.format("请求Url：{0}", url)).append("\r\n");
        stb.append(MessageFormat.format("请求状态：{0}", code)).append("\r\n");
        stb.append(MessageFormat.format("请求时间：{0}", sendData)).append("\r\n");
        stb.append(MessageFormat.format("处理用时：{0}ms", ms)).append("\r\n");
        stb.append(MessageFormat.format("请求Header参数：{0}", paramHeaderStr)).append("\r\n");
        stb.append(MessageFormat.format("请求Params参数：{0}", paramStr)).append("\r\n");
        stb.append(MessageFormat.format("响应Header参数：{0}", resultHeaderStr)).append("\r\n");
        stb.append(MessageFormat.format("响应结果：{0}", resultStr)).append("\r\n");
        stb.append(MessageFormat.format("处理结果：{0}", message)).append("\r\n");


        //stb.append(MessageFormat.format("异常消息：{0}", errorMsg)).append("\r\n");

        stb.append("===================Http请求日志==============================");
        stb.append("\r\n");
        stb.append("\r\n");

        return stb.toString();
    }


}
