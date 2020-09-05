package com.ym.gulimall.order.vo;

import com.ym.gulimall.order.entity.OrderEntity;
import lombok.Data;

@Data
public class SubmitOrderResponseVo {

    private OrderEntity order;
    private Integer code; // 0 成功，错误状态码
}
