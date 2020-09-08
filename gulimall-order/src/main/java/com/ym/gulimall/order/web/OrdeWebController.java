package com.ym.gulimall.order.web;

import com.ym.gulimall.order.service.OrderService;
import com.ym.gulimall.order.vo.OrderConfirmVo;
import com.ym.gulimall.order.vo.OrderSubmitVo;
import com.ym.gulimall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class OrdeWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo orderConfirmVo = orderService.confirmOrder();
        model.addAttribute("orderConfirmData", orderConfirmVo);
        return "confirm";
    }

    /**
     * 下单功能
     *
     * @param
     * @return
     */
    @PostMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes redirectAttributes) {

        SubmitOrderResponseVo responseVo = orderService.submitOrder(vo);

        System.out.println("订单提交的数据 " + vo);

        if (responseVo.getCode() == 0) {

            model.addAttribute("submitOrderResp", responseVo);
            return "pay";
        } else {

            String msg = "下单失败: ";
            switch (responseVo.getCode()) {
                case 1:
                    msg += "订单信息过期，请刷新再次提交";
                    break;
                case 2:
                    msg += "订单商品价格发送变化，请确认后再次提及";
                    break;
                case 3:
                    msg += "库存锁定石板，商品库存不足";
                    break;
            }
            redirectAttributes.addFlashAttribute("msg", "");
            return "redirect:http://order.gulimall.com/toTrade";
        }

    }
}
