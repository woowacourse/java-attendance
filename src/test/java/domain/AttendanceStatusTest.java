package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 상태 결정에 대한 테스트")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class AttendanceStatusTest {

    @ParameterizedTest(name = "{0} => {1}")
    @CsvSource({
        "13:05,ON_TIME",
        "13:06,LATE",
        "13:31,ABSENCE",
    })
    void 월요일_시간별_출석_시_출석상태(LocalTime attendTime, AttendanceStatus expectedStatus) {
        var monday = LocalDate.of(2025, 2, 24);
        var result = AttendanceStatus.determine(LocalDateTime.of(monday, attendTime));

        assertThat(result).isEqualTo(expectedStatus);
    }

    @ParameterizedTest(name = "{0} {1} => {2}")
    @CsvSource({
        "2025-02-25,10:05,ON_TIME", // 화요일
        "2025-02-25,10:06,LATE",
        "2025-02-25,10:31,ABSENCE",
        "2025-02-26,10:05,ON_TIME", // 수요일
        "2025-02-26,10:06,LATE",
        "2025-02-26,10:31,ABSENCE",
        "2025-02-27,10:05,ON_TIME", // 목요일
        "2025-02-27,10:06,LATE",
        "2025-02-27,10:31,ABSENCE",
        "2025-02-28,10:05,ON_TIME", // 금요일
        "2025-02-28,10:06,LATE",
        "2025-02-28,10:31,ABSENCE",

    })
    void 화수목금_시간별_출석_시_출석상태(LocalDate attendDate, LocalTime attendTime,
        AttendanceStatus expectedStatus) {
        var result = AttendanceStatus.determine(LocalDateTime.of(attendDate, attendTime));

        assertThat(result).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource({
        "2025-02-22T10:00:00", // 토요일
        "2025-02-23T10:00:00" // 일요일
    })
    void 주말에_출석_시_예외가_발생한다(LocalDateTime attendTime) {
        assertThatThrownBy(() -> AttendanceStatus.determine(attendTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "2025-03-01T10:00:00", // 삼일절
        "2025-05-05T10:00:00" // 목요일 어린이날
    })
    void 공휴일에_출석_시_예외가_발생한다(LocalDateTime attendTime) {
        assertThatThrownBy(() -> AttendanceStatus.determine(attendTime))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({"07:59", "23:01"})
    void 캠퍼스_운영시간이_아닐_때_출석_시_예외가_발생한다(LocalTime attendTime) {
        var weekday = LocalDate.of(2025, 2, 25);
        assertThatThrownBy(() -> AttendanceStatus.determine(LocalDateTime.of(weekday, attendTime)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
