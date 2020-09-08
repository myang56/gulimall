package com.ym.gulimall.search.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchParam {

    private String keyword; //
    private Long catalog3Id;

    /**
     * sort=saleCount_asc/desc
     * sort=skuPrice_asc/desc
     * sort=hotScore_asc/desc
     */
    private String sort; //sort condition

    /**
     * filter condition
     * hasStock, skuPrice, brandId, catalogId3, attrs
     */
    private Integer hasStock = 1; //
    private String skuPrice; // price range
    private List<Long> brandId; // can choose multiple brand
    private List<String> attrs;
    private Integer pageNum = 1;
    private String queryString;


}
