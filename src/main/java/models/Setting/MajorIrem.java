package models.Setting;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MajorIrem {

    @Data
    @AllArgsConstructor
    public class MajorItem {

        private String majorName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MajorItem)) return false;
            MajorItem that = (MajorItem) o;
            return majorName.equals(that.majorName);
        }

        @Override
        public String toString() {
            return String.format(
                    "MajorItem[majorName=%s]",
                    majorName
            );
        }
    }

}
