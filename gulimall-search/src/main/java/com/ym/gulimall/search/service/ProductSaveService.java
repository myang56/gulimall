package com.ym.gulimall.search.service;

import com.ym.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> esSkuModels) throws IOException;
}
