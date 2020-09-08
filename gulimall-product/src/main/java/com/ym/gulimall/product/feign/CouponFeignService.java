package com.ym.gulimall.product.feign;

import com.ym.common.to.SkuReductionTo;
import com.ym.common.to.SpuBoundTo;
import com.ym.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-coupon")
public interface CouponFeignService {


    /**
     * 1.  couponFeignService.saveSpuBounds(spuBoundTo);
     * 1. @RequestBody 将这个对象转化为json
     * 2. 找到gulimall-coupon 服务，给/coupon/spubounds/save 发送请求
     * 将上一步转的json放在requestbody位置，发送请求
     * 3. 对方服务收到请求，requestbody里有json数据
     *
     * @param spuBoundTo
     * @return
     * @RequestBody SpuBoundsEntity spuBounds 将requestbody的json转化为SpuBoundsEntity spuBounds
     * 只要json数据模型是使用同一个to
     */

    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    @PostMapping("/coupon/skufullreduction/saveinfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
