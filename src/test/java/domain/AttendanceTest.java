package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Nested
    @DisplayName("1.1 닉네임과 등교 시간을 받으면 오늘 날짜로 출석 기록을 생성할 수 있다.")
    class AttendanceCheckTest {
        private final LocalDate MON_DATE = LocalDate.of(2024, 12, 24);
        private final LocalDate TUE_DATE = LocalDate.of(2024, 12, 25);

        @Test
        @DisplayName("화요일은 10시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendance() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(10, 5);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, TUE_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("출석");
        }

        @Test
        @DisplayName("화요일은 10시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendance() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(10, 30);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, TUE_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("지각");
        }

        @Test
        @DisplayName("화요일은 10시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendance() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(10, 30, 1);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, TUE_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("결석");
        }

        @Test
        @DisplayName("월요일은 13시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(13, 5);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, MON_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("출석");
        }

        @Test
        @DisplayName("월요일은 13시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(13, 30);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, MON_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("지각");
        }

        @Test
        @DisplayName("월요일은 13시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            String nickname = "노랑";
            LocalTime time = LocalTime.of(13, 30, 1);
            // when
            String attendanceRecord = attendance.checkAttendance(nickname, time, MON_DATE);
            // then
            assertThat(attendanceRecord).isEqualTo("결석");
        }
    }
}
