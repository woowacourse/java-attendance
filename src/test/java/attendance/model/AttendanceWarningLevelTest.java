package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출결 위험 수준 테스트")
class AttendanceWarningLevelTest {

    @DisplayName("누적 지각 및 결석 횟수에 따라 위험 수준을 판단할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "0, 1, CLEAN",
            "0, 2, WARNING",
            "0, 3, MEETING",
            "0, 5, MEETING",
            "0, 6, EXPULSION",

            "3, 1, WARNING",
            "6, 1, MEETING",
            "5, 4, MEETING",
            "6, 4, EXPULSION",
            "5, 5, EXPULSION"
    })
    void attendanceWarningLevelJudgeTest(int lateCount, int absenceCount, AttendanceWarningLevel expected) {
        // when
        AttendanceWarningLevel level = AttendanceWarningLevel.judge(lateCount, absenceCount);

        // then
        assertThat(level)
                .isEqualTo(expected);
    }
}
