package com.hellcat.elasticsearch.infrastructure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JacksonUtils {

    private JacksonUtils() {

    }

    private static final Logger log = LoggerFactory.getLogger(JacksonUtils.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String EMPTY_OBJECT = "{}";

    public static final String EMPTY_LIST = "[]";

    public static final String EMPTY_MAP = EMPTY_OBJECT;

    public static final String EMPTY_ARRAYS = EMPTY_LIST;

    static {
        //序列化时，跳过null属性
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //序列化时，遇到空bean（无属性）时不会失败
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //反序列化时，遇到未知属性（在bean上找不到对应属性）时不会失败
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //反序列化时，将空数组([])当做null来处理（以便把空数组反序列化到对象属性上——对php生成的json的map属性很有用）
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        //不通过fields来探测（仅通过标准getter探测）
        mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, false);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            log.warn("[JacksonUtils.parse] invalid param! json is empty or clazz is null!");
            return null;
        }
        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.warn("[JacksonUtils.parse] mapper.readValue failed! json={}, clazz={}",
                    json, clazz.getSimpleName(), e);
            return null;
        }
    }

    public static <T> T parse(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json) || typeReference == null) {
            log.warn("[JacksonUtils.parse] invalid param! json is empty or typeReference is null!");
            return null;
        }
        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.warn("[JacksonUtils.parse] mapper.readValue failed! json={}, typeReference={}",
                    json, typeReference.getType(), e);
            return null;
        }
    }

    public static <T> List<T> parseList(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            log.warn("[JacksonUtils.parseList] invalid param! json is empty or clazz is null!");
            return Collections.emptyList();
        }

        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(json, typeFactory.constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            log.warn("[JacksonUtils.parseList] mapper.readValue failed! json={}, clazz={}",
                    json, clazz.getSimpleName(), e);
            return Collections.emptyList();
        }
    }

    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClazz, Class<V> vClazz) {
        if (StringUtils.isEmpty(json) || kClazz == null || vClazz == null) {
            log.warn("[JacksonUtils.parseMap] invalid param! json is empty or kClazz or vClazz is null!");
            return Collections.emptyMap();
        }

        try {
            TypeFactory typeFactory = mapper.getTypeFactory();
            return mapper.readValue(json, typeFactory.constructMapType(Map.class, kClazz, vClazz));
        } catch (Exception e) {
            log.warn("[JacksonUtils.parseMap] mapper.readValue failed! json={}, kClazz={}, vClazz={}",
                    json, kClazz.getSimpleName(), vClazz.getSimpleName(), e);
            return Collections.emptyMap();
        }
    }

    public static <T> T convert(Object src, Class<T> clazz) {
        if (ObjectUtils.isEmpty(src) || clazz == null) {
            log.warn("[JacksonUtils.convert] invalid param! json is empty or clazz is null!");
            return null;
        }
        try {
            return mapper.convertValue(src, clazz);
        } catch (Exception e) {
            log.warn("[JacksonUtils.convert] mapper.convertValue failed! json={}, clazz={}",
                    src, clazz.getSimpleName(), e);
            return null;
        }
    }

    public static <T> T convert(Object src, TypeReference<T> clazz) {
        if (ObjectUtils.isEmpty(src) || clazz == null) {
            log.warn("[JacksonUtils.parse] invalid param! json is empty or clazz is null!");
            return null;
        }
        try {
            return mapper.convertValue(src, clazz);
        } catch (Exception e) {
            log.warn("[JacksonUtils.convert] mapper.convertValue failed! json={}, clazz={}",
                    src, clazz.getType().getTypeName(), e);
            return null;
        }
    }

    public static String toJson(Object bean) {
        if (ObjectUtils.isEmpty(bean)) {
            log.warn("[JacksonUtils.toJson] invalid param! bean is null!");
            return StringUtils.EMPTY;
        }
        try {
            return mapper.writeValueAsString(bean);
        } catch (Exception e) {
            log.warn("[JacksonUtils.toJson] mapper.writeValueAsString failed! bean={}", bean, e);
            return StringUtils.EMPTY;
        }
    }
}
