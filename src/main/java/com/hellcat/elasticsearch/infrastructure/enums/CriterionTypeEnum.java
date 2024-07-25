package com.hellcat.elasticsearch.infrastructure.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum CriterionTypeEnum implements IEnum {

    EQUAL_TO(1, 1, "equalTo"),

    NOT_EQUAL_TO(2, 2, "notEqualTo"),

    IN(3, 11, "in"),

    GREATER_THAN(4, 21, "greaterThan"),

    GREATER_THAN_EQUAL_TO(5, 22, "greaterThanAndEqualTo"),

    LESS_THAN(6, 31, "lessThan"),

    LESS_THAN_EQUAL_TO(7, 32, "lesserThanAndEqualTo"),

    UNKNOWN(-1, -1, "unknown"),
    ;


    CriterionTypeEnum(int index, int code, String msg) {
        this.index = index;
        this.code = code;
        this.msg = msg;
    }


    private final int index;

    private final int code;

    private final String msg;

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private static final Map<Integer, CriterionTypeEnum> CODE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(IEnum::getCode, Function.identity()));

    public static CriterionTypeEnum findByCode(int code) {
        return CODE_MAP.getOrDefault(code, UNKNOWN);
    }
}
