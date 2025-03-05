package domain.attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static domain.attendance.TimeTable.isAttendanceDay;
import static java.time.DayOfWeek.*;
import static org.assertj.core.api.Assertions.*;

class AttendanceDateTest {
    @Nested
    class AttendanceDateConstructTest {
        AttendanceDate attendanceDate;

        @BeforeEach
        void setUpLocalDate() {
            attendanceDate = new AttendanceDate(LocalDateTime.of(
                    2025,
                    2,
                    27,
                    11,
                    10)); // 목요일
        }

        @DisplayName("AttendanceDate의 요일을 검사한다.")
        @Test
        void checkDayOfWeek() {
            assertThat(attendanceDate.getDayOfWeek()).isEqualTo(THURSDAY);
        }
    }

    @Nested
    class AttendanceDateStatusTest {
        @Nested
        class IsMonday {
            @DisplayName("10:05 경우 출석")
            @Test
            void weekOfDayAttendanceTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 13, 5)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() == MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isAttendance()).isTrue());
            }

            @DisplayName("10:05 이후의 경우 지각")
            @Test
            void weekOfDayTardyTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 13, 6)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() == MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isTardy()).isTrue());
            }

            @DisplayName("10:030 이후의 경우 결석")
            @Test
            void weekOfDayTAbsenceTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 13, 31)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() == MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isAbsence()).isTrue());
            }
        }

        @Nested
        class ExceptMonday {
            @DisplayName("10:05 경우 출석")
            @Test
            void weekOfDayAttendanceTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 10, 5)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() != MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isAttendance()).isTrue());
            }

            @DisplayName("10:05 이후의 경우 지각")
            @Test
            void weekOfDayTardyTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 10, 6)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() != MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isTardy()).isTrue());
            }

            @DisplayName("10:30 이후의 경우 결석")
            @Test
            void weekOfDayTAbsenceTest() {
                List<AttendanceDate> list = IntStream.range(1, 28)
                        .filter(day -> isAttendanceDay(LocalDate.of(2025, 2, day)))
                        .mapToObj(day -> new AttendanceDate(LocalDateTime.of(2025, 2, day, 10, 31)))
                        .toList();

                list.stream()
                        .filter(date -> date.getDayOfWeek() != MONDAY)
                        .forEach(attendanceDate -> assertThat(attendanceDate.isAbsence()).isTrue());
            }
        }
    }
}
