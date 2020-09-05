package com.ym.gulimall.order.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrderConfirmVo {

    // shipping address, ums_member_receive_address
    @Setter @Getter
    List<MemberAddressVo> address;

    // all selected order item
    @Setter @Getter
    List<OrderItemVo> items;
    // coupon
    @Setter @Getter
    Integer integration;

    @Setter @Getter
    Map<Long, Boolean> stocks;

    @Setter @Getter
    String orderToken;

//    BigDecimal total;
//    BigDecimal payPrice;

    // 这里写了getCount, lombok会自动赋予一个属性count
    public Integer getCount() {
        Integer count = 0;
        if (items != null) {
            for (OrderItemVo item : items) {
               count += item.getCount();
            }
        }
        return count;
    }

    public BigDecimal getTotal() {

        BigDecimal sum = new BigDecimal("0");
        if (items != null) {
            for (OrderItemVo item : items) {
                BigDecimal multipy = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                sum = sum.add(multipy);
            }
        }
        return sum;
    }

    public BigDecimal getPayPrice()  {
        return getTotal();
    }

}
