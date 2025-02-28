package domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import fixture.LocalDateFixture;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureTimeTest {
    @Test
    @DisplayName("해당 날짜가 교육이 있는 날인지 확인한다")
    void isLectureTimeTest() {
        // when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.MONDAY)).isTrue();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.TUESDAY)).isTrue();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.WEDNESDAY)).isTrue();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.THURSDAY)).isTrue();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.FRIDAY)).isTrue();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.SATURDAY)).isFalse();
            softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.SUNDAY)).isFalse();
        });
    }

    @Test
    @DisplayName("해당 날짜에 맞는 교육 시간을 반환한다")
    void fromTest() {
        // when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(LectureTime.from(LocalDateFixture.MONDAY))
                    .isEqualByComparingTo(LectureTime.MONDAY);
            softAssertions.assertThat(LectureTime.from(LocalDateFixture.TUESDAY))
                    .isEqualByComparingTo(LectureTime.TUESDAY);
            softAssertions.assertThat(LectureTime.from(LocalDateFixture.WEDNESDAY))
                    .isEqualByComparingTo(LectureTime.WEDNESDAY);
            softAssertions.assertThat(LectureTime.from(LocalDateFixture.THURSDAY))
                    .isEqualByComparingTo(LectureTime.THURSDAY);
            softAssertions.assertThat(LectureTime.from(LocalDateFixture.FRIDAY))
                    .isEqualByComparingTo(LectureTime.FRIDAY);
        });
    }

    @Test
    @DisplayName("해당 날짜가 교육이 있는 날이 아니면 예외를 발생시킨다")
    void fromTest_Exception() {
        // when & then
        assertAll(() -> {
            Assertions.assertThatThrownBy(() -> {
                LectureTime.from(LocalDateFixture.SATURDAY);
            }).isInstanceOf(IllegalArgumentException.class);
            Assertions.assertThatThrownBy(() -> {
                LectureTime.from(LocalDateFixture.SUNDAY);
            }).isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    @DisplayName("날짜와 시간으로 교육 시작 시각으로부터 몇분 늦었는지 계산한다")
    void calculateElapsedMinutesTest() {
        // when
        int diff = LectureTime.calculateElapsedMinutes(LocalDateFixture.MONDAY,
                LocalTime.of(13, 32));

        // then
        Assertions.assertThat(diff).isEqualTo(32);
    }
}