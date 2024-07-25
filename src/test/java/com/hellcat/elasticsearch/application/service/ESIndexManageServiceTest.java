package com.hellcat.elasticsearch.application.service;

import com.hellcat.elasticsearch.ElasticsearchClientApplicationTests;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchRes;
import com.hellcat.elasticsearch.infrastructure.utils.JacksonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ESIndexManageServiceTest extends ElasticsearchClientApplicationTests {

    @Autowired
    private ESIndexManageService esIndexManageService;

    @Test
    public void searchIndex() {
        ESIndexSearchReq req = new ESIndexSearchReq();
        ESIndexSearchRes res = esIndexManageService.searchIndex(req);
        System.out.println(JacksonUtils.toJson(res));
    }

    @Test
    public void searchIndexProperties() {
        String indexName = "ledger_user";
        ESIndexPropertiesSearchReq req = new ESIndexPropertiesSearchReq();
        req.setIndexName(indexName);
        ESIndexPropertiesSearchRes res = esIndexManageService.searchIndexProperties(req);
        System.out.println(JacksonUtils.toJson(res));
    }
}