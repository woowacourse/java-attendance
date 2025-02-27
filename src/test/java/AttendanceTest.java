import exception.DuplicateAttendanceException;
import exception.InvalidDateException;
import exception.NotOperatingTimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceTest {
    @DisplayName("출석 기록이 존재하지 않는 경우 새로운 출석을 등록할 수 있다.")
    @Test
    void test1() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(10, 0);

        // when & then
        assertDoesNotThrow(() -> attendanceStorage.register(date, enterTime));
    }

    @DisplayName("출석 기록이 존재하는 경우 출석을 등록할 수 없다.")
    @Test
    void test2() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(10, 0);
        attendanceStorage.register(date, enterTime);

        // when &  then
        assertThatThrownBy(() -> {
            attendanceStorage.register(date, LocalTime.of(10, 1));
        }).isInstanceOf(DuplicateAttendanceException.class);
    }

    @DisplayName("입력 시간이 캠퍼스 운영 시간이 아닌 경우에는 출석을 등록할 수 없다.")
    @ParameterizedTest
    @CsvSource(value = {"7, 59", "23, 1", "0, 0"})
    void test3(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);

        // when &  then
        assertThatThrownBy(() -> {
            LocalTime enterTime = LocalTime.of(hour, minutes);
            attendanceStorage.register(date, enterTime);
        }).isInstanceOf(NotOperatingTimeException.class);
    }

    @DisplayName("토요일에는 출석을 등록할 수 없다.")
    @Test
    void test4() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate saturday = LocalDate.of(2025, 2, 22);

        // when & then
        assertThatThrownBy(() -> {
            LocalTime enterTime = LocalTime.of(10, 0);
            attendanceStorage.register(saturday, enterTime);
        }).isInstanceOf(InvalidDateException.class);
    }

    @DisplayName("일요일에는 출석을 등록할 수 없다.")
    @Test
    void test5() {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate sunday = LocalDate.of(2025, 2, 23);

        // when & then
        assertThatThrownBy(() -> {
            LocalTime enterTime = LocalTime.of(10, 0);
            attendanceStorage.register(sunday, enterTime);
        }).isInstanceOf(InvalidDateException.class);
    }

    @DisplayName("제시간에 출석을 기록한 경우, 기록된 출석 내역과 '출석' 상태를 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 0", "10, 1", "10, 5"})
    void test6(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(hour, minutes);

        // when
        Attendance attendance = attendanceStorage.register(date, enterTime);

        // then
        assertThat(attendance.getDateTime()).isEqualTo(LocalDateTime.of(date, enterTime));
        assertThat(attendance.getStatus()).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("지각 시간에 출석을 기록한 경우, 기록된 출석 내역과 '지각' 상태를 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 6", "10, 30"})
    void test7(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(hour, minutes);

        // when
        Attendance attendance = attendanceStorage.register(date, enterTime);

        // then
        assertThat(attendance.getDateTime()).isEqualTo(LocalDateTime.of(date, enterTime));
        assertThat(attendance.getStatus()).isSameAs(AttendanceStatus.LATE);
    }
}
