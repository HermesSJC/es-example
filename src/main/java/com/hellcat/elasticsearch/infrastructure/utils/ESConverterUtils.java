package com.hellcat.elasticsearch.infrastructure.utils;

import com.hellcat.elasticsearch.infrastructure.domain.condition.SearchExample;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;
import com.hellcat.elasticsearch.infrastructure.enums.CriterionTypeEnum;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.StatusToXContentObject;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ESConverterUtils {

    private static final Logger log = LoggerFactory.getLogger(ESConverterUtils.class);

    public static <T extends ESBasePO> SearchRequest convertRequest(ESDataSearchReq<T> req) {
        Assert.notNull(req, "es search req can not be null");
        Assert.hasLength(req.getIndexName(), "index name can not be null");
        Assert.notNull(req.getClazz(), "data class can not be null");

        SearchRequest request = new SearchRequest(req.getIndexName());
        SearchExample example = req.getSearchExample();
        if (example == null) {
            return request;
        }

        SearchSourceBuilder sourceBuilder = buildSourceBuilder(example);
        request.source(sourceBuilder);
        return request;
    }

    public static <T extends ESBasePO> ESDataSearchRes<T> convertResponse(SearchResponse response, ESDataSearchRes<T> res, ESDataSearchReq<T> req) {
        SearchHits hits = response.getHits();
        if (hits == null) {
            log.warn("[ESConverterUtils] convertResponse | index={} search hits is null", req.getIndexName());
            return res;
        }
        long total = 0L;
        if (hits.getTotalHits() == null) {
            log.warn("[ESConverterUtils] convertResponse | index={} search totalHists is null", req.getIndexName());
        } else {
            total = hits.getTotalHits().value;
        }

        SearchHit[] innerHits = hits.getHits();
        List<T> dataList = new ArrayList<>((int) (total) + 1);
        if (innerHits == null) {
            log.warn("[ESConverterUtils] convertResponse | index={} search innerHits is null", req.getIndexName());
        } else if (innerHits.length == 0) {
            log.info("[ESConverterUtils] convertResponse | index={} search innerHits length is zero", req.getIndexName());
        } else {
            for (SearchHit innerHit : innerHits) {
                T data = JacksonUtils.convert(innerHit.getSourceAsMap(), req.getClazz());
                if (data != null) {
                    data.set_id(innerHit.getId());
                    dataList.add(data);
                } else {
                    log.warn("[ESConverterUtils] convertResponse | index={} search data map convert data failed, source={}",
                            req.getIndexName(), innerHit.getSourceAsMap());

                }
            }
        }

        res.setTotal((int) total);
        res.setDataList(dataList);
        return res;
    }


    public static String checkResponse(StatusToXContentObject response) {
        if (response == null) {
            return "response is null";
        }
        if (response.status() == null) {
            return "status of response is unknown";
        }
        if (!response.status().equals(RestStatus.OK)) {
            return "es get index failed! code is:" + response.status();
        }
        return null;
    }

    protected static SearchSourceBuilder buildSourceBuilder(SearchExample example) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        if (example.validLimit()) {
            sourceBuilder.size(example.getLimit());
        }
        if (example.validOffset()) {
            sourceBuilder.from(example.getOffset());
        }
        if (StringUtils.hasText(example.getOrderBy())) {
            sourceBuilder.sort(example.getOrderBy());
        }

        if (CollectionUtils.isEmpty(example.getConditionList())) {
            return sourceBuilder;
        }


        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        for (SearchExample.Condition condition : example.getConditionList()) {
            //TODO 后续还有多个and 和 or的情况 首先完成单个的情况
            Assert.notEmpty(condition.getCriteriaList(), "condition.criteria can not be empty");
            for (SearchExample.Condition.Criterion criterion : condition.getCriteriaList()) {
                CriterionTypeEnum type = CriterionTypeEnum.findByCode(criterion.getType());
                switch (type) {
                    case EQUAL_TO:
                        queryBuilder.filter(QueryBuilders.termQuery(criterion.getPropertiesName(), criterion.getObject()));
                        break;
                    case NOT_EQUAL_TO:
                        queryBuilder.mustNot(QueryBuilders.termQuery(criterion.getPropertiesName(), criterion.getObject()));
                        break;
                    case IN:
                        queryBuilder.filter(QueryBuilders.termsQuery(criterion.getPropertiesName(), criterion.getObjectList()));
                        break;
                    case LESS_THAN:
                        queryBuilder.filter(QueryBuilders.rangeQuery(criterion.getPropertiesName()).lt(criterion.getObject()));
                        break;
                    case LESS_THAN_EQUAL_TO:
                        queryBuilder.filter(QueryBuilders.rangeQuery(criterion.getPropertiesName()).lte(criterion.getObject()));
                        break;
                    case GREATER_THAN:
                        queryBuilder.filter(QueryBuilders.rangeQuery(criterion.getPropertiesName()).gt(criterion.getObject()));
                        break;
                    case GREATER_THAN_EQUAL_TO:
                        queryBuilder.filter(QueryBuilders.rangeQuery(criterion.getPropertiesName()).gte(criterion.getObject()));
                        break;
                    case UNKNOWN:
                    default:
                        log.warn("[ESConverterUtils] buildSourceBuilder | criterion is invalid! its value={}",
                                JacksonUtils.toJson(criterion));
                }
            }
        }
        sourceBuilder.query(queryBuilder);
        return sourceBuilder;
    }
}
