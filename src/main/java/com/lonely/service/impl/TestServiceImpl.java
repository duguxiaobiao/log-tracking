package com.lonely.service.impl;

import com.lonely.datasources.TestUtil;
import com.lonely.log.aspect.annotations.ServiceLog;
import com.lonely.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ztkj-hzb
 * @Date 2019/10/15 17:55
 * @Description
 */
//@ServiceLog
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestUtil testUtil;

    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }

    @Override
    public String testChangeDb(String dataId) {
        return testUtil.changeDataSourceById(dataId);
    }
}
