package com.hellcat.elasticsearch.infrastructure.domain.es;

import com.hellcat.elasticsearch.infrastructure.domain.po.ESBasePO;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class ESDataSearchRes<T extends ESBasePO> extends ESBaseRes {

    public ESDataSearchRes() {
        this(0, Collections.emptyList());
    }

    public ESDataSearchRes(Integer total, List<T> dataList) {
        super(null);
        this.total = total;
        this.dataList = dataList;
    }

    private Integer total;

    private List<T> dataList;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ESDataSearchRes<?> that = (ESDataSearchRes<?>) o;
        return Objects.equals(total, that.total) && Objects.equals(dataList, that.dataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), total, dataList);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESDataSearchRes.class.getSimpleName() + "[", "]")
                .add("total=" + total)
                .add("dataList=" + dataList)
                .add("errMeg='" + errMeg + "'")
                .toString();
    }
}
