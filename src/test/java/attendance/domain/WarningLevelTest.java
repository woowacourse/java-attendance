package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class WarningLevelTest {
    @DisplayName("제적 대상자 확인 테스트")
    @ParameterizedTest
    @CsvSource({"6,1,0", "6,0,0", "5,4,0", "5,3,0", "4,6,0", "3,9,0", "2,12,0", "1,15,0", "0,18,0", "0,19,0"})
    void test1(int absentCount, int lateCount, int presentCount) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        attendanceStatusCounts.put(AttendanceStatus.ABSENT, absentCount);
        attendanceStatusCounts.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCounts.put(AttendanceStatus.PRESENT, presentCount);

        WarningLevel actual = WarningLevel.calculateBy(attendanceStatusCounts);

        assertThat(actual).isEqualTo(WarningLevel.EXPELLED);
    }

    @DisplayName("면담 대상자 확인 테스트")
    @ParameterizedTest
    @CsvSource({"5,0,0", "5,2,0", "4,0,0", "4,5,0", "3,0,0", "3,8,0", "2,3,0", "2,11,0", "1,6,0", "1,14,0", "0,9,0",
            "0,17,0"})
    void test2(int absentCount, int lateCount, int presentCount) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        attendanceStatusCounts.put(AttendanceStatus.ABSENT, absentCount);
        attendanceStatusCounts.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCounts.put(AttendanceStatus.PRESENT, presentCount);

        WarningLevel actual = WarningLevel.calculateBy(attendanceStatusCounts);

        assertThat(actual).isEqualTo(WarningLevel.ONE_ON_ONE);
    }

    @DisplayName("경고 대상자 확인 테스트")
    @ParameterizedTest
    @CsvSource({"2,0,0", "2,2,0", "1,3,0", "1,5,0", "0,6,0", "0,8,0"})
    void test3(int absentCount, int lateCount, int presentCount) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        attendanceStatusCounts.put(AttendanceStatus.ABSENT, absentCount);
        attendanceStatusCounts.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCounts.put(AttendanceStatus.PRESENT, presentCount);

        WarningLevel actual = WarningLevel.calculateBy(attendanceStatusCounts);

        assertThat(actual).isEqualTo(WarningLevel.WARNING);
    }

    @DisplayName("해당없음 확인 테스트")
    @ParameterizedTest
    @CsvSource({"1,0,0", "1,2,0", "0,0,0", "0,5,0"})
    void test4(int absentCount, int lateCount, int presentCount) {
        Map<AttendanceStatus, Integer> attendanceStatusCounts = new EnumMap<>(AttendanceStatus.class);
        attendanceStatusCounts.put(AttendanceStatus.ABSENT, absentCount);
        attendanceStatusCounts.put(AttendanceStatus.LATE, lateCount);
        attendanceStatusCounts.put(AttendanceStatus.PRESENT, presentCount);

        WarningLevel actual = WarningLevel.calculateBy(attendanceStatusCounts);

        assertThat(actual).isEqualTo(WarningLevel.NONE);
    }
}
