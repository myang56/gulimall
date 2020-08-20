package com.ym.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.ym.common.utils.R;
import com.ym.gulimall.auth.vo.UserLoginVo;
import com.ym.gulimall.auth.feign.MemberFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    MemberFeignService memberFeignService;
    /**
     * 发送一个请求直接跳转的一个页面
     * SpringMVC viewController 将请求和页面映射
     * @return
     */

//    @GetMapping("/login.html")
//    public String loginPage() {
//
//        return "login";
//    }
//
//    @GetMapping("/reg.html")
//    public String regPage() {
//
//        return "reg";
//    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes) {

        R login = memberFeignService.login(vo);
        if (login.getCode() == 0) { // success
            return "redirect:http://gulimall.com";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
