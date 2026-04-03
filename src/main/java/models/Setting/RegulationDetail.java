package models.Setting;

import lombok.Data;

@Data
public class RegulationDetail {

    private String totalCredits;
    private String requiredCredits;
    private String electiveCredits;
    private String gpa;
    private String course;
    private String major;
}