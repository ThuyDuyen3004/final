package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassItem {

    private String cohort;
    private String major;
    private String teacher;
    private String className;
}