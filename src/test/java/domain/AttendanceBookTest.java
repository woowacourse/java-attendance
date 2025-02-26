package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceBookTest {
    @DisplayName("기존에 존재하던 출석 기록을 수정할 수 있다.")
    @Test
    void test1() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 26);
        LocalTime time = LocalTime.of(10, 0);
        LocalTime modifiedTime = LocalTime.of(10, 10);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.register(date, time);

        // when
        attendanceBook.replace(date, modifiedTime);
        Attendance modifiedAttendance = attendanceBook.findAttendanceByDate(date);

        // then
        assertThat(modifiedAttendance.getTime().get()).isEqualTo(modifiedTime);
    }

    @DisplayName("출석 기록이 남지 않은 날짜의 출석 기록도 수정할 수 있다.")
    @Test
    void test2() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 26);
        LocalTime time = LocalTime.of(10, 10);
        AttendanceBook attendanceBook = new AttendanceBook();

        // when
        attendanceBook.replace(date, time);
        Attendance modifiedAttendance = attendanceBook.findAttendanceByDate(date);

        // then
        assertThat(modifiedAttendance.getTime().get()).isEqualTo(time);
    }
}
