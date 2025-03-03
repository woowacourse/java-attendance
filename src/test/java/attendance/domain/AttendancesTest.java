package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.exception.AttendanceArgumentException;
import attendance.interfaces.SystemDateTime;

class AttendancesTest {
    private final SystemDateTime systemDateTime = new AttendanceDateTime();

    @Test
    @DisplayName("출석일이 주말일 경우, 예외가 발생한다")
    void error_cantAttendanceOnWeekend() {
        LocalDateTime weekend = LocalDateTime.of(2024, 12, 14, 10, 0);
        var attendances = new Attendances(systemDateTime);

        assertThatThrownBy(() -> attendances.addAttendance(weekend))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("출석일이 휴일일 경우, 예외가 발생한다")
    void error_cantAttendanceOnHoliday() {
        LocalDateTime holiday = LocalDateTime.of(2024, 12, 25, 10, 0);
        var attendances = new Attendances(systemDateTime);

        assertThatThrownBy(() -> attendances.addAttendance(holiday))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 25일 수요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("이미 출석했을 경우, 예외가 발생한다.")
    void error_duplicateAttendance() {
        LocalDateTime duplicate = LocalDateTime.of(2024, 12, 13, 10, 0);
        var attendances = new Attendances(systemDateTime);
        attendances.addAttendance(duplicate);

        assertThatThrownBy(() -> attendances.addAttendance(duplicate))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("이미 출석하였습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    @DisplayName("출석하지 않은 날을 반환 할 때, 무단결석(Truancy)으로 저장한다.")
    void test_getAttendancesWithTruancy() {
        var attendances = new Attendances(systemDateTime);
        attendances.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 0));
        List<Attendance> attendancesWithTruancy = attendances.getAttendancesWithTruancy();

        var attendanceCount = attendancesWithTruancy.stream()
            .filter(attendance -> !attendance.isTruancy())
            .count();
        var truancyCount = attendancesWithTruancy.stream()
            .filter(Attendance::isTruancy)
            .count();

        assertAll(
            () -> assertThat(attendanceCount).isEqualTo(1),
            () -> assertThat(truancyCount).isEqualTo(16),
            () -> assertThat(attendanceCount + truancyCount).isEqualTo(attendancesWithTruancy.size())
        );
    }

    @Test
    @DisplayName("출석하지 않은 날을 반환 할 때, 결석으로 저장한다.")
    void test_updateStatics() {
        var attendances = new Attendances(systemDateTime);
        attendances.addAttendance(LocalDateTime.of(2024, 12, 11, 10, 0));

        Map<LocalDate, AttendanceStatus> attendanceStatusMap = attendances.updateStatics();

        var attendanceCount = attendanceStatusMap.values().stream()
            .filter(status -> !status.equals(AttendanceStatus.ABSENCE))
            .count();
        var absenceCount = attendanceStatusMap.values().stream()
            .filter(status -> status.equals(AttendanceStatus.ABSENCE))
            .count();

        assertAll(
            () -> assertThat(attendanceCount).isEqualTo(1),
            () -> assertThat(absenceCount).isEqualTo(16),
            () -> assertThat(attendanceCount + absenceCount).isEqualTo(attendanceStatusMap.values().size())
        );
    }
}
