package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("제적 위험 단계")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class WarningLevelTest {

    @ParameterizedTest
    @CsvSource({
            "0, 6, REMOVE",
            "2, 6, REMOVE",
            "3, 5, REMOVE",
            "5, 5, REMOVE",
            "0, 5, COUNSELING",
            "0, 3, COUNSELING",
            "3, 2, COUNSELING",
            "2, 2, WARNING",
            "5, 1, WARNING",
            "6, 0, WARNING",
            "3, 0, NONE",
            "0, 0, NONE"
    })
    void 출석_상태에_맞게_제적_위험_상황을_반환한다(int latenessCount, int absenceCount, WarningLevel expected) {
        Map<AttendanceStatus, Integer> statusCount = new EnumMap<>(AttendanceStatus.class);

        statusCount.put(AttendanceStatus.LATENESS, latenessCount);
        statusCount.put(AttendanceStatus.ABSENCE, absenceCount);

        assertThat(WarningLevel.of(statusCount)).isEqualTo(expected);
    }
}
