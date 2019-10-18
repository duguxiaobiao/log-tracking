package com.lonely.test;

import com.lonely.http.OkHttpUtil;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 15:51
 * @Description
 */
public class TestHttp {


    public static void main(String[] args) throws IOException {

        Response data = OkHttpUtil.getInstance().getData("http://www.baidu.com");
        System.out.println(data.body().string());

    }


}
