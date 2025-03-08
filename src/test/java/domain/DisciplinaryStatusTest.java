package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DisciplinaryStatusTest {

    @DisplayName("결석 및 지각 개수에 따라 알맞은 status 반환")
    @ParameterizedTest
    @CsvSource({
            "0, 0, NONE",
            "1, 0, NONE",
            "2, 0, WARNING",
            "0, 6, WARNING",
            "3, 0, MEETING",
            "3, 3, MEETING",
            "5, 0, MEETING",
            "5, 2, MEETING",
            "5, 3, EXPULSION",
            "6, 0, EXPULSION"
    })
    void determineDisciplinaryStatus(final int absenceCount, final int latenessCount, final String expectedStatusName) {
        // given
        // when
        final DisciplinaryStatus status = DisciplinaryStatus.findByAbsenceAndLatenessCount(absenceCount, latenessCount);

        // then
        Assertions.assertThat(status.name()).isEqualTo(expectedStatusName);
    }
}
