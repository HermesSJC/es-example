package com.hellcat.elasticsearch.application.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hellcat.elasticsearch.ElasticsearchClientApplicationTests;
import com.hellcat.elasticsearch.infrastructure.domain.condition.SearchExample;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;
import com.hellcat.elasticsearch.infrastructure.utils.JacksonUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class ESDataManageServiceTest extends ElasticsearchClientApplicationTests {

    @Autowired
    private ESDataManageService esDataManageService;


    @Test
    public void search() {
        String indexName = "ledger_user";

        ESDataSearchReq<LedgerESPO> req = new ESDataSearchReq<>();
        req.setIndexName(indexName);
        req.setClazz(LedgerESPO.class);

        SearchExample example = new SearchExample();
        SearchExample.Condition condition = example.createCondition();
        condition.andEqualTo("user_id", 6457L);
//        example.setOffset(0);
//        example.setLimit(1);
        req.setSearchExample(example);

        ESDataSearchRes<LedgerESPO> res = esDataManageService.search(req);
        System.out.println(JacksonUtils.toJson(res));

    }

    public static class LedgerESPO extends ESBasePO {

        @JsonProperty("user_id")
        private Long userId;

        @JsonProperty("profile_url")
        private String profileUrl;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public String getProfileUrl() {
            return profileUrl;
        }

        public void setProfileUrl(String profileUrl) {
            this.profileUrl = profileUrl;
        }
    }
}