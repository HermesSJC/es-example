package com.hellcat.elasticsearch.infrastructure.enums;

public enum LogicTypeEnum implements IEnum {

    AND(1, 1, "AND"),

    OR(2, 2, "OR"),

    FIRST(3, 3, "AND"),

    UNKNOWN(-1, -1, "");

    ;

    LogicTypeEnum(int index, int value, String msg) {
        this.index = index;
        this.code = value;
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
}
