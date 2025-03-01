package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @Test
    @DisplayName("출석 시간을 수정한다.")
    void test1() {
        // given
        String rawCheckInDateTime = "2024-12-12 10:10";
        Attendance attendance = Attendance.of(rawCheckInDateTime);
        LocalTime updateTime = LocalTime.of(10, 0);

        // when
        attendance.update(updateTime);

        // then
        assertThat(attendance.getCheckInDate()).isEqualTo(LocalDate.of(2024, 12, 12));
        assertThat(attendance.getCheckInTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(attendance.getAttendanceType()).isEqualTo(AttendanceType.SUCCESS);
    }
}
