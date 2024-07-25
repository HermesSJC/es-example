package com.hellcat.elasticsearch.infrastructure.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESBeanConfig {

    public static final String ES_CLIENT_BEAN_NAME = "restHighLevelClient";

    @Autowired
    private ESConfig esConfig;

    @Bean(ESBeanConfig.ES_CLIENT_BEAN_NAME)
    public RestHighLevelClient restHighLevelClient() {
        HttpHost httpHost = new HttpHost(esConfig.getIp(), esConfig.getPort());
        RestClientBuilder restClientBuilder = RestClient.builder(httpHost);
        return new RestHighLevelClient(restClientBuilder);
    }
}
