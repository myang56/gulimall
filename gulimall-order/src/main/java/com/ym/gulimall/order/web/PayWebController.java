package com.ym.gulimall.order.web;

import com.alipay.api.AlipayApiException;
import com.ym.gulimall.order.config.AlipayTemplate;
import com.ym.gulimall.order.service.OrderService;
import com.ym.gulimall.order.vo.PayVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    OrderService orderService;

    @ResponseBody
    @GetMapping(value ="/payOrder", produces = "text/html")
    public String payOrder(@RequestParam("orderSn") String orderSn) throws AlipayApiException {

//        private String out_trade_no; // 商户订单号 必填
//        private String subject; // 订单名称 必填
//        private String total_amount;  // 付款金额 必填
//        private String body; // 商品描述 可空
        PayVo payVo = orderService.getOrderPay(orderSn);
        String pay = alipayTemplate.pay(payVo);  // 返回的是一个页面，直接将此页面交给浏览器就行
        System.out.println(pay);
        return pay;
    }


}
