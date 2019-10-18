package com.lonely.log.aspect.bean;

import lombok.Data;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 16:42
 * @Description sql分析实体
 */
@Data
public class SqlAnalysisBean implements Serializable {


    private String requestId;

    /**
     * 请求参数
     */
    private String parameter;
    /**
     * mybatis mapper 命名空间
     */
    private String mapper;
    /**
     * 完整sql 语句
     */
    private String sqls;
    /**
     * sql异常消息
     */
    private String errorMsg;

    /**
     * 构建sql执行日志信息
     *
     * @return
     */
    public String printLog() {
        StringBuilder stb = new StringBuilder("\n");
        stb.append("===================Sql执行日志===========================");
        stb.append("\r\n");

        stb.append(MessageFormat.format("请求ID：{0}", requestId)).append("\r\n");
        stb.append(MessageFormat.format("请求参数：{0}", parameter)).append("\r\n");
        stb.append(MessageFormat.format("命名空间：{0}", mapper)).append("\r\n");
        stb.append(MessageFormat.format("Sql：{0}", sqls)).append("\r\n");
        stb.append(MessageFormat.format("异常消息：{0}", errorMsg)).append("\r\n");

        stb.append("===================Sql执行日志=================================");
        stb.append("\r\n");
        stb.append("\r\n");

        return stb.toString();
    }
}
