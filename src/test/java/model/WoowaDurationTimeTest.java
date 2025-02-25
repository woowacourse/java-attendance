package model;

import attendance.model.AttendanceDate;
import attendance.model.WoowaDurationTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

class WoowaDurationTimeTest {

    @Test
    void 월요일에_대한_출석_정보를_조회한다() {
        AttendanceDate mondayAttendance = new AttendanceDate(LocalDate.of(2025, 2, 17));
        assertThat(WoowaDurationTime.from(mondayAttendance)).isEqualTo(WoowaDurationTime.MONDAY);
    }

    @Test
    void 요일별_시작_시간을_확인한다() {
        assertThat(WoowaDurationTime.MONDAY.getStartTime()).isEqualTo(LocalTime.of(13, 0));
        assertThat(WoowaDurationTime.TUESDAY.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(WoowaDurationTime.WEDNESDAY.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(WoowaDurationTime.THURSDAY.getStartTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(WoowaDurationTime.FRIDAY.getStartTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 특정_날짜가_운영일인지_확인한다() {
        LocalDate weekday = LocalDate.of(2025, 2, 17); // 평일
        LocalDate weekend = LocalDate.of(2025, 2, 15); // 토요일
        LocalDate holiday = LocalDate.of(2024, 12, 25); // 공휴일
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(WoowaDurationTime.isDurationDate(weekday)).isTrue();
        softly.assertThat(WoowaDurationTime.isDurationDate(weekend)).isFalse();
        softly.assertThat(WoowaDurationTime.isDurationDate(holiday)).isFalse();
        softly.assertAll();

    }
}
