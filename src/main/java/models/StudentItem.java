package models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class StudentItem {

    private String mssv;
    private String fullName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentItem)) return false;
        StudentItem that = (StudentItem) o;
        return Objects.equals(mssv, that.mssv) &&
                Objects.equals(fullName, that.fullName);
    }

    @Override
    public String toString() {
        return String.format(
                "StudentItem[mssv=%s, fullName=%s]",
                mssv,
                fullName
        );
    }
}