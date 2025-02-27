package attendance.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @CsvSource(value = {
            "24,13,5,true", "27,10,5,true",
            "24,13,6,false", "27,10,6,false"
    })
    @ParameterizedTest
    void 출석_기록을_알려주면_출석_완료인지_알려준다(int day, int hour, int minute, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, 2, day);
        LocalTime localTime = LocalTime.of(hour, minute);
        Attendance attendance = new Attendance(LocalDateTime.of(localDate, localTime));
        AttendanceTime attendanceTime = new AttendanceTime(localTime);

        assertThat(AttendanceStatus.isAttendance(attendance, attendanceTime)).isEqualTo(expected);
    }

    @CsvSource(value = {
            "24,13,6,true", "27,10,6,true", "24,13,30,true", "27,10,30,true",
            "24,13,5,false", "27,10,5,false", "24,13,31,false", "27,10,31,false"
    })
    @ParameterizedTest
    void 출석_기록을_알려주면_지각인지_알려준다(int day, int hour, int minute, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, 2, day);
        LocalTime localTime = LocalTime.of(hour, minute);
        Attendance attendance = new Attendance(LocalDateTime.of(localDate, localTime));
        AttendanceTime attendanceTime = new AttendanceTime(localTime);

        assertThat(AttendanceStatus.isLate(attendance, attendanceTime)).isEqualTo(expected);
    }

    @CsvSource(value = {
            "24,13,31,true", "27,10,31,true", "24,23,0,true", "27,23,0,true",
            "24,13,30,false", "27,10,30,false"
    })
    @ParameterizedTest
    void 출석_기록을_알려주면_결석인지_알려준다(int day, int hour, int minute, boolean expected) {
        LocalDate localDate = LocalDate.of(2025, 2, day);
        LocalTime localTime = LocalTime.of(hour, minute);
        Attendance attendance = new Attendance(LocalDateTime.of(localDate, localTime));
        AttendanceTime attendanceTime = new AttendanceTime(localTime);

        assertThat(AttendanceStatus.isAbsent(attendance, attendanceTime)).isEqualTo(expected);
    }

    @Test
    void 결석_횟수와_지각_횟수를_알려주면_총_결석_횟수를_알려준다() {
        int absentCount = 1;
        int lateCount = 5;

        assertThat(AttendanceStatus.calculateTotalAbsentCount(absentCount, lateCount)).isEqualTo(2);
    }

}
