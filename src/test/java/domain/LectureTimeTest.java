package domain;

import static org.junit.jupiter.api.Assertions.assertAll;

import fixture.LocalDateFixture;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class LectureTimeTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {
        @Test
        @DisplayName("해당 날짜가 교육이 있는 날이면 true를 반환한다")
        void isLectureTime_test_true() {
            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.MONDAY)).isTrue();
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.TUESDAY)).isTrue();
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.WEDNESDAY)).isTrue();
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.THURSDAY)).isTrue();
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.FRIDAY)).isTrue();
            });
        }

        @Test
        @DisplayName("해당 날짜가 교육이 없는 날이면 false를 반환한다")
        void isLectureTime_test_false() {
            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.SATURDAY)).isFalse();
                softAssertions.assertThat(LectureTime.isLectureDate(LocalDateFixture.SUNDAY)).isFalse();
            });
        }

        @Test
        @DisplayName("날짜와 시간으로 교육 시작 시각으로부터 몇분 늦었는지 계산한다")
        void calculateElapsedMinutes_test() {
            // when
            int diff = LectureTime.calculateElapsedMinutes(LocalDateFixture.MONDAY,
                    LocalTime.of(13, 32));

            // then
            Assertions.assertThat(diff).isEqualTo(32);
        }


        @Test
        @DisplayName("해당 날짜에 맞는 교육 시간을 반환한다")
        void from_test() {
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

    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

        @Test
        @DisplayName("해당 날짜에 해당하는 교육 시간을 반환한다. 교육이 없는 날이면 예외를 발생시킨다.")
        void from_test_exception() {
            // when & then
            assertAll(() -> {
                Assertions.assertThatThrownBy(() ->
                        LectureTime.from(LocalDateFixture.SATURDAY)
                ).isInstanceOf(IllegalArgumentException.class);
                Assertions.assertThatThrownBy(() ->
                        LectureTime.from(LocalDateFixture.SUNDAY)
                ).isInstanceOf(IllegalArgumentException.class);
            });
        }
    }
}