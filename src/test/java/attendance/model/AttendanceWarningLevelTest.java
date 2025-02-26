package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 경고 수준 테스트")
class AttendanceWarningLevelTest {

    @DisplayName("지각과 결석 횟수로 경고 수준을 판단할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "0, 1, CLEAN",
            "2, 1, CLEAN",

            "0, 2, WARNING",
            "5, 1, WARNING",

            "6, 1, MEETING",
            "0, 3, MEETING",
            "2, 5, MEETING",

            "3, 5, EXPULSION",
            "0, 6, EXPULSION"
    })
    void warningLevelDetermineTest(int lateCount, int absentCount, AttendanceWarningLevel expected) {
        // when
        AttendanceWarningLevel level = AttendanceWarningLevel.determine(lateCount, absentCount);

        // then
        assertThat(level == expected)
                .isTrue();
    }

    @DisplayName("출석 경고 수준으로 한글 라벨을 알 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "CLEAN, 정상",
            "WARNING, 경고",
            "MEETING, 면담",
            "EXPULSION, 제적"
    })
    void warningLevelGetKoreanLabelTest(AttendanceWarningLevel level, String expected) {
        // when
        String koreanLabel = level.getKoreanLabel();

        // then
        assertThat(koreanLabel)
                .isEqualTo(expected);
    }
}
