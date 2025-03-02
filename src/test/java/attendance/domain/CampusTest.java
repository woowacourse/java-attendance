package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusTest {
    @DisplayName("정규 시작 시간 출석 테스트")
    @ParameterizedTest
    @CsvSource({"3,8,0,0", "3,9,59,59", "3,10,0,0", "3,10,5,0", "24,8,0,0", "24,9,59,59", "24,10,0,0", "24,10,5,0"})
    void test1(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.PRESENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정규 시작 시간 지각 테스트")
    @ParameterizedTest
    @CsvSource({"3,10,5,1", "3,10,30,0", "3,10,17,59", "24,10,5,1", "24,10,30,0", "24,10,29,0"})
    void test2(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.LATE;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("정규 시작 시간 결석 테스트")
    @ParameterizedTest
    @CsvSource({"3,10,30,1", "3,10,31,0", "3,18,0,0", "3,23,0,0", "24,10,30,1", "24,10,31,0", "24,18,0,0", "24,23,0,0"})
    void test3(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.ABSENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("월요일 시작 시간 출석 테스트")
    @ParameterizedTest
    @CsvSource({"2,8,0,0", "2,12,59,59", "2,13,0,0", "2,13,5,0", "23,8,0,0", "23,12,59,59", "23,13,0,0", "23,13,5,0"})
    void test4(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.PRESENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("월요일 시작 시간 지각 테스트")
    @ParameterizedTest
    @CsvSource({"2,13,5,1", "2,13,30,0", "2,13,17,59", "23,13,5,1", "23,13,30,0", "23,13,29,0"})
    void test5(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.LATE;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("월요일 시작 시간 결석 테스트")
    @ParameterizedTest
    @CsvSource({"2,13,30,1", "2,13,31,0", "2,18,0,0", "2,23,0,0", "23,13,30,1", "23,13,31,0", "23,18,0,0", "23,23,0,0"})
    void test6(int dayOfMonth, int hour, int minute, int second) {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, dayOfMonth, hour, minute, second);
        AttendanceStatus expected = AttendanceStatus.ABSENT;

        AttendanceStatus actual = Campus.calculateAttendanceStatus(attendance);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("캠퍼스 휴일 여부 확인 테스트")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false", "3,false", "7,true", "24,false", "25,true", "31,false"})
    void test7(int dayOfMonth, boolean expected) {
        LocalDate date = LocalDate.of(2024, 12, dayOfMonth);
        LocalTime time = LocalTime.of(8, 0);
        LocalDateTime attendance = LocalDateTime.of(date, time);

        boolean actual = Campus.isOffDay(attendance);

        assertThat(actual).isEqualTo(expected);
    }
}
