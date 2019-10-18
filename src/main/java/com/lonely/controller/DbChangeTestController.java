package com.lonely.controller;

import com.lonely.datasources.DbChangeUtil;
import com.lonely.datasources.TestUtil;
import com.lonely.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ztkj-hzb
 * @Date 2019/10/16 17:29
 * @Description 数据源切换测试请求
 */
@RestController
@RequestMapping("/dbChange")
public class DbChangeTestController {

    @Autowired
    private TestService testService;

    @Autowired
    private DbChangeUtil dbChangeUtil;

    @RequestMapping("/change")
    public String dbChange(@RequestParam String dataSourceId){
        //String result = DbChangeUtil.getInstance().changeDataSourceById(dataSourceId);
        //String result = new TestUtil().changeDataSourceById(dataSourceId);

        return this.dbChangeUtil.changeDataSourceById(dataSourceId);
    }

}
