package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class SubjectItem {

    private String subjectCode;
    private String subjectName;
    private String credit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectItem)) return false;
        SubjectItem that = (SubjectItem) o;
        return Objects.equals(subjectCode, that.subjectCode) &&
                Objects.equals(subjectName, that.subjectName) &&
                Objects.equals(credit, that.credit);
    }

    @Override
    public String toString() {
        return String.format(
                "SubjectItem[subjectCode=%s, subjectName=%s, credit=%s]",
                subjectCode,
                subjectName,
                credit
        );
    }
}