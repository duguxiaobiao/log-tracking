package com.lonely.files;

import com.lonely.test.TestStream;
import com.lonely.utils.SizeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author ztkj-hzb
 * @Date 2019/10/17 14:11
 * @Description 文件上传下载日志格式输出测试
 */
@Slf4j
public class TestFileLog {

    public static void main(String[] args) {
        TestFileLog testFileLog = new TestFileLog();
        testFileLog.testUpload();
    }

    /**
     * 模拟文件上传
     */
    public void testUpload() {

        //测试数据
        String path = TestFileLog.class.getResource("/log4j2.xml").getPath();
        File file = new File(path);
        try {
            String readFileToString = FileUtils.readFileToString(file, "utf-8");

            //xxx 上传oss，这里忽略，只需要统计文件大小
            String size = SizeConvertUtil.getNetFileSizeDescription(readFileToString.getBytes().length);

            log.info("上传指定文件：{} 成功，文件大小：{}", "log4j2.xml", size);

        } catch (Exception e) {
            //上传失败
            log.error("上传指定文件：{}失败，异常原因：{}", "log4j2.xml", ExceptionUtils.getStackTrace(e));
        }


    }


}
