import static org.assertj.core.api.Assertions.assertThat;

import domain.Crews;
import domain.Penalty;
import domain.StatisticsResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PenaltyTest {

    @DisplayName("제적 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("expelledSource")
    void getPenalty_1(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.EXPELLED);
    }

    private static Stream<Arguments> expelledSource() {
        return Stream.of(
            Arguments.arguments(List.of(6, 0)),
            Arguments.arguments(List.of(4, 6)),
            Arguments.arguments(List.of(4, 7))
        );
    }

    @DisplayName("면담 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("counselingSource")
    void getPenalty_2(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.COUNSELING);
    }

    private static Stream<Arguments> counselingSource() {
        return Stream.of(
            Arguments.arguments(List.of(3, 0)),
            Arguments.arguments(List.of(2, 6)),
            Arguments.arguments(List.of(0, 9))
        );
    }

    @DisplayName("경고 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("warningSource")
    void getPenalty_3(List<Integer> list) {
        int absenceCount = list.get(0);
        int latenessCount = list.get(1);
        assertThat(Penalty.of(absenceCount, latenessCount)).isEqualTo(Penalty.WARNING);
    }

    private static Stream<Arguments> warningSource() {
        return Stream.of(
            Arguments.arguments(List.of(2, 0)),
            Arguments.arguments(List.of(1, 3)),
            Arguments.arguments(List.of(0, 6))
        );
    }

    @DisplayName("제적 위험자를 확인한다.")
    @Test
    void calculateExpelledWarning_1() {
        Crews crews = new Crews();

        List<LocalDateTime> testRecords1 = List.of(LocalDateTime.of(2024, 12, 2, 13, 6), // 지각
            LocalDateTime.of(2024, 12, 3, 10, 0), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 1), // 출석
            LocalDateTime.of(2024, 12, 5, 10, 2),// 출석
            LocalDateTime.of(2024, 12, 6, 10, 1),// 출석
            LocalDateTime.of(2024, 12, 9, 13, 0),// 출석
            LocalDateTime.of(2024, 12, 10, 10, 0) // 출석
        );

        List<LocalDateTime> testRecords2 = List.of(LocalDateTime.of(2024, 12, 2, 13, 8), // 지각
            LocalDateTime.of(2024, 12, 3, 10, 5), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 7),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 6),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 6),// 지각
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        crews.createCrew("이든", testRecords1);
        crews.createCrew("빙봉", testRecords2); // 면담 대상자

        LocalDate nowDate = LocalDate.of(2024, 12, 11);
        Map<String, StatisticsResult> result = Penalty.calculateExpelledWarning(nowDate,
            crews.getCrews());

        assertThat(result).containsOnlyKeys("빙봉");
    }
}