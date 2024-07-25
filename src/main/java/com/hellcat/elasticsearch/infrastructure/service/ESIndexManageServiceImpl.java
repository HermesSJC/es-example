package com.hellcat.elasticsearch.infrastructure.service;

import com.hellcat.elasticsearch.application.constants.ESConstants;
import com.hellcat.elasticsearch.infrastructure.domain.entity.ESIndexPropertiesDTO;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchRes;
import com.hellcat.elasticsearch.application.service.ESIndexManageService;
import com.hellcat.elasticsearch.infrastructure.config.ESBeanConfig;
import com.hellcat.elasticsearch.infrastructure.utils.ESConverterUtils;
import com.hellcat.elasticsearch.infrastructure.utils.JacksonUtils;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetadata;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ESIndexManageServiceImpl implements ESIndexManageService {

    private static final Logger log = LoggerFactory.getLogger(ESIndexManageServiceImpl.class);


    @Autowired
    @Qualifier(ESBeanConfig.ES_CLIENT_BEAN_NAME)
    private RestHighLevelClient restHighLevelClient;

    @Override
    public ESIndexSearchRes searchIndex(ESIndexSearchReq req) {
        try {
            ESIndexSearchRes res = new ESIndexSearchRes();
            GetAliasesRequest request = new GetAliasesRequest();
            GetAliasesResponse response = restHighLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
            String errMsg = ESConverterUtils.checkResponse(response);
            if (StringUtils.hasText(errMsg)) {
                res.setErrMeg(errMsg);
                return res;
            }
            Map<String, Set<AliasMetadata>> aliases = response.getAliases();
            List<String> result = aliases.keySet().stream()
                    .filter(key -> !key.trim().startsWith("."))
                    .collect(Collectors.toList());
            if (req != null && !CollectionUtils.isEmpty(req.getIndexNameList())) {
                Set<String> indexSet = new HashSet<>(req.getIndexNameList());
                result = result.stream()
                        .filter(indexSet::contains)
                        .collect(Collectors.toList());
            }

            res.setIndexNameList(result);
            return res;
        } catch (Exception e) {
            log.error("[ESIndexManageService] searchIndex | restHighLevelClient.indices().getAlias(), req={}",
                    JacksonUtils.toJson(req), e);
            ESIndexSearchRes res = new ESIndexSearchRes();
            res.setErrMeg(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "unknown error");
            return res;
        }
    }

    @Override
    public ESIndexPropertiesSearchRes searchIndexProperties(ESIndexPropertiesSearchReq req) {
        try {
            ESIndexPropertiesSearchRes res = new ESIndexPropertiesSearchRes();
            if (req == null || !StringUtils.hasText(req.getIndexName())) {
                res.setErrMeg("req is invalid");
                return res;
            }
            GetIndexRequest request = new GetIndexRequest(req.getIndexName());
            GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
            if (response == null || CollectionUtils.isEmpty(response.getMappings()) || CollectionUtils.isEmpty(response.getSettings())) {
                res.setErrMeg("es get index properties failed!");
                return res;
            }
            Map<String, MappingMetadata> mappings = response.getMappings();
            MappingMetadata mappingMetadata = mappings.get(req.getIndexName());
            if (mappingMetadata != null) {
                Map<String, Object> sourceMap = (Map<String, Object>) mappingMetadata.sourceAsMap().get(ESConstants.PROPERTIES_KEY);
                Map<String, ESIndexPropertiesDTO> propertiesMap = new HashMap<>(sourceMap.size() + 1);
                for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
                    String key = entry.getKey();
                    Map<String, Object> prop = (Map<String, Object>) entry.getValue();
                    String type = prop.getOrDefault(ESConstants.TYPE_KEY, "").toString();

                    ESIndexPropertiesDTO properties = new ESIndexPropertiesDTO();
                    properties.setType(type);
                    propertiesMap.put(key, properties);
                }
                res.setProperties(propertiesMap);
            } else {
                log.warn("[ESIndexManageService] searchIndex | restHighLevelClient.indices().get() mappings is empty");
            }

            Map<String, Settings> settings = response.getSettings();
            Settings setting = settings.get(req.getIndexName());
            if (setting != null) {
                String replicasNumberStr = setting.get(ESConstants.INDEX_PREFIX + ESConstants.REPLICAS_NUMBER_KEY, "-1");
                res.setReplicasNumber(Integer.parseInt(replicasNumberStr));

                String shardsNumberStr = setting.get(ESConstants.INDEX_PREFIX + ESConstants.SHARDS_NUMBER_KEY, "-1");
                res.setShardsNumber(Integer.parseInt(shardsNumberStr));

                String uuid = setting.get(ESConstants.INDEX_PREFIX + ESConstants.UUID_KEY, "");
                res.setUuid(uuid);
            } else {
                log.warn("[ESIndexManageService] searchIndex | restHighLevelClient.indices().get() setting is empty");
            }
            return res;
        } catch (Exception e) {
            log.error("[ESIndexManageService] searchIndex | restHighLevelClient.indices().get(), req={}",
                    JacksonUtils.toJson(req), e);
            ESIndexPropertiesSearchRes res = new ESIndexPropertiesSearchRes();
            res.setErrMeg(StringUtils.hasText(e.getMessage()) ? e.getMessage() : "unknown error");
            return res;
        }
    }
}
