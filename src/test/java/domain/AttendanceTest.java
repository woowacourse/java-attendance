package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private final LocalDate MONDAY_DATE = LocalDate.of(2025, 2, 24);
    private final LocalDate TUESDAY_DATE = LocalDate.of(2025, 2, 25);

    @Nested
    @DisplayName("1.1 닉네임과 등교 시간을 받으면 오늘 날짜로 출석 기록을 생성할 수 있다.")
    class AttendanceCheckTest {
        @Test
        @DisplayName("화요일은 10시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendance() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(10, 5);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("출석");
        }

        @Test
        @DisplayName("화요일은 10시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendance() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(10, 30);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("지각");
        }

        @Test
        @DisplayName("화요일은 10시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendance() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(10, 30, 1);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("결석");
        }

        @Test
        @DisplayName("월요일은 13시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(13, 5);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("출석");
        }

        @Test
        @DisplayName("월요일은 13시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(13, 30);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("지각");
        }

        @Test
        @DisplayName("월요일은 13시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendanceOnMonday() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalTime time = LocalTime.of(13, 30, 1);
            // when
            String attendanceRecord = attendance.checkAttendance(crew, LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceRecord).isEqualTo("결석");
        }
    }

    @Test
    @DisplayName("1.2 이미 출석한 경우 예외를 발생시킬 수 있다.")
    void testValidateDuplicateAttendance() {
        // given
        Attendance attendance = new Attendance();
        Crew crew = new Crew("노랑");
        LocalDateTime dateTime = TUESDAY_DATE.atTime(10, 0);
        attendance.checkAttendance(crew, dateTime);
        // when & then
        assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
    }
}
