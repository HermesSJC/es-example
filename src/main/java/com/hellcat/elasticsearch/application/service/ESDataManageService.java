package com.hellcat.elasticsearch.application.service;

import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchReq;
import com.hellcat.elasticsearch.infrastructure.domain.es.ESDataSearchRes;
import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;

public interface ESDataManageService {

    <T extends ESBasePO> ESDataSearchRes<T> search(ESDataSearchReq<T> req);
}
