package com.lonely.log.aspect.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author ztkj-hzb
 * @Date 2019/10/14 13:33
 * @Description 输出日志bean
 */
@Data
public class LogBean implements Serializable {

    /**
     * 请求id
     */
    private String requestId;
    /**
     * 请求地址
     */
    private String requestPath;
    /**
     * 调用的方法名
     */
    private String method;
    /**
     * 调用用户
     */
    private String userName;
    /**
     * 入参
     */
    private String paramStr;

    /**
     * 返回值
     */
    private Object returned;
    /**
     * 调用类路径
     */
    private String clasz;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 开始时间
     */
    private long startTimeMillis;
    /**
     * 结束时间
     */
    private long endTimeMillis;
    /**
     * mac地址
     */
    private String macAdress;

    /**
     * 描述信息
     */
    private String operation;

    /**
     * 异常消息
     */
    private String errorMsg;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 打印结束日志
     *
     * @return
     */
    public String printEndLog() {

        long ms = endTimeMillis - startTimeMillis;

        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endTimeMillis);

        String resultStr = JSON.toJSONString(returned);

        StringBuilder stb = new StringBuilder("\n");
        stb.append("===================接口日志End===========================");
        stb.append("\r\n");

        stb.append(MessageFormat.format("请求ID：{0}", requestId)).append("\r\n");
        stb.append(MessageFormat.format("请求人：{0}", userName)).append("\r\n");
        stb.append(MessageFormat.format("请求地址：{0}", requestPath)).append("\r\n");
        stb.append(MessageFormat.format("处理类：{0}", clasz)).append("\r\n");
        stb.append(MessageFormat.format("处理方法：{0}", method)).append("\r\n");
        stb.append(MessageFormat.format("结束时间：{0}", optTime)).append("\r\n");
        stb.append(MessageFormat.format("处理用时：{0}ms", ms)).append("\r\n");
        stb.append(MessageFormat.format("客户端IP：{0}", clientIp)).append("\r\n");
        stb.append(MessageFormat.format("参数：{0}", paramStr)).append("\r\n");
        stb.append(MessageFormat.format("返回结果：{0}", resultStr)).append("\r\n");
        stb.append(MessageFormat.format("异常消息：{0}", errorMsg)).append("\r\n");

        stb.append("===================接口日志==============================");
        stb.append("\r\n");
        stb.append("\r\n");

        return stb.toString();
    }

    /**
     * 开始日志打印
     *
     * @return
     */
    public String pringStartLog() {
        String optTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTimeMillis);
        StringBuilder stb = new StringBuilder("\n");
        stb.append("===================接口日志Start===========================");
        stb.append("\r\n");

        stb.append(MessageFormat.format("请求ID：{0}", requestId)).append("\r\n");
        stb.append(MessageFormat.format("请求人：{0}", userName)).append("\r\n");
        stb.append(MessageFormat.format("请求地址：{0}", requestPath)).append("\r\n");
        stb.append(MessageFormat.format("处理类：{0}", clasz)).append("\r\n");
        stb.append(MessageFormat.format("处理方法：{0}", method)).append("\r\n");
        stb.append(MessageFormat.format("请求时间：{0}", optTime)).append("\r\n");
        stb.append(MessageFormat.format("客户端IP：{0}", clientIp)).append("\r\n");
        stb.append(MessageFormat.format("参数：{0}", paramStr)).append("\r\n");

        stb.append("===================接口日志=================================");
        stb.append("\r\n");
        stb.append("\r\n");

        return stb.toString();
    }

}
