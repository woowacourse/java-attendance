package domain;

import domain.attendance.AttendanceType;
import domain.date.AttendanceDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceDateTimeTest {

    @Test
    void getAttendanceTypeTest1() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, 13, 0);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.PRESENT);
    }

    @Test
    void getAttendanceTypeTest2() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, 13, 6);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.LATE);
    }

    @Test
    void getAttendanceTypeTest3() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, 13, 31);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }

    @Test
    void getAttendanceTypeTest4() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(3, 13, 0);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }
}
