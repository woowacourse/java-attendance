package model;

import attendance.model.Attendance;
import attendance.model.AttendanceDate;
import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceDateTimeTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-03, 10:00, ATTEND",
            "2024-12-03, 10:06, LATE",
            "2024-12-03, 10:31, ABSENCE"
    })
    void 출석_일시로_부터_출결_결과를_조회한다(String date, String time, Attendance expectedAttendance) {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(LocalDate.parse(date)),
                new AttendanceTime(LocalTime.parse(time))
        );
        assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(expectedAttendance);
    }

    @Test
    void 출석_일시를_수정한다() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                new AttendanceTime(LocalTime.of(10, 31))
        );
        AttendanceTime modifyTime = new AttendanceTime(LocalTime.of(10, 5));
        attendanceDateTime.modifyAttendanceTime(modifyTime);
        assertThat(attendanceDateTime.getAttendanceTime()).isEqualTo(modifyTime);
    }

    @Test
    void 출석_시간을_조회한다() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 31));
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 3)),
                attendanceTime
        );
        assertThat(attendanceDateTime.getAttendanceTime()).isEqualTo(attendanceTime);
    }

    @Test
    void 출석일시를_깊은복사한다() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(LocalDate.of(2024, 12, 10)),
                new AttendanceTime(LocalTime.of(10, 9))
        );
        AttendanceDateTime cloned = attendanceDateTime.copy();
        assertThat(attendanceDateTime).isNotSameAs(cloned);
        assertThat(attendanceDateTime.getAttendanceDate().localDate()).isEqualTo(LocalDate.of(2024, 12, 10));
        assertThat(attendanceDateTime.getAttendanceTime().localTime()).isEqualTo(LocalTime.of(10, 9));
    }
}
