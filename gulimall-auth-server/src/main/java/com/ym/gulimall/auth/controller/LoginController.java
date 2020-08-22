package com.ym.gulimall.auth.controller;

import com.alibaba.fastjson.TypeReference;
import com.ym.common.constant.AuthServerConstant;
import com.ym.common.utils.R;
import com.ym.common.vo.MemberRespVo;
import com.ym.gulimall.auth.vo.UserLoginVo;
import com.ym.gulimall.auth.feign.MemberFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/login.html")
    public String loginPage(HttpSession session) {
        Object attribute = session.getAttribute(AuthServerConstant.LOGIN_USER);
        if (attribute == null) {
            return "login";
        } else {
            return "redirect:http://gulimall.com";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes redirectAttributes, HttpSession session) {

        R login = memberFeignService.login(vo);
        if (login.getCode() == 0) { // success
            MemberRespVo data = login.getData("data", new TypeReference<MemberRespVo>() {});
            session.setAttribute(AuthServerConstant.LOGIN_USER, data);
            return "redirect:http://gulimall.com";
        } else {
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", login.getData("msg", new TypeReference<String>(){}));
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
