package models.Setting;

import java.util.Objects;

public class RegulationItem {

    private String name;
    private String course;
    private String major;

    public RegulationItem(String name, String course, String major) {
        this.name = normalize(name);
        this.course = normalize(course);
        this.major = normalize(major);
    }

    private String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public String getMajor() {
        return major;
    }

    private String extractCourseNumber(String course) {
        return course == null ? null : course.replaceAll("[^0-9]", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegulationItem)) return false;

        RegulationItem that = (RegulationItem) o;

        return Objects.equals(name, that.name)
                && Objects.equals(extractCourseNumber(course), extractCourseNumber(that.course))
                && Objects.equals(major, that.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, extractCourseNumber(course), major);
    }
}