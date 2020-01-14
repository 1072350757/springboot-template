package com.baronj.template;

import com.alibaba.fastjson.JSONObject;
import com.baronj.template.mapper.TestMapper;
import com.baronj.template.service.TestService;
import com.baronj.template.util.HttpClientUtils;
import com.baronj.template.util.RedisManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    @Test
    public void post() throws IOException {
        String geturl = "http://www.dmzshequ.com/";
        String cookie = "UM_distinctid=16f8e24c83e32b-04e0280595f05a-39627c0f-1aeaa0-16f8e24c840645; 0yok_2132_saltkey=gYBbIsRW; 0yok_2132_lastvisit=1578634324; 0yok_2132_sid=lHo4IP; CNZZDATA1275101113=1366002711-1578636706-%7C1578642111; 0yok_2132_sendmail=1; 0yok_2132_seccode=440.bc26a9bcc2279c863a; 0yok_2132_ulastactivity=f473XFELKw%2FSFwlAyCEsNkW0CTfNfUiai153Dha3QWLzeExNHUQR; 0yok_2132_auth=f473XFELKw%2FSFwlAyCF9PkawDjPKehvA2g92ChW%2FEm2tfRwfRkpFkA4e0QHowHUxBqU6Q%2FPM3HRz3zadYrQlml47hQ; 0yok_2132_lastcheckfeed=18377%7C1578642281; 0yok_2132_checkfollow=1; 0yok_2132_lip=114.84.178.241%2C1578637921; 0yok_2132_nofavfid=1; 0yok_2132_onlineusernum=252; 0yok_2132_checkpm=1; 0yok_2132_noticeTitle=1; 0yok_2132_lastact=1578642290%09misc.php%09patch";
        Map<String, String> headers = new TreeMap<>();
        headers.put("cookie", cookie);
        String content = HttpClientUtils.doGet(geturl, headers);
        String key = "name=\"formhash\" value=\"";
        int i = content.indexOf(key);
        content = content.substring(i, i + key.length() + 8);
        String hash = content.replace(key, "");

        String url = "http://www.dmzshequ.com/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=1&sign_as=1&inajax=1";
        headers.put("Host", "www.dmzshequ.com");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
        Map<String, Object> params = new TreeMap<>();
        params.put("formhash", hash);
        params.put("qdxq", "fd");
        params.put("qdmode", 1);
        params.put("todaysay", "没什么说的");
        params.put("fastreply", 0);
        System.out.println(HttpClientUtils.doPost(url, headers, params));
    }

}
