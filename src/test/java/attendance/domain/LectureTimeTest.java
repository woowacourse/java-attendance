package attendance.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureTimeTest {

    @Test
    @DisplayName("요일에 맞는 운영시간 객체를 계산한다")
    void fromTest() {
        // when then
        assertSoftly(softly -> {
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 02, 24))).isEqualTo(LectureTime.MONDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 02, 25))).isEqualTo(LectureTime.TUESDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 02, 26))).isEqualTo(LectureTime.WEDNESDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 02, 27))).isEqualTo(LectureTime.THURSDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 02, 28))).isEqualTo(LectureTime.FRIDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 03, 01))).isEqualTo(LectureTime.SATURDAY);
            softly.assertThat(LectureTime.from(LocalDate.of(2025, 03, 02))).isEqualTo(LectureTime.SUNDAY);
        });
    }

    @Test
    @DisplayName("늦은 시간(분)을 계산한다")
    void getLateTimeOfTest() {
        // when
        LocalDate monday = LocalDate.of(2025, 02, 24);
        LocalDate otherDay = LocalDate.of(2025, 02, 25);

        // then
        assertSoftly(softly -> {
            softly.assertThat(LectureTime.from(monday).getLateTimeOf(LocalTime.of(13, 00))).isEqualTo(0);
            softly.assertThat(LectureTime.from(monday).getLateTimeOf(LocalTime.of(13, 05))).isEqualTo(5);
            softly.assertThat(LectureTime.from(otherDay).getLateTimeOf(LocalTime.of(10, 10))).isEqualTo(10);
            softly.assertThat(LectureTime.from(otherDay).getLateTimeOf(LocalTime.of(10, 35))).isEqualTo(35);
        });
    }
}
