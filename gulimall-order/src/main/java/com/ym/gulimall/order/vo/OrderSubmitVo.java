package com.ym.gulimall.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSubmitVo {

    private Long addrId;
    private Integer payType;
    // 优惠，发票
    // 防重令牌
    private String orderToken;
    private BigDecimal payPrice;
    private String note; // 备注
    // 用户相关信息，直接去session中取出登录的用户
}
