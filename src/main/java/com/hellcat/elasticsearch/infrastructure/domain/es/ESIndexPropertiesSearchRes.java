package com.hellcat.elasticsearch.infrastructure.domain.es;

import com.hellcat.elasticsearch.infrastructure.domain.entity.ESIndexPropertiesDTO;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public class ESIndexPropertiesSearchRes extends ESBaseRes {

    public ESIndexPropertiesSearchRes() {
        super(null);
    }

    private Integer shardsNumber;

    private Integer replicasNumber;

    private Map<String, ESIndexPropertiesDTO> properties;

    private String uuid;

    public Map<String, ESIndexPropertiesDTO> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ESIndexPropertiesDTO> properties) {
        this.properties = properties;
    }

    public Integer getShardsNumber() {
        return shardsNumber;
    }

    public void setShardsNumber(Integer shardsNumber) {
        this.shardsNumber = shardsNumber;
    }

    public Integer getReplicasNumber() {
        return replicasNumber;
    }

    public void setReplicasNumber(Integer replicasNumber) {
        this.replicasNumber = replicasNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ESIndexPropertiesSearchRes that = (ESIndexPropertiesSearchRes) o;
        return Objects.equals(shardsNumber, that.shardsNumber) && Objects.equals(replicasNumber, that.replicasNumber) && Objects.equals(properties, that.properties) && Objects.equals(uuid, that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shardsNumber, replicasNumber, properties, uuid);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ESIndexPropertiesSearchRes.class.getSimpleName() + "[", "]")
                .add("shardsNumber=" + shardsNumber)
                .add("replicasNumber=" + replicasNumber)
                .add("properties=" + properties)
                .add("uuid='" + uuid + "'")
                .add("errMeg='" + errMeg + "'")
                .toString();
    }
}
