package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LectureTimeTest {

    @ParameterizedTest
    @CsvSource({
        "2025,02,03,13,00,18,00",
        "2025,02,04,10,00,18,00",
        "2025,02,05,10,00,18,00",
        "2025,02,06,10,00,18,00",
        "2025,02,07,10,00,18,00",
    })
    @DisplayName("요일에 맞는 교육 시간을 반환한다.")
    void fromTest(int year, int month, int day, int startHour, int startMinute, int endHour, int endMinute) {
        // when
        LectureTime lectureTime = LectureTime.from(LocalDate.of(year, month, day));

        // then
        assertThat(lectureTime.getStartTime()).isEqualTo(LocalTime.of(startHour, startMinute));
        assertThat(lectureTime.getEndTime()).isEqualTo(LocalTime.of(endHour, endMinute));
    }
}
