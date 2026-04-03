package models.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClassItem {

    private String className;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassItem)) return false;
        ClassItem that = (ClassItem) o;
        return className.equals(that.className);
    }

    @Override
    public String toString() {
        return String.format(
                "ClassItem[className=%s]",
                className
        );
    }
}
