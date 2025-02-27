package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeTest {

    @DisplayName("캠퍼스 운영 시간 외의 시간이 들어올 경우 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,25,7,59", "2025,2,25,23,1"
    })
    void 캠퍼스_운영_시간_외의_시간이_들어올_경우_예외를_발생한다(final int year, final int month, final int dayOfMonth, final int hour,
                                          final int minute) {

        // given

        // when & then
        assertThatThrownBy(() -> new Time(LocalDateTime.of(year, month, dayOfMonth, hour, minute)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시간에 들어올 경우 예외를 발생하지 않는다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,25,8,0", "2025,2,25,23,0"
    })
    void 캠퍼스_운영_시간에_들어올_경우_예외를_발생하지_않는다(final int year, final int month, final int dayOfMonth, final int hour,
                                        final int minute) {

        // given

        // when & then
        assertThatCode(() -> {
            Time time = new Time(LocalDateTime.of(year, month, dayOfMonth, hour, minute));
        }).doesNotThrowAnyException();
    }

    @DisplayName("주말에 출석할 경우 예외가 발생한다.")
    @Test
    void 주말에_출석할_경우_예외가_발생한다() {

        // given

        // when & then
        assertThatThrownBy(() -> new Time(LocalDateTime.of(2025, 2, 22, 10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 주말 및 공휴일은 출석할 수 없습니다.");
    }

    @DisplayName("출석 시간을 수정한다.")
    @Test
    void 출석_시간을_수정한다() {

        // given
        Time time = new Time(LocalDateTime.of(2025, 2, 27, 10, 0));
        LocalTime modifyTime = LocalTime.of(10, 6);

        // when
        time.modify(modifyTime);

        // then
        assertThat(time.getStatus(10, 0)).isEqualTo(AttendanceStatus.LATE);

    }

    @DisplayName("년월일이 같다면 true 다르다면 false를 반환한다")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,27,27,10,0,true",
            "2025,2,27,28,10,0,false"
    })
    void 년월일이_같다면_true_다르다면_false를_반환한다(int year, int month, int day, int otherDay,
                                        int hour, int minute, boolean result) {

        // given
        Time time = new Time(LocalDateTime.of(year, month, day, hour, minute));

        // when
        boolean isEqual = time.isSameLocalDate(LocalDate.of(year, month, otherDay));

        // then
        assertThat(isEqual).isEqualTo(result);
    }

    @DisplayName("년 월이 같다면 true, 다르면 false를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,true", "2025,3, false"
    })
    void 년_월이_같다면_true_다르면_false를_반환한다(int year, int month, boolean result) {

        // given
        Time time = new Time(LocalDateTime.of(2025, 2, 27, 10, 5));

        // when
        boolean isSame = time.isSameYearAndMonth(year, month);

        // then
        assertThat(isSame).isEqualTo(result);
    }
}
