package models.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class TrainingProgramItem {

    private String major;
    private String cohort;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingProgramItem)) return false;
        TrainingProgramItem that = (TrainingProgramItem) o;
        return Objects.equals(normalize(major), normalize(that.major)) &&
                Objects.equals(normalize(cohort), normalize(that.cohort));
    }

    @Override
    public int hashCode() {
        return Objects.hash(normalize(major), normalize(cohort));
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    @Override
    public String toString() {
        return String.format(
                "TrainingProgramItem[major=%s, cohort=%s]",
                major,
                cohort
        );
    }
}