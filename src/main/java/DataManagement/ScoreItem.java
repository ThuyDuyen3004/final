package DataManagement;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.Objects;


    @Data
    @AllArgsConstructor
    public class ScoreItem {

        private String stt;
        private String lop;
        private String hoLot;
        private String ten;
        private String ngaySinh;
        private Map<String, String> scores;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScoreItem)) return false;
            ScoreItem that = (ScoreItem) o;
            return Objects.equals(stt, that.stt) &&
                    Objects.equals(lop, that.lop) &&
                    Objects.equals(hoLot, that.hoLot) &&
                    Objects.equals(ten, that.ten) &&
                    Objects.equals(ngaySinh, that.ngaySinh) &&
                    Objects.equals(scores, that.scores);
        }

        @Override
        public String toString() {
            return String.format(
                    "ScoreItem[stt=%s, lop=%s, hoLot=%s, ten=%s, ngaySinh=%s, scores=%s]",
                    stt, lop, hoLot, ten, ngaySinh, scores
            );
        }
    }


