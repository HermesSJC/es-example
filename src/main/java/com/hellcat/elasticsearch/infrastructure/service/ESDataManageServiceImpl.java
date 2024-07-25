package com.hellcat.elasticsearch.infrastructure.service;

import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchRes;
import com.hellcat.elasticsearch.application.service.ESDataManageService;
import com.hellcat.elasticsearch.infrastructure.config.ESBeanConfig;
import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;
import com.hellcat.elasticsearch.infrastructure.utils.ESConverterUtils;
import com.hellcat.elasticsearch.infrastructure.utils.JacksonUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ESDataManageServiceImpl implements ESDataManageService {

    private static final Logger log = LoggerFactory.getLogger(ESDataManageServiceImpl.class);

    @Autowired
    @Qualifier(ESBeanConfig.ES_CLIENT_BEAN_NAME)
    private RestHighLevelClient restHighLevelClient;


    @Override
    public <T extends ESBasePO> ESDataSearchRes<T> search(ESDataSearchReq<T> req) {
        try {
            ESDataSearchRes<T> res = new ESDataSearchRes<>();
            SearchRequest request = ESConverterUtils.convertRequest(req);
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            String errMsg = ESConverterUtils.checkResponse(response);
            if (StringUtils.hasText(errMsg)) {
                res.setErrMeg(errMsg);
                return res;
            }
            return ESConverterUtils.convertResponse(response, res, req);
        } catch (Exception e) {
            log.error("[ESDataManageService] search | restHighLevelClient.search(), req={}",
                    JacksonUtils.toJson(req), e);
            ESDataSearchRes<T> res = new ESDataSearchRes<>();
            res.setErrMeg(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "unknown error");
            return res;
        }
    }
}
