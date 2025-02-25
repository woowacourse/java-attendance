package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @Test
    @DisplayName("날짜와 시간을 입력받아 출석 상태를 반환 한다.")
    void Judge_Attendance_Status_By_Date_And_Time() {
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(
                LocalDate.of(2024, 12, 2), LocalTime.of(10, 1)))
                .isEqualTo("출석");

        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(
                LocalDate.of(2024, 12, 2), LocalTime.of(10, 6)))
                .isEqualTo("지각");
        assertThat(AttendanceStatus.judgeAttendanceStatusByDateAndTime(
                LocalDate.of(2024, 12, 2), LocalTime.of(10, 33)))
                .isEqualTo("결석");
    }
}