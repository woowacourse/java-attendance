package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CampusTest {

    @DisplayName("주말이나 공휴일이 아닌지 검증한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-03",
            "2024-12-10"
    })
    void operationTimeTest(final LocalDate date) {
        // Given
        Campus campus = new Campus();

        // When & Then
        Assertions.assertThatCode(() -> {
            campus.validateOperationDate(date);
        }).doesNotThrowAnyException();
    }

    @DisplayName("운영 시간인지 검증한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-03T08:00",
            "2024-12-03T23:00"
    })
    void operationTimeTest(final LocalDateTime time) {
        // Given
        Campus campus = new Campus();

        // When & Then
        Assertions.assertThatCode(() -> {
            campus.validateOperationTime(time);
        }).doesNotThrowAnyException();
    }

    @DisplayName("주말이나 공휴일이면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-01",
            "2024-12-25"
    })
    void notOperationDateTest(final LocalDate date) {
        // Given
        Campus campus = new Campus();

        // When & Then
        assertThatThrownBy(() -> campus.validateOperationDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR]", "등교일이 아닙니다.");
    }

    @DisplayName("운영일이나 운영 시간이 아니라면 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({
            "2024-12-03T07:59",
            "2024-12-03T23:01"
    })
    void notOperationTimeTest(final LocalDateTime time) {
        // Given
        Campus campus = new Campus();

        // When & Then
        assertThatThrownBy(() -> campus.validateOperationTime(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }
}
