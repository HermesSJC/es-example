package com.hellcat.elasticsearch.infrastructure.domain.es;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ESIndexSearchRes extends ESBaseRes {

    public ESIndexSearchRes() {
        super(null);
    }

    private List<String> indexNameList;

    public List<String> getIndexNameList() {
        return indexNameList;
    }

    public void setIndexNameList(List<String> indexNameList) {
        this.indexNameList = indexNameList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESIndexSearchRes that = (ESIndexSearchRes) o;
        return Objects.equals(indexNameList, that.indexNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNameList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESIndexSearchRes.class.getSimpleName() + "[", "]")
                .add("indexList=" + indexNameList)
                .toString();
    }
}
