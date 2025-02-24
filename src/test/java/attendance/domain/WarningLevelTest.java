package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("제적 상황 Enum")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WarningLevelTest {
    @Test
    void 결석이_2회_이상이면_경고_대상자를_반환한다() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 2);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        assertThat(WarningLevel.of(attendanceStatuses))
                .isEqualTo(WarningLevel.WARNING);
    }

    @Test
    void 결석이_3회_이상이면_면담_대상자를_반환한다() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 3);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        assertThat(WarningLevel.of(attendanceStatuses))
                .isEqualTo(WarningLevel.COUNSELING);
    }

    @Test
    void 결석이_6회_이상이면_제적_대상자를_반환한다() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 6);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        assertThat(WarningLevel.of(attendanceStatuses))
                .isEqualTo(WarningLevel.REMOVE);
    }

    @Test
    void 결석이_2회_미만이면_경고_해당_없음을_반환한다() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 1);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 0);

        assertThat(WarningLevel.of(attendanceStatuses))
                .isEqualTo(WarningLevel.NONE);
    }

    @Test
    void 지각_3회당_결석_1회로_반환한다() {
        Map<AttendanceStatus, Integer> attendanceStatuses = new EnumMap<>(AttendanceStatus.class);
        attendanceStatuses.put(AttendanceStatus.ABSENCE, 5);
        attendanceStatuses.put(AttendanceStatus.PRESENT, 0);
        attendanceStatuses.put(AttendanceStatus.LATENESS, 5);

        assertThat(WarningLevel.of(attendanceStatuses))
                .isEqualTo(WarningLevel.REMOVE);
    }

}
