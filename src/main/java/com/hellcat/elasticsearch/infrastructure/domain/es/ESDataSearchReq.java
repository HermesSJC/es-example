package com.hellcat.elasticsearch.infrastructure.domain.es;

import com.hellcat.elasticsearch.infrastructure.domain.condition.SearchExample;
import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;
import org.springframework.util.StringUtils;

public class ESDataSearchReq<T extends ESBasePO> {

    private String indexName;

    private SearchExample searchExample;

    private Class<T> clazz;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
        if (this.searchExample != null) {
            this.searchExample.setIndexName(this.indexName);
        }
    }

    public SearchExample getSearchExample() {
        return searchExample;
    }

    public void setSearchExample(SearchExample searchExample) {
        this.searchExample = searchExample;
        if (StringUtils.hasText(this.indexName)) {
            this.searchExample.setIndexName(this.indexName);
        }
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
