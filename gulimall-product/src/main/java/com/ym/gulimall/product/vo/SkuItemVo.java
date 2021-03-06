package com.ym.gulimall.product.vo;

import com.ym.gulimall.product.entity.SkuImagesEntity;
import com.ym.gulimall.product.entity.SkuInfoEntity;
import com.ym.gulimall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;

@Data
public class SkuItemVo {

    SkuInfoEntity info;

    boolean hasStock = true;

    List<SkuImagesEntity> images;

    List<SKuItemSaleAttrVo> saleAttr;

    SpuInfoDescEntity desp;

    List<SpuItemAttrGroupVo> groupAttrs;

    SeckillInfoVo seckillInfo; // 当前商品秒杀优惠信息


}
