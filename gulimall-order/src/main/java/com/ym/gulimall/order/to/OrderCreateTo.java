package com.ym.gulimall.order.to;

import com.ym.gulimall.order.entity.OrderEntity;
import com.ym.gulimall.order.entity.OrderItemEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.text.Bidi;
import java.util.List;

@Data
public class OrderCreateTo {

    private OrderEntity order;
    private List<OrderItemEntity> orderItems;
    private BigDecimal payPrice; // 订单应付价格
    private BigDecimal fare;
}
