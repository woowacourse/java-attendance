package model;

import static model.AttendanceTime.DEFAULT_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryByDateTest {

    @DisplayName("해당 날짜의 기본 AttendanceHistoryByDate를 얻는다")
    @Test
    void getDefaultTest() {
        LocalDate date = LocalDate.of(2024, 12, 3);

        assertThat(AttendanceHistoryByDate.getDefault(date)).isEqualTo(
                new AttendanceHistoryByDate(date, DEFAULT_TIME, AttendanceType.ABSENT));
    }
}
