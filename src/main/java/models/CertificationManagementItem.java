package models;

import java.util.Objects;

public class CertificationManagementItem {

    private String certificationName;
    private String course;

    public CertificationManagementItem(String certificationName, String course) {
        this.certificationName = normalize(certificationName);
        this.course = normalize(course);
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    public String getCertificationName() {
        return certificationName;
    }

    public String getCourse() {
        return course;
    }

    private String extractCourseNumber(String course) {
        return course == null ? null : course.replaceAll("[^0-9]", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificationManagementItem)) return false;

        CertificationManagementItem that = (CertificationManagementItem) o;

        return Objects.equals(certificationName, that.certificationName)
                && Objects.equals(
                extractCourseNumber(course),
                extractCourseNumber(that.course)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                certificationName,
                extractCourseNumber(course)
        );
    }
}