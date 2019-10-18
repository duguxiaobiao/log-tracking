package com.lonely.log.aspect.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 16:40
 * @Description 数据库数据源切换记录日志实体
 */
@Data
public class DbDataSourceChangeLogBean implements Serializable {


    /**
     * 返回的数据源key
     */
    private String changeKey;

    /**
     * 调用类
     */
    private String className;

    /**
     * 调用方法
     */
    private String methodName;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 待切换的数据源id
     */
    private String dbId;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 异常消息
     */
    private String errorMsg;


    /**
     * 数据源切换日志打印
     *
     * @return
     */
    public String dbChangeLogPrint() {

        StringBuilder stb = new StringBuilder("\n");
        stb.append("===================数据源切换日志===========================");
        stb.append("\r\n");

        stb.append(MessageFormat.format("请求ID：{0}", requestId)).append("\r\n");
        stb.append(MessageFormat.format("请求人：{0}", userId)).append("\r\n");
        stb.append(MessageFormat.format("处理类：{0}", className)).append("\r\n");
        stb.append(MessageFormat.format("处理方法：{0}", methodName)).append("\r\n");
        stb.append(MessageFormat.format("待切换的数据源Id：{0}", dbId)).append("\r\n");
        stb.append(MessageFormat.format("切换后的数据源Key：{0}", changeKey)).append("\r\n");
        stb.append(MessageFormat.format("异常消息：{0}", errorMsg)).append("\r\n");

        stb.append("===================数据源切换日志==============================");
        stb.append("\r\n");
        stb.append("\r\n");

        return stb.toString();
    }

}
