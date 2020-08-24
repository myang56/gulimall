package com.ym.gulimall.cart.controller;

import com.ym.common.constant.AuthServerConstant;
import com.ym.gulimall.cart.interceptor.CartInterceptor;
import com.ym.gulimall.cart.service.CartService;
import com.ym.gulimall.cart.vo.Cart;
import com.ym.gulimall.cart.vo.CartItem;
import com.ym.gulimall.cart.vo.UserInfoTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check) {

        cartService.checkItem(skuId, check);
        return "redirect:http://cart.gulimall.com/cart.html";
    }


    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {

        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @GetMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {

        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }



    /**
     * 浏览器有一个cookie，user-key：标识用户身份，一个月后过期
     * 如果第一次使用jd的购物车功能，都会给一个临时的用户身份
     * 浏览器以后保存，每次访问都会带上这个cookie
     *
     * 登录：session有
     * 没登录，按照cookie里面带俩的user-key来做
     * 第一次：如果没有临时用户，帮忙创建一个临时用户
     * @param
     * @return
     */
    @GetMapping("/cart.html")
    public String cartListPage(Model model) throws ExecutionException, InterruptedException {

        // 快速得到用户信息，id，user-key
//        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
//        System.out.println(userInfoTo);
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    @GetMapping("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                            RedirectAttributes ra) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId, num);
//        model.addAttribute("item", cartItem);
        ra.addAttribute("skuId", skuId); // 相当于把这个信息换到结果url后面的param
        return "redirect:http://cart.gulimall.com/addToCartSuccess.html";
    }

    /**
     * 前面addTocart方法重定向到这个方法，这个方法就是查一下购车车，避免前面方法页面不断刷新，然后不断加进购物车的操作
     * @param skuId
     * @param model
     * @return
     */
    @GetMapping("/addToCartSuccess.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {

        // 重定向到成功页面，再次查询购物车数据即可
        CartItem item = cartService.getCartItem(skuId);
        model.addAttribute("item", item);
        return "success";
    }
}
