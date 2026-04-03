package models.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class CertificateItem {

    private String studentName;
    private boolean military;
    private boolean physical;
    private boolean language;
    private boolean it;

    // 👉 chuẩn hóa name tránh fail do space
    public String getNormalizedName() {
        return studentName == null ? "" : studentName.trim().replaceAll("\\s+", " ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificateItem)) return false;
        CertificateItem that = (CertificateItem) o;

        return military == that.military &&
                physical == that.physical &&
                language == that.language &&
                it == that.it &&
                Objects.equals(getNormalizedName(), that.getNormalizedName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getNormalizedName(),
                military,
                physical,
                language,
                it
        );
    }

    @Override
    public String toString() {
        return String.format(
                "CertificateItem[name=%s | QS=%s | TD=%s | NN=%s | IT=%s]",
                getNormalizedName(),
                military,
                physical,
                language,
                it
        );
    }
}