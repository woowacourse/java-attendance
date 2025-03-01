package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import attendance.fixture.DateTimeFixture;

class LectureTimeTest {

    @ParameterizedTest
    @CsvSource({
        "0,MONDAY",
        "1,TUESDAY",
        "2,WEDNESDAY",
        "3,THURSDAY",
        "4,FRIDAY",
        "5,SATURDAY",
        "6,SUNDAY",
    })
    @DisplayName("요일에 맞는 운영시간 객체를 계산한다")
    void fromTest(int daysToAdd, LectureTime expected) {
        // when
        LocalDate date = DateTimeFixture.MONDAY.plusDays(daysToAdd);

        // then
        assertThat(LectureTime.from(date)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "2025,2,24,13,05", // MONDAY
        "2025,2,24,13,35", // MONDAY
        "2025,2,25,10,05", // OTHER DAY
        "2025,2,25,10,35", // OTHER DAY
    })
    @DisplayName("늦은 시간(분)을 계산한다")
    void getLateTimeOfTest(int year, int month, int day, int hour, int minute) {
        // when
        LocalDate date = LocalDate.of(year, month, day);

        // then
        assertThat(LectureTime.from(date).getLateTimeOf(LocalTime.of(hour, minute))).isEqualTo(minute);
    }
}
