package com.hellcat.elasticsearch.infrastructure.domain.es;

import java.util.Objects;
import java.util.StringJoiner;

public class ESBaseRes {

    public ESBaseRes() {
        this(null);
    }

    public ESBaseRes(String errMeg) {
        this.errMeg = errMeg;
    }

    public boolean success() {
        return this.errMeg == null || errMeg.isEmpty();
    }

    protected String errMeg;

    public String getErrMeg() {
        return errMeg;
    }

    public void setErrMeg(String errMeg) {
        this.errMeg = errMeg;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESBaseRes esBaseRes = (ESBaseRes) o;
        return Objects.equals(errMeg, esBaseRes.errMeg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errMeg);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESBaseRes.class.getSimpleName() + "[", "]")
                .add("errMeg='" + errMeg + "'")
                .toString();
    }
}
