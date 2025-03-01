package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {
    @DisplayName("domain.AttendanceDateTime 생성 테스트")
    @Test
    void dateTimeTest1() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 25, 14, 39);
        Assertions.assertThat(attendanceDateTime).isInstanceOf(AttendanceDateTime.class);
    }

    @DisplayName("domain.AttendanceDateTime 휴일 테스트")
    @Test
    void dateTimeTest2() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 25, 0, 0);
        Assertions.assertThat(attendanceDateTime.isRestDay()).isTrue();
    }

    @DisplayName("domain.AttendanceDateTime 휴일 테스트")
    @Test
    void dateTimeTest3() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 0, 0);
        Assertions.assertThat(attendanceDateTime.isRestDay()).isFalse();
    }

    @DisplayName("domain.AttendanceDateTime getAttendanceType() 테스트")
    @Test
    void getAttendanceTypeTest1() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10,0);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ATTENDANCE);
    }

    @Test
    void getAttendanceTypeTest2() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10,30);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.LATE);
    }

    @Test
    void getAttendanceTypeTest3() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10,31);
        Assertions.assertThat(attendanceDateTime.getAttendanceType()).isEqualTo(AttendanceType.ABSENCE);
    }

    @DisplayName("isSchoolTime() 테스트")
    @Test
    void isSchoolTimeTest1() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10,31);
        Assertions.assertThat(attendanceDateTime.isSchoolTime()).isTrue();
    }

    @Test
    void isSchoolTimeTest2() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 7,59);
        Assertions.assertThat(attendanceDateTime.isSchoolTime()).isFalse();
    }

    @Test
    void isSchoolTimeTest3() {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 23,1);
        Assertions.assertThat(attendanceDateTime.isSchoolTime()).isFalse();
    }
}

