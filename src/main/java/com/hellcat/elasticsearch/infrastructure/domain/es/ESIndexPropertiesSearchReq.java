package com.hellcat.elasticsearch.infrastructure.domain.es;

import java.util.Objects;
import java.util.StringJoiner;

public class ESIndexPropertiesSearchReq {

    protected String indexName;

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESIndexPropertiesSearchReq that = (ESIndexPropertiesSearchReq) o;
        return Objects.equals(indexName, that.indexName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexName);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESIndexPropertiesSearchReq.class.getSimpleName() + "[", "]")
                .add("indexName='" + indexName + "'")
                .toString();
    }
}
