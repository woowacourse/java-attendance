package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeTest {
    @Test
    void getAttendanceTypeTest1() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(new AttendanceDate(2),
                new AttendanceTime(13, 0));
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.PRESENT);
    }

    @Test
    void getAttendanceTypeTest2() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(new AttendanceDate(2),
                new AttendanceTime(13, 6));
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.LATE);
    }

    @Test
    void getAttendanceTypeTest3() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(new AttendanceDate(2),
                new AttendanceTime(13, 31));
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }

    // TODO: 주말이거나 공휴일인 경우 예외를 던지기
    @Test
    void getAttendanceTypeTest4() {
        AttendanceDateTime attendanceDateTime = new AttendanceDateTime(new AttendanceDate(3),
                new AttendanceTime(13, 0));
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }
}