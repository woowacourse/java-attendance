import exception.DuplicateAttendanceException;
import exception.InvalidDateException;
import exception.NotOperatingTimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceStorageTest {
    @DisplayName("출석 기록이 존재하지 않는 경우 새로운 출석을 등록할 수 있다.")
    @Test
    void test1() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(10, 0);

        // when & then
        assertDoesNotThrow(() -> attendanceStorage.register(date, enterTime));
    }

    @DisplayName("출석 기록이 존재하는 경우 출석을 등록할 수 없다.")
    @Test
    void test2() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
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
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
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
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
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
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate sunday = LocalDate.of(2025, 2, 23);

        // when & then
        assertThatThrownBy(() -> {
            LocalTime enterTime = LocalTime.of(10, 0);
            attendanceStorage.register(sunday, enterTime);
        }).isInstanceOf(InvalidDateException.class);
    }

    @DisplayName("제시간에 출석을 기록한 경우, 성공적으로 출석을 등록할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 0", "10, 1", "10, 5"})
    void test6(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(hour, minutes);

        // when
        final boolean result = attendanceStorage.register(date, enterTime);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("지각 시간에 출석을 기록한 경우, 성공적으로 출석을 등록할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 6", "10, 30"})
    void test7(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(hour, minutes);

        // when
        final boolean result = attendanceStorage.register(date, enterTime);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("결석 시간에 출석을 기록한 경우, 성공적으로 출석을 등록할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 31", "22, 30"})
    void test8(int hour, int minutes) {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 27);
        LocalTime enterTime = LocalTime.of(hour, minutes);

        // when
        final boolean result = attendanceStorage.register(date, enterTime);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("새로운 출석을 기록한 후, 성공적으로 해당 기록을 조회할 수 있다.")
    @Test
    void test9() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime enterTime = LocalTime.of(10, 5);

        // when
        attendanceStorage.register(date, enterTime);
        Attendance attendance = attendanceStorage.getAttendanceByDate(date);

        // then
        assertThat(attendance.isTimeRecorded()).isTrue();
        assertThat(attendance.getTime()).isEqualTo(enterTime);
    }

    @DisplayName("출석 기록이 존재하지 않는 날짜의 출석 기록을 수정하고, 이를 조회할 수 있다.")
    @Test
    void test10() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime modifyTime = LocalTime.of(10, 5);

        // when
        attendanceStorage.modify(date, modifyTime);
        Attendance attendance = attendanceStorage.getAttendanceByDate(date);

        // then
        assertAll(
                () -> assertThat(attendance.isTimeRecorded()).isTrue(),
                () -> assertThat(attendance.getTime()).isEqualTo(modifyTime)
        );
    }

    @DisplayName("출석 기록이 존재하는 날짜의 출석 기록을 수정할 수 있다.")
    @Test
    void test11() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime enterTime = LocalTime.of(10, 5);
        LocalTime modifiedTime = LocalTime.of(10, 0);
        attendanceStorage.register(date, enterTime);

        // when
        attendanceStorage.modify(date, modifiedTime);
        Attendance attendance = attendanceStorage.getAttendanceByDate(date);

        // then
        assertAll(
                () -> assertThat(attendance.isTimeRecorded()).isTrue(),
                () -> assertThat(attendance.getTime()).isEqualTo(modifiedTime)
        );
    }

    @DisplayName("날짜를 입력하면 전날까지의 출석 통계 결과를 반환할 수 있다.")
    @Test
    void test12() {
        // given
        AttendanceStorage attendanceStorage = AttendanceStorage.of(List.of(
                new ExistAttendance(LocalDate.of(2025, 2, 24), LocalTime.of(13, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 25), LocalTime.of(10, 30)),
                new ExistAttendance(LocalDate.of(2025, 2, 27), LocalTime.of(10, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 28), LocalTime.of(10, 0))
        )); // 출석 3 지각 1 결석 1
        LocalDate startDate = LocalDate.of(2025, 2, 24);
        LocalDate endDate = LocalDate.of(2025, 3, 1);

        // when
        AttendanceStatistic statistic = attendanceStorage.getStatisticByDateRange(startDate, endDate);

        // then
        assertAll(
                () -> assertThat(statistic.getAttendanceCount()).isEqualTo(3),
                () -> assertThat(statistic.getLateCount()).isEqualTo(1),
                () -> assertThat(statistic.getAbsenceCount()).isEqualTo(1)
        );
    }
}
