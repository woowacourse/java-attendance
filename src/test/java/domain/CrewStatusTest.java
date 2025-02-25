package domain;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewStatusTest {

    @DisplayName("출석 기록 기반 제적 상태 확인 테스트")
    @ParameterizedTest
    @MethodSource("provideAttendancesAndCrewStatus")
    void checkCrewStatusTest(int lateCount, int unattendedCount, CrewStatus expected) {
        assertThat(CrewStatus.checkCrewStatus(lateCount, unattendedCount))
                .isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendancesAndCrewStatus() {
        return Stream.of(
                Arguments.arguments(3, 0, CrewStatus.NORMAL),
                Arguments.arguments(3, 1, CrewStatus.WARNING),
                Arguments.arguments(3, 2, CrewStatus.INTERVIEW),
                Arguments.arguments(0, 6, CrewStatus.EXPELLED)
        );
    }
}
