package com.ym.gulimall.search.service;

import com.ym.gulimall.search.vo.SearchParam;
import com.ym.gulimall.search.vo.SearchResult;

public interface MallSearchService {

    /**
     * all search param
     * @param param
     * @return
     */
    SearchResult search(SearchParam param);
}
