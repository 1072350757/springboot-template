package com.baronj.template;

import com.alibaba.fastjson.JSONObject;
import com.baronj.template.mapper.TestMapper;
import com.baronj.template.service.TestService;
import com.baronj.template.util.RedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootTemplateApplicationTests {

    @Autowired
    private RedisManager redisManager;
    @Autowired
    private TestService testService;
    @Autowired
    private TestMapper testMapper;

    @Test
    void redisTest() {
        redisManager.setExpire("key", "value", 100);
    }

    @Test
    void mybatisTest() {
        try {
//            com.baronj.template.entity.Test test = new com.baronj.template.entity.Test();
//            test.setName("1312131");
//            test.setAge(22);
//            int insert = testService.insert(test);
//            System.out.println("插入数量：" + insert);

//            com.baronj.template.entity.Test test = new com.baronj.template.entity.Test();
//            test.setName("99");
//            test.setAge(99);
//            test.setId(7);
//            testService.update(test);

//            testService.delete(7);
            System.out.println(testService.query());
            System.out.println(testService.get(2));
            List<com.baronj.template.entity.Test> list = testMapper.selectList(null);
            System.out.println(JSONObject.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
