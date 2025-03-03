package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTimeTest {

    @DisplayName("AttendanceDateTime 생성 - 정상 생성 확인")
    @Test
    void dateTimeTest1() {
        // given
        int year = 2024, month = 12, day = 25, hour = 14, minute = 39;

        // when
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(year, month, day, hour, minute);

        // then
        Assertions.assertThat(attendanceDateTime).isInstanceOf(AttendanceDateTime.class);
    }

    @DisplayName("AttendanceDateTime 휴일 여부 - 크리스마스(12/25) 휴일 확인")
    @Test
    void dateTimeTest2() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 25, 0, 0);

        // when
        boolean isRestDay = attendanceDateTime.isRestDay();

        // then
        Assertions.assertThat(isRestDay).isTrue();
    }

    @DisplayName("AttendanceDateTime 휴일 여부 - 평일(12/26) 휴일 아님")
    @Test
    void dateTimeTest3() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 0, 0);

        // when
        boolean isRestDay = attendanceDateTime.isRestDay();

        // then
        Assertions.assertThat(isRestDay).isFalse();
    }

    @DisplayName("getAttendanceType() - 정상 출석")
    @Test
    void getAttendanceTypeTest1() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10, 0);

        // when
        AttendanceType actual = attendanceDateTime.getAttendanceType();

        // then
        Assertions.assertThat(actual).isEqualTo(AttendanceType.ATTENDANCE);
    }

    @DisplayName("getAttendanceType() - 지각")
    @Test
    void getAttendanceTypeTest2() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10, 30);

        // when
        AttendanceType actual = attendanceDateTime.getAttendanceType();

        // then
        Assertions.assertThat(actual).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("getAttendanceType() - 결석")
    @Test
    void getAttendanceTypeTest3() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10, 31);

        // when
        AttendanceType actual = attendanceDateTime.getAttendanceType();

        // then
        Assertions.assertThat(actual).isEqualTo(AttendanceType.ABSENCE);
    }

    @DisplayName("isSchoolTime() - 수업 시간 내 (True)")
    @Test
    void isSchoolTimeTest1() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 10, 31);

        // when
        boolean isSchoolTime = attendanceDateTime.isSchoolTime();

        // then
        Assertions.assertThat(isSchoolTime).isTrue();
    }

    @DisplayName("isSchoolTime() - 수업 시작 전 (False)")
    @Test
    void isSchoolTimeTest2() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 7, 59);

        // when
        boolean isSchoolTime = attendanceDateTime.isSchoolTime();

        // then
        Assertions.assertThat(isSchoolTime).isFalse();
    }

    @DisplayName("isSchoolTime() - 수업 종료 후 (False)")
    @Test
    void isSchoolTimeTest3() {
        // given
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2024, 12, 26, 23, 1);

        // when
        boolean isSchoolTime = attendanceDateTime.isSchoolTime();

        // then
        Assertions.assertThat(isSchoolTime).isFalse();
    }
}
