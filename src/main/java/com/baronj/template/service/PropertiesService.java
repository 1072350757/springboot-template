package com.baronj.template.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baronj.template.entity.Properties;
import com.baronj.template.mapper.IPropertiesMapper;
import com.baronj.template.util.HttpClientUtils;
import com.baronj.template.util.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class PropertiesService {
    @Autowired
    private IPropertiesMapper propertiesMapper;

    /**
     * it学习网签到
     */
    public void dmzshequSignIn() {
        try {
            //从数据库获取cookie
            QueryWrapper<Properties> wrapper = new QueryWrapper<>();
            wrapper.eq("name", "dmzshequ_cookie");
            Properties properties = propertiesMapper.selectOne(wrapper);
            String cookie = properties.getValue();

            //请求主页 获取hash值
            String geturl = "http://www.dmzshequ.com/";
            Map<String, String> headers = new TreeMap<>();
            headers.put("cookie", cookie);
            String content = HttpClientUtils.doGet(geturl, headers);
            if (content.contains("<a href=\"member.php?mod=logging&amp;action=login&amp;mobile=2\" title=\"登录\">登录</a>")) {//如果包含登录则表示cookie失效
                MailUtils.sendMail("1072350757@qq.com", "论坛签到预警通知", "it学习网cookie已过期，请重新获取cookie并替换数据库数据。");
                return;
            }
            String key = "name=\"formhash\" value=\"";
            int i = content.indexOf(key);
            content = content.substring(i, i + key.length() + 8);
            String hash = content.replace(key, "");

            //签到
            String url = "http://www.dmzshequ.com/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=1&sign_as=1&inajax=1";
            headers.put("Host", "www.dmzshequ.com");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
            Map<String, Object> params = new TreeMap<>();
            params.put("formhash", hash);
            params.put("qdxq", "fd");
            params.put("qdmode", 1);
            params.put("todaysay", "没什么说的");
            params.put("fastreply", 0);
            String signMessage = HttpClientUtils.doPost(url, headers, params);
            log.info(signMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * it学习网摇一摇
     */
    public void dmzshequYyy() {
        try {
            //从数据库获取cookie
            QueryWrapper<Properties> wrapper = new QueryWrapper<>();
            wrapper.eq("name", "dmzshequ_cookie");
            Properties properties = propertiesMapper.selectOne(wrapper);
            String cookie = properties.getValue();

            //获取hash
            String yyyPage = "http://www.dmzshequ.com/plugin.php?id=yinxingfei_zzza:yinxingfei_zzza_hall";
            Map<String, String> headers = new TreeMap<>();
            headers.put("cookie", cookie);
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
            String yyyPageContent = HttpClientUtils.doGet(yyyPage, headers);
            String key = "<input type=\"hidden\" name=\"formhash\" value=\"";
            String formhash = yyyPageContent.substring(yyyPageContent.indexOf(key), yyyPageContent.indexOf(key) + key.length() + 8).replace(key, "");

            //摇一摇
            String yyyUrl = "http://www.dmzshequ.com/plugin.php?id=yinxingfei_zzza:yinxingfei_zzza_post";
            headers.put("Host", "www.dmzshequ.com");
            headers.put("Connection", "keep-alive");
            headers.put("Cache-Control", "max-age=0");
            headers.put("Origin", "http://www.dmzshequ.com");
            headers.put("Upgrade-Insecure-Requests", "1");
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            headers.put("Referer", "http://www.dmzshequ.com/plugin.php?id=yinxingfei_zzza:yinxingfei_zzza_hall");
            headers.put("Accept-Encoding", "gzip, deflate");
            headers.put("Accept-Language", "zh-CN,zh;q=0.9");
            Map<String, Object> param = new TreeMap<>();
            param.put("formhash", formhash);
            String response = HttpClientUtils.doPost(yyyUrl, headers, param);
            response = response.substring(response.indexOf("<div id=\"messagetext\""), response.indexOf("<div id=\"messagetext\"") + 100);
            System.out.println(response);

            //查询摇奖得到的金币数
            String url = "http://www.dmzshequ.com/plugin.php?id=yinxingfei_zzza:yinxingfei_zzza_hall&yjjs=yes";
            String content = HttpClientUtils.doGet(url, headers);
            String t = "<p style=\"font-size:14px;\">";
            content = content.substring(content.indexOf(t), content.indexOf(t) + t.length() + 23).replace(t, "");
            System.out.println(content);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 红蓝安全网签到
     */
    public void hlodaySignIn() {
        try {
            //从数据库获取cookie
            QueryWrapper<Properties> wrapper = new QueryWrapper<>();
            wrapper.eq("name", "hloday_cookie");
            Properties properties = propertiesMapper.selectOne(wrapper);
            String cookie = properties.getValue();
            String indexUrl = "https://www.hloday.com/";
            Map<String, String> headers = new TreeMap<>();
            headers.put("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36");
            headers.put("cookie", cookie);
            String content = HttpClientUtils.doGet(indexUrl, headers);
            //cookie无效 重新登录获取
            if (!content.contains("title=\"访问我的空间\">1072350757</a></strong>")) {
                log.info("红蓝社区论坛 - 重新登录获取cookie...");
                String postUrl = "https://www.hloday.com/member.php?mod=logging&action=login&loginsubmit=yes&infloat=yes&lssubmit=yes&inajax=1";
                Map<String, Object> params = new TreeMap<>();
                params.put("fastloginfield", "username");
                params.put("username", "1072350757");
                params.put("password", "Jiang1072350757");
                params.put("quickforward", "yes");
                params.put("handlekey", "ls");
                cookie = HttpClientUtils.loginGetCookie(postUrl, headers, params);
                //新cookie保存到数据库
                properties.setValue(cookie);
                propertiesMapper.updateById(properties);

                //重新获取cookie后 再获取页面信息 查找hash值
                headers.put("cookie", cookie);
                content = HttpClientUtils.doGet(indexUrl, headers);
            }
            //获取hash
            int index = content.indexOf("formhash=");
            String hash = content.substring(index, index + 17).replace("formhash=", "");
            //签到
            String signUrl = "https://www.hloday.com/plugin.php?id=dsu_paulsign:sign&operation=qiandao&infloat=1&sign_as=1&inajax=1";
            Map<String, Object> signPram = new TreeMap<>();
            signPram.put("formhash", hash);
            signPram.put("qdxq", "fd");
            signPram.put("qdmode", 2);
            signPram.put("todaysay", "");
            signPram.put("fastreply", 0);
            String response = HttpClientUtils.doPost(signUrl, headers, signPram);
            log.info(response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
