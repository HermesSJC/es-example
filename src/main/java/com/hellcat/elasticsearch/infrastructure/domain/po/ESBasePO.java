package com.hellcat.elasticsearch.infrastructure.domain.po;

import java.util.Objects;
import java.util.StringJoiner;

public abstract class ESBasePO {

    protected String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESBasePO esBasePO = (ESBasePO) o;
        return Objects.equals(_id, esBasePO._id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESBasePO.class.getSimpleName() + "[", "]")
                .add("_id='" + _id + "'")
                .toString();
    }
}
