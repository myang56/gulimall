package com.ym.gulimall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.ym.common.to.es.SkuEsModel;
import com.ym.gulimall.search.config.GulimallElasticSearchConfig;
import com.ym.gulimall.search.constant.ESConstant;
import com.ym.gulimall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean productStatusUp(List<SkuEsModel> esSkuModels) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel model : esSkuModels) {
            IndexRequest indexRequest = new IndexRequest(ESConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);

            bulkRequest.add(indexRequest);
        }

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);

        // TODO 如果批量保存出现错误
        boolean result = bulkResponse.hasFailures();
        List<String> collect = Arrays.stream(bulkResponse.getItems()).map(item -> {
            return item.getId();
        }).collect(Collectors.toList());
        log.info("商品上架完成, {}, 返回数据：{} ", collect, bulkResponse.toString());
        return result;
    }
}
