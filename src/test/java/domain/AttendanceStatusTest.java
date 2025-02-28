package domain;

import static org.assertj.core.api.Assertions.*;
import static util.Constants.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @DisplayName("월요일의 출석 상태를 구할 수 있다.")
    @Test
    void test1() {
        Attendance attendance = generateAttendance(9, 13, 5);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.ATTEND);
    }

    @DisplayName("화~금요일의 출석 상태를 구할 수 있다.")
    @Test
    void test2() {
        Attendance attendance = generateAttendance(10, 10, 5);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.ATTEND);
    }

    @DisplayName("월요일의 지각 상태를 구할 수 있다.")
    @Test
    void test3() {
        Attendance attendance = generateAttendance(9, 13, 6);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.LATE);
    }

    @DisplayName("화~금요일의 지각 상태를 구할 수 있다.")
    @Test
    void test4() {
        Attendance attendance = generateAttendance(10, 10, 6);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.LATE);
    }

    @DisplayName("월요일의 결석 상태를 구할 수 있다.")
    @Test
    void test5() {
        Attendance attendance = generateAttendance(9, 13, 31);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.ABSENT);
    }

    @DisplayName("화~금요일의 결석 상태를 구할 수 있다.")
    @Test
    void test6() {
        Attendance attendance = generateAttendance(10, 10, 31);

        assertEqualAttendanceStatus(attendance, AttendanceStatus.ABSENT);
    }

    Attendance generateAttendance(int day, int hour, int minute) {
        return new Attendance(LocalDateTime.of(TODAY.getYear(), TODAY.getMonth(), day, hour, minute));
    }

    void assertEqualAttendanceStatus(Attendance attendance, AttendanceStatus expected) {
        assertThat(AttendanceStatus.from(attendance)).isEqualTo(expected);
    }
}
