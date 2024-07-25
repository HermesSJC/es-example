package com.hellcat.elasticsearch.infrastructure.domain.es;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ESIndexSearchReq {

    protected List<String> indexNameList;

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
        ESIndexSearchReq that = (ESIndexSearchReq) o;
        return Objects.equals(indexNameList, that.indexNameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexNameList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESIndexSearchReq.class.getSimpleName() + "[", "]")
                .add("indexNameList=" + indexNameList)
                .toString();
    }
}
