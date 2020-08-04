package com.ym.gulimall.search.controller;

import com.ym.common.exception.BizCodeEnume;
import com.ym.common.to.es.SkuEsModel;
import com.ym.common.utils.R;
import com.ym.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequestMapping("/search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> esSkuModels) {

        boolean result;

        try {
            result = productSaveService.productStatusUp(esSkuModels);
        } catch (IOException e) {
            log.error("ESSaveController 商品上架错误: {}", e);
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }

        if (!result) {
            return R.ok();
        } else {
            return R.error(BizCodeEnume.PRODUCT_UP_EXCEPTION.getCode(), BizCodeEnume.PRODUCT_UP_EXCEPTION.getMsg());
        }
    }
}
