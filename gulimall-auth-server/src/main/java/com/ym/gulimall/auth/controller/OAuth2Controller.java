package com.ym.gulimall.auth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.ym.common.utils.HttpUtils;
import com.ym.common.utils.R;
import com.ym.gulimall.auth.feign.MemberFeignService;
import com.ym.gulimall.auth.vo.MemberRespVo;
import com.ym.gulimall.auth.vo.SocialUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class OAuth2Controller {

    @Autowired
    MemberFeignService memberFeignService;

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code, HttpSession session) throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("client_id", "1580593711");
        map.put("client_secret", "61376eed461b33778d9f7fe22b7807ef");
        map.put("grant_type", "authorization_code");
        map.put("redirect_uri", "http://auth.gulimall.com/oauth2.0/weibo/success");
        map.put("code", code);
        HttpResponse response = HttpUtils.doPost("https://api.weibo.com", "/oauth2/access_token", "post", new HashMap<>(), map, new HashMap<>());

        if (response.getStatusLine().getStatusCode() == 200) {

            String json = EntityUtils.toString(response.getEntity());
            SocialUser socialUser = JSON.parseObject(json, SocialUser.class);
            log.info("登录成功: 用户： {}", socialUser.getUid());
            log.info("用户： {}", socialUser);

            // 获取用户的登录平台，然后判断用户是否该注册到系统中
            // 如果当前用户是第一次进网站，自动注册进来
            R r = memberFeignService.oauthLogin(socialUser);
            if (r.getCode() == 0) {
                // session 子域共享问题
                MemberRespVo data = r.getData(new TypeReference<MemberRespVo>() {});
                session.setAttribute("loginUser", data);
                log.info("登录成功: 用户： {}", data.toString());
                return "redirect:http://gulimall.com";
            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }


        } else {
            return "redirect:http://auth.gulimall.com/login.html";
        }

    }

}
