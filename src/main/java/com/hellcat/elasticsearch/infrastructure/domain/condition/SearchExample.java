package com.hellcat.elasticsearch.infrastructure.domain.condition;


import com.hellcat.elasticsearch.infrastructure.enums.CriterionTypeEnum;
import com.hellcat.elasticsearch.infrastructure.enums.LogicTypeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class SearchExample {


    public SearchExample() {
        this(StringUtils.EMPTY);
    }

    public SearchExample(String indexName) {
        this(indexName, -1, -1);
    }

    public SearchExample(String indexName, Integer offset, Integer limit) {
        this(indexName, offset, limit, StringUtils.EMPTY);
    }

    public SearchExample(String indexName, Integer offset, Integer limit, String orderBy) {
        this.indexName = indexName;
        this.offset = offset;
        this.limit = limit;
        this.orderBy = orderBy;
        this.conditionList = new ArrayList<>();
    }

    private String orderBy;

    private Integer offset;

    private Integer limit;

    private String indexName;

    private final List<Condition> conditionList;

    public Condition createCondition() {
        if (!conditionList.isEmpty()) {
            throw new RuntimeException("condition has been created!");
        }
        Condition condition = new Condition(LogicTypeEnum.FIRST.getCode());
        conditionList.add(condition);
        return condition;
    }

    public Condition and() {
        if (CollectionUtils.isEmpty(conditionList)) {
            throw new RuntimeException("you must use createCondition instead of and");
        }

        Condition condition = new Condition(LogicTypeEnum.AND.getCode());
        conditionList.add(condition);
        return condition;
    }

    public Condition or() {
        if (CollectionUtils.isEmpty(conditionList)) {
            throw new RuntimeException("you must use createCondition instead of or");
        }

        Condition condition = new Condition(LogicTypeEnum.OR.getCode());
        conditionList.add(condition);
        return condition;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getIndexName() {
        return indexName;
    }


    public List<Condition> getConditionList() {
        return conditionList;
    }

    public boolean validLimit() {
        return this.limit != null && this.limit > 0;
    }

    public boolean validOffset() {
        return this.offset != null && this.offset >= 0;
    }

    public static class Condition {

        public Condition() {
            this(LogicTypeEnum.AND.getCode());
        }

        public Condition(Integer logicType) {
            this.logicType = logicType;
            this.criteriaList = new ArrayList<>();
        }

        private Integer logicType;

        private final List<Criterion> criteriaList;

        public void setLogicType(Integer logicType) {
            this.logicType = logicType;
        }

        public Integer getLogicType() {
            return logicType;
        }

        public List<Criterion> getCriteriaList() {
            return criteriaList;
        }

        public Condition andEqualTo(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.EQUAL_TO.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andNotEqualTo(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.NOT_EQUAL_TO.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andIn(String propertiesName, List<?> value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.IN.getCode(), propertiesName);
            criterion.setObjectList(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andIn(String propertiesName, Object[] value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.IN.getCode(), propertiesName);
            criterion.setObjectList(Arrays.stream(value).collect(Collectors.toList()));
            criteriaList.add(criterion);
            return this;
        }

        public Condition andGreaterThan(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.GREATER_THAN.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andGreaterThanAndEqualTo(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.GREATER_THAN_EQUAL_TO.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andLesserThan(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.LESS_THAN.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public Condition andLesserThanAndEqualTo(String propertiesName, Object value) {
            Criterion criterion = new Criterion(CriterionTypeEnum.LESS_THAN_EQUAL_TO.getCode(), propertiesName);
            criterion.setObject(value);
            criteriaList.add(criterion);
            return this;
        }

        public static class Criterion {

            public Criterion() {
                this(0, StringUtils.EMPTY);
            }

            public Criterion(Integer type, String propertiesName) {
                this.type = type;
                this.propertiesName = propertiesName;
            }

            private String propertiesName;

            private List<?> objectList;

            private Object object;

            private Integer type;

            public String getPropertiesName() {
                return propertiesName;
            }

            public void setPropertiesName(String propertiesName) {
                this.propertiesName = propertiesName;
            }

            public List<?> getObjectList() {
                return objectList;
            }

            public void setObjectList(List<?> objectList) {
                this.objectList = objectList;
            }

            public Object getObject() {
                return object;
            }

            public void setObject(Object object) {
                this.object = object;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }
        }
    }
}
