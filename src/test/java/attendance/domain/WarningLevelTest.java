package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WarningLevelTest {

    @DisplayName("결석이 2회 이상인 경우 경고 대상자")
    @Test
    void test1() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 2);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        WarningLevel level = WarningLevel.calculateLevel(attendanceStatuses);
        assertThat(level)
                .isEqualTo(WarningLevel.WARNING);
    }

    @DisplayName("결석이 3회 이상인 경우 면담 대상자")
    @Test
    void test2() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 3);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        WarningLevel level = WarningLevel.calculateLevel(attendanceStatuses);
        assertThat(level)
                .isEqualTo(WarningLevel.SUPERVISED);
    }

    @DisplayName("결석이 6회 이상인 경우 제적 대상자")
    @Test
    void test3() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 6);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        WarningLevel level = WarningLevel.calculateLevel(attendanceStatuses);
        assertThat(level)
                .isEqualTo(WarningLevel.EXPELLED);
    }

    @DisplayName("결석이 2회 미만인 경우 해당 없음")
    @Test
    void test4() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 1);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        WarningLevel level = WarningLevel.calculateLevel(attendanceStatuses);
        assertThat(level)
                .isEqualTo(WarningLevel.NONE);
    }

    @DisplayName("지각 3회는 결석 1회로 간주")
    @Test
    void test5() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 5);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 5);

        WarningLevel level = WarningLevel.calculateLevel(attendanceStatuses);
        assertThat(level)
                .isEqualTo(WarningLevel.EXPELLED);
    }
}
