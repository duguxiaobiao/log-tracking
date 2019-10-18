package com.lonely.test;

import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author ztkj-hzb
 * @Date 2019/10/17 13:48
 * @Description
 */
public class TestStream {


    public static void main(String[] args) throws IOException {

        String path = TestStream.class.getResource("/log4j2.xml").getPath();
        File file = new File(path);
        System.out.println("文件大小" + file.length());
        System.out.println("文件大小" + getNetFileSizeDescription(file.length()));

        String fileToString = FileUtils.readFileToString(file, "utf-8");
        System.out.println("字符串大小:" + fileToString.length());
        System.out.println("字符串大小:" + getNetFileSizeDescription(fileToString.length()));


        //System.out.println(fileToString);
        System.out.println("字符大小：" + fileToString.getBytes().length);
        System.out.println("字符大小：" + getNetFileSizeDescription(fileToString.getBytes().length));


        byte[] bytes = new byte[1024];

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        System.out.println(byteArrayInputStream.available());
    }



    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
}
