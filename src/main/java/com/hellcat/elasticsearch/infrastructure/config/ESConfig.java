package com.hellcat.elasticsearch.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class ESConfig {

    @Value("${elasticsearch.ip}")
    protected String ip;

    @Value("${elasticsearch.port}")
    protected Integer port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ESConfig esConfig = (ESConfig) o;
        return Objects.equals(ip, esConfig.ip) && Objects.equals(port, esConfig.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
