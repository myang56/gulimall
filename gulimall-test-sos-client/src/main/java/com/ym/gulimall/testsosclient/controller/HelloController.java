package com.ym.gulimall.testsosclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;


import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @Value("${sso.server.url}")
    String ssoServerUrl;

    /**
     * 无需登录即可访问
     * sso.server.url=http://ssoserver.com:8080/login.html
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/employees")
    public String employees(Model model, HttpSession session, @RequestParam(value="token", required = false) String token){

        if (!StringUtils.isEmpty(token)) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> entity = restTemplate.getForEntity("http://ssoserver.com:8080/userInfo?token="+token, String.class);
            String body = entity.getBody();
            session.setAttribute("loginUser",body);
        }

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null) {

            return "redirect:" + ssoServerUrl + "?redirect_url=http://client1.com:8081/employees   ";
        } else {
            List<String> employees = new ArrayList<>();
            employees.add("张三");
            employees.add("李四");

            model.addAttribute("employees", employees);
            return "list";
        }
    }
}
