package com.hellcat.elasticsearch.application.service;

import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexPropertiesSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESIndexSearchRes;
import org.springframework.stereotype.Service;

@Service
public interface ESIndexManageService {

    ESIndexSearchRes searchIndex(ESIndexSearchReq request);


    ESIndexPropertiesSearchRes searchIndexProperties(ESIndexPropertiesSearchReq request);
}
