package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.Attendance;
import attendance.domain.AttendanceHistory;
import attendance.domain.AttendanceStatus;

public class AttendanceHistoryTest {

    @Test
    @DisplayName("생성될 때, 등교하지 않은 날을 포함하여, 모든 등교날에 대해 저장한다.")
    void test_createTotalAttendanceHistory() {
        var datetime = LocalDateTime.of(2024, 12, 11, 10, 0);
        var date = datetime.toLocalDate();
        var newAttendance = Attendance.from(datetime);
        Map<LocalDate, Attendance> attendances = new HashMap<>();
        attendances.put(date, newAttendance);

        var attendanceHistory = AttendanceHistory.from(attendances);
        var history = attendanceHistory.history();

        assertAll(
            () -> assertThat(history.keySet().size()).isEqualTo(17),
            () -> assertThat(history.get(date).get()).isEqualTo(newAttendance),
            () -> assertThat(history.keySet().stream()
                .filter(d -> !d.equals(date))
                .allMatch(d -> history.get(d).isEmpty()))
                .isTrue()
        );
    }

    @Test
    @DisplayName("해당 크루의 출석 상태 통계를 반환한다.")
    void test_getAttendanceStateStatistic() {
        var datetime = LocalDateTime.of(2024, 12, 11, 10, 0);
        var date = datetime.toLocalDate();
        var attendance = Attendance.from(datetime);
        var attendanceLate = Attendance.from(datetime.plusMinutes(10));
        Map<LocalDate, Attendance> attendances = new HashMap<>();
        attendances.put(date, attendance);
        attendances.put(date.plusDays(1), attendanceLate);

        var attendanceHistory = AttendanceHistory.from(attendances);
        EnumMap<AttendanceStatus, Integer> statistic = attendanceHistory.countStatusOnHistory();

        assertEquals(1, statistic.get(AttendanceStatus.ATTENDANCE));
        assertEquals(1, statistic.get(AttendanceStatus.LATE));
        assertEquals(15, statistic.get(AttendanceStatus.ABSENCE));
    }

}
