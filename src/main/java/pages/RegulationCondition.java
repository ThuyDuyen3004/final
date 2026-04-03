package models.Setting;

import java.util.Objects;

public class RegulationCondition {

    private String condition;
    private String operator;
    private String value;

    public RegulationCondition(String condition, String operator, String value) {
        this.condition = normalize(condition);
        this.operator = normalize(operator);
        this.value = normalize(value);
    }

    private String normalize(String s) {
        return s == null ? null : s.trim().toLowerCase();
    }

    public String getCondition() {
        return condition;
    }

    public String getOperator() {
        return operator;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegulationCondition)) return false;
        RegulationCondition that = (RegulationCondition) o;

        return Objects.equals(condition, that.condition)
                && Objects.equals(operator, that.operator)
                && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, operator, value);
    }
}