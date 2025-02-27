package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @Nested
    @DisplayName("1.4 등교일이 아닐 경우(주말, 공휴일) 예외를 발생시킬 수 있다.")
    public class ValidateDayOffTest {
        @Test
        @DisplayName("토요일에 등교할 경우 예외를 발생시킬 수 있다.")
        void testSaturdayException() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalDateTime dateTime = LocalDateTime.of(2025, 3, 1, 10, 0);
            // when & then
            assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 3월 1일 토요일은 등교일이 아닙니다.");
        }

        @Test
        @DisplayName("일요일에 등교할 경우 예외를 발생시킬 수 있다.")
        void testSundayException() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalDateTime dateTime = LocalDateTime.of(2025, 3, 2, 10, 0);
            // when & then
            assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 3월 2일 일요일은 등교일이 아닙니다.");
        }

        @Test
        @DisplayName("법정공휴일에 등교할 경우 예외를 발생시킬 수 있다.")
        void validateHolidayException() {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalDateTime dateTime = LocalDateTime.of(2025, 3, 3, 10, 0);
            // when & then
            assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 3월 3일 월요일은 등교일이 아닙니다.");
        }

        @ParameterizedTest
        @DisplayName("방학에 등교할 경우 예외를 발생시킬 수 있다.")
        @CsvSource({"2025-04-07", "2025-04-14", "2025-08-25"})
        void validateVacationException(LocalDate date) {
            // given
            Attendance attendance = new Attendance();
            Crew crew = new Crew("노랑");
            LocalDateTime dateTime = date.atTime(10, 0);
            // when & then
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(date)));
        }
    }

    @ParameterizedTest
    @DisplayName("1.5 캠퍼스 운영 시간이 아닐 경우 예외를 발생시킬 수 있다.")
    @CsvSource({"07:59", "23:01"})
    void testValidateOperatingTime(LocalTime time) {
        // given
        Attendance attendance = new Attendance();
        Crew crew = new Crew("노랑");
        LocalDateTime dateTime = MONDAY_DATE.atTime(time);
        // when & then
        assertThatThrownBy(() -> attendance.checkAttendance(crew, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
    }
}
